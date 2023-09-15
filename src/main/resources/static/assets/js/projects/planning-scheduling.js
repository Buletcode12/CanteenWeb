$(document).ready(function(){
	
	 $("#browseBtn").click(function(){
		 $('#basic1').simpleTreeTable({
	    	    expander: $('#expander'),
	    	    collapser: $('#collapser'),
	    	    store: 'session',
	    	    storeKey: 'simple-tree-table-basic'
	    	  });
	    	$("#myModal").modal('show');
	    	
	    	
	    })
	     
	
	    var chk1 = $("input[type='checkbox'][value='1']");
    

	$('#check1').change(function() {
        if(this.checked) {
          
           chk1.prop('checked',this.checked);
        }
    });
	
	var chk2 = $("input[type='checkbox'][value='2']");
	
	$('#checkbox2').change(function() {
        if(this.checked) {
          
        	    chk2.prop('checked',this.checked);
        	
        }
    });
	    
	  
	
	 
	$("#selectCategory").click(function(){ 
		$("#myModalSelected").modal("show");
		$("#myModal").modal("hide");
		closeNav();
		
			
    })
	//getYearDataList();
	
	$("#submitYear").click(function(){
		obj = {};
		
		obj.year = $("#yearVal").val();
		
		$(".formValidation").remove();
		allPValid = true;
		if( $("#yearVal").val() == null || $("#yearVal").val() == ""){
			allPValid = false;
			validationAll("Year Required","yearVal");
		}
		
		if(allPValid) {
			
	    	$("body").addClass("overlay");
			submitYearData(obj);
		}
	})
	
	
	$("#saveBudgetPlan").click(function(){
		
		
    	$("body").addClass("overlay");
		
		var data = $("#hiddenMonthDataset").val();
		
		var monthlist = data.split(",");
		var monthset = [];
		for(var i = 0; i < monthlist.length - 1; i++) {
			monthset.push(monthlist[i].toLowerCase())
		}
		//console.log(monthset)
		/* var dataset = [];
		$("#basic > #tbodyData > tr").each(function(){
			var id = $(this).attr("id");
			console.log(id)
			var obj = {};
			obj['id'] = id;
			obj['year'] = $("#hiddenYear").val();
			for(var i = 0; i < monthset.length; i++) {
				var j = i + 1;
				var monthData = $(this).find(".m"+j+"_"+id).val();
				if(monthData == "" || monthData == null) {
					monthData = 0;
				} else {
					monthData = monthData.replace(",","");
				}
				obj[monthset[i]] = parseFloat(monthData);
			}
			var total = $(this).find(".total_"+id).val();
			if(total == "" || total == null) {
				total = 0;
			} else {
				total = total.replace(",","");
			}
			obj['total'] = parseFloat(total);
			dataset.push(obj)
		}) */
		submitBudgetPlan(dataset);
	})
})


	function openNav() {
		 $("#serviceName").val('');
		 $("#date").val('');
		 $("#time").val('');
		 $("#description").val('');
		// $("#toPlace").val('');
		document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:250px;";

		document.getElementById("main").style.width = "75%";
	}

	function closeNav() {
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("main").style.width = "100%";
		$("#planId").val('');
		$("#date").val('');
		$("#time").val('');
		$("#description").val('');
		//$("#toPlace").val('');
		//$("#rowEdit").val(null);
	}
	
	$(function() {
		$("input[name='advanceReq']").click(function() {
			if ($("#No").is(":checked")) {
				$("#amount").hide();
				$("#advanceAmount").val("");
				
			} else {
				$("#amount").show();
			}
		});
	});

	var count1 = 0;
	function allCheck1() {
		count1++;

		if (count1 == 1) {
			$('.checkCls1').prop("checked", true);
		} else {
			count1 = 0;
			$('.checkCls1').prop("checked", false);
		}
	}

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";
		if ($('#searchBar').val() == null || $('#searchBar').val() == "") {
			id.style.display = "none";
		}
	}

	$(document).ready(function() {
		
		var userid = $("#sessionId").val();
		var userrole = $("#sessionRole").val();
		var roleid = "";
		for(var i = 6; i <= userrole.length; i=i+6){  
		    roleid = roleid + '"' +userrole.slice(i-6, i)+ '",';
		}
		roleid = roleid.substring(0, roleid.length - 1); 
		//gridOptions.api.setRowData();
		
	
		
		
		

		// date format FROM date

		var dateFormat = localStorage.getItem("dateFormat");
		$("#fromDateCalendar").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
			minDate : new Date(),
		}).on("change", function() {
			$('#fromDate').val($(this).val());
		})

		$('#fromDate').blur(function() {
			$("#fromDateCalendar").val($(this).val());
		})
		


		//     date format TO date

		var dateFormat = localStorage.getItem("dateFormat");
		$("#toDateCalendar").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
			minDate : new Date(),
		}).on("change", function() {
			$('#endDate').val($(this).val());
		})

		$('#endDate').blur(function() {
			$("#toDateCalendar").val($(this).val());
		})


		
		///TO_date
		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#startDate').val($(this).val());
		})

		$('#startDate').blur(function() {
			$("#DateCalendar").val($(this).val());
		})
		
		
 $("#DateCalendar1").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#endDate').val($(this).val());
		})

		$('#endDate').blur(function() {
			$("#DateCalendar").val($(this).val());
		})
 
	

	/* $("#toDateCalendarTime").datetimepicker({
		format : 'H:i',
		closeOnDateSelect : false,
		timepicker : true,
		datepicker : false,
		step: 15
	}).on("change", function() {
		$('#time').val($(this).val());
	})

	$('#time').blur(function() {
		$("#toDateCalendarTime").val($(this).val());
	}) */

		//changeOpt();

		$("#statusDiv").hide();
		$('#reqDltBtn').attr('disabled', true);
		$('#approve').attr('disabled', true);
		$('#reject').attr('disabled', true);
		$('#deleteChild').attr('disabled', true);
		$('.collapse').on('show.bs.collapse', function() {
			$(this).siblings('.panel-heading').addClass('active');
		});

		$('.collapse').on('hide.bs.collapse', function() {
			$(this).siblings('.panel-heading').removeClass('active');
		});
		$("#newBtn").click(function() {
			$("#myGrid").hide();
			$("#reqTable").hide();
			//$("#searchDiv").hide();
			$(".btn-hs").show();
			$("#btndiv").hide();
			$("#listdiv").hide();
			$("#totalReq").hide();
			$("#searchRowDiv").hide();
			$("#ttbtn").hide();
			$("#demo").show();
			
		})

	});
	
	/* -------------------search bar for mygrid------------------------ */

	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";

		if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
		}
	}
	
	
	function submitYearData(dataset) {
		$.ajax({
	        type: "POST",
	        url: "planning-scheduling-save-year",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(dataset),
	        success: function(response) {
	            if (response.message == "success") {
	            	/* $('.loader').hide();
	            	$("body").removeClass("overlay"); */
	            	closeNav();
	            	
	            	var div = '<div id="'+response.body.yearId+'" class="databasebox_mail divcls" style="text-align: center !important;" onclick=openDetails("'+response.body.yearId+'","'+response.body.year+'","'+response.body.monthDtls+'")>'+
							'<a href="javascript:void(0)"><div style="font-size: 34px;">' + response.body.year + '</div> '+
						'<span style="font-size: 15px;">'+response.body.monthDtls+'</span></a></div>';
					$("#yearDiv").append(div);
	            }
	        }, error: function(data) {
	        	console.log(data)
	        	/* $('.loader').hide();
	        	$("body").removeClass("overlay"); */
	        }
		});
	}
	
	function getYearDataList() {
		$("#yearDiv").empty();
		//$('.loader').show();
		//$("body").addClass("overlay");
		$.ajax({
	        type: "POST",
	        url: "planning-scheduling-get-year-list",
	        dataType: "json",
	        contentType: "application/json",
	        data: "A",
	        success: function(response) {
	            if (response.message == "success") {
	            	console.log(response.body)
	            	
	            	var id = response.body[0].year;
	            	var mnth =response.body[0].monthDtls;
	            	mnth = mnth.replaceAll(" ","");
	            	mnth = mnth.split("-");
	            	setMonthList(mnth[0],mnth[2]);
	            	for(var i = 0; i < response.body.length; i++) {
	            		var mnth = response.body[i].monthDtls;
	            		mnth = mnth.replaceAll(" ","");
	            		mnth = mnth.split("-");
	            		var start = mnth[0];
	            		var end = mnth[2];
	            		var div = '<div id="'+response.body[i].yearId+'" class="databasebox_mail divcls" style="text-align: center !important;" onclick=openDetails("'+response.body[i].yearId+'","'+response.body[i].year+'","'+start+'","'+end+'")>'+
								'<a href="javascript:void(0)"><div style="font-size: 34px;">' + response.body[i].year + '</div> '+
							'<span style="font-size: 15px;">'+response.body[i].monthDtls+'</span></a></div>';
	            		$("#yearDiv").append(div);
	            		
	            		
	            		
	            		if(i == 0) {
	            			$("#"+response.body[i].yearId).removeClass("databasebox_mail");
	            			$("#"+response.body[i].yearId).addClass("databasebox_mailactive");
	            		}
	            	}
	            	
	            	$("#hiddenYear").val(response.body[0].year);
	            	
	            	getBudgetPlanData(id);
	            	
	            }
	        }, error: function(data) {
	        	console.log(data)
	        }
		});
	}
	
	
	function getBudgetPlanData(id) {
		$("#tbodyData").empty();
		obj = {};
		obj.year = id;
		obj.id = $("#hiddenMonthDataset").val();
		$.ajax({
	        type: "POST",
	        url: "planning-scheduling-get-cc-list",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(obj),
	        success: function(response) {
	            if (response.message == "success") {
	            	console.log(response.body)
	            	for(var i = 0; i < response.body.length; i++) {
	            		var row = "";
	            		if(i == 0) {
	            			if(response.body[i].count > 0) {
	            				row = '<tr data-node-id="'+response.body[i].id+'" class="abc" id="'+response.body[i].id+'">'+
	    						'<td class="firstnode">'+response.body[i].slNo+' - '+response.body[i].name+'</td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m1_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m1)+'"><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m2_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m2)+'"><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m3_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m3)+'"><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m4_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m4)+'"><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m5_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m5)+'"><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m6_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m6)+'"><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m7_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m7)+'"><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m8_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m8)+'"><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m9_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m9)+'"><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m10_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m10)+'"><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m11_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m11)+'"><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m12_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m12)+'"><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="total_'+response.body[i].id+'" value="'+formatNumber(response.body[i].total)+'"></td></tr>';
	            			} else {
	            				row = '<tr data-node-id="'+response.body[i].id+'" class="abc" id="'+response.body[i].id+'">'+
	    						'<td class="firstnode">'+response.body[i].slNo+' - '+response.body[i].name+'</td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m1_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m1)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",1)><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m2_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m2)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",2)><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m3_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m3)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",3)><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m4_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m4)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",4)><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m5_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m5)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",5)><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m6_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m6)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",6)><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m7_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m7)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",7)><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m8_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m8)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",8)><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m9_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m9)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",9)><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m10_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m10)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",10)><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m11_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m11)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",11)><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m12_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m12)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",12)><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="total_'+response.body[i].id+'" value="'+formatNumber(response.body[i].total)+'" disabled></td></tr>';
	            			}
	            			
	            		} else {
	            			if(response.body[i].count > 0) {
	            				row = '<tr data-node-id="'+response.body[i].id+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].id+'">'+
	    						'<td class="firstnode">'+response.body[i].slNo+' - '+response.body[i].name+'</td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m1_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m1)+'"><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m2_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m2)+'"><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m3_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m3)+'"><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m4_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m4)+'"><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m5_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m5)+'"><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m6_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m6)+'"><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m7_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m7)+'"><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m8_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m8)+'"><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m9_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m9)+'"><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m10_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m10)+'"><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m11_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m11)+'"><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="m12_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m12)+'"><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'+
	    						'<td><input type="text"  disabled data-idval="'+response.body[i].id+'" class="total_'+response.body[i].id+'" value="'+formatNumber(response.body[i].total)+'"></td></tr>';
	            			} else {
	            				
	            				if(response.body[i].chartAccList.length > 0) {
	            					row = '<tr data-node-id="'+response.body[i].id+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].id+'">'+
	        						'<td class="firstnode">'+response.body[i].slNo+' - '+response.body[i].name+'</td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m1_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m1)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",1)><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m2_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m2)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",2)><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m3_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m3)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",3)><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m4_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m4)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",4)><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m5_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m5)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",5)><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m6_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m6)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",6)><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m7_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m7)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",7)><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m8_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m8)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",8)><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m9_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m9)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",9)><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m10_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m10)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",10)><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m11_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m11)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",11)><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="m12_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m12)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",12)><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text" disabled data-idval="'+response.body[i].id+'" class="total_'+response.body[i].id+'" value="'+formatNumber(response.body[i].total)+'" disabled></td></tr>';
	        						var row1 = "";
	        						for(var j = 0; j < response.body[i].chartAccList.length; j++) {
	        							row1 = row1 + '<tr data-node-id="'+response.body[i].chartAccList[j].id+'" data-node-pid="'+response.body[i].chartAccList[j].parentId+'" class="abc" id="'+response.body[i].chartAccList[j].id+'">'+
	            						'<td class="firstnode">'+response.body[i].chartAccList[j].slNo+' - '+response.body[i].chartAccList[j].name+'</td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m1_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m1)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",1)><input type="hidden" value="'+response.body[i].chartAccList[j].m1+'" id="hdm1_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m2_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m2)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",2)><input type="hidden" value="'+response.body[i].chartAccList[j].m2+'" id="hdm2_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m3_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m3)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",3)><input type="hidden" value="'+response.body[i].chartAccList[j].m3+'" id="hdm3_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m4_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m4)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",4)><input type="hidden" value="'+response.body[i].chartAccList[j].m4+'" id="hdm4_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m5_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m5)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",5)><input type="hidden" value="'+response.body[i].chartAccList[j].m5+'" id="hdm5_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m6_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m6)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",6)><input type="hidden" value="'+response.body[i].chartAccList[j].m6+'" id="hdm6_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m7_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m7)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",7)><input type="hidden" value="'+response.body[i].chartAccList[j].m7+'" id="hdm7_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m8_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m8)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",8)><input type="hidden" value="'+response.body[i].chartAccList[j].m8+'" id="hdm8_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m9_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m9)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",9)><input type="hidden" value="'+response.body[i].chartAccList[j].m9+'" id="hdm9_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m10_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m10)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",10)><input type="hidden" value="'+response.body[i].chartAccList[j].m10+'" id="hdm10_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m11_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m11)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",11)><input type="hidden" value="'+response.body[i].chartAccList[j].m11+'" id="hdm11_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="m12_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].m12)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].chartAccList[j].id+'","'+response.body[i].chartAccList[j].parentId+'",12)><input type="hidden" value="'+response.body[i].chartAccList[j].m12+'" id="hdm12_'+response.body[i].chartAccList[j].id+'"></td>'+
	            						'<td><input type="text"  data-idval="'+response.body[i].chartAccList[j].id+'" class="total_'+response.body[i].chartAccList[j].id+'" value="'+formatNumber(response.body[i].chartAccList[j].total)+'" disabled></td></tr>';
	        						}
	        						row = row + row1;
	            				} else {
	            					row = '<tr data-node-id="'+response.body[i].id+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].id+'">'+
	        						'<td class="firstnode">'+response.body[i].slNo+' - '+response.body[i].name+'</td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m1_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m1)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",1)><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m2_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m2)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",2)><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m3_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m3)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",3)><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m4_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m4)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",4)><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m5_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m5)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",5)><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m6_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m6)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",6)><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m7_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m7)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",7)><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m8_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m8)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",8)><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m9_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m9)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",9)><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m10_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m10)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",10)><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m11_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m11)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",11)><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="m12_'+response.body[i].id+'" value="'+formatNumber(response.body[i].m12)+'" onkeyup=setTotalRowWise(event);setParentData("'+response.body[i].id+'","'+response.body[i].parentId+'",12)><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'+
	        						'<td><input type="text"  data-idval="'+response.body[i].id+'" class="total_'+response.body[i].id+'" value="'+formatNumber(response.body[i].total)+'" disabled></td></tr>';
	            				}
	            			}
	            		}
						$("#tbodyData").append(row);
	            	}
	            	
	            	/* $('.loader').hide();
	            	$("body").removeClass("overlay"); */
	            	/* 
	            	$('#basic').simpleTreeTable({
	            	    expander: $('#expander'),
	            	    collapser: $('#collapser'),
	            	    store: 'session',
	            		storeKey: 'simple-tree-table-basic'
	            	}); */
	            }
	        }, error: function(data) {
	        	console.log(data)
	        	/* $('.loader').hide();
	        	$("body").removeClass("overlay"); */
	        }
		});
	}
	
	function submitYearData(dataset) {
		$.ajax({
	        type: "POST",
	        url: "planning-scheduling-save",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(dataset),
	        success: function(response) {
	            if (response.message == "success") {
	            	/* $('.loader').hide();
	            	$("body").removeClass("overlay"); */
	            	closeNav();
	            	
	            	var div = '<div id="'+response.body.yearId+'" class="databasebox_mail divcls" style="text-align: center !important;" onclick=openDetails("'+response.body.yearId+'","'+response.body.year+'","'+response.body.monthDtls+'")>'+
							'<a href="javascript:void(0)"><div style="font-size: 34px;">' + response.body.year + '</div> '+
						'<span style="font-size: 15px;">'+response.body.monthDtls+'</span></a></div>';
					$("#yearDiv").append(div);
	            }
	        }, error: function(data) {
	        	console.log(data)
	        	/* $('.loader').hide();
	        	$("body").removeClass("overlay"); */
	        }
		});
	}
	
	
 function cancel() {
	 $(".formValidation").remove();
		$("#reqTable").show();
		//$("#searchDiv").show();
		$(".btn-hs").show();
		$("#myGrid").show();
		$("#myGrid1").show();
		$("#demo").hide();
		$("#totalReqs").show();
		$("#totalReq").show();
		$("#searchRowDiv").show();
		var userid = $("#sessionId").val();
		var userrole = $("#sessionRole").val();
		var roleid = "";
		for(var i = 6; i <= userrole.length; i=i+6){  
		    roleid = roleid + '"' +userrole.slice(i-6, i)+ '",';
		}
		roleid = roleid.substring(0, roleid.length - 1); 
		
		$("#employeeId").val('');
		$("#fromDate").val('');
		$("#toDate").val('');
		$("#planId").val('');
		$("#date").val('');
		$("#time").val('');
		$("#description").val('');
		//$("#toPlace").val('');
		$("#rowEdit").val(null);
	};

 
