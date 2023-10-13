//******variable dec */
let totalPrice = 0;
var deleteId = "";



     
  //****************onRowClickedAkm*********** ******/   
     function onRowClickedAkm(param) {	
		   var totalPrice = 0;
		   var selectedRows = param.api.getSelectedRows();
		   
		   if(selectedRows){
			 // alert("sdfsd")
			  const fruits = [];
              fruits.push(selectedRows);
              
               //alert(fruits)
		   }		 		
		  console.log("selectedRows"+selectedRows);
		 
		   		     selectedRows.forEach(item => {
		   		       if (item.hasOwnProperty('price')) {
		   		         const priceValue = parseFloat(item.price); 
		   		         if (!isNaN(priceValue)) { 
		   		           totalPrice += priceValue; 
		   		         }
		   		       }
		   		     });
		   		   document.getElementById("allPrice").value = totalPrice.toFixed(2);   		   		  
		      }
		  
		
		
//************************Delete Function**********************************************		
	  function deleteIncentive() {		
			$.ajax({
				type : "GET",
				url : "assign-delete-id?id=" + deleteId,
				success : function(response) {
					if (response.message == "Success") {
						swal("Menu delete successfully!", " ", "success");
						cancelBtn();
						agGrid.simpleHttpRequest({
							url : "assign-all-throughAjax"
						}).then(function(data) {
							gridOptions.api.setRowData(data);
						});
					}
				}
	
			});
	
		$('#delete').attr("disabled", true);
		}
				
				
