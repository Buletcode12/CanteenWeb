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
	//******************Combo call *************************/
		/*function getCombo(){			
			alert("hiii"+comboId)			
			console.log("hiii")
		}*/

	//*******************************Category call**********************
		/*		var firstFetchedData = []; // Store the first fetched data
									
					function getIncentiveStatus() {
					    var catId = $("#categry").val();
					    var subCatId = $("#subcategry").val();
					    var variant = $("#variant").val();
					      
					    //d  alert("csdc")
					   
					    $.ajax({
							    type: "GET",
							    url: "assign/canteen-item-list?catId=" + catId + "&subCatId=" + subCatId + "&variant=" + variant,
							    async: false,
							    success: function(response) {
							        
							        var items = response;											       
							        var tbody = $("#t_draggable1 tbody");

							        for (var i = 0; i < items.length; i++) {
							            var item = items[i];
							            // Create a new row for each item
							            var newRow = $("<tr>");
							            newRow.append("<th scope='row'></th>");
							            newRow.append("<td>" + item.itemId + "</td>");
							            newRow.append("<td>" + item.itemName + "</td>");
							            newRow.append("<td>" + item.price + "</td>");
							            // You can add more columns or data as needed
							            tbody.append(newRow);
							        }
							    }
							});
			    	   }   */
			    	   
				 var addedItemIds = [];					
					function getIncentiveStatus() {
					    var catId = $("#categry").val();
					    var subCatId = $("#subcategry").val();
					    var variant = $("#variant").val();
					
					    $.ajax({
					        type: "GET",
					        url: "assign/canteen-item-list?catId=" + catId + "&subCatId=" + subCatId + "&variant=" + variant,
					        async: false,
					        success: function (response) {
					            var items = response;
					            var tbody = $("#t_draggable1 tbody");
					
					            for (var i = 0; i < items.length; i++) {
					                var item = items[i];
					                if (!addedItemIds.includes(item.itemId)) {
					                    // Create a new row for each unique item
					                    var newRow = $("<tr>");
					                  
					                    newRow.append("<td>" + item.itemId + "</td>");
					                    newRow.append("<td>" + item.itemName + "</td>");
					                    newRow.append("<td>" + item.price + "</td>");
					                    tbody.append(newRow);
					
					                    // Add the item's ID to the list of added item IDs
					                    addedItemIds.push(item.itemId);
					                } else {
					                    // Handle the case where the item is already in the table (show an error message, for example)
					                    swal("Already Selected!");
					                }
					            }
					        }
					    });
					}




