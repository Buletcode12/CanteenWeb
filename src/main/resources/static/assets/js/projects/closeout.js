//Multiple Document Upload Ends

		$(document)
			.ready(
					function() {
						
						
						
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
						new agGrid.Grid(gridDiv, gridOptions);
						var gridDivNew = document.querySelector('#myGridNew');
						new agGrid.Grid(gridDivNew, gridOptionsNew);

					       var gridDiv4 = document.querySelector('#myGrid2');
							  new agGrid.Grid(gridDiv4, gridOptions4);
						
						
						var dateFormat = localStorage.getItem("dateFormat");
						$("#DateCalendar").datetimepicker({
							//format : dateFormat,
							format : 'm-d-Y',
							closeOnDateSelect : true,
							timepicker : false,
						}).on("change", function() {
							$('#cName').val($(this).val());
						})

						$('#cName').blur(function() {
							$("#DateCalendar").val($(this).val());
						})

						//     date format TO date

						var dateFormat = localStorage.getItem("dateFormat");
						$("#DateCalendar1").datetimepicker({
							//format : dateFormat,
							format : 'm-d-Y',
							closeOnDateSelect : true,
							timepicker : false,
						}).on("change", function() {
							$('#creationDate').val($(this).val());
						})

						$('#creationDate').blur(function() {
							$("#DateCalendar1").val($(this).val());
						})				
       $("#demo").hide();
       $("#main").hide();
       $("#cancel").hide();
		
		 
	});

		var columnDefs4 = [{
		    headerName: "WIN or Issue",
		    field: "activity", 
		    width:150,
		}, {
		    headerName: "Describe what happened",
		    field: "planned",
		    width:300,
		    type: "rightAligned"
		},{
		    headerName:"What was the impact?",
		    field: "forecast",
		    width:310,
		    type: "rightAligned"
		},{
		    headerName: "How Does This Change Future Projects?",
		    field: "variance",
		    width:310,
		    type: "rightAligned",
		},{
		    headerName: "Action Items",
		    field: "action",
		    width:310,
		    type: "rightAligned"
		}];


		const rowData2 = [
			{
				activity: "ISSUE",
				planned: "Project was out sick for 2 weeks and there was no replacement,so we had to wait for her ",
				forecast: "The project was delayed 4 weeks and the client was upset.A $25000 credit was issued to the client",
				variance: "We need to have redundancy in the IT department to ensure there is always someone available",
				action: "Chat with CEO and HR about hiring additional IT help"
			},
			{
				activity: "WIN",
				planned: "The client was so happy with the final presentation that she offered us a 2 year exclusive contract!",
				forecast: "This contract is gowing to double our revenue growth over the next 2 years",
				variance: "The new style for in-person client presentation should be used on more projects,when possible",
				action: "Share the new client presentation format with other teams"
			} ,
			{
				activity: "WIN",
				planned: "The client was so happy with the final presentation that she offered us a 2 year exclusive contract!",
				forecast: "This contract is gowing to double our revenue growth over the next 2 years",
				variance: "The new style for in-person client presentation should be used on more projects,when possible",
				action: "Share the new client presentation format with other teams"
			}
		];


		var gridOptions4 = {
			columnDefs : columnDefs4,
			rowData: rowData2,
			defaultColDef : {
				sortable : true,
				filter : true,
				resizable : true,
				width : 200,
				height : 20
			},
			//rowSelection : 'single',
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
			},{
			    headerName: "WIN or Issue",
			    field: "activity", 
			    width:150,
			}, {
			    headerName: "Describe what happend",
			    field: "planned",
			    width:300,
			    type: "rightAligned"
			},{
			    headerName:"What was the impact?",
			    field: "forecast",
			    width:310,
			    type: "rightAligned"
			},{
			    headerName: "How Does This Change Future Projects?",
			    field: "variance",
			    width:310,
			    type: "rightAligned",
			},{
			    headerName: "Action Items",
			    field: "action",
			    width:310,
			    type: "rightAligned"
			}
		];
		const rowdataNew=[ 
			{
				activity: "ISSUE",
				planned: "Project was out sick for 2 weeks and there was no replacement,so we had to wait for her ",
				forecast: "The project was delayed 4 weeks and the client was upset.A $25000 credit was issued to the client",
				variance: "We need to have redundancy in the IT department to ensure there is always someone available",
				action: "Chat with CEO and HR about hiring additional IT help"
			},
			{
				activity: "WIN",
				planned: "The client was so happy with the final presentation that she offered us a 2 year exclusive contract!",
				forecast: "This contract is gowing to double our revenue growth over the next 2 years",
				variance: "The new style for in-person client presentation should be used on more projects,when possible",
				action: "Share the new client presentation format with other teams"
			}  ,
			{
				activity: "WIN",
				planned: "The client was so happy with the final presentation that she offered us a 2 year exclusive contract!",
				forecast: "This contract is gowing to double our revenue growth over the next 2 years",
				variance: "The new style for in-person client presentation should be used on more projects,when possible",
				action: "Share the new client presentation format with other teams"
			}
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

var columnDefs = [
			{
				headerCheckboxSelection : true,
				headerCheckboxSelectionFilteredOnly : true,
				checkboxSelection : true,
				width : 10,
				sortable : false,
				filter : false,
				resizable : true
			},
				{
					headerName : "Project ID",
					field : "projectID",
					
					width : 150,
					//onCellClicked: editId,
					/* cellRenderer : function(params){
	                    return '<a (click)="editId()">1</a>'
	                }, */ 
					cellStyle : {
						textAlign : 'center'
					}
				},/*  {
					headerName : "Project ID",
					field : "projectID",

				}, */ {
					headerName : "Project Name",
					field : "projectName",
				}, {
					headerName : 'Location',
					field : "location",
					type : 'rightAligned',
					//valueFormatter : currencyFormatter,
					width : 130
				
				},{
					headerName : 'Customer',
					field : "pIncharge",
				}, {
					headerName : 'Project Planned Date',
					field : "cName",
				}, {
					headerName : "Project Completion Date",
					field : "creationDate",
					width : 450
				}
				  ];
const rowData = [
	  // Existing row data...

	  {
	    budgetId: "",
	    budgetCategory: "Category 1",
	    item: "Item 1",
	    unit: "Unit 1",
	    qty: 10,
	    rate: 50,
	    nos: 5,
	    mob: "Mob 1",
	    amount: 500,
	    projected: 1000,
	    actual: 800,
	    createdBy: "John",
	    createdOn: "2023-05-01",
	    updatedBy: "Jane",
	    updatedOn: "2023-05-10",
	    projectID: "1",
	    projectName: "ABC Project-Odisha,India",
	    creationDate: "2023-05-15",
	    location: "Patakura,Kendrapada,Odisha",
	    state: "Odisha",
	    pPin: "132103",
	    pIncharge: "Govt Of Odisha,Works Department",
	    cName: "In Progress",
	    cAddress: "Govt of Odisha,Works",
	    cState: "Customer State 1",
	    cPin: "Customer Pin 1",
	    email: "customer1@example.com",
	    mobile: "1234567890",
	    remark: "Some remarks",
	    status: "Active",
	  },
	  // Add more data as needed
	];

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});

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
		onSelectionChanged : onSelectionChanged
	};
	
	