//**********************Add function ***********************
		   function saveTableData() {
		         var selectedRows = gridOptionschaildview.api.getModel().rowsToDisplay.map(rowNode => rowNode.data);
		          var requestData = {
		    	    comboId:  $("#comboId").text(),
		    	    comboName:  $("#comboName").val(),
		    	    itemList: selectedRows,
		    	    allPrice: $("#allPrice").val()
		       };
		       
		if (requestData.comboName == null || requestData.comboName == "") {

			validation = false;
			$("#errmsg_name").css('color', 'red');
			$("#errmsg_name").css('font-size', '10px');
			$("#errmsg_name").html("Combo Name Required").show().fadeOut(
					6000);
			return requestData;
		}
		if (requestData.allPrice == null || requestData.allPrice == "") {

			validation = false;
			$("#errmsg_price").css('color', 'red');
			$("#errmsg_price").css('font-size', '10px');
			$("#errmsg_price").html("All Price Required").show().fadeOut(
					6000);
			return requestData;
		}
		       
		
		      //console.log("requestData"+JSON.stringify(requestData))
		      $.ajax({
		          type: "POST",
		           url: "assign-add-dtls",
		           contentType: "application/json",
		           data: JSON.stringify(requestData), // Send requestData as the request payload
		            success: function (response) {
		            if (response.message == "Success") {
		            	 console.log(response.message == "Success");
		                  swal("Menu added successfully!", " ", "success");
		                  cancelBtn();
		                  agGrid.simpleHttpRequest({
		                  url: "assign-all-throughAjax"
		                }).then(function (data) {
		                gridOptions.api.setRowData(data);
		               });
		            }
		        },
		        error: function (data) {
		            // Handle error
		        }
		    });
		  }
			

	//***********************Subcategry function**********************/
	    function clubMemberGetDetail(){
		    var clubMemberId = $('#clubmember').val();
			   $.ajax({
				 type : "GET",
				 url : "assign-getMemberDetails?id=" + subcategry,
				 async : false,
				 success : function(response) {
					//console.log("response------" + JSON.stringify(response));
					  if (response.message == "Success") {
						$("#rangefrom").val(response.body[0].memberRangeFrom);
						$("#rangeto").val(response.body[0].memberRangeTo);
					}
				  }
			   })
		    }
		
	
	//************************categry********************* */
		function getIncentiveStatus(){	
			 var categry = $('#categry').val();
			 $.ajax({
				type : "GET",
				url : "assign-getIncentiveDetails?id=" + categry,
				async : false,
				success : function(response) {
					if (response.message == "Success") {
						$("#incentivecode").val(response.body[0].itemId);
					}
				}
			})
		}
		
		

	//*******************************Category call**********************
				var firstFetchedData = []; // Store the first fetched data
									
					function getIncentiveStatus() {
					    var catId = $("#categry").val();
					    var subCatId = $("#subcategry").val();
					    var variant = $("#variant").val();
					    
					    $.ajax({
					        type: "GET",
					        url: "assign/canteen-item-list?catId=" + catId + "&subCatId=" + subCatId + "&variant=" + variant,
					        async: false,
					        success: function(response) {
					            console.log("response-xzfgfdgnf-----" + JSON.stringify(response));
					
					            var assignItemTablelist = gridOptionschaildview.api.getModel().rowsToDisplay.map(rowNode => rowNode.data);
					            let finalData = [];
					
					            if (assignItemTablelist.length > 0) {
					                // Compare the first fetched data with the second fetched data
					                
					                
					                finalData = response.filter(item => !assignItemTablelist.some(searchItem => {
					                    return JSON.stringify(item.itemId) === JSON.stringify(searchItem.itemId);
					                    
					                }));
					               
					            } else {
					                gridOptionschaildview.api.setRowData(response);
					                firstFetchedData = response; // Store the first fetched data
					                
					            }
					
					            if (finalData.length > 0) {
					                gridOptionschaildview.api.setRowData();
					                gridOptionschaildview.api.setRowData(assignItemTablelist.concat(finalData));
					            }
					        }
					  });
				}

	
	
	//******************************View Function***********************	
	    function getIncentiveStatusall() {
			$.ajax({
				type : "GET",
				url : "assign-all-throughAjax",
				async : false,
				success : function(response) {
					//console.log("response------" + JSON.stringify(response));
					if (response.body != "") {
						
						gridOptions.api.setRowData();
						gridOptions.api.setRowData(response);
					}
	
				}
			})
		} 
		

 //*******************************Auto Search *************************/            
	    let itemDataOnSearch = []; 
	    let comboDataOnSearch = [];
	  //searching
	      function getProductList() {
			 $("#itemId").val("");
			  var search = $("#itemName").val();
			  if (search) {
				$.ajax({
						type : "POST",
							url : "assign-menu-list",
							dataType : 'json',
							contentType : 'application/json',
							data : search,
							success : function(response) {
								if (response.code == "success") {
									if (response.body.length != 0) {
										itemDataOnSearch = [];
										itemDataOnSearch = response.body;
										$("#search").css("background", "#FFF");
										var content = '<ul id="autocomplete-list1" >';
										for (var i = 0; i < response.body.length; i++) {
											content += '<li style="margin-left:0px; font-weight:40; font-size:14px; color:#343a40;     background-color: #dbdbdb;"  class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
													+ response.body[i].itemId + '\',\''+response.body[i].itemName+'\')">'
													+ response.body[i].itemName
													+ '</li>';}
										content += '<li  >'
												+ '</li>';
										content += '</ul>';
										////console.log("content " + content)
										$("#suggesstion-box11_").show();
										$("#suggesstion-box11_").html(content);
	
									} else {
										$("#search").css("background", "#FFF");
										var content = '<ul id="autocomplete-list1">';
										content += '<li style="margin-left:0px; font-weight:100; font-size:14px; color:#ccc;     background-color: #dbdbdb;">'
												+ "No Data Found" + '</li>';
										content += '<li style="margin-left:-30px;" '
												+ '</li>';
										content += '</ul>';
										$("#suggesstion-box11_").show();
										$("#suggesstion-box11_").html(content);
										
									}
								}
							
							},
							error : function(data) {
								//console.log(data);
							}
						})
			        }
	       	}

	    function selectAutocompleteValue1(itemid,itemname) {
		//alert(itemname)
		   $("#itemName").val(itemname);
		     totalPrice = 0;
	         console.log("Serahc", itemDataOnSearch);
	          //gridOptionschaildview.api.setRowData(itemDataOnSearch);
					// gridOptionschaildview.api.setRowData(assignItemTablelist.concat(finalData));
		     itemDataOnSearch = itemDataOnSearch.filter(itm => itm.itemId == itemid);	
	        //	console.log("Serahc DATA Autocolplete", itemDataOnSearch);
		          const prices = [];
		          var allprice = 0 ;
		       itemDataOnSearch.forEach(item => {
	             if (item.hasOwnProperty('price')) {
	              prices.push(item.price);
	              var intValue = parseInt(prices);
	              allprice = allprice+intValue;
	           }
	          });   
		 //document.getElementById("allPrice").value = allprice;  
		 	
	       if (itemDataOnSearch.length > 0) { 	
	      	   const assignItemTableData = gridOptionschaildview.api.getModel().rowsToDisplay.map(rowNode => rowNode.data);
	          if(assignItemTableData.length > 0){
	        	console.log("dsfsdsdvsdv"+assignItemTableData)
	        	
	        	const filteredArray = assignItemTableData.filter(item => itemDataOnSearch.some(searchItem => {
	        	    	return JSON.stringify(item.itemId) === JSON.stringify(searchItem.itemId);
	        	  	})
	        	);
	        	console.log(filteredArray, "  Duplicate object");
	        	if(filteredArray.length > 0){
	        		
							swal("Already Selected!");
							
	          	var totalPrice = 0.0;
		          filteredArray.forEach(item => {
	              if (item.hasOwnProperty('price')) {
	            	totalPrice += parseFloat(item.price)
	            }
	          });
	     	//	document.getElementById("allPrice").value = totalPrice;  				
	        	} else{
			        		let gridList = [...assignItemTableData, ...itemDataOnSearch];
			        		gridOptionschaildview.api.setRowData(gridList);
			        		//gridOptionschaildassign.api.setRowData(jsondata);
			     
				   		     let totalPrice = 0;
				   		     gridList.forEach(item => {
				   		       if (item.hasOwnProperty('price')) {
				   		         const priceValue = parseFloat(item.price); 
				   		         if (!isNaN(priceValue)) { 
				   		           totalPrice += priceValue; 
				   		         }
				   		       }
				   		     });
				   		  //   document.getElementById("allPrice").value = totalPrice.toFixed(2); 
			        }
			        }else{
			        	gridOptionschaildview.api.setRowData(itemDataOnSearch);
			        }
		        let gridList = [...assignItemTableData, ...itemDataOnSearch];
		        $("#suggesstion-box11_").hide(); 
		    } else {
		        // Handle the case where itemId is not available
		        $("#itemId").val("");
		        $("#itemName").val("");
		        $("#price").val("");
		        $("#search").val("");
		        $("#search").attr('data-procat', "");
		        $("#suggesstion-box11_").hide();
		        
		        itemDataOnSearch.forEach((rowData) => {
				  rowData.yourCheckboxColumnName = true; // Replace 'yourCheckboxColumnName' with the actual name of your checkbox column
				});
		        
		        gridOptionschaildview.api.setRowData(itemDataOnSearch);
		    }
		   
		}
		  
		  
		  
		  
		  
		  
		  //******************Searching *****************/  
			 //  let itemDataOnSearch = []; 
	    //searching
	     function getComboList() {
			 // alert("sacsdca")
			 $("#comboId").val("");
			  var search = $("#comboName").val();
			  if (search) {
				$.ajax({
						type : "POST",
							url : "assign-combo-list",
							dataType : 'json',
							contentType : 'application/json',
							data : search,
							success : function(response) {
								if (response.code == "success") {
									//alert(response.code == "success")
									if (response.body.length != 0) {
										comboDataOnSearch = [];
										comboDataOnSearch = response.body;
										$("#search").css("background", "#FFF");
										var content = '<ul id="autocomplete-list1" >';
										for (var i = 0; i < response.body.length; i++) {
											content += '<li style="margin-left:0px; font-weight:400; font-size:14px; color:#343a40;   background-color: #dbdbdb;"  class="autocompletedata cp" onClick="selectAutocompleteValue2(\''
													+ response.body[i].comboId + '\',\''+response.body[i].comboName+'\')">'
													+ response.body[i].comboName
													+ '</li>';}
										content += '<li  >'
												+ '</li>';
										content += '</ul>';
										////console.log("content " + content)
										$("#suggesstion-box21_").show();
										$("#suggesstion-box21_").html(content);
	
									} else {
										$("#search").css("background", "#FFF");
										var content = '<ul id="autocomplete-list1">';
										content += '<li style="margin-left:0px; font-weight:100; font-size:14px; color:#ccc;     background-color: #dbdbdb;">'
												+ "No Data Found" + '</li>';
										content += '<li style="margin-left:-30px;" '
												+ '</li>';
										content += '</ul>';
										$("#suggesstion-box21_").show();
										$("#suggesstion-box21_").html(content);
										
									}
								}
							
							},
							error : function(data) {
								//console.log(data);
							}
						})
			        }
	         	}
 		