//master save data

function masterSaveData() {
	var item = {};
	var valid = true;
	var data = 1;
	var rowEdit = $("#rowEdit").val();
	if (true) {

		gridOptions.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});
		
		item.category = $("#category").val();
		item.subCategory = $("#subCategory").val();
		item.projectName = $("#projectName").val();
		item.projectLocation = $("#projectLocation").val();
		item.projectinCharge = $("#projectinCharge").val();
		item.planId = $("#planId").val();
		item.priority = $("#priority").val();
		item.taskName = $("#taskName").val();
		item.startDate = $("#startDate").val();
		item.endDate = $("#endDate").val();
		item.assignedTo = $("#assignedTo").val();
		item.duration = $("#duration").val();
		item.predecessors = $("#predecessors").val();
		item.notes = $("#notes").val();
		item.slnoId = data;
		item.projectplanId = data;
		var datas = [];

		if (valid) {
			cancel();
		if (rowEdit) {
			var rowNode = gridOptions.api.getRowNode(rowEdit);
			rowNode.setData(item);
		} else {

			gridOptions.api.forEachNode(function(rowNode, index) {
				datas.push(rowNode.data);
			});

			datas.push(item)
			gridOptions.api.setRowData(datas);
		}
		} else{
			$('#demo').show();
			
		}
	}
}