//**************************combo name********************* */


    //***********************Combo Function call view  ***********************************/	
						  function getCombo() {
						    var comboId = $("#comboId").val();
						    var addedComboIds = new Set(); // Use a Set to keep track of added combos
						    
						    $.ajax({
						        type: "GET",
						        url: "assign/canteen-combo-list?comboId=" + comboId,
						        async: false,
						        success: function (response) {
						            var items = response;
						            var tbody = $("#t_draggable2 tbody");
						
						            // Loop through the items in the response
						            for (var i = 0; i < items.length; i++) {
						                var item = items[i];
						                
						                // Check if the comboId has not been added to the table
						                if (!addedComboIds.has(item.comboId)) {
						                    // Create a new row for each unique item
						                    var newRow = $("<tr>");
						                   
						                    newRow.append("<td>" + item.comboId + "</td>");
						                    newRow.append("<td>" + item.comboName + "</td>");
						                    newRow.append("<td>" + item.allPrice + "</td>");
						                    tbody.append(newRow);
						                    
						                    // Add the comboId to the Set of added combos
						                    addedComboIds.add(item.comboId);
						                } else {
						                    // Handle the case where the combo is already in the table (show an error message, for example)
						                    swal("Combo already selected!");
						                }
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
		
		
  //******************Searching  Combo*****************/  		
	     function getComboList() {
			 //alert("sacsdca")
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
 		
                       var accumulatedComboNames = [];
							function selectAutocompleteValue2(comboId, comboName) {
							    var selectedItems = document.getElementById('selected-items');
							    $("#comboId").val(comboId);
							
							    var currentComboName = $("#comboName").val();
							
							    if (currentComboName) {
							        accumulatedComboNames.push({ comboName, comboId });
							
							        const selectedItem = document.createElement('div');
							        selectedItem.className = 'selected-item';
							        selectedItem.innerHTML = `
							            <span hidden>${comboId}</span>
							            <span>${comboName}</span>
							            <span class="remove-button" onclick="removeSelectedItem(this, '${comboName}', '${comboId}')">X</span>
							        `;
							        selectedItems.appendChild(selectedItem);
							
							        $("#comboName").val("");
							    } else {
							        accumulatedComboNames = [{ comboName, comboId }];
							        $("#comboName").val("");
							        		   
							}
							 var items = accumulatedComboNames;	
						     var tbody = $("#t_draggable2 tbody");	
					                									 
							 $("#suggesstion-box21_").hide();
							 			       					        
					         //for (var i = 0; i < items.length; i++) {
					            //var item = items[i];
					           // alert(comboId)	
					            //if (item.itemId === comboId) {
					            //Create a new row for the selected item
					                //alert("hi")
					                var row = "<tr><td>" + comboId + "</td>" +
					                "<td>" + comboName + "</td>" +
					                "<td>" +  + "</td></tr>";
					                $("#t_draggable2 tbody").append(row);					                					              					                
					                addedItemIds.push(comboId);	
					               
					                			                							           					              
					            //}					               
					        //}
					        					       
					     }											
							 
						
							
					function removeSelectedItem(element, comboId) {
						
					   const selectedItems = document.getElementById('selected-items');
					    selectedItems.removeChild(element.parentNode);
				
					    const index = addedItemIds.indexOf(comboId);
					    if (index !== -1) {
								   alert(index)
					        addedItemIds.splice(index, 1);
					       
					    }
						   alert(selectedItems)
					    // Remove the corresponding row from the table
					    $("#t_draggable2 tbody td td:first-child").each(function () {
					        if ($(this).text() === selectedItems) {
								
					            $(this).closest("tr").remove();
					        }
					    });
							
                       }


					    // To remove a suggestion on click		
							function removeLastSuggestion() {
							    if (accumulatedComboNames.length > 0) {
							        accumulatedComboNames.pop(); // Remove the last suggestion
							        $("#comboName").val(accumulatedComboNames.join(', ')); // Update the field value
							    }
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


				 var addedItemIds = []; // To keep track of added item IDs					
					function selectAutocompleteValue1(itemid, itemname) {
					    // Check if the item ID is already in the addedItemIds array
					    if (addedItemIds.includes(itemid)) {
							$("#suggesstion-box11_").hide();
					        swal("Item is already selected!");
					    } else {
					        // Add the item to the table
					        $("#itemName").val(itemname);
					        console.log("Search", itemDataOnSearch);
					        console.log("Items" + itemDataOnSearch);
					
					        var items = itemDataOnSearch;
					        var tbody = $("#t_draggable2 tbody");
					
					        for (var i = 0; i < items.length; i++) {
					            var item = items[i];
					            if (item.itemId === itemid) {
					                // Create a new row for the selected item
					                var newRow = $("<tr>");
					               
					                newRow.append("<td>" + item.itemId + "</td>");
					                newRow.append("<td>" + item.itemName + "</td>");
					                newRow.append("<td>" + item.price + "</td>");
					                tbody.append(newRow);
					
					                addedItemIds.push(itemid);									           
					                $("#suggesstion-box11_").hide();
					            }
					        }
					    }
					}

		  
		  
		  
 //******************Searching *****************/  
 //  let itemDataOnSearch = []; 
	/*    //searching
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
	         	}*/
 
 		
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
		
/*		var accumulatedComboNames = [];		
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
		}*/

 
		    	
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