/*// Initialize a variable to accumulate selected combo names
var accumulatedComboNames = "";

function selectAutocompleteValue2(comboId, comboName) {
	
   // $("#comboId").val(comboId);
    
   
    accumulatedComboNames += comboName + " ,"; // You can use a comma or any other separator

    // Set the value for #comboNdddddddsdame with the accumulatedComboNames
    $("#comboAllName").val(accumulatedComboNames);
    
    // Hide the suggestion box
    $("#suggesstion-box21_").hide();
    
    // Log the selected comboName
    console.log(comboName);
    
    // You can perform additional actions or set data in other fields here
}
*/


/*var accumulatedComboNames = "";

function selectAutocompleteValue2(comboId, comboName) {
    accumulatedComboNames += comboName + " ,"; // You can use a comma or any other separator
    $("#comboAllName").val(accumulatedComboNames);
    $("#suggesstion-box21_").hide();
    console.log(comboName);

    // Add a click event listener to clear the field on user click
    $("#comboAllName").on("click", function() {
        accumulatedComboNames = "";
        $("#comboAllName").val("");
    });

    // You can perform additional actions or set data in other fields here
}*/

	   
		/*		   
		 var accumulatedComboNames = "";
			
	       function selectAutocompleteValue2(comboId, comboName) {
			    // Set the value for #comboId
			    $("#comboId").val(comboId);
			
			    // Append the selected comboName to the search field value
			    var currentComboName = $("#comboName").val();
			    
			    if (currentComboName) {
			        accumulatedComboNames += '' + comboName +' ,';
			    }
			    
			    $("#comboName").val(accumulatedComboNames);
			
			    // Hide the suggestion box
			    $("#suggesstion-box21_").hide();
			
			    // Log the selected comboName
			    console.log(comboName);
			    // You can perform additional actions or set data in other fields here
			}
			
*/
		
		var accumulatedComboNames = [];		
		function selectAutocompleteValue2(comboId, comboName) {
		    // Set the value for #comboId
		    $("#comboId").val(comboId);
		
		    // Append the selected comboName to the search field value
		    var currentComboName = $("#comboName").val();
		
		    if (currentComboName) {
		        accumulatedComboNames.push(comboName); // Add the selected comboName to the array
		        $("#comboName").val(accumulatedComboNames.join(', ')); // Join the array and set it as the field value
		    } else {
		        accumulatedComboNames = [comboName]; // If the field is empty, start a new array
		        $("#comboName").val(comboName);
		    }
		
		    // Hide the suggestion box
		    $("#suggesstion-box21_").hide();
		
		    // Log the selected comboName
		    console.log(comboName);
		
		    // You can perform additional actions or set data in other fields here
		}

    // To remove a suggestion on click
		function removeLastSuggestion() {
		    if (accumulatedComboNames.length > 0) {
		        accumulatedComboNames.pop(); // Remove the last suggestion
		        $("#comboName").val(accumulatedComboNames.join(', ')); // Update the field value
		    }
		}

 
		    	