function saveTravelRequisition(datas) {
	console.log(datas);
	////$('.loader').show();
	//$("body").addClass("overlay");
	$.ajax({
		type : "POST",
		url : "travel-requisition-add-travel-details",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(datas),
		success : function(response) {
			////$('.loader').hide();
			if (response.code == "success") {
				cancel();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				closeNav()
			} else {
				//$('.loader').hide();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}
		},
		error : function(data) {
			//$('.loader').hide();
		}
	}) //ajax ends
}

	$("#newBtn").click(function() {
		$("#myGrid").hide();
		$("#reqTable").hide();
		$(".btn-hs").hide();
		$("#demo").show();
		$("#totalReq").hide();
		$("#searchRowDiv").hide();
		$("#ttbtn").hide();
	})

	var columnDefs = [
			{
				headerCheckboxSelection : false,
				headerCheckboxSelectionFilteredOnly : false,
				checkboxSelection : true,
				width : 10,
				sortable : false,
				filter : false,
				resizable : true

			},
			{
				headerName : 'Plan Id',
				width : 120,
				field : "projectplanId",
				/* cellRenderer : function(params) {
							return '<a id="projectplanId" onclick=editTravel("'
							+ params.data.projectplanId
							+ '") href="javascript:void(0)">'
							+ params.data.projectplanId + '</a>';
				} */
/* 				cellRenderer : function(params) {
					return '<a id="requisitionId" onclick=editTravel("'
							+ params.data.travelingReqId+','
							+ params.data.employeeId+','+params.data.status
							+ '") href="javascript:void(0)">'
							+ params.data.travelingReqId + '</a>';

				} */
			}, {
				headerName : 'Category',
				width : 120,
				field : "category",
			}, {
				headerName : 'Sub Category',
				width : 150,
				field : "subCategory",
			}, {
				headerName : 'Project Name',
				field : "projectName",
				width : 180,
			}, {
				headerName : 'Project Location',
				field : "projectLocation",
				width : 180,
				cellStyle : {
					textAlign : 'center'
				}
			},
			{
				headerName : 'Project In Charge',
				field : "projectinCharge",
				width : 180,
				cellStyle : {
					textAlign : 'center'
				}
			}, {
				headerName : 'Serial No',
				field : "slnoId",
				width : 150,
				cellStyle : {
					textAlign : 'center'
				}
			}, {
				headerName : 'Priority',
				field : "priority",
				width : 150,
			},{
				headerName : 'Task Name',
				width : 150,
				field : "taskName",
				/* cellRenderer : function(params) {
					if (params.data.status =="Pending") { 
						return '<div style="color:#a9a9a9">Pending</div>';
					} else if (params.data.status =="Forwarded") {
						return '<div style="color:#ffa500">Forwarded</div>';
					} else if (params.data.status == "Approved") {
						return '<div style="color:#0642f5">Approved</div>';
					} else {
						return '<div style="color:#ff8242">Rejected</div>';
					}	
				} */
			}, {
				headerName : 'Start Date',
				field : "startDate",
				width : 150,
				cellStyle : {
					textAlign : 'center'
				},
/* 				cellRenderer : function(params) {
					if (params.data.advanceReq=='0') {
						return 'NO';
					} else {
						return 'YES';
					}
				} */
			}, {
				headerName : 'End Date',
				width : 150,
				field : "endDate",
			}, {
				headerName : 'Assigned To',
				width : 180,
				field : "assignedTo"

			}, {
				headerName : 'Duration',
				field : "duration"
			},{
				headerName : 'Predecessors',
				width : 180,
				field : "predecessors"

			},{
				headerName : 'Notes',
				field : "notes",
				width : 200,
			}

			 ];
	
	
	var columnDefs1 = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Project ID",
		field : "projectId",
	}, {
		headerName : "Project Name",
		field : "projectName",
	}, {
		headerName : "Creation Date",
		field : "creationDate",
		width : 150,
	}, {
		headerName : 'Location',
		field : "location",
		width : 130,
	}, {
		headerName : "State",
		field : "stateId",
		width : 150,
	}, {
		headerName : 'Pin',
		field : "pinId",
	}, {
		headerName : 'Project Incharge',
		field : "projectIncharge",
	}, {
		headerName : 'Customer Name',
		field : "customerName",
	}, {
		headerName : 'Customer Address',
		field : "customerAddress",
	}, {
		headerName : 'State',
		field : "stateId1",
		width : 150,

	}, {
		headerName : 'Pin',
		field : "pinId1",
		width : 150,

	}, {
		headerName : 'Email',
		field : "email",
		width : 150,

	}, {
		headerName : 'Mobile',
		field : "mobile",
		width : 150,

	}, {
		headerName : 'Remarks',
		field : "remarks",
		width : 150,

	}, {
		headerName : 'Status',
		field : "status",
		width : 150,

	} ];
	const rowData1 = [
			{
				projectId : "RFP001",
				projectName : "Iterarch Building Product PVT ltd",
				creationDate : "15-05-2023",
				location : "Panipat,Haryana",
				stateId : "Haryana",
				pinId : "132103",
				projectIncharge : "Incharge 1",
				customerName : "Customer 1",
				customerAddress : "GRASIM INDUSTRIES LTD., 48-62, HSIIDC, INDUSTRIAL ESTATE REFINERY ROAD, PANIPAT",
				stateId1 : "Haryana",
				pinId1 : "Customer Pin 1",
				email : "customer1@example.com",
				mobile : "1234567890",
				remarks : "Some remarks",
				status : "Active",

			},
			{
				projectId : "RFP002",
				projectName : "SMCC CONSTRUCTION INDIA LIMITED",
				creationDate : "15-05-2023",
				location : "Panipat,Haryana",
				stateId : "Haryana",
				pinId : "132140",
				projectIncharge : "Incharge 1",
				customerName : "Customer 1",
				customerAddress : "PANIPAT REFINERY",
				stateId1 : "Haryana",
				pinId1 : "Customer Pin 1",
				email : "customer1@example.com",
				mobile : "1234567890",
				remarks : "Some remarks",
				status : "Active",

			},
			{
				projectId : "RFP003",
				projectName : "Bhusan Power & Steel",
				creationDate : "15-05-2023",
				location : "BECHARAJI, GUJARAT",
				stateId : "GUJARAT",
				pinId : "382130",
				projectIncharge : "Incharge 1",
				customerName : "Customer 1",
				customerAddress : "TDS LITHIUM-ION BATTERY GUJARAT PVT LTD.PLOT NO.1,2,3 & 9, BLOCK NO.334 & 335",
				stateId1 : "GUJARAT",
				pinId1 : "Customer Pin 1",
				email : "customer1@example.com",
				mobile : "1234567890",
				remarks : "Some remarks",
				status : "Active",

			},

			{
				projectId : "RFP004",
				projectName : "Shree Cement",
				creationDate : "15-05-2023",
				location : "AURANGABAD, BIHAR",
				stateId : "BIHAR",
				pinId : "132103",
				projectIncharge : "Incharge 1",
				customerName : "Customer 1",
				customerAddress : "Address 1",
				stateId1 : "Customer State 1",
				pinId1 : "Customer Pin 1",
				email : "customer1@example.com",
				mobile : "1234567890",
				remarks : "Some remarks",
				status : "Active",

			},

	];

	

	// for activity table

	/* var activityDefs = [
			{
				headerCheckboxSelection : true,
				headerCheckboxSelectionFilteredOnly : true,
				checkboxSelection : true,
				sortable : false,
				filter : false,
				resizable : true,
				width : 30
			},
			{
				headerName : "Serial No",
				field : "slnoId",
				width : 150,
				cellRenderer : function(params) {
					return '<a onclick=editRow("' + params.data.slnoId
							+ '") href="javascript:void(0)">'
							+ params.data.slnoId + '</a>';
				}
			}, {
				headerName : "Priority",
				field : "priority",
				width : 250
			}, {
				headerName : "Task Name",
				field : "taskName",
				width : 200,
			}, {
				headerName : "Start Date",
				field : "startDate",
				width : 200,
				cellStyle : {
					textAlign : 'center'
				},
			}, {
				headerName : "End Date",
				field : "endDate",
				width : 200,
				cellStyle : {
					textAlign : 'center'
				},
			},

			{
				headerName : "Assigned To",
				field : "assignedTo",
				width : 200,
				cellStyle : {
					textAlign : 'center'
				},
			},{
				headerName : "Duration",
				field : "duration",
				width : 200,
				cellStyle : {
					textAlign : 'center'
				},
			}, {
				headerName : "Predecessors",
				field : "predecessors",
				width : 200,
				cellStyle : {
					textAlign : 'center'
				},
			},{
				headerName : "Notes",
				field : "notes",
				width : 470
			},]; */
	
	
	/* 
	const rowData1 = [
		{
			slnoId:"1",
		    priority:"Medium",
		    taskName:"Painting",
		    startDate:"2023-06-12",
		    endDate:"2023-08-23",
		    assignedTo:"Sradha Panda",
		    duration:"3weeks",
		    predecessors:"",
		    notes:"In Progress",
		},
		{
			slnoId:"2",
		    priority:"Medium",
		    taskName:"Sheeting/Decking",
		    startDate:"2023-06-12",
		    endDate:"2023-08-23",
		    assignedTo:"Sradha Panda",
		    duration:"3weeks",
		    predecessors:"",
		    notes:"In Progress",
		},
		{
			slnoId:"3",
		    priority:"Medium",
		    taskName:"Fabrication",
		    startDate:"2023-06-12",
		    endDate:"2023-08-23",
		    assignedTo:"Sradha Panda",
		    duration:"3weeks",
		    predecessors:"",
		    notes:"In Progress",
		},
		
		{
			slnoId:"4",
		    priority:"Medium",
		    taskName:"Civil Work",
		    startDate:"2023-06-12",
		    endDate:"2023-08-23",
		    assignedTo:"Sradha Panda",
		    duration:"3weeks",
		    predecessors:"",
		    notes:"In Progress",
		},
		{
			slnoId:"5",
		    priority:"Medium",
		    taskName:"Farana- F-15",
		    startDate:"2023-06-12",
		    endDate:"2023-08-23",
		    assignedTo:"Sradha Panda",
		    duration:"3weeks",
		    predecessors:"",
		    notes:"In Progress",
		},
		{
			slnoId:"6",
		    priority:"Medium",
		    taskName:"Crane- 250 MT",
		    startDate:"2023-06-12",
		    endDate:"2023-08-23",
		    assignedTo:"Sradha Panda",
		    duration:"3weeks",
		    predecessors:"",
		    notes:"In Progress",
		},
		{
			slnoId:"7",
		    priority:"Medium",
		    taskName:"Crane- 80 MT",
		    startDate:"2023-06-12",
		    endDate:"2023-08-23",
		    assignedTo:"Sradha Panda",
		    duration:"3weeks",
		    predecessors:"",
		    notes:"In Progress",
		},
	] */
	
	
	const rowData = [
		{
			projectplanId : "1",
			category : "Manpower",
			subCategory : "Painting",
			projectName : "Iterarch Building Product PVT ltd",
			projectLocation : "Haryana",
			projectinCharge : "Incharge 1",
			slnoId : "1",
			priority : "Medium",
			taskName : "Painting",
			startDate : "12-06-2023",
			endDate : "21-07-2023",
			assignedTo : "Sradha panda",
			duration : "2 week",
			predecessors : "",
			notes : "yes",
			
		},
		
		{
			projectplanId : "2",
			category : "Manpower",
			subCategory : "Fabrication",
			projectName : "Iterarch Building Product PVT ltd",
			projectLocation : "Haryana",
			projectinCharge : "Incharge 1",
			slnoId : "2",
			priority : "Medium",
			taskName : "Fabrication",
			startDate : "12-06-2023",
			endDate : "21-07-2023",
			assignedTo : "Sradha panda",
			duration : "3 week",
			predecessors : "",
			notes : "yes",
			
		},
		
		{
			projectplanId : "3",
			category : "Hiring of Equipments/Machineries",
			subCategory : "Farana- F-17",
			projectName : "Bhusan Power & Steel",
			projectLocation : "Odisha",
			projectinCharge : "Incharge 1",
			slnoId : "3",
			priority : "Medium",
			taskName : "Painting",
			startDate : "12-06-2023",
			endDate : "21-07-2023",
			assignedTo : "Sradha panda",
			duration : "2 week",
			predecessors : "",
			notes : "yes",
			
		},
		
		{
			projectplanId : "4",
			category : "Manpower",
			subCategory : "Stud Welding",
			projectName : "Iterarch Building Product PVT ltd",
			projectLocation : "Haryana",
			projectinCharge : "Incharge 1",
			slnoId : "4",
			priority : "Medium",
			taskName : "Fabrication",
			startDate : "12-06-2023",
			endDate : "21-07-2023",
			assignedTo : "Sradha panda",
			duration : "2 week",
			predecessors : "",
			notes : "yes",
			
		},
		{
			projectplanId : "5",
			category : "Purchase Tools & Tackies",
			subCategory : "Torque Wrench",
			projectName : "Iterarch Building Product PVT ltd",
			projectLocation : "Haryana",
			projectinCharge : "Incharge 1",
			slnoId : "5",
			priority : "Medium",
			taskName : "Fabrication",
			startDate : "12-06-2023",
			endDate : "21-07-2023",
			assignedTo : "Sradha panda",
			duration : "2 week",
			predecessors : "",
			notes : "yes",
			
		},
		

		];

	// let the grid know which columns and what data to use
	var gridOptions = {
		columnDefs : columnDefs,
		rowData: rowData,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 20
		},
		rowSelection : 'multiple',
		//onSelectionChanged : onSelectionChanged
	};
	
	
	var gridOptions1 = {
			columnDefs : columnDefs1,
			rowData: rowData1,
			defaultColDef : {
				sortable : true,
				filter : true,
				resizable : true,
				width : 200,
				height : 20
			},
			rowSelection : 'multiple',
			//onSelectionChanged : onSelectionChanged
		};
	
	var columnDefsNew = [ 
		 {
			headerCheckboxSelection : true,
			headerCheckboxSelectionFilteredOnly : true,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			resizable : true
		} ,{
			
			headerName : "BudgetId",
			field : "projectplanId",
			hide : true,
			width : 200,
		},{
		
		headerName : " Category",
		field : "category",
		width : 150,
	}, {
		headerName : ' Sub-Category',
		field : "subCategory",
		width : 150
	}, {
		headerName : 'Project Name',
		field : "projectName",
		width : 150,
	},{
		
		headerName : " Project Location",
		field : "projectLocation",
		width : 150,
	}, {
		headerName : ' Project-In-Charge',
		field : "projectinCharge",
		width : 150
	},
	];
	const rowdataNew=[ {
		projectplanId : "1",
		category : "Purchase Civil Material",
		subCategory : "Tiles/Mables/Chips",
		projectName : "Iterarch Building Product PVT ltd",
		projectLocation : "Haryana",
		projectinCharge : "Incharge 1",
		}, {
			projectplanId : "2",
			category : "Purchase Civil Material",
			subCategory : "Sand",
			projectName : "Iterarch Building Product PVT ltd",
			projectLocation : "Chatisgarh",
			projectinCharge : "Incharge 3",
		},
		{
			projectplanId : "3",
			category : "Manpower",
			subCategory : "Painting",
			projectName : "Iterarch Building Product PVT ltd",
			projectLocation : "Haryana",
			projectinCharge : "Incharge 1",
		},
	];
	var gridOptionsNew = {
			columnDefs : columnDefsNew,
			rowData : rowdataNew,
			defaultColDef : {
				sortable : true,
				filter : true,
				resizable : true,
				width : 200,
				height : 20
			},
			rowSelection : 'multiple',
			onSelectionChanged : onSelectionChanged
		};

	// let the grid know which columns and what data to use product table
	/* var activityOptions = {
		columnDefs : activityDefs,
		rowData: rowData1,
		rowSelection : 'multiple',
		groupSelectsChildren : true,
		suppressRowClickSelection : true,
		suppressAggFuncInHeader : true,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200
		},
		onSelectionChanged : onSelectionChangeChild,
		getRowNodeId : function(data) {
			return data.slnoId;
		}
	}; */
	
	var id = "";
	var creq="";
	function onSelectionChangeChild() {
		 var selectedNodes = activityOptions.api.getSelectedNodes();
		 var selectedData = selectedNodes.map(node => node.data);
		 creq= selectedData.map(node => node.slnoId);
		 var empID= selectedData.map(node => node.employeeId);
		 
		var selectedRows = activityOptions.api.getSelectedRows();
		id = "";
		for (var i = 0; i < selectedRows.length; i++) {

			id = id + '"' + selectedRows[i].travelingReqId + '",';
			console.log(selectedRows[i].status);
		}
		id = id.substring(0, id.length - 1);

		var rowCount = 0;
		selectedRows.forEach(function(i) {
			rowCount = rowCount + 1;
		});
		if (rowCount > 0) {
			$('#newchild').attr('disabled', true);
			$('#deleteChild').attr('disabled', false);
		
	}else{
		$('#newchild').attr('disabled', false);
		$('#deleteChild').attr('disabled', true);
		
	}
	}
	
	
	var id = "";
	var treq="";
	function onSelectionChanged() {
		
		 var selectedNodes = gridOptions.api.getSelectedNodes();
		 var selectedData = selectedNodes.map(node => node.data);
		 treq= selectedData.map(node => node.travelingReqId);
		 var empID= selectedData.map(node => node.employeeId);
		 
		var selectedRows = gridOptions.api.getSelectedRows();
		id = "";
		for (var i = 0; i < selectedRows.length; i++) {

			id = id + '"' + selectedRows[i].travelingReqId + '",';
			console.log(selectedRows[i].status);
		}
		id = id.substring(0, id.length - 1);

		var rowCount = 0;
		selectedRows.forEach(function(i) {
			rowCount = rowCount + 1;
		});
	 
		
		 var uid=$("#sessionId").val();
		 var adRole=$("#adRole").val();
		if (rowCount > 0) {
			$('#newBtn').attr('disabled', true);
		
			if(empID!=uid){
				 if (selectedData.map(node => node.status) =="Approved") {
						$('#reqDltBtn').attr("disabled", true);
						$('#approve').attr('disabled', true);
						$('#reject').attr('disabled', true);
			        } else {
			        	$('#approve').attr('disabled', false);
						$('#reject').attr('disabled', false);
						$('#reqDltBtn').attr("disabled", true);
			        }
			
			}else{
				if(adRole=='rol001' || adRole=='rol010'){
					$('#approve').attr('disabled', false);
					$('#reject').attr('disabled', false);
				}else{
					$('#approve').attr('disabled', true);
					$('#reject').attr('disabled', true);
				}
				 if (selectedData.map(node => node.status) =="Pending") {
					$('#reqDltBtn').attr("disabled", false);
				}else{
					$('#reqDltBtn').attr("disabled", true);
				}
			}

		} else {
			$('#reqDltBtn').attr("disabled", true);
			$('#approve').attr('disabled', true);
			$('#reject').attr('disabled', true);
			$('#newBtn').attr('disabled', false);
			//$('#delete').attr('disabled', false);
		}

	}
	// for editing child table of shift 
	function editRow(rowNo) {
		var rowNode = activityOptions.api.getRowNode(rowNo);

		openNav();

		$("#rowEdit").val(rowNo);
		$("#planId").val(rowNode.data.planId);
		$("#serviceName").val(rowNode.data.serviceName);
		$("#date").val(rowNode.data.date);
		$("#time").val(rowNode.data.time);
		$("#description").val(rowNode.data.description);
	//	$("#toPlace").val(rowNode.data.toPlace);
	}

	//editing the employee shift details parent table

	function editTravel() {
         
 
	}

	// delete  

	function deleteOnclick() {
		var selectedRows = gridOptions.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString += selectedRow.travelingReqId;
		});
		if (selectedRowsString) {
			var item = {};
			item.travelingReqId = selectedRowsString;
			$.ajax({
				type : "POST",
				url : "travel-requisition-delete-th-ajax",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(item),
				success : function(response) {
					if (response.message == "Success") {
						cancel();
						$('#delete').modal('hide');
						location.reload();
					} else {
						swal({
							title : response.code,
							text : response.message,
							type : "warning"
						})
					}
				},
				error : function(data) {
					console.log(data)
				}
			})
		} else {
			$("#alert").modal('show');
			document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
		}

	}

	// delete selected record from ag grid
	function deleteDetailsOnclick() {
		$('.modal').hide();
		var selectedRows = activityOptions.api.getSelectedRows();
		activityOptions.api.applyTransaction({
			remove : selectedRows
		});
		//$('#newchild').show();
		//$('#deletechild').hide;
		cancelModalProductBtn();
		
	}

	//for closeing modal box for dlt  product
	function cancelModalProductBtn() {
		$("#deleteModalBtn").removeAttr("disabled");
		$('#deleteDetails').modal('hide');
		//$('#newchild').show();
		//$('#deletechild').hide;
	}

	function deleteFun() {
		$('#delete').modal('show');
	}

	function deleteDetails() {
		$('#deleteDetails').modal('show');
	}

	function cancelModalBtn() {
		$("#deleteModalBtn").removeAttr("disabled");
	}

	// save data in aggrid table sidenav save

	function saveTableData() {
		var item = {};
		var valid = true;
		var data = 1;
		var rowEdit = $("#rowEdit").val();
		if (true) {

			activityOptions.api.forEachNode(function(rowNode, index) {
				if (!rowEdit) {
					data = data + 1;
				}
			});
			item.planId = $("#planId").val();
			item.priority = $("#priority").val();
			item.taskName = $("#taskName").val();
			item.startDate = $("#startDate").val();
			item.endDate = $("#endDate").val();
			item.assignedTo = $("#assignedTo").val();
			item.duration = $("#duration").val();
			item.predecessors = $("#predecessors").val();
			item.notes = $("#notes").val();
			item.slnoId = data;
			var datas = [];
			
			if (item.priority == null || item.priority == "") {
				valid = validationUpdated("Priority is Required", "priority");
				
			}
			if (item.taskName == null || item.taskName == "") {
				valid = validationUpdated("Task Name Required", "taskName");
				
			}
			if (item.startDate == null || item.startDate == "") {
				valid = validationUpdated("Start Date Required", "startDate");
				
			}
			if (item.endDate == null || item.endDate == "") {
				valid = validationUpdated("End Date Required", "endDate");
				
			}
			if (item.assignedTo == null || item.assignedTo == "") {
				valid = validationUpdated("Assigned To Required", "assignedTo");
				
			}
			if (item.duration == null || item.duration == "") {
				valid = validationUpdated("Duration Required", "duration");
				
			}
			if (item.predecessors == null || item.predecessors == "") {
				valid = validationUpdated("Predecessors Required", "predecessors");
				
			}
			if (item.notes == null || item.notes == "") {
				valid = validationUpdated("Notes Required", "notes");
				
			}
 
			if (valid) {
				closeNav();
			if (rowEdit) {
				var rowNode = activityOptions.api.getRowNode(rowEdit);
				rowNode.setData(item);
			} else {

				activityOptions.api.forEachNode(function(rowNode, index) {
					datas.push(rowNode.data);
				});

				datas.push(item)
				activityOptions.api.setRowData(datas);
			}
			} else{
				$('#mySidenav').show();
				
			}
		}
	}
	// setup the grid after the page has finished loading
	document.addEventListener('DOMContentLoaded', function() {

		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		
		
		var gridDiv1 = document.querySelector('#myGrid1');
		new agGrid.Grid(gridDiv1, gridOptions1)
		
		var gridDiv2 = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv2, gridOptionsNew)

		/* var gridDiv = document.querySelector('#activity');
		new agGrid.Grid(gridDiv, activityOptions); */

	});

	function switchPage() {

		$('#basic').simpleTreeTable({
    	    expander: $('#expander'),
    	    collapser: $('#collapser'),
    	    store: 'session',
    	    storeKey: 'simple-tree-table-basic'
    	  });
		
		 $("#myGrid1").hide();
		 $("#totalReq").hide();
		 $("#totalReqs").hide();
		
		 $("#date").val('');
		 $("#time").val('');
		 $("#description").val('');
		 //$("#toPlace").val('');
		 $("#rowEdit").val(null);
		 $("#fromDate").val();
		 $("#toDate").val();
		 $("#employeeId").val();
		 $("#placeName").val();
			$("#purpose").val();
			$("#advanceReq").val();
		var selectedRows = gridOptions.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString += selectedRow.itemId;
		});
		////$('.loader').show();
		//$("body").addClass("overlay");
		////$('.loader').hide();
		//$("body").removeClass("overlay");
		$("#deleteChild").attr('disabled', true);
		$("#newchild").attr('disabled', false);
	}
	
	/* -------------------function for approve button----------------- */
	var empid = "";
	var empname = "";
	function approveRequisition() {
		empid = $("#sessionId").val();
		empname = $("#sessionName").val();
		comment = $("#comment").val();
		var userrole = $("#sessionRole").val();
		
		var roleID = "";
		for(var i = 6; i <= userrole.length; i=i+6){  
			roleID = roleID + '"' +userrole.slice(i-6, i)+ '",';
		}
		roleID = roleID.substring(0, roleID.length - 1); 
		
		$.ajax({
			type : "GET",
			url : "travel-requisition-approve?approveId=" + treq + "&name=" + empid+"&comment="+comment+"&roleid="+roleID,
			async : false,
			success : function(response) {

				if (response.message == "Success") {
					cancel();
					$('#approve').attr('disabled', true);
					$('#reqDltBtn').attr('disabled', true);
					$('#reject').attr('disabled', true);
					$('#newBtn').attr('disabled', false);
					$("#comment").val(null);
				}

			},
			error : function(data) {
			}
		});
	}

	/*-----------------------function for reject button------------------- */
	var rejempid = "";
	var rejempname = "";
	function rejectRequsition() {
		rejempid = $("#sessionId").val();
		rejempname = $("#sessionName").val();
		comment = $("#comment").val();
var userrole = $("#sessionRole").val();
		
		var roleid = "";
		for(var i = 6; i <= userrole.length; i=i+6){  
			roleid = roleid + '"' +userrole.slice(i-6, i)+ '",';
		}
		roleid = roleid.substring(0, roleid.length - 1); 
		$.ajax({
			type : "GET",
			url : "travel-requisition-reject?rejectId=" + treq + "&name=" + rejempid+"&comment="+comment+"&roleid="+roleid,
			async : false,
			success : function(response) {

				if (response.message == "Success") {
					cancel();
					
					$('#approve').attr('disabled', true);
					$('#reqDltBtn').attr('disabled', true);
					$('#reject').attr('disabled', true);
					$('#newBtn').attr('disabled', false);
					$("#comment").val(null);
				}

			},
			error : function(data) {
			}
		});
	}

