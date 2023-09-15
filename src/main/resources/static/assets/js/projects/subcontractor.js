//setup the grid after the page has finished loading
	$(document).ready(function() {

		var gridDiv2 = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv2, gridOptions2);

		var gridDiv3 = document.querySelector('#myGrid3');
		new agGrid.Grid(gridDiv3, gridOptions3);
		$("#myGrid2").show();

		$('#delete').attr("disabled", true);
	});

	/* 
	 $(document).ready(function() {
	
	 gridOptions2.api.setRowData();
	 gridOptions3.api.setRowData()
	
	 }) */
	function editId(data) {
		$("#demo").hide();
		$("#myGrid2").hide();
		$("#delete").hide();
		$("#yeshh1").hide();
		$("#rfqid").hide();
		$("#myGrid3").show();
		$("#yeshh").show();
		$("#child").show();
	}

	function openNav() {

		document.getElementById("mySidenav1").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:0px;";

		document.getElementById("yeshh1").style.width = "75%";
	}

	function closeNav() {

		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("yeshh").style.width = "100%";
	}

	function openNav1() {

		document.getElementById("mySidenav").style.cssText = "width: 350px; position: absolute; right:-20px; overflow: hidden; height:auto; top:200px;";

		document.getElementById("yeshh").style.width = "73%";
	}
	function cancel() {
		$("#myGrid2").show();
		$("#myGrid3").hide();
		$("#yeshh").hide();
		$("#yeshh1").show();
		$("#delete").show();

		document.getElementById("main").style.width = "100%";
	}
	function closeNav1() {

		document.getElementById("mySidenav1").style.width = "0";
		document.getElementById("yeshh1").style.width = "100%";
	}

	var columnDefs2 = [ {
		headerName : "Contractor ID",
		field : "contractorId",
		cellRenderer : function(params) {
			return '<span style="color: blue;">' + params.value + '</span>';
		},
	}, {
		headerName : "Contractor Name",
		field : "contractorName",
	}, {
		headerName : "Email",
		field : "email",
	}, {
		headerName : "Phone",
		field : "phone",
	}, {
		headerName : "Contractor Type",
		field : "contractorType",
	}, {
		headerName : "GSTIN",
		field : "gstIn",
	}, {
		headerName : "PAN",
		field : "panId",
	}, {
		headerName : "Address1",
		field : "address1",
	}, {
		headerName : "Address2",
		field : "address2",
	}, {
		headerName : "City",
		field : "city",
	}, {
		headerName : "State",
		field : "state",
	}, {
		headerName : "PIN",
		field : "pinId",
	}, {
		headerName : "Status",
		field : "status",
	} ];

	const rowData = [ {
		contractorId : 1,
		contractorName : "John Doe",
		email : "johndoe@example.com",
		phone : "1234567890",
		contractorType : "Type A",
		gstIn : "GST123",
		panId : "PAN123",
		address1 : "123 Street",
		address2 : "Apt 4B",
		city : "Cityville",
		state : "Stateville",
		pinId : "12345",
		status : "Active"
	},
	// Add more objects here for additional rows
	];
	var gridOptions2 = {
		columnDefs : columnDefs2,
		rowData : rowData,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 180,
			height : 10
		},
	//rowSelection: 'single',
	//rowMultiSelectWithClick: true,
	//onSelectionChanged: onChangedCandidateScheduleEdit

	};
	var columnDefs3 = [ {
		headerName : "Project ID",
		field : "projectId",
	}, {
		headerName : "Project Name",
		field : "projectName",
	}, {
		headerName : "Task/Activity",
		field : "task"
	}, {
		headerName : "Scope of the Work",
		field : "scopeoftheWork"
	}, {
		headerName : "Duration of the Work",
		field : "durationoftheWork"
	}, {
		headerName : "License Verified",
		field : "licenseVerified"
	}, {
		headerName : "Statement of Intent Recieved",
		field : "statementofintentRecieved"
	}, {
		headerName : "Request to Sublet Recieved",
		field : "requesttosubletRecieved"
	}, {
		headerName : "Schedule of Work Recieved",
		field : "scheduleofworkRecieved"
	}, {
		headerName : "Drawings Provided",
		field : "drawingsProvided"
	}, {
		headerName : "Punchlist Complete",
		field : "punchlistComplete"
	}, {
		headerName : "Date of Notice to Proceed Issued",
		field : "dateofnoticetoproceedIssued"
	}, {
		headerName : "Date Contract Executed",
		field : "datecontractExecuted"
	}, {
		headerName : "Date Insurance Recieved",
		field : "dateinsuranceRecieved"
	}, {
		headerName : "Other Required Documentation",
		field : "otherrequiredDocumentation"
	} ];

	const rowData1 = [ {
		projectId : 1,
		projectName : "Interarch Building Products",
		task : "Fabrication",
		scopeoftheWork : "Fabrication",
		durationoftheWork : "2 week",
		licenseVerified : "Yes",
		statementofintentRecieved : "Yes",
		requesttosubletRecieved : "No",
		scheduleofworkRecieved : "Yes",
		drawingsProvided : "Yes",
		punchlistComplete : "No",
		dateofnoticetoproceedIssued : "2023-01-01",
		datecontractExecuted : "2023-01-05",
		dateinsuranceRecieved : "2023-01-10",
		otherrequiredDocumentation : "Other Doc",
	},
	// Add m
	{
		projectId : 2,
		projectName : "Indian Oil Corporation",
		task : "Painting",
		scopeoftheWork : "Fabrication",
		durationoftheWork : "5 week",
		licenseVerified : "Yes",
		statementofintentRecieved : "Yes",
		requesttosubletRecieved : "No",
		scheduleofworkRecieved : "Yes",
		drawingsProvided : "Yes",
		punchlistComplete : "No",
		dateofnoticetoproceedIssued : "2023-01-01",
		datecontractExecuted : "2023-01-05",
		dateinsuranceRecieved : "2023-01-10",
		otherrequiredDocumentation : "Other Doc",
	}, {
		projectId : 3,
		projectName : "Interarch Building Products",
		task : "Painting",
		scopeoftheWork : "Painting",
		durationoftheWork : "5 week",
		licenseVerified : "Yes",
		statementofintentRecieved : "Yes",
		requesttosubletRecieved : "No",
		scheduleofworkRecieved : "Yes",
		drawingsProvided : "Yes",
		punchlistComplete : "No",
		dateofnoticetoproceedIssued : "2023-01-01",
		datecontractExecuted : "2023-01-05",
		dateinsuranceRecieved : "2023-01-10",
		otherrequiredDocumentation : "Other Doc",
	}, {
		projectId : 4,
		projectName : "Bhusan Power & Steel",
		task : "Painting",
		scopeoftheWork : "Painting",
		durationoftheWork : "5 week",
		licenseVerified : "Yes",
		statementofintentRecieved : "Yes",
		requesttosubletRecieved : "No",
		scheduleofworkRecieved : "Yes",
		drawingsProvided : "Yes",
		punchlistComplete : "No",
		dateofnoticetoproceedIssued : "2023-01-01",
		datecontractExecuted : "2023-01-05",
		dateinsuranceRecieved : "2023-01-10",
		otherrequiredDocumentation : "Other Doc",
	}, {
		projectId : 5,
		projectName : "Shree Cement",
		task : "Painting",
		scopeoftheWork : "Painting",
		durationoftheWork : "5 week",
		licenseVerified : "Yes",
		statementofintentRecieved : "Yes",
		requesttosubletRecieved : "No",
		scheduleofworkRecieved : "Yes",
		drawingsProvided : "Yes",
		punchlistComplete : "No",
		dateofnoticetoproceedIssued : "2023-01-01",
		datecontractExecuted : "2023-01-05",
		dateinsuranceRecieved : "2023-01-10",
		otherrequiredDocumentation : "Other Doc",
	},

	];
	var gridOptions3 = {
		columnDefs : columnDefs3,
		rowData : rowData1,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 242,
			height : 10
		},
	/* 	rowSelection : 'single',
		onSelectionChanged : onChangedCandidateFeedbackEdit */

	};

	function saveTableData() {
		closeNav1();
		var item = {};
		var valid = true;
		var data = 1;
		var rowEdit = $("#rowEdit").val();
		if (true) {

			gridOptions3.api.forEachNode(function(rowNode, index) {
				if (!rowEdit) {
					data = data + 1;
				}
			});
			item.projectName = $("#projectName").val();
			item.task = $("#task").val();
			item.scopeoftheWork = $("#scopeoftheWork").val();
			item.durationoftheWork = $("#durationoftheWork").val();
			item.licenseVerified = $("#licenseVerified").val();
			item.statementofintentRecieved = $("#statementofintentRecieved")
					.val();
			item.requesttosubletRecieved = $("#requesttosubletRecieved").val();
			item.scheduleofworkRecieved = $("#scheduleofworkRecieved").val();
			item.drawingsProvided = $("#drawingsProvided").val();
			item.punchlistComplete = $("#punchlistComplete").val();
			item.dateofnoticetoproceedIssued = $("#dateofnoticetoproceedIssued")
					.val();
			item.datecontractExecuted = $("#datecontractExecuted").val();
			item.dateinsuranceRecieved = $("#dateinsuranceRecieved").val();
			item.otherrequiredDocumentation = $("#otherrequiredDocumentation")
					.val();
			item.projectId = data;
			var datas = [];

			if (valid) {
				closeNav1();
				if (rowEdit) {
					var rowNode = gridOptions3.api.getRowNode(rowEdit);
					rowNode.setData(item);
				} else {

					gridOptions3.api.forEachNode(function(rowNode, index) {
						datas.push(rowNode.data);
					});

					datas.push(item)
					gridOptions3.api.setRowData(datas);
				}
			} else {
				$('#mySidenav').show();

			}
		}
	}
	function saveTableData1() {
		closeNav();
		var item = {};
		var valid = true;
		var data = 1;
		var rowEdit = $("#rowEdit").val();
		if (true) {

			gridOptions2.api.forEachNode(function(rowNode, index) {
				if (!rowEdit) {
					data = data + 1;
				}
			});
			item.contractorName = $("#contractorName").val();
			item.email = $("#email").val();
			item.phone = $("#phone").val();
			item.contractorType = $("#contractorType").val();
			item.gstIn = $("#gstIn").val();
			item.panId = $("#panId").val();
			item.address1 = $("#address1").val();
			item.address2 = $("#address2").val();
			item.city = $("#city").val();
			item.state = $("#state").val();
			item.pinId = $("#pinId").val();
			item.status = $("#status").val();
			item.contractorId = data;
			var datas = [];

			if (valid) {
				closeNav1();
				if (rowEdit) {
					var rowNode = gridOptions2.api.getRowNode(rowEdit);
					rowNode.setData(item);
				} else {

					gridOptions2.api.forEachNode(function(rowNode, index) {
						datas.push(rowNode.data);
					});

					datas.push(item)
					gridOptions2.api.setRowData(datas);
				}
			} else {
				$('#mySidenav1').show();

			}
		}
	}