//*********************Edit function ****************//
		 function editPage(id) {
				var editId = id;
				$("#demo").show();
				$("#add").hide();
				$("#copy").hide();
				$("#delete").hide();
				$("#myGrid").hide();
				$("#searchRowDiv").hide();
				$("#totalReq").hide();
				$("#statusDiv").hide();
				$("#idDiv").hide();
				$("#collapseFour").hide();
				$("#headingFour").hide();
				$("#myGridActivity").hide();
			
				$.ajax({
					type : "GET",
					url : "assign-edit?id=" + editId,
					async : false,
					success : function(response) {
						//console.log("response------" + JSON.stringify(response.body));
						if (response.message == "Success") {
							
							const stringifyData = JSON.stringify(response.body);
							const jsondata = JSON.parse(stringifyData);
							console.log("Edit data"+jsondata)
							
							gridOptionschaildview.api.setRowData(jsondata);
							
			     
							$("#add").hide();
							$("#copy").hide();
							$("#delete").hide();
							$("#myGrid").hide();
							$("#searchRowDiv").hide();
							$("#totalReq").hide();
							$("#statusDiv").hide();
							$("#idDiv").hide();
							$("#collapseFour").hide();
							$("#headingFour").hide();
							$("#myGridActivity").hide();
			
							$("#demo").show();
							
							$(".container").hide();
			               
							$("#comboId").text(response.body[0].comboId);
							$("#comboName").val(response.body[0].comboName);
							$("#allPrice").val(response.body[0].allPrice);	
						}			
					}
				})
			  }
					                    
		        function newBtn() {		
				$("#itemId").empty();
				$("#copy").hide();
				$("#add").hide();
				$("#delete").hide();
				$("#myGrid").hide();
				$("#searchRowDiv").hide();
				$("#totalReq").hide();
				$("#statusDiv").hide();
				$("#idDiv").hide();
				$("#collapseFour").hide();
				$("#headingFour").hide();
				$("#myGridActivity").hide();
				$("#demo").show();
		
			}
			// for cancel button
			function cancelBtn() {
				$("#add").show();
				$("#copy").show();
				$("#delete").show();
				$("#totalReq").show();
				$("#myGrid").show();
				$("#searchRowDiv").show();
				$("#demo").hide();
		
				$('#itemId').val("");
				$('#itemName').val("");
				$('#price').val("");
				$('#categry').val("");
				$('#subcategry').val("");
				$('#variant').val("");
				$('#status').val("");
		
				agGrid.simpleHttpRequest({
					url : "assign-all-throughAjax"
				}).then(function(data) {
					gridOptions.api.setRowData(data);
				});
				
			}
		


