//import * as validationFile from "../edms/variableNvalidation.js";
//import * as workspaceBtn from "../edms/workSpaceBtn.js";
/*workspaceBtn.newBtn.addEventListener('click', newPlans);
workspaceBtn.cancel.addEventListener('click', cancel);
workspaceBtn.reqDltBtn.addEventListener('click', deleteFun);
workspaceBtn.deleteMdl.addEventListener('click', deleteOnclick);*/
function newPlans() {
	hideShowMultipleElement(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], false);
	hideShowMultipleElement(["#demo"], true);
	//validationFile.hideShowMultipleElements(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], false);
	closeNav();
	//validationFile.hideShowMultipleElements(["#demo"], true);
	$("#itemname").val('');
	$("#description").val('');
	$("#rowEdit").val('');
	$("#deleteChild").attr('disabled', true);
	$("#newchild").attr('disabled', false);
}

$(document).ready(function() {
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#activity');
	new agGrid.Grid(gridDiv, activityOptions);

	agGrid.simpleHttpRequest({
		url: 'work-space-view'
	}).then(function(data) {
		console.log("dtatatattatat",data);
		//const jsonString = data.body.map(item => item.replace(/'/g, '"'));
		// Convert to JSON array
		//const jsonArray = jsonString.map(item => JSON.parse(item));
		/*var len = jsonArray.length;
		$('#totalReq').find('span').html(len);
		gridOptions.api.setRowData(jsonArray);*/
		var l1=[];
		
		for (i=0; i<data.body.length; i++){
			if(data.body[i] != ""){
				l1.push(JSON.parse(data.body[i]));
			}
		}
		console.log(l1);
		
		
		
		
		var len = l1.length;
		$('#totalReq').find('span').html(len);
		gridOptions.api.setRowData(l1);
	});
	activityOptions.api.setRowData();
	$('#reqDltBtn').attr('disabled', true);
	$('#deleteChild').attr('disabled', true);
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

	$('.collapse').on('hide.bs.collapse', function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});




});

/* -------------------search bar for mygrid------------------------ */

/*function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
}*/

function cancelBars() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'WorkSpaceId',
		width: 150,
		field: "workspaceId",


	}, {
		headerName: 'Folder Name',
		width: 200,
		field: "parentFolderName",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Type',
		width: 200,
		field: "accessType",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Created By',
		field: "owner",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Created Date',
		field: "createdOn",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Description',
		field: "description",
		width: 400,
	}, {
		headerName: 'Access',
		field: "access",
		width: 200,
	}, {
		headerName: 'Owner',
		field: "owner",
		width: 200,
	}, {
		headerName: 'Due Date',
		field: "due_date",
		width: 400,
	}, {
		headerName: 'Default Tags',
		field: "defaultTags",
		width: 400,
	}];

// let the grid know which columns and what data to use
var rowdata = [];
var gridOptions = {
	columnDefs: columnDefs,
	rowData: rowdata,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	onSelectionChanged: onSelectionChanged,
	onRowClicked: function(params) {
		// This function will be called when a row is clicked
		const clickedRowData = params.data; // Get the data of the clicked row
		console.log('Row Clicked:', clickedRowData);
		editWorkspace(clickedRowData.workspaceId);

	},
};
//function for row select parents
var id = "";

function onSelectionChanged() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	id = selectedData.map(node => node.qcId);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newBtn').attr('disabled', true);
		$('#reqDltBtn').attr("disabled", false);
	} else {
		$('#reqDltBtn').attr("disabled", true);
		$('#newBtn').attr('disabled', false);
	}
}
// for activity table
var activityDefs = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30
	}, {
		headerName: "SL No.",
		field: "siNo",
		width: 400,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.partId) {
				return '<a onclick=editProduct("' + params.data.siNo
					+ '") href="javascript:void(0)">'
					+ params.data.siNo + '</a>';
			} else {
				return '<a onclick=editProduct("' + params.data.siNo
					+ '") href="javascript:void(0)">'
					+ params.data.siNo + '</a>';
			}
		}
	},
	{
		headerName: "Activity",
		field: "activityName",
		width: 400,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Activity",
		field: "activityId",
		width: 400,
		hide: true,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Status",
		field: "statusName",
		width: 400,
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "User/Group",
		field: "groupName",
		width: 450,
		cellStyle: {
			textAlign: 'center'
		},
	},];


