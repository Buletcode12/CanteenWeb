$(document).ready(function() {
		$("#myGrid").hide();
		$("#myGrid1").hide();
		$("#cancelgrid").hide();
		$("#cancelgrid2").hide();
		$("#addgrid").hide();
		$("#addgrid2").hide();
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		var gridDiv1 = document.querySelector('#myGrid1');
		new agGrid.Grid(gridDiv1, gridOptions1);

		var gridDiv2 = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv2, gridOptions2)

		var gridDiv3 = document.querySelector('#myGrid3');
		new agGrid.Grid(gridDiv3, gridOptions3)
	});

	var columnDefs = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Slno.",
		field : "slno",
		width : 150,
		/* 
		 cellRenderer : function(params) {
		 return '<a instrno="editId" onclick=editId("'
		 + params.data.slno
		 + '") href="javascript:void(0)">'
		 + params.data.slno + '</a>';
		 }, */
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Start Date",
		field : "sdate",
		editable : true,
		width : 150
	}, {
		headerName : "End Date",
		field : "edate",
		width : 150,
		editable : true,
	}, {
		headerName : 'Phase/Task',
		field : "phase",
		editable : true,
		type : 'rightAligned',
		//valueFormatter : currencyFormatter,
		width : 150
	}, {
		headerName : "Assigned To",
		field : "assign",
		editable : true,
		width : 150
	}, {
		headerName : "Quality Needed",
		field : "qty",
		editable : true,
		width : 150
	}, {
		headerName : "Planned Hours",
		field : "planned",
		editable : true,
		width : 150
	}, {
		headerName : 'Notes',
		field : "note",
		editable : true,
		width : 150
	}, {
		headerName : 'Created By',
		field : "createdBy",
	}, {
		headerName : 'Created On',
		field : "createdOn",
	}, {
		headerName : 'Updated By',
		field : "updatedBy",
	}, {
		headerName : 'Updated On',
		field : "updatedOn",
	} ];
	const rowData = [
	// First row
	{
		slno : 1,
		sdate : "2023-06-01",
		edate : "2023-06-05",
		phase : "Phase 1",
		assign : "John",
		qty : "High",
		planned : 8,
		note : "Note 1",
		createdBy : "User1",
		createdOn : "2023-05-30",
		updatedBy : "User2",
		updatedOn : "2023-05-31"
	},
	// Second row
	{
		slno : 2,
		sdate : "2023-06-06",
		edate : "2023-06-10",
		phase : "Phase 2",
		assign : "Jane",
		qty : "Medium",
		planned : 6,
		note : "Note 2",
		createdBy : "User3",
		createdOn : "2023-05-29",
		updatedBy : "User4",
		updatedOn : "2023-05-30"
	},
	// Add more rows as needed...
	];
	var gridOptions = {
		columnDefs : columnDefs,
		rowData : rowData,

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
	var columnDefs1 = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Slno.",
		field : "slno",
		width : 150,
		/* 
		 cellRenderer : function(params) {
		 return '<a instrno="editId" onclick=editId("'
		 + params.data.slno
		 + '") href="javascript:void(0)">'
		 + params.data.slno + '</a>';
		 }, */
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : " Planned Start Date",
		field : "psdate",
		width : 150
	}, {
		headerName : "Planned End Date",
		field : "pedate",
		width : 150,
	}, {
		headerName : " Actual Start Date",
		field : "asdate",
		width : 150
	}, {
		headerName : "Actual End Date",
		field : "aedate",
		width : 150,
	}, {
		headerName : 'Phase/Task',
		field : "phase",
		type : 'rightAligned',
		//valueFormatter : currencyFormatter,
		width : 150
	}, {
		headerName : "Assigned To",
		field : "assign",
		width : 150
	}, {
		headerName : "Quality Needed",
		field : "qtyneed",
		width : 150
	}, {
		headerName : "Quality Received",
		field : "qtyreceive",
		width : 150
	}, {
		headerName : "Planned Hours",
		field : "plannedhr",
		width : 150
	}, {
		headerName : "Actual Hours",
		field : "actualhr",
		width : 150
	}, {
		headerName : "Attachment",
		field : "attachment",
		width : 150
	}, {
		headerName : 'Notes',
		field : "note",
		width : 150
	}, {
		headerName : 'Created By',
		field : "createdBy",
	}, {
		headerName : 'Created On',
		field : "createdOn",
	}, {
		headerName : 'Updated By',
		field : "updatedBy",
	}, {
		headerName : 'Updated On',
		field : "updatedOn",
	} ];
	const rowData1 = [
	// First row
	{
		slno : 1,
		psdate : "2023-06-01",
		pedate : "2023-06-05",
		asdate : "2023-06-02",
		aedate : "2023-06-06",
		phase : "Phase 1",
		assign : "John",
		qtyneed : "High",
		qtyreceive : "Medium",
		plannedhr : 8,
		actualhr : 7,
		attachment : "File 1",
		note : "Note 1",
		createdBy : "User1",
		createdOn : "2023-05-30",
		updatedBy : "User2",
		updatedOn : "2023-05-31"
	},
	// Second row
	{
		slno : 2,
		psdate : "2023-06-06",
		pedate : "2023-06-10",
		asdate : "2023-06-07",
		aedate : "2023-06-12",
		phase : "Phase 2",
		assign : "Jane",
		qtyneed : "Medium",
		qtyreceive : "Low",
		plannedhr : 6,
		actualhr : 5,
		attachment : "File 2",
		note : "Note 2",
		createdBy : "User3",
		createdOn : "2023-05-29",
		updatedBy : "User4",
		updatedOn : "2023-05-30"
	},
	// Add more rows as needed...
	];
	var gridOptions1 = {
		columnDefs : columnDefs1,
		rowData : rowData1,

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
	function OpenNav() {
		$("#drpdowndetails").hide();
		$("#cancelgrid").show();
		$("#cancelgrid2").hide();
		$("#addgrid").show();
		$("#addgrid2").hide();
		$("#cancel").hide();
		$("#saveBudgetPlan").hide();
		$("#download").hide();
		$("#cancelgrid").show();
		$("#basic").hide();
		$("#myGrid").show();
		$("#myGrid1").hide();
	}
	function CloseNav() {
		$("#drpdowndetails").show();
		$("#cancelgrid").hide();
		$("#cancelgrid2").hide();
		$("#addgrid").hide();
		$("#addgrid2").hide();
		$("#basic").show();
		$("#myGrid").hide();
		$("#cancel").show();
		$("#myGrid1").hide();
		$("#saveBudgetPlan").show();
		$("#download").show();
	}
	function OpenNav2() {
		$("#drpdowndetails").hide();
		$("#cancelgrid").hide();
		$("#cancelgrid2").show();
		$("#addgrid").hide();
		$("#addgrid2").show();
		$("#cancel").hide();
		$("#cancel2").hide();
		$("#saveBudgetPlan").hide();
		$("#download").hide();
		$("#basic").hide();
		$("#myGrid").hide();
		$("#myGrid1").show();
	}
	function CloseNav2() {
		$("#drpdowndetails").show();
		$("#cancelgrid").hide();
		$("#cancelgrid2").hide();
		$("#addgrid").hide();
		$("#addgrid2").hide();
		$("#basic").show();
		$("#myGrid").hide();
		$("#myGrid1").hide();
		$("#cancel").show();
		$("#saveBudgetPlan").show();
		$("#download").show();
	}
	function AddNav2() {

		/* $("#activityDateId").val("");
		$("#activityStartTimeId").val("");
		$("#activityEndTimeId").val("");
		$("#activityId").val("");
		$("#activityRemarkId").val(""); */
		document.getElementById("mySidenav2").style.cssText = "width:25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:109px;";
		document.getElementById("myGrid1").style.width = "75%";
		$("#addNotiId").hide();
		$("#cancelNotiId").hide();
		$("#addId").hide();
		$("#cancelId").hide();
	}
	function closeNav3() {
		//$("#mainaddbtn").show();
		document.getElementById("mySidenav2").style.width = "0";
		document.getElementById("myGrid1").style.width = "100%";
		$("#addId").show();
		$("#cancelId").show();
		$("#addNotiId").show();
		$("#cancelNotiId").show();
	}
	function AddNav3() {

		/* $("#activityDateId").val("");
		$("#activityStartTimeId").val("");
		$("#activityEndTimeId").val("");
		$("#activityId").val("");
		$("#activityRemarkId").val(""); */
		document.getElementById("mySidenav3").style.cssText = "width:25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:109px;";
		document.getElementById("myGrid1").style.width = "75%";
		$("#addNotiId").hide();
		$("#cancelNotiId").hide();
		$("#addId").hide();
		$("#cancelId").hide();
	}
	function closeNav4() {
		//$("#mainaddbtn").show();
		document.getElementById("mySidenav3").style.width = "0";
		document.getElementById("myGrid").style.width = "100%";
		$("#cancelgrid").hide();
		$("#cancelgrid2").hide();
		$("#addgrid").hide();
		$("#addgrid2").hide();
		$("#basic").show();
		//$("#myGrid").hide();
		$("#myGrid1").hide();
		$("#cancel").show();
		$("#saveBudgetPlan").show();
		$("#download").show();
	}

	$(document).ready(function() {

		getYearDataList();

		$("#submitYear").click(function() {
			obj = {};

			obj.year = $("#yearVal").val();

			$(".formValidation").remove();
			allPValid = true;
			if ($("#yearVal").val() == null || $("#yearVal").val() == "") {
				allPValid = false;
				validationAll("Year Required", "yearVal");
			}

			if (allPValid) {
				$('.loader').show();
				$("body").addClass("overlay");
				submitYearData(obj);
			}
		})

		$('#basic').simpleTreeTable({
			expander : $('#expander'),
			collapser : $('#collapser'),
			store : 'session',
			storeKey : 'simple-tree-table-basic'
		});

		$("#saveBudgetPlan").click(function() {

			$('.loader').show();
			$("body").addClass("overlay");

			var data = $("#hiddenMonthDataset").val();

			var monthlist = data.split(",");
			var monthset = [];
			for (var i = 0; i < monthlist.length - 1; i++) {
				monthset.push(monthlist[i].toLowerCase())
			}
			//console.log(monthset)
			var dataset = [];
			$("#basic > #tbodyData > tr").each(function() {
				var id = $(this).attr("id");
				console.log(id)
				var obj = {};
				obj['id'] = id;
				obj['year'] = $("#hiddenYear").val();
				for (var i = 0; i < monthset.length; i++) {
					var j = i + 1;
					var monthData = $(this).find(".m" + j + "_" + id).val();
					if (monthData == "" || monthData == null) {
						monthData = 0;
					} else {
						monthData = monthData.replace(",", "");
					}
					obj[monthset[i]] = parseFloat(monthData);
				}
				var total = $(this).find(".total_" + id).val();
				if (total == "" || total == null) {
					total = 0;
				} else {
					total = total.replace(",", "");
				}
				obj['total'] = parseFloat(total);
				dataset.push(obj)
			})
			submitBudgetPlan(dataset);
		})
	})

	function submitBudgetPlan(dataset) {
		$.ajax({
			type : "POST",
			url : "project-execution-save",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(dataset),
			success : function(response) {
				if (response.message == "success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
				}
			},
			error : function(data) {
				console.log(data)
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		});
	}

	function formatNumber(num) {
		return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
	}

	function getBudgetPlanData(id) {
		$("#tbodyData").empty();
		obj = {};
		obj.year = id;
		obj.id = $("#hiddenMonthDataset").val();
		$
				.ajax({
					type : "POST",
					url : "project-execution-get-cc-list",
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(obj),
					success : function(response) {
						if (response.message == "success") {
							console.log(response.body)
							for (var i = 0; i < response.body.length; i++) {
								var row = "";
								if (i == 0) {
									if (response.body[i].count > 0) {
										row = '<tr data-node-id="'+response.body[i].id+'" class="abc" id="'+response.body[i].id+'">'
												+ '<td class="firstnode">'
												+ response.body[i].slNo
												+ ' - '
												+ response.body[i].name
												+ '</td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m1_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m1)
												+ '"><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m2_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m2)
												+ '"><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m3_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m3)
												+ '"><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m4_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m4)
												+ '"><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m5_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m5)
												+ '"><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m6_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m6)
												+ '"><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m7_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m7)
												+ '"><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m8_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m8)
												+ '"><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m9_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m9)
												+ '"><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m10_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m10)
												+ '"><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m11_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m11)
												+ '"><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m12_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m12)
												+ '"><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="total_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].total)
												+ '"></td></tr>';
									} else {
										row = '<tr data-node-id="'+response.body[i].id+'" class="abc" id="'+response.body[i].id+'">'
												+ '<td class="firstnode">'
												+ response.body[i].slNo
												+ ' - '
												+ response.body[i].name
												+ '</td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m1_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m1)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",1)><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m2_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m2)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",2)><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m3_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m3)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",3)><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m4_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m4)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",4)><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m5_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m5)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",5)><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m6_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m6)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",6)><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m7_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m7)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",7)><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m8_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m8)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",8)><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m9_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m9)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",9)><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m10_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m10)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",10)><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m11_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m11)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",11)><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="m12_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m12)
												+ '" onkeyup=setTotalRowWise(event);setParentData("'
												+ response.body[i].id
												+ '","'
												+ response.body[i].parentId
												+ '",12)><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  data-idval="'
												+ response.body[i].id
												+ '" class="total_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].total)
												+ '" disabled></td></tr>';
									}

								} else {
									if (response.body[i].count > 0) {
										row = '<tr data-node-id="'+response.body[i].id+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].id+'">'
												+ '<td class="firstnode">'
												+ response.body[i].slNo
												+ ' - '
												+ response.body[i].name
												+ '</td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m1_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m1)
												+ '"><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m2_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m2)
												+ '"><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m3_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m3)
												+ '"><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m4_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m4)
												+ '"><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m5_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m5)
												+ '"><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m6_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m6)
												+ '"><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m7_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m7)
												+ '"><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m8_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m8)
												+ '"><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m9_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m9)
												+ '"><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m10_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m10)
												+ '"><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m11_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m11)
												+ '"><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="m12_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].m12)
												+ '"><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'
												+ '<td><input type="text"  disabled data-idval="'
												+ response.body[i].id
												+ '" class="total_'
												+ response.body[i].id
												+ '" value="'
												+ formatNumber(response.body[i].total)
												+ '"></td></tr>';
									} else {

										if (response.body[i].chartAccList.length > 0) {
											row = '<tr data-node-id="'+response.body[i].id+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].id+'">'
													+ '<td class="firstnode">'
													+ response.body[i].slNo
													+ ' - '
													+ response.body[i].name
													+ '</td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m1_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m1)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",1)><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m2_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m2)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",2)><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m3_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m3)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",3)><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m4_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m4)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",4)><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m5_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m5)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",5)><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m6_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m6)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",6)><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m7_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m7)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",7)><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m8_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m8)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",8)><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m9_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m9)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",9)><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m10_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m10)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",10)><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m11_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m11)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",11)><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="m12_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m12)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",12)><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'
													+ '<td><input type="text" disabled data-idval="'
													+ response.body[i].id
													+ '" class="total_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].total)
													+ '" disabled></td></tr>';
											var row1 = "";
											for (var j = 0; j < response.body[i].chartAccList.length; j++) {
												row1 = row1
														+ '<tr data-node-id="'+response.body[i].chartAccList[j].id+'" data-node-pid="'+response.body[i].chartAccList[j].parentId+'" class="abc" id="'+response.body[i].chartAccList[j].id+'">'
														+ '<td class="firstnode">'
														+ response.body[i].chartAccList[j].slNo
														+ ' - '
														+ response.body[i].chartAccList[j].name
														+ '</td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m1_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m1)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",1)><input type="hidden" value="'+response.body[i].chartAccList[j].m1+'" id="hdm1_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m2_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m2)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",2)><input type="hidden" value="'+response.body[i].chartAccList[j].m2+'" id="hdm2_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m3_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m3)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",3)><input type="hidden" value="'+response.body[i].chartAccList[j].m3+'" id="hdm3_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m4_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m4)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",4)><input type="hidden" value="'+response.body[i].chartAccList[j].m4+'" id="hdm4_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m5_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m5)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",5)><input type="hidden" value="'+response.body[i].chartAccList[j].m5+'" id="hdm5_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m6_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m6)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",6)><input type="hidden" value="'+response.body[i].chartAccList[j].m6+'" id="hdm6_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m7_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m7)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",7)><input type="hidden" value="'+response.body[i].chartAccList[j].m7+'" id="hdm7_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m8_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m8)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",8)><input type="hidden" value="'+response.body[i].chartAccList[j].m8+'" id="hdm8_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m9_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m9)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",9)><input type="hidden" value="'+response.body[i].chartAccList[j].m9+'" id="hdm9_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m10_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m10)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",10)><input type="hidden" value="'+response.body[i].chartAccList[j].m10+'" id="hdm10_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m11_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m11)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",11)><input type="hidden" value="'+response.body[i].chartAccList[j].m11+'" id="hdm11_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="m12_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].m12)
														+ '" onkeyup=setTotalRowWise(event);setParentData("'
														+ response.body[i].chartAccList[j].id
														+ '","'
														+ response.body[i].chartAccList[j].parentId
														+ '",12)><input type="hidden" value="'+response.body[i].chartAccList[j].m12+'" id="hdm12_'+response.body[i].chartAccList[j].id+'"></td>'
														+ '<td><input type="text"  data-idval="'
														+ response.body[i].chartAccList[j].id
														+ '" class="total_'
														+ response.body[i].chartAccList[j].id
														+ '" value="'
														+ formatNumber(response.body[i].chartAccList[j].total)
														+ '" disabled></td></tr>';
											}
											row = row + row1;
										} else {
											row = '<tr data-node-id="'+response.body[i].id+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].id+'">'
													+ '<td class="firstnode">'
													+ response.body[i].slNo
													+ ' - '
													+ response.body[i].name
													+ '</td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m1_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m1)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",1)><input type="hidden" value="'+response.body[i].m1+'" id="hdm1_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m2_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m2)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",2)><input type="hidden" value="'+response.body[i].m2+'" id="hdm2_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m3_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m3)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",3)><input type="hidden" value="'+response.body[i].m3+'" id="hdm3_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m4_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m4)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",4)><input type="hidden" value="'+response.body[i].m4+'" id="hdm4_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m5_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m5)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",5)><input type="hidden" value="'+response.body[i].m5+'" id="hdm5_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m6_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m6)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",6)><input type="hidden" value="'+response.body[i].m6+'" id="hdm6_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m7_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m7)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",7)><input type="hidden" value="'+response.body[i].m7+'" id="hdm7_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m8_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m8)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",8)><input type="hidden" value="'+response.body[i].m8+'" id="hdm8_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m9_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m9)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",9)><input type="hidden" value="'+response.body[i].m9+'" id="hdm9_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m10_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m10)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",10)><input type="hidden" value="'+response.body[i].m10+'" id="hdm10_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m11_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m11)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",11)><input type="hidden" value="'+response.body[i].m11+'" id="hdm11_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="m12_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].m12)
													+ '" onkeyup=setTotalRowWise(event);setParentData("'
													+ response.body[i].id
													+ '","'
													+ response.body[i].parentId
													+ '",12)><input type="hidden" value="'+response.body[i].m12+'" id="hdm12_'+response.body[i].id+'"></td>'
													+ '<td><input type="text"  data-idval="'
													+ response.body[i].id
													+ '" class="total_'
													+ response.body[i].id
													+ '" value="'
													+ formatNumber(response.body[i].total)
													+ '" disabled></td></tr>';
										}
									}
								}
								$("#tbodyData").append(row);
							}

							$('.loader').hide();
							$("body").removeClass("overlay");

							$('#basic').simpleTreeTable({
								expander : $('#expander'),
								collapser : $('#collapser'),
								store : 'session',
								storeKey : 'simple-tree-table-basic'
							});
						}
					},
					error : function(data) {
						console.log(data)
						$('.loader').hide();
						$("body").removeClass("overlay");
					}
				});
	}

	function setParentData(id, parentId, slno) {

		var m1 = $(".m" + slno + "_" + id).val();
		if (m1 == null || m1 == "") {
			m1 = 0;
		} else {
			m1 = m1.replace(",", "");
		}
		var hidVal = $("#hdm" + slno + "_" + id).val();
		var pVal = $("#tbodyData > #" + parentId).find(
				".m" + slno + "_" + parentId).val();
		if (pVal == null || pVal == "") {
			pVal = 0;
		} else {
			pVal = pVal.replace(",", "");
		}
		pVal = parseFloat(pVal) + parseFloat(m1) - parseFloat(hidVal);
		$("#hdm" + slno + "_" + id).val(m1);
		$("#" + parentId).find(".m" + slno + "_" + parentId).val("");
		$("#" + parentId).find(".m" + slno + "_" + parentId).val(
				formatNumber(pVal));
		var pId = $("#" + parentId).attr("data-node-pid");
		setTotalValue(parentId);
		setParentData(parentId, pId, slno);

	}

	function setTotalValue(dataval) {
		var m1 = $(".m1_" + dataval).val();
		if (m1 == null || m1 == "") {
			m1 = 0;
		} else {
			m1 = m1.replace(",", "");
		}
		var m2 = $(".m2_" + dataval).val();
		if (m2 == null || m2 == "") {
			m2 = 0;
		} else {
			m2 = m2.replace(",", "");
		}
		var m3 = $(".m3_" + dataval).val();
		if (m3 == null || m3 == "") {
			m3 = 0;
		} else {
			m3 = m3.replace(",", "");
		}
		var m4 = $(".m4_" + dataval).val();
		if (m4 == null || m4 == "") {
			m4 = 0;
		} else {
			m4 = m4.replace(",", "");
		}
		var m5 = $(".m5_" + dataval).val();
		if (m5 == null || m5 == "") {
			m5 = 0;
		} else {
			m5 = m5.replace(",", "");
		}
		var m6 = $(".m6_" + dataval).val();
		if (m6 == null || m6 == "") {
			m6 = 0;
		} else {
			m6 = m6.replace(",", "");
		}
		var m7 = $(".m7_" + dataval).val();
		if (m7 == null || m7 == "") {
			m7 = 0;
		} else {
			m7 = m7.replace(",", "");
		}
		var m8 = $(".m8_" + dataval).val();
		if (m8 == null || m8 == "") {
			m8 = 0;
		} else {
			m8 = m8.replace(",", "");
		}
		var m9 = $(".m9_" + dataval).val();
		if (m9 == null || m9 == "") {
			m9 = 0;
		} else {
			m9 = m9.replace(",", "");
		}
		var m10 = $(".m10_" + dataval).val();
		if (m10 == null || m10 == "") {
			m10 = 0;
		} else {
			m10 = m10.replace(",", "");
		}
		var m11 = $(".m11_" + dataval).val();
		if (m11 == null || m11 == "") {
			m11 = 0;
		} else {
			m11 = m11.replace(",", "");
		}
		var m12 = $(".m12_" + dataval).val();
		if (m12 == null || m12 == "") {
			m12 = 0;
		} else {
			m12 = m12.replace(",", "");
		}

		var sum = 0;

		sum = parseFloat(m1) + parseFloat(m2) + parseFloat(m3) + parseFloat(m4)
				+ parseFloat(m5) + parseFloat(m6) + parseFloat(m7)
				+ parseFloat(m8) + parseFloat(m9) + parseFloat(m10)
				+ parseFloat(m11) + parseFloat(m12);

		$(".total_" + dataval).val(formatNumber(sum));
	}

	function setTotalRowWise(e) {
		console.log(e)

		var dataval = e.target.dataset.idval;
		console.log(dataval)

		var m1 = $(".m1_" + dataval).val();
		if (m1 == null || m1 == "") {
			m1 = 0;
		} else {
			m1 = m1.replace(",", "");
		}
		var m2 = $(".m2_" + dataval).val();
		if (m2 == null || m2 == "") {
			m2 = 0;
		} else {
			m2 = m2.replace(",", "");
		}
		var m3 = $(".m3_" + dataval).val();
		if (m3 == null || m3 == "") {
			m3 = 0;
		} else {
			m3 = m3.replace(",", "");
		}
		var m4 = $(".m4_" + dataval).val();
		if (m4 == null || m4 == "") {
			m4 = 0;
		} else {
			m4 = m4.replace(",", "");
		}
		var m5 = $(".m5_" + dataval).val();
		if (m5 == null || m5 == "") {
			m5 = 0;
		} else {
			m5 = m5.replace(",", "");
		}
		var m6 = $(".m6_" + dataval).val();
		if (m6 == null || m6 == "") {
			m6 = 0;
		} else {
			m6 = m6.replace(",", "");
		}
		var m7 = $(".m7_" + dataval).val();
		if (m7 == null || m7 == "") {
			m7 = 0;
		} else {
			m7 = m7.replace(",", "");
		}
		var m8 = $(".m8_" + dataval).val();
		if (m8 == null || m8 == "") {
			m8 = 0;
		} else {
			m8 = m8.replace(",", "");
		}
		var m9 = $(".m9_" + dataval).val();
		if (m9 == null || m9 == "") {
			m9 = 0;
		} else {
			m9 = m9.replace(",", "");
		}
		var m10 = $(".m10_" + dataval).val();
		if (m10 == null || m10 == "") {
			m10 = 0;
		} else {
			m10 = m10.replace(",", "");
		}
		var m11 = $(".m11_" + dataval).val();
		if (m11 == null || m11 == "") {
			m11 = 0;
		} else {
			m11 = m11.replace(",", "");
		}
		var m12 = $(".m12_" + dataval).val();
		if (m12 == null || m12 == "") {
			m12 = 0;
		} else {
			m12 = m12.replace(",", "");
		}
		var sum = 0;

		sum = parseFloat(m1) + parseFloat(m2) + parseFloat(m3) + parseFloat(m4)
				+ parseFloat(m5) + parseFloat(m6) + parseFloat(m7)
				+ parseFloat(m8) + parseFloat(m9) + parseFloat(m10)
				+ parseFloat(m11) + parseFloat(m12);

		$(".total_" + dataval).val(formatNumber(sum));
	}

	function submitYearData(dataset) {
		$
				.ajax({
					type : "POST",
					url : "project-execution-save-year",
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(dataset),
					success : function(response) {
						if (response.message == "success") {
							$('.loader').hide();
							$("body").removeClass("overlay");
							closeNav();

							var div = '<div id="'
									+ response.body.yearId
									+ '" class="databasebox_mail divcls" style="text-align: center !important;" onclick=openDetails("'
									+ response.body.yearId
									+ '","'
									+ response.body.year
									+ '","'
									+ response.body.monthDtls
									+ '")>'
									+ '<a href="javascript:void(0)"><div style="font-size: 34px;">'
									+ response.body.year + '</div> '
									+ '<span style="font-size: 15px;">'
									+ response.body.monthDtls
									+ '</span></a></div>';
							$("#yearDiv").append(div);
						}
					},
					error : function(data) {
						console.log(data)
						$('.loader').hide();
						$("body").removeClass("overlay");
					}
				});
	}

	function getYearDataList() {
		$("#yearDiv").empty();
		$('.loader').show();
		$("body").addClass("overlay");
		$
				.ajax({
					type : "POST",
					url : "project-execution-get-year-list",
					dataType : "json",
					contentType : "application/json",
					data : "A",
					success : function(response) {
						if (response.message == "success") {
							console.log(response.body)

							var id = response.body[0].year;
							var mnth = response.body[0].monthDtls;
							mnth = mnth.replaceAll(" ", "");
							mnth = mnth.split("-");
							setMonthList(mnth[0], mnth[2]);
							for (var i = 0; i < response.body.length; i++) {
								var mnth = response.body[i].monthDtls;
								mnth = mnth.replaceAll(" ", "");
								mnth = mnth.split("-");
								var start = mnth[0];
								var end = mnth[2];
								var div = '<div id="'
										+ response.body[i].yearId
										+ '" class="databasebox_mail divcls" style="text-align: center !important;" onclick=openDetails("'
										+ response.body[i].yearId
										+ '","'
										+ response.body[i].year
										+ '","'
										+ start
										+ '","'
										+ end
										+ '")>'
										+ '<a href="javascript:void(0)"><div style="font-size: 34px;">'
										+ response.body[i].year + '</div> '
										+ '<span style="font-size: 15px;">'
										+ response.body[i].monthDtls
										+ '</span></a></div>';
								$("#yearDiv").append(div);

								if (i == 0) {
									$("#" + response.body[i].yearId)
											.removeClass("databasebox_mail");
									$("#" + response.body[i].yearId).addClass(
											"databasebox_mailactive");
								}
							}

							$("#hiddenYear").val(response.body[0].year);

							getBudgetPlanData(id);

						}
					},
					error : function(data) {
						console.log(data)
					}
				});
	}

	function setMonthList(start, end) {
		var dataset = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Nov", "Dec" ];
		var total = [ "Total" ];
		var a = dataset.indexOf(start);
		var y = dataset.splice(a);
		var newdataset = y.concat(dataset);
		var newdataset = newdataset.concat(total);
		$("#hiddenMonthDataset").val(newdataset);
		/* $("#theadData").empty();
		var head = "<th class='firstnode'></th>";
		$("#theadData").append(head);
		for(var i = 0; i < newdataset.length; i++) {
			var dyhead = '<th class="dyheadCls">'+newdataset[i]+'</th>';
			$("#theadData").append(dyhead);
		} */
	}

	function openDetails(id, year, start, end) {
		setMonthList(start, end);
		$("#hiddenYear").val(year);
		$(".divcls").removeClass("databasebox_mailactive");
		$(".divcls").addClass("databasebox_mail");
		$("#" + id).addClass("databasebox_mailactive");
		$('.loader').show();
		$("body").addClass("overlay");
		getBudgetPlanData(year);
	}

	function openNavAddActivity() {

		/* $("#activityDateId").val("");
		$("#activityStartTimeId").val("");
		$("#activityEndTimeId").val("");
		$("#activityId").val("");
		$("#activityRemarkId").val(""); */
		document.getElementById("mySidenav").style.cssText = "width:25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:109px;";
		document.getElementById("mainId").style.width = "75%";
		$("#addNotiId").hide();
		$("#cancelNotiId").hide();
		$("#addId").hide();
		$("#cancelId").hide();
	}
	function closeNav() {
		//$("#mainaddbtn").show();
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("mainId").style.width = "100%";
		$("#addId").show();
		$("#cancelId").show();
		$("#addNotiId").show();
		$("#cancelNotiId").show();
	}

	var columnDefs2 = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Project Id",
		field : "projectId",
	}, {
		headerName : "Project Name",
		field : "projectName",
	}, {
		headerName : "Creation Date",
		field : "creationDate",
		width : 150,
	}, {
		headerName : "Category",
		field : "category",
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
	const rowData2 = [
			{
				projectId : "RFP001",
				projectName : "Iterarch Building Product PVT ltd",
				creationDate : "15-05-2023",
				category : "Manpower",
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
				category : "Manpower",
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
				category : "Purchase tools and tackles",
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
				category : "Hiring of equipment and machineries",
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

	var gridOptions2 = {
		columnDefs : columnDefs2,
		rowData : rowData2,
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

	var columnDefs3 = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Category",
		field : "categoryId",
		width : 250,
	}, {
		headerName : "Sub Category",
		field : "subCategoryId",
		width : 250,
	}, ];
	const rowData3 = [ {
		categoryId : "Manpower",
		subCategoryId : "Painting",

	}, {
		categoryId : "Manpower",
		subCategoryId : "fabrication",

	}, {
		categoryId : "Manpower",
		subCategoryId : "Erection",

	}, {
		categoryId : "Manpower",
		subCategoryId : "Stud Welding",

	}, {
		categoryId : "Manpower",
		subCategoryId : "Sheeting/Decking",

	}, {
		categoryId : "Manpower",
		subCategoryId : "Civil Work",

	}, {
		categoryId : "Manpower",
		subCategoryId : "Electrical Work",

	}, {
		categoryId : "Manpower",
		subCategoryId : "Dismantling",

	}, {
		categoryId : "Manpower",
		subCategoryId : "Labour Supply",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Farana-F-15",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Farana-F-17",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Farana-F-20",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Farana-F-23",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Crane-250 MT",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Crane-150 MT",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Crane-100 MT ",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Crane-80 MT ",

	}, {
		categoryId : "Hiring of Equipments/Machenaries",
		subCategoryId : "Crane-50 MT",

	}, {
		categoryId : "Purchase tools and tackles",
		subCategoryId : "Bhusan Power & Steel",

	},];
	var gridOptions3 = {
		columnDefs : columnDefs3,
		rowData : rowData3,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 20
		},
		rowSelection : 'multiple',
	};

	function ClickSearch() {
		$('#myModal').modal('show');

	}

	function cancelR() {
		$('#myModal').modal('hide');
	}
	function submitR() {
		$('#myModal').modal('hide');
	}