//****************gridDiv  call********************/      
       $(function() {
				var gridDiv = document.querySelector('#myGrid');
				new agGrid.Grid(gridDiv, gridOptions);
				agGrid.simpleHttpRequest({
					url : "assign-all-throughAjax"
				}).then(function(data) {
					var len = data.length;
					$('#totalReq').find('span').html(len);
					gridOptions.api.setRowData(data);
				});

				$("#myGrid").show();
				$("#delete").attr("disabled", true);
				var gridDiv = document.querySelector('#myGrid2');
				new agGrid.Grid(gridDiv, gridOptionschaildview);
				//gridOptionschaildview.api.setRowData();
				agGrid.simpleHttpRequest({
					url : "assign-throughAjax"
				}).then(function(data) {
					var len = data.length;
					$('#totalReq').find('span').html(len);
		
					gridOptionschaildview.api.setRowData(data);
					
				});
				$("#myGrid").show();
				$("#delete").attr("disabled", true);
			});
		
				
					

//************************************columnDefs *************************
				 var  columnDefs= [ {
					headerCheckboxSelection : true,
					headerCheckboxSelectionFilteredOnly : true,
					checkboxSelection : true,
					width : 10,
					sortable : false,
					filter : false,
					resizable : true
				},
				{
					headerName : 'Combo Id',
					field : "comboId",
					width : 350,
					cellRenderer : function(params) {
						return '<a onclick=editPage(\'' + params.data.comboId
								+ '\') href="javascript:void(0)">'
								+ params.data.comboId + '</a>';
					}
				},
			
				{
					headerName : "Combo Name",
					field : "comboName",
					width : 350,
					cellStyle : {
						textAlign : 'center'
					},
					  cellRenderer : function(params) {
							return '<div style="color:black;font-weight: bold;">'
							+ params.data.comboName 
							+ '</div>';
			      	}
				}, {
					headerName : "Total Price",
					field : "allPrice",
					cellStyle : {
						textAlign : 'center'
					},
					  cellRenderer : function(params) {
							return '<div style="color:black;font-weight: bold;">'
							+ params.data.allPrice 
							+ '</div>';
			      	}
				}];
			
				 const gridOptions = {
							columnDefs : columnDefs,
							defaultColDef : {
								sortable : true,
								filter : true,
								resizable : true,
								width : 220,
								height : 10
							},
							//rowSelection : 'multiple',
							suppressRowClickSelection : true,
							onSelectionChanged : rowSelect,
							getRowNodeId : function(data) {
								return data.comboId;
							}
						};
				
								 	
					function rowSelect() {
						//alert('hello select');
						var selectedRows = gridOptions.api.getSelectedRows();
						deleteId = "";
				
						for (var i = 0; i < selectedRows.length; i++) {
							deleteId = deleteId + '"' + selectedRows[i].comboId + '",';
							// deleteId = deleteId  + selectedRows[i].paymentId + ',';
						}
						deleteId = deleteId.substring(0, deleteId.length - 1);
						console.log(deleteId)
						var rowCount = 0;
				
						selectedRows.forEach(function(selectedRow, index) {
							rowCount = rowCount + 1;
						});
				
						if (rowCount > 0) {
							$('#delete').attr("disabled", false);
				
						} else {
							$('#delete').attr("disabled", true);
						}
					}
				
			
			
				
				
	//********************************chaildview****************************//
	
				const chaildview = [
						{
							headerCheckboxSelection : true,
							checkboxSelection : true,
							width : 8,
							sortable : false,
							filter : false,
							resizable : true
			
						},
						{
							headerName : 'Item Id',
							field : "itemId",
							width : 200
						},
			
						{
							headerName : "Item Name",
							field : "itemName",
							width : 200,
							cellStyle : {
								textAlign : 'center'
							}
						}, {
							headerName : "Price",
							field : "price",
							width : 200,
							cellStyle : {
								textAlign : 'right'
							}
						}
			
				];
			
				 var gridOptionschaildview = {
							columnDefs : chaildview,
							defaultColDef : {
								sortable : true,
								filter : true,
								resizable : true,
								width : 200,
								height : 20
							},
							rowSelection : 'multiple',
							onSelectionChanged : onRowClickedAkm
							//onRowClicked : onRowClickedAkm
						};
						
					//*****************AutoComlite Search********************** */
					// Initialize function, create initial tokens with itens that are already selected by the user
