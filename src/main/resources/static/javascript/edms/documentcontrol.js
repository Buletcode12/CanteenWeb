$(document).ready(function() {
		$("#demo").hide();
		$("#newWord").hide();
		$("#documentUpload").hide();
		$("#linkUpload").hide();
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		var gridDiv1 = document.querySelector('#myGridworkflow');
		new agGrid.Grid(gridDiv1, gridOptions1);
		CKEDITOR.replace('commentck', {
			height : 200,
			removePlugins : 'wsc',
			scayt_autoStartup : true,
			scayt_maxSuggestions : 3,
			autoParagraph : false,
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
				headerName : "Document No",
				field : "docId",
				cellRenderer : function(params) {
					return '<a id="docId" onclick=accessControl1("'
					+params.data.docId
					+ '") href="javascript:void(0)">'
					+ params.data.docId + '</a>';

				}
			}, {
				headerName : "Date",
				field : "date",
			}, {
				headerName : "Version",
				field : "version",
			}, {
				headerName : "Type",
				field : "type",
			}, {
				headerName : 'Name',
				field : "name",
			}, {
				headerName : "Description",
				field : "desc",
			}, {
				headerName : 'Access',
				field : "access",
				cellRenderer : function(params) {
					return '<a onclick=accessControl("' + params.data.access+'") href="javascript:void(0)"><i class="bi bi-pencil-square"></i></a>';
				}
			}, {
				headerName : "Assigned",
				field : "assign",
			}, {
				headerName : "Due Date",
				field : "dueDate",
			}, {
				headerName : "Status",
				field : "status",
			}, ];
	var rowData = [
		  {
		    docId: "DOC123",
		    date: "2023-08-22",
		    version: "1.0",
		    type: "Report",
		    name: "Sample Report",
		    desc: "A sample report",
		    access: "Public",
		    assign: "John Doe",
		    dueDate: "2023-09-15",
		    status: "Pending"
		  },
		  {
		    docId: "DOC456",
		    date: "2023-08-23",
		    version: "2.0",
		    type: "Presentation",
		    name: "Project Presentation",
		    desc: "A project presentation",
		    access: "Private",
		    assign: "Jane Smith",
		    dueDate: "2023-09-30",
		    status: "In Progress"
		  },
		  {
		    docId: "DOC789",
		    date: "2023-08-24",
		    version: "1.5",
		    type: "Document",
		    name: "User Manual",
		    desc: "A user manual document",
		    access: "Public",
		    assign: "Alex Johnson",
		    dueDate: "2023-09-10",
		    status: "Completed"
		  },
		  {
		    docId: "DOC101",
		    date: "2023-08-25",
		    version: "3.2",
		    type: "Report",
		    name: "Quarterly Report",
		    desc: "A quarterly financial report",
		    access: "Private",
		    assign: "Michael Brown",
		    dueDate: "2023-09-20",
		    status: "Pending Review"
		  },
		  {
		    docId: "DOC202",
		    date: "2023-08-26",
		    version: "1.1",
		    type: "Document",
		    name: "Policy Document",
		    desc: "An organizational policy document",
		    access: "Public",
		    assign: "Emily White",
		    dueDate: "2023-09-05",
		    status: "Draft"
		  }
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
		rowSelection : 'single',
		onSelectionChanged : onSelectionChanged
	};
	var columnDefs1 = [ {
		headerName : "User Id",
		field : "userId",
		width : 100,
	}, {
		headerName : "Access",
		field : "access",
		width : 150,
	}, {
		headerName : "Type",
		field : "type",
		width : 150,
	}, ];
const rowData1 = [ {
    userId: 1,
    access: "Read",
    type: "Admin",
  },
  {
    userId: 2,
    access: "Write",
    type: "User",
  },
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
rowSelection : 'single',
onSelectionChanged : onSelectionChanged
};
	
	
	
	
	/ APPLY FOR REQUISITION ENDS /

	// setup the grid after the page has finished loading
	function cancelbtn() {
		$('#reqDltBtn').attr('disabled', true);
		$("#myGrid").show();
		$("#btn1").hide();
		$("#demo").hide();
		$("#powerpoint").hide();
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


	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

	function addId() {

		document.getElementById("mySidenav1").style.cssText = "width: 350px; position: absolute; right:-20px; overflow: hidden; height:auto; top:25px;";
		document.getElementById("upperOne").style.width = "73%";
	}

	function cancel() {
		$("#demo").hide();
		$("#newWord").hide();
	}

	function closeNav1() {

		document.getElementById("mySidenav1").style.width = "0";
		document.getElementById("upperOne").style.width = "100%";
	}
function openSesame(){
	var k = $("#docControl").val();
	//alert(k)
	if( k == "uf"){
		$("#demo").show();
		$("#newWord").hide();
		$("#documentUpload").hide();
		$("#linkUpload").hide();
	}
	else if( k == "nwd"){
		$("#newWord").show();
		$("#demo").hide();
		$("#documentUpload").hide();
		$("#linkUpload").hide();
	}
	else if( k == "ud"){
		$("#documentUpload").show();
		$("#newWord").hide();
		$("#demo").hide();
		$("#linkUpload").hide();
	}
	else if( k == "aal"){
		$("#linkUpload").show();
		$("#documentUpload").hide();
		$("#newWord").hide();
		$("#demo").hide();	
	}
	else{
		$("#demo").hide();	
		$("#powerpoint").hide();
		$("#newWord").hide();
		$("#documentUpload").hide();
		$("#linkUpload").hide();
	}
}

function accessControl(){
	$('#myModal').modal('show');
	
}
function accessControl1(){
	$('#myModal1').modal('show');
	
}
function reminderOpen(){
	$('#myModalReminder').modal('show');
	
}
function accessControlOpen(){
	$('#myModal').modal('show');
	$('#myModal1').modal('hide');
}
function retentionOpen(){
	$('#myModalRetention').modal('show');
}
function lockfileOpen(){
	$('#myModalLock').modal('show');
}
function newVersionOpen(){
	$('#uploadModal').modal('show');
}
function startWorkFlowOpen(){
	$('#myModalStartWorkflow').modal('show');
}