$("#budgetId").click(function() {
	$("#a").css("display", "block");
	$("#b").css("display", "block");
});


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
	function editId() {
         $("#cancel").hide();
		 $("#main").show();
		 $("#myGrid").hide();
		 $("#cancel").show();
		 $("#newPage").hide();
		 	
		 
		
	}

	function cancel() {
		location.reload();
	}


function saveTableData() {		
	 $("#myGrid").show();
	 $("#addId").show();
	 $("#searchRowDiv").show();
	var item = {};
	var data=1;
	var rowEdit = $("#rowEdit").val();
	gridOptions.api.forEachNode(function (rowNode, index) {
		
        if (!rowEdit) {
            data = data + 1;
        }
    });
	
	item.projectName = $("#projectName").val();
	item.location = $("#location").val();
	item.pIncharge = $("#pIncharge").val();
	item.cName = $("#cName").val();
	item.creationDate = $("#creationDate").val();
	item.projectId = data;
		var datas = [];
		closeNav1();
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
}
	function newPage() {

		document.getElementById("mySidenav1").style.cssText = "width: 350px; position: absolute; right:-20px; overflow: hidden; height:auto; top:25px;";

		document.getElementById("upperline").style.width = "73%";


	}

	function cancel() {
		location.reload();
	}

	
	function openNav() {

	    document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto;";

	    document.getElementById("yeshh1").style.width = "75%";
	}

	function closeNav() {

	    document.getElementById("mySidenav").style.width = "0";
	    document.getElementById("yeshh1").style.width = "100%";
	}
	
	function closeNav1() {

	    document.getElementById("mySidenav1").style.width = "0";
	    document.getElementById("upperline").style.width = "100%";
	}
	
	function savechild() {
		closeNav();
		var item = {};
		
			
			
		item.activity = $("#activity").val();
		item.planned = $("#location").val();
		item.forecast = $("#pIncharge").val();
		item.variance = $("#cName").val();
		item.action = $("#creationDate").val();
			
			//item.slnoId = data;
			//item.projectId = data;
			var datas = [];
			
			datas.push(item)
			gridOptions4.api.setRowData(datas);
		

	}
	
	/* 
	function savechild(){
		$("#activity").show();
		var item = {};
		var data=1;
		var rowEdit = $("#rowEdit").val();
		gridOptions4.api.forEachNode(function (rowNode, index) {
			
	        if (!rowEdit) {
	            data = data + 1;
	        }
	    });

		item.activity = $("#activity").val();
		item.planned = $("#location").val();
		item.forecast = $("#pIncharge").val();
		item.variance = $("#cName").val();
		item.action = $("#creationDate").val();
			var datas = [];
			closeNav();
			if (rowEdit) {
				var rowNode = gridOptions4.api.getRowNode(rowEdit);
				rowNode.setData(item);
				
			} else {

				gridOptions4.api.forEachNode(function(rowNode, index) {
					datas.push(rowNode.data);
				});

				datas.push(item)
				gridOptions4.api.setRowData(datas);
			}
			
			
	} */
	
	function search() {
		$('#deleteid').modal('show');
	}
	function cancelR() {
		$('#deleteid').modal('hide');
	}
	function copy() {
		
		 const selectedRows1 = gridOptionsNew.api.getSelectedRows();
		 
		console.log(selectedRows1);
		
		var datas = [];	 	
		gridOptionsNew.api.forEachNode(function(rowNode, index) {
			datas.push(rowNode.data);
			
		});
		
		datas.push(selectedRows1)
		
		gridOptions4.api.setRowData(datas);
		
		cancelR();
		  
	}
	