function init(element) {
    // Create div that wroaps all the elements inside (select, elements selected, search div) to put select inside
    const wrapper = document.createElement("div");
    wrapper.addEventListener("click", clickOnWrapper);
    wrapper.classList.add("multi-select-component");

    // Create elements of search
    const search_div = document.createElement("div");
    search_div.classList.add("search-container");
    const input = document.createElement("input");
    input.classList.add("selected-input");
    input.setAttribute("autocomplete", "off");
    input.setAttribute("tabindex", "0");
    input.addEventListener("keyup", inputChange);
    input.addEventListener("keydown", deletePressed);
    input.addEventListener("click", openOptions);

    const dropdown_icon = document.createElement("a");
    dropdown_icon.setAttribute("href", "#");
    dropdown_icon.classList.add("dropdown-icon");

    dropdown_icon.addEventListener("click", clickDropdown);
    const autocomplete_list = document.createElement("ul");
    autocomplete_list.classList.add("autocomplete-list")
    search_div.appendChild(input);
    search_div.appendChild(autocomplete_list);
    search_div.appendChild(dropdown_icon);

    // set the wrapper as child (instead of the element)
    element.parentNode.replaceChild(wrapper, element);
    // set element as child of wrapper
    wrapper.appendChild(element);
    wrapper.appendChild(search_div);

    createInitialTokens(element);
    addPlaceholder(wrapper);
}

function removePlaceholder(wrapper) {
    const input_search = wrapper.querySelector(".selected-input");
    input_search.removeAttribute("placeholder");
}

function addPlaceholder(wrapper) {
    const input_search = wrapper.querySelector(".selected-input");
    const tokens = wrapper.querySelectorAll(".selected-wrapper");
    if (!tokens.length && !(document.activeElement === input_search))
        input_search.setAttribute("placeholder", "---------");
}


// Function that create the initial set of tokens with the options selected by the users
function createInitialTokens(select) {
    let {
        options_selected
    } = getOptions(select);
    const wrapper = select.parentNode;
    for (let i = 0; i < options_selected.length; i++) {
        createToken(wrapper, options_selected[i]);
    }
}


// Listener of user search
function inputChange(e) {
    const wrapper = e.target.parentNode.parentNode;
    const select = wrapper.querySelector("select");
    const dropdown = wrapper.querySelector(".dropdown-icon");

    const input_val = e.target.value;

    if (input_val) {
        dropdown.classList.add("active");
        populateAutocompleteList(select, input_val.trim());
    } else {
        dropdown.classList.remove("active");
        const event = new Event('click');
        dropdown.dispatchEvent(event);
    }
}


// Listen for clicks on the wrapper, if click happens focus on the input
function clickOnWrapper(e) {
    const wrapper = e.target;
    if (wrapper.tagName == "DIV") {
        const input_search = wrapper.querySelector(".selected-input");
        const dropdown = wrapper.querySelector(".dropdown-icon");
        if (!dropdown.classList.contains("active")) {
            const event = new Event('click');
            dropdown.dispatchEvent(event);
        }
        input_search.focus();
        removePlaceholder(wrapper);
    }

}

function openOptions(e) {
    const input_search = e.target;
    const wrapper = input_search.parentElement.parentElement;
    const dropdown = wrapper.querySelector(".dropdown-icon");
    if (!dropdown.classList.contains("active")) {
        const event = new Event('click');
        dropdown.dispatchEvent(event);
    }
    e.stopPropagation();

}