/* Function for commentModal show */
	function rejectRequistionModal() {

	    $('#commentModal').modal('toggle');
	    $("#approveLeaveSubmitBtn").hide();
	    $("#rejectLeaveSubmitBtn").show();
	}
	function approveRequistionModal() {

	    $('#commentModal').modal('toggle');
	    $("#approveLeaveSubmitBtn").show();
	    $("#rejectLeaveSubmitBtn").hide();

	}
	
	function downloadDetails() { 
		  var dataset = [];
		  gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		        dataset.push(rowNode.data);
		    });
		  gridOptions.api.exportDataAsCsv(dataset);
		}
	
	 function check1(fieldId) {
				var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
				var val=parseInt(tempVal);
				if(val >=100){
					$("#" + fieldId).val(val);
				   }
		      else{
					$("#messageParagraph").text(
					"Please add more than Rs.100 ");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$("#" + fieldId).val(null);
					
				}
			}	
	 function checkAmount1(fieldId) {   
			var myField = document.getElementById("advanceAmount")
			 var reg = /^\d{0,8}(\.\d{0,2})?$/;   
			 if (reg.test(myField.value))
			 {       
				 $("#" + fieldId).val();
				 reg = '';  
			 }else{        
				 $("#" + fieldId).val(null);    
				 }
			 } 
		
		
	 function check(fieldId) {
			var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
			$("#" + fieldId).val(tempVal);
		}
		function setFromToDate() {
			$("#messageParagraph").text("Please choose to date greater than or equal to from date ");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$("#fromDate").val("");
			$("#toDate").val("");
		}
		function dateChange() {
			
			var fromdate = $('#fromDate').val();
			var todate = $('#toDate').val();
			var fd = fromdate.split("-");
			var td = todate.split("-");
			
			if (fromdate != '' && todate != '') {
				if(fd[2]<=td[2]){
					if(fd[1]==td[1]){
						if(fd[0]<=td[0]){
							
						}else{
							setFromToDate();
						}
					}else if(fd[1]<td[1]){
						
					}else{
						setFromToDate();
					}
					
				}else{
					setFromToDate();
				}
			}else{
				
			}
		}
		
	function category(){
		if($("#category").val("Manpower"));{
			$("#subCategory").val("Fabrication");
			$("#subCategory").val("Painting");
			
			
		}
	}
		
	function saveselected(){
		$("#myModal").modal("hide");
		$("#myModalSelected").modal("hide");
		
	}

	function search() {
		$('#deleteid').modal('show');
	}
	function cancelR() {
		$('#deleteid').modal('hide');
	}
	function copy() {
		
		/*  const selectedRows1 = gridOptionsNew.api.getSelectedRows();
		
		console.log(selectedRows1);
		
		var datas = [];	 	
		gridOptionsNew.api.forEachNode(function(rowNode, index) {
			datas.push(rowNode.data);
			
		});
		
		datas.push(selectedRows1)
		
		gridOptions.api.setRowData(datas); */
		
		// Check if the function is already in progress
		if (copy.isRunning) {
			return;
		}

		// Set the function as "in progress"
		copy.isRunning = true;

		const selectedRows1 = gridOptionsNew.api.getSelectedRows();

		const datas1 = [];
		gridOptionsNew.api.forEachNode(function(rowNode, index) {
			datas1.push(rowNode.data);
		});

		gridOptions.api.setRowData(datas1.concat(selectedRows1));

		// Get the current row count
		var rowCount = gridOptions.api.getModel().getRowCount();
		var projectplanId = 1; // Increment the slnoId value

		// Update the slnoId field in gridOptions
		gridOptions.api.forEachNode(function(rowNode) {
			rowNode.setDataValue('projectplanId', projectplanId++);
		});

		// Reset the function state
		copy.isRunning = false;
		
		cancelR();
		  
	}