// let the grid know which columns and what data to use product table
var activityOptions = {
	columnDefs: activityDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200
	},
	onSelectionChanged: onSelectionChangeChild,
	getRowNodeId: function(data) {
		return data.activityId;
	}
};
//function for row select parents
var childid = "";
function onSelectionChangeChild() {
	var selectedNodes = activityOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	childid = selectedData.map(node => node.slnoId);
	var selectedRows = activityOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newchild').attr('disabled', true);
		$('#deleteChild').attr('disabled', false);
	} else {
		$('#newchild').attr('disabled', false);
		$('#deleteChild').attr('disabled', true);
	}
}

function editWorkspace(id) {
	$
		.ajax({
			type: "GET",
			url: "work-space-edit?id=" + id,
			success: function(response) {
				if (response) {
					newPlans();
					var jsonData = JSON.parse(response.body);
					//var allData = jsonData.workSpace;
					
					$("#workspaceId").val(jsonData.workspaceId);
					$("#parentFolderName").val(jsonData.parentFolderName);
					$("#newFolderName").val(jsonData.newFolderName);
					$("#accessType").val(jsonData.accessType);
					$("#owner").val(jsonData.owner);
					$("#defaultTags").val(jsonData.defaultTags);
					$("#description").val(jsonData.description);
					activityOptions.api.setRowData(jsonData.accessControl);
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
}

//parents new plan

//function for cancel
function cancel() {
	hideShowMultipleElement(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], true);
	hideShowMultipleElement(["#demo"], false);

	//validationFile.hideShowMultipleElements(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], true);
	closeNav();
	//validationFile.hideShowMultipleElements(["#demo"], false);


	$(".formValidation").remove();
	$("#reqDltBtn").attr('disabled', true);
	$("#newBtn").attr('disabled', false);

	$("#qcId").val('');
	$("#itemname").val('');
	$("#itemid").val('');
	$("#description").val();
	closeNav();
}
// function for openNav child	
function openNav() {
	$("#parameterName").val('');
	$("#parameterId").val('');
	$("#parameterValue").val('');
	$("#rowEdit").val(null);
	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:380px;";
	document.getElementById("main").style.width = "75%";
}
//function for closeNav child
function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("main").style.width = "100%";
	$("#parameterName").val('');
	$("#parameterId").val('');
	$("#parameterValue").val('');
	$("#rowEdit").val(null);
}
//master save data
function masterSaveData() {
	var event = {};
	var accessControl = [];
	var notification = [];
	activityOptions.api.forEachNode(function(rowNode, index) {
		accessControl.push(rowNode.data);
	});

	event.workspaceId = $("#workspaceId").val();
	event.parentFolderName = $("#parentFolderName").val();
	event.newFolderName = $("#newFolderName").val();
	event.accessType = $("#accessType").val();
	event.owner = $("#owner").val();
	event.ownerId = $("#ownerId").val();
	event.defaultTags = $("#defaultTags").val();
	event.description = $("#description").val();
	event.accessControl = {
		"read": ["U001", "U002", "U005", "U006"],
		"write": ["U001", "U004", "U005", "U008"],
		"modify": ["U001", "U006", "U009"],
		"delete": ["U003", "U005"]
	};
	var data = JSON.stringify(event);

	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "work-space-add-new",
		dataType: "json",
		contentType: "application/json",
		data: data,
		success: function(response) {
			$('.loader').hide();
			if (response.code == "success") {
				cancel();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				closeNav()
			} else {
				$('.loader').hide();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}
		},
		error: function(data) {
			$('.loader').hide();
		}
	})
}