// Function that create a token inside of a wrapper with the given value
function createToken(wrapper, value) {
    const search = wrapper.querySelector(".search-container");
    // Create token wrapper
    const token = document.createElement("div");
    token.classList.add("selected-wrapper");
    const token_span = document.createElement("span");
    token_span.classList.add("selected-label");
    token_span.innerText = value;
    const close = document.createElement("a");
    close.classList.add("selected-close");
    close.setAttribute("tabindex", "-1");
    close.setAttribute("data-option", value);
    close.setAttribute("data-hits", 0);
    close.setAttribute("href", "#");
    close.innerText = "x";
    close.addEventListener("click", removeToken)
    token.appendChild(token_span);
    token.appendChild(close);
    wrapper.insertBefore(token, search);
}


// Listen for clicks in the dropdown option
function clickDropdown(e) {

    const dropdown = e.target;
    const wrapper = dropdown.parentNode.parentNode;
    const input_search = wrapper.querySelector(".selected-input");
    const select = wrapper.querySelector("select");
    dropdown.classList.toggle("active");

    if (dropdown.classList.contains("active")) {
        removePlaceholder(wrapper);
        input_search.focus();

        if (!input_search.value) {
            populateAutocompleteList(select, "", true);
        } else {
            populateAutocompleteList(select, input_search.value);

        }
    } else {
        clearAutocompleteList(select);
        addPlaceholder(wrapper);
    }
}


// Clears the results of the autocomplete list
function clearAutocompleteList(select) {
    const wrapper = select.parentNode;

    const autocomplete_list = wrapper.querySelector(".autocomplete-list");
    autocomplete_list.innerHTML = "";
}

// Populate the autocomplete list following a given query from the user
function populateAutocompleteList(select, query, dropdown = false) {
    const {
        autocomplete_options
    } = getOptions(select);


    let options_to_show;

    if (dropdown)
        options_to_show = autocomplete_options;
    else
        options_to_show = autocomplete(query, autocomplete_options);

    const wrapper = select.parentNode;
    const input_search = wrapper.querySelector(".search-container");
    const autocomplete_list = wrapper.querySelector(".autocomplete-list");
    autocomplete_list.innerHTML = "";
    const result_size = options_to_show.length;

    if (result_size == 1) {

        const li = document.createElement("li");
        li.innerText = options_to_show[0];
        li.setAttribute('data-value', options_to_show[0]);
        li.addEventListener("click", selectOption);
        autocomplete_list.appendChild(li);
        if (query.length == options_to_show[0].length) {
            const event = new Event('click');
            li.dispatchEvent(event);

        }
    } else if (result_size > 1) {

        for (let i = 0; i < result_size; i++) {
            const li = document.createElement("li");
            li.innerText = options_to_show[i];
            li.setAttribute('data-value', options_to_show[i]);
            li.addEventListener("click", selectOption);
            autocomplete_list.appendChild(li);
        }
    } else {
        const li = document.createElement("li");
        li.classList.add("not-cursor");
        li.innerText = "No options found";
        autocomplete_list.appendChild(li);
    }
}


// Listener to autocomplete results when clicked set the selected property in the select option 
function selectOption(e) {
    const wrapper = e.target.parentNode.parentNode.parentNode;
    const input_search = wrapper.querySelector(".selected-input");
    const option = wrapper.querySelector(`select option[value="${e.target.dataset.value}"]`);

    option.setAttribute("selected", "");
    createToken(wrapper, e.target.dataset.value);
    if (input_search.value) {
        input_search.value = "";
    }

    input_search.focus();

    e.target.remove();
    const autocomplete_list = wrapper.querySelector(".autocomplete-list");


    if (!autocomplete_list.children.length) {
        const li = document.createElement("li");
        li.classList.add("not-cursor");
        li.innerText = "No options found";
        autocomplete_list.appendChild(li);
    }

    const event = new Event('keyup');
    input_search.dispatchEvent(event);
    e.stopPropagation();
}


// function that returns a list with the autcomplete list of matches
function autocomplete(query, options) {
    // No query passed, just return entire list
    if (!query) {
        return options;
    }
    let options_return = [];

    for (let i = 0; i < options.length; i++) {
        if (query.toLowerCase() === options[i].slice(0, query.length).toLowerCase()) {
            options_return.push(options[i]);
        }
    }
    return options_return;
}


// Returns the options that are selected by the user and the ones that are not
function getOptions(select) {
    // Select all the options available
    const all_options = Array.from(
        select.querySelectorAll("option")
    ).map(el => el.value);

    // Get the options that are selected from the user
    const options_selected = Array.from(
        select.querySelectorAll("option:checked")
    ).map(el => el.value);

    // Create an autocomplete options array with the options that are not selected by the user
    const autocomplete_options = [];
    all_options.forEach(option => {
        if (!options_selected.includes(option)) {
            autocomplete_options.push(option);
        }
    });

    autocomplete_options.sort();

    return {
        options_selected,
        autocomplete_options
    };

}

// Listener for when the user wants to remove a given token.
function removeToken(e) {
    // Get the value to remove
    const value_to_remove = e.target.dataset.option;
    const wrapper = e.target.parentNode.parentNode;
    const input_search = wrapper.querySelector(".selected-input");
    const dropdown = wrapper.querySelector(".dropdown-icon");
    // Get the options in the select to be unselected
    const option_to_unselect = wrapper.querySelector(`select option[value="${value_to_remove}"]`);
    option_to_unselect.removeAttribute("selected");
    // Remove token attribute
    e.target.parentNode.remove();
    input_search.focus();
    dropdown.classList.remove("active");
    const event = new Event('click');
    dropdown.dispatchEvent(event);
    e.stopPropagation();
}

// Listen for 2 sequence of hits on the delete key, if this happens delete the last token if exist
function deletePressed(e) {
    const wrapper = e.target.parentNode.parentNode;
    const input_search = e.target;
    const key = e.keyCode || e.charCode;
    const tokens = wrapper.querySelectorAll(".selected-wrapper");

    if (tokens.length) {
        const last_token_x = tokens[tokens.length - 1].querySelector("a");
        let hits = +last_token_x.dataset.hits;

        if (key == 8 || key == 46) {
            if (!input_search.value) {

                if (hits > 1) {
                    // Trigger delete event
                    const event = new Event('click');
                    last_token_x.dispatchEvent(event);
                } else {
                    last_token_x.dataset.hits = 2;
                }
            }
        } else {
            last_token_x.dataset.hits = 0;
        }
    }
    return true;
}

// You can call this function if you want to add new options to the select plugin
// Target needs to be a unique identifier from the select you want to append new option for example #multi-select-plugin
// Example of usage addOption("#multi-select-plugin", "tesla", "Tesla")
function addOption(target, val, text) {
    const select = document.querySelector(target);
    let opt = document.createElement('option');
    opt.value = val;
    opt.innerHTML = text;
    select.appendChild(opt);
}

document.addEventListener("DOMContentLoaded", () => {

    // get select that has the options available
    const select = document.querySelectorAll("[data-multi-select-plugin]");
    select.forEach(select => {

        init(select);
    });

    // Dismiss on outside click
    document.addEventListener('click', () => {
        // get select that has the options available
        const select = document.querySelectorAll("[data-multi-select-plugin]");
        for (let i = 0; i < select.length; i++) {
            if (event) {
                var isClickInside = select[i].parentElement.parentElement.contains(event.target);

                if (!isClickInside) {
                    const wrapper = select[i].parentElement.parentElement;
                    const dropdown = wrapper.querySelector(".dropdown-icon");
                    const autocomplete_list = wrapper.querySelector(".autocomplete-list");
                    //the click was outside the specifiedElement, do something
                    dropdown.classList.remove("active");
                    autocomplete_list.innerHTML = "";
                    addPlaceholder(wrapper);
                }
            }
        }
    });

});

									  
									  				
 //************************ItemchildViewassign******************************** */
				/* var ItemchildViewassign = [{
					headerCheckboxSelection : true,
					headerCheckboxSelectionFilteredOnly : true,
					checkboxSelection : true,
					width : 10,
					sortable : false,
					filter : false,
					resizable : true
				}, {
					headerName : "Item Id",
					field : "itemId",
					width : 175
			
				}, {
					headerName : "Item Name",
					field : "itemName",
					width : 175
				}, {
					headerName : "Price",
					field : "price",
					width : 175
				}, {
					headerName : "Action",
					field : "param",
					width : 95,
					cellStyle : {
						textAlign : 'center'
						
					},
					  cellRenderer : function(param) {
						  return `
						  <div >
					        <div style="color: black; font-weight: bold;"></div>
					        <button onclick=onRowClickedForAssignItem('${param.data.itemId}')>Delete</button>
					    </div>
					    `;
			      	}
				}];
				var gridOptionschaildassign = {
					columnDefs : ItemchildViewassign,
					defaultColDef : {
						sortable : true,
						filter : true,
						resizable : true,
						width : 200,
						height : 20
					},
					rowSelection : 'multiple',
					//onSelectionChanged : onRowClicked
					//onRowClicked : onRowClicked
				};
				    */