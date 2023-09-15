//Multiple Document Upload Ends

	$(document).ready(function() {
		$("#demo").hide();
		agGrid.simpleHttpRequest({
			url : 'view-candidate-view-ajax'
		}).then(function(data) {
			var len = data.length;
			$('#totalCandidate').find('span').html(len);
			gridOptions.api.setRowData(data);

		});
	});

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";
		if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
		}
	}

	var columnDefs = [
			{
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
				width : 150
			}, {
				headerName : 'Location',
				field : "location",
				type : 'rightAligned',
				width : 130
			}, {
				headerName : "State",
				field : "state",
				width : 150
			}, {
				headerName : 'Pin',
				field : "pPin",
			}, {
				headerName : 'Project Incharge',
				field : "pIncharge",
			}, {
				headerName : 'Customer Name',
				field : "cName",
			}, {
				headerName : 'Customer Address',
				field : "cAddress",
			}, {
				headerName : 'State',
				field : "cState",
				width : 150,

			}, {
				headerName : 'Pin',
				field : "cPin",
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
				field : "remark",
				width : 150,

			}, {
				headerName : 'status',
				field : "status",
				width : 150,

			} ];
	const rowData = [
	{
		projectId : "RFP001",
		projectName : "Iterarch Building Product PVT ltd",
		creationDate : "2023-05-15",
		location : "Panipat,Haryana",
		state : "Haryana",
		pPin : "132103",
		pIncharge : "Incharge 1",
		cName : "Customer 1",
		cAddress : "GRASIM INDUSTRIES LTD., 48-62, HSIIDC, INDUSTRIAL ESTATE REFINERY ROAD, PANIPAT",
		cState : "Haryana",
		cPin : "Customer Pin 1",
		email : "customer1@example.com",
		mobile : "1234567890",
		remark : "Some remarks",
		status : "Active",
		
	},
	{
		projectId : "RFP002",
		projectName : "SMCC CONSTRUCTION INDIA LIMITED",
		creationDate : "2023-05-15",
		location : "Panipat,Haryana",
		state : "Haryana",
		pPin : "132140",
		pIncharge : "Incharge 1",
		cName : "Customer 1",
		cAddress : "PANIPAT REFINERY",
		cState : "Haryana",
		cPin : "Customer Pin 1",
		email : "customer1@example.com",
		mobile : "1234567890",
		remark : "Some remarks",
		status : "Active",
		
	},
	{
		projectId : "RFP003",
		projectName : "Bhusan Power & Steel",
		creationDate : "2023-05-15",
		location : "BECHARAJI, GUJARAT",
		state : "GUJARAT",
		pPin : "382130",
		pIncharge : "Incharge 1",
		cName : "Customer 1",
		cAddress : "TDS LITHIUM-ION BATTERY GUJARAT PVT LTD.PLOT NO.1,2,3 & 9, BLOCK NO.334 & 335",
		cState : "GUJARAT",
		cPin : "Customer Pin 1",
		email : "customer1@example.com",
		mobile : "1234567890",
		remark : "Some remarks",
		status : "Active",
		
	},
	
	{
		projectId : "RFP004",
		projectName : "Shree Cement",
		creationDate : "2023-05-15",
		location : "AURANGABAD, BIHAR",
		state : "BIHAR",
		pPin : "132103",
		pIncharge : "Incharge 1",
		cName : "Customer 1",
		cAddress : "Address 1",
		cState : "Customer State 1",
		cPin : "Customer Pin 1",
		email : "customer1@example.com",
		mobile : "1234567890",
		remark : "Some remarks",
		status : "Active",
		
	},
	

	];

	function addCandidate() {

		$('#hideTbl').hide();
		$('#buttonDetails').hide();
		$('#myGrid').hide();
		$('#demo').show();
		$("#btn1").show();
		$('#candidateId').val("");

		$("#collapseOne").collapse({
			toggle : false
		}).collapse('show');

	}

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});

	var gridOptions1 = {
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
		onSelectionChanged : onSelectionChanged
	};

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});

	/* APPLY FOR REQUISITION ENDS */

	// setup the grid after the page has finished loading
	$(document).ready(function() {

		$("#cancelFridTrial").attr('disabled', true);
		$("#transportvalue").hide();

		$('#delete').attr('disabled', true);

		$("#reject").attr('disabled', true);
		$("#approve").attr('disabled', true);
		var today = new Date();
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		var yyyy = today.getFullYear();

		//today = dd + '-' + mm + '-' + yyyy;

		today = mm + '-' + dd + '-' + yyyy;

		$('#fromDate').val(today);
		$('#toDate').val(today);
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions1);

		var dateFormat = localStorage.getItem("dateFormat");
		$("#fromDateCalendar").datetimepicker({
			//format : dateFormat,
			format : 'm-d-Y',
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#fromDate').val($(this).val());
		})

		$('#fromDate').blur(function() {
			$("#fromDateCalendar").val($(this).val());
		})

		//     date format TO date

		var dateFormat = localStorage.getItem("dateFormat");
		$("#toDateCalendar").datetimepicker({
			//format : dateFormat,
			format : 'm-d-Y',
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#toDate').val($(this).val());
		})

		$('#toDate').blur(function() {
			$("#toDateCalendar").val($(this).val());
		})

		agGrid.simpleHttpRequest({
			url : "manage-budget-view",
		}).then(function(data) {
			var len = data.length;
			$('#totalReq').find('span').html(len);
			if (len > 0) {
				gridOptions.api.setRowData(data);
			} else {
				gridOptions.api.setRowData();
			}
			console.log("manmayee", data)
		});
	});

	function search() {
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var fromDate = fromDate.split('-');
		var fromDate = fromDate[2] + '-' + fromDate[1] + '-' + fromDate[0];
		var toDate = toDate.split('-');
		var toDate = toDate[2] + '-' + toDate[1] + '-' + toDate[0];
		var vendor = $("#vendorCode").val();

		agGrid.simpleHttpRequest(
				{
					url : "processingcost-setdateview?fromDate=" + fromDate
							+ "&toDate=" + toDate + "&vendor=" + vendor,
				}).then(function(data) {
			var len = data.length;
			$('#totalReq').find('span').html(len);
			if (len > 0) {
				gridOptions.api.setRowData(data);
			} else {
				gridOptions.api.setRowData();
			}
		});

	}

	function cancelbtn() {
		$('#reqDltBtn').attr('disabled', true);
		$("#myGrid").show();
		$("#btn1").hide();
		$("#demo").hide();
		$("#hideTbl").show();
		$("#buttonDetails").show();
	}

	function onSelectionChanged() {
		var selectedRows = gridOptions.api.getSelectedRows();
		var rowCount = 0;
		selectedRows.forEach(function(selectedRow, index) {
			rowCount = rowCount + 1;
		});

		if (rowCount > 0) {
			$('#delete').attr('disabled', false);
			$("#reject").attr('disabled', false);
			$("#approve").attr('disabled', false);
		} else {
			$('#delete').attr('disabled', true);
			$("#reject").attr('disabled', true);
			$("#approve").attr('disabled', true);
		}

	}

	function deleteCustonClick() {
		var selectedRows = gridOptions.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString += selectedRow.budgetId;
		});
		var item = {};
		item.budgetId = selectedRowsString;
		//alert(JSON.stringify(item));
		$.ajax({
			type : "POST",
			url : "manage-budget-delete",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(item),
			success : function(response) {
				if (response.message == "Success") {
					cancelModalBtn();
					location.reload();
					agGrid.simpleHttpRequest({
						url : "manage-budget-view"
					}).then(function(data) {
						gridOptions.api.setRowData(data);
					});

				}
			}

		});
		$('#delete').attr("disabled", true);
	}

	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

	function addId() {

		document.getElementById("mySidenav1").style.cssText = "width: 350px; position: absolute; right:-20px; overflow: hidden; height:auto; top:25px;";

		document.getElementById("upperOne").style.width = "73%";


	}

	function cancel() {
		location.reload();
	}

	function closeNav1() {

		document.getElementById("mySidenav1").style.width = "0";
		document.getElementById("upperOne").style.width = "100%";
	}

/* 	function saveTableData() {
		var item = {};
		var valid = true;
		var data = 1;
		var rowEdit = $("#rowEdit").val();

		gridOptions1.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});

		item.creationID = $("#creationID").val();
		item.locationID = $("#locationID").val();
		item.stateID = $("#stateID").val();
		item.pinID = $("#pinID").val();
		item.project = $("#project").val();
		item.cname = $("#cname").val();
		item.caddress = $("#caddress").val();
		item.cstate = $("#cstate").val();
		item.cpin = $("#cpin").val();
		item.email = $("#email").val();
		item.mobile = $("#mobile").val();
		item.remarks = $("#remarks").val();
		item.status = $("#status").val();
		item.projectId = data;
		var datas = [];

		if (valid) {
			closeNav1();
			if (rowEdit) {
				var rowNode = gridOptions1.api.getRowNode(rowEdit);
				rowNode.setData(item);
			} else {
				gridOptions1.api.forEachNode(function(rowNode, index) {
					datas.push(rowNode.data);
				});

				datas.push(item);
				gridOptions1.api.setRowData(datas);
			}

		} else {
			$('#mySidenav1').show();
		}
	}
	 */
	
	
function saveTableData() {		
		$("#demo").hide();
		 $("#myGrid").show();
		 $("#addId").show();
		 $("#searchRowDiv").show();
		var item = {};
		var data=1;
		var rowEdit = $("#rowEdit").val();
		gridOptions1.api.forEachNode(function (rowNode, index) {
            if (!rowEdit) {
                data = data + 1;
            }
        });
			item.projectName = $("#projectname").val();
			item.creationDate  = $("#creationID").val();	
			item.location  = $("#locationID").val();
			item.state  = $("#stateID").val();
			item.pPin  = $("#pinID").val();
			item.pIncharge  = $("#project").val();
			item.cName  = $("#cname").val();
			item.cAddress  = $("#caddress").val();
			item.cState  = $("#cstate").val();
			item.cPin  = $("#cpin").val();
			item.email  = $("#email").val();
			item.mobile  = $("#mobile").val();
			item.remark  = $("#remarks").val();
			item.status  = $("#status").val();
			
			item.projectId = data;
			var datas = [];	 		
			if (rowEdit) {
				var rowNode = gridOptions1.api.getRowNode(rowEdit);
				rowNode.setData(item);
			} else {

				gridOptions1.api.forEachNode(function(rowNode, index) {
					datas.push(rowNode.data);
				});

				datas.push(item)
				gridOptions1.api.setRowData(datas);
			}
	}	
	
	