function savePlanDetails(datas) {
	console.log(datas);
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "qc-master-add",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			$('.loader').hide();
			if (response.code == "success") {
				cancel();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				closeNav()
			} else {
				$('.loader').hide();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}
		},
		error: function(data) {
			$('.loader').hide();
		}
	}) //ajax ends
}
// save data in aggrid table sidenav save

function saveTableData() {
	var datas = [];
	var item = {};
	var data = 1;
	var valid = true;
	var rowEdit = $("#rowEdit").val();

	activityOptions.api.forEachNode(function(rowNode, index) {
		if (!rowEdit) {
			data = data + 1;
		} else {
			data = rowEdit;
		}
	});

	//var name = $("#activityName").val();
	item.siNo = data;
	item.activityName = $("#operationType").find('option:selected').text();
	item.activityId = $("#operationType").val();
	item.statusName = $("#statusName").val();
	item.groupName = $("#groupName").val();
	/*  if (item.activityName == null || item.activityName == "") {
		 valid = validationUpdated("Activity Name is Required", "activityName");
	 }
	 if (item.statusName == null || item.statusName == "") {
		 valid = validationUpdated("Status is Required", "statusName");
	 }
	 if (item.groupName == null || item.groupName == "") {
		 valid = validationUpdated("Group is Required", "groupName");
	 } */

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
			console.log("AAAAAAAA " + JSON.stringify(datas))
			activityOptions.api.setRowData(datas);
		}
	} else {
		$('#mySidenav').show();
	}
}
function editProduct(id) {
	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:380px;";
	document.getElementById("main").style.width = "75%";
	var rowNode = activityOptions.api.getRowNode(id);
	$("#rowEdit").val(id);

	$("#operationType").val(rowNode.data.activityId);
	$("#statusName").val(rowNode.data.statusName);
	$("#groupName").val(rowNode.data.groupName);
}
//editing the plan details parent table


// function for delete plan  
function deleteOnclick() {



	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.workspaceId;
	});

	var eventId = selectedRowsString;
	if (eventId) {
		$.ajax({
			type: "GET",
			url: 'work-space-delete?id=' + eventId,
			success: function(response) {
				if (response.message == "Success") {
					agGrid.simpleHttpRequest({
						url: 'work-space-view'
					}).then(function(data) {
						var jsonData = JSON.parse(data.body);
						var allData = jsonData.workSpace;
						console.log(allData)
						var len = allData.length;
						$('#totalReq').find('span').html(len);
						gridOptions.api.setRowData(allData);
					});

					cancel();
					$("#messageParagraph").text("WorkSpace deleted sucessfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('#delete').modal('hide');
				} else {
					$("#messageParagraph").text("Something went to wrong!");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			},
			error: function(data) {
				console.log(data)
			}
		})
	} else {
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
	}

}

// delete selected record from child ag grid
function deleteDetailsOnclick() {
	$('.modal').hide();
	var selectedRows = activityOptions.api.getSelectedRows();
	activityOptions.api.applyTransaction({
		remove: selectedRows
	});
	cancelModalProductBtn();
}
//for closeing modal box for dlt  product
function cancelModalProductBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
	$('#deleteDetails').modal('hide');
}

function deleteFun() {
	$('#delete').modal('show');
}

function deleteDetails() {
	$('#deleteDetails').modal('show');
	closeNav();
}

function cancelModalBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
}
function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#searchBar').val() == null || $('#searchBar').val() == "") {
		id.style.display = "none";
	}
}
//function for download
function downloadDetails() {
	var dataset = [];
	gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		dataset.push(rowNode.data);
	});
	gridOptions.api.exportDataAsCsv(dataset);
}


function getparameterId() {
	var parmId = $("#parameterName").val();
	$("#parameterId").val(parmId);
}

function getItemid() {
	var parmId = $("#itemname").val();
	$("#itemid").val(parmId);
}

