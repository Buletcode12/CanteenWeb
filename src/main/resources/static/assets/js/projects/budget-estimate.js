//Multiple Document Upload Ends

	$(document).ready(function() {

		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#creationDate').val($(this).val());
		})

		$('#creationDate').blur(function() {
			$("#DateCalendar").val($(this).val());
		})

		agGrid.simpleHttpRequest({
			url : 'view-candidate-view-ajax'
		}).then(function(data) {
			var len = data.length;
			$('#totalCandidate').find('span').html(len);
			gridOptions.api.setRowData(data);

		});
	});

	var columnDefs1 = [ {
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

	var columnDefs = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Budget ID",
		field : "budgetId",

		width : 150,

		/* cellRenderer : function(params) {
			return '<a instrno="editId" onclick=editId("'
					+ params.data.budgetId
					+ '") href="javascript:void(0)">'
					+ params.data.budgetId + '</a>';
		}, */
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Budget Category",
		field : "budgetCategory",
	}, {
		headerName : "Items",
		field : "item",
		width : 150
	}, {
		headerName : 'Units',
		field : "unit",
		type : 'rightAligned',
		//valueFormatter : currencyFormatter,
		width : 130
	}, {
		headerName : "Quantity",
		field : "qty",
		width : 150
	}, {
		headerName : "Rate",
		field : "rate",
	}, {
		headerName : "Numbers",
		field : "nos",
		width : 150
	}, {
		headerName : 'Mob/Demob',
		field : "mob",
		type : 'rightAligned',
		width : 130
	}, {
		headerName : "Amount",
		field : "amount",
		width : 150
	}, {
		headerName : 'Projected Expences',
		field : "projected",
		width : 150,

	}, {
		headerName : 'Actual Expences',
		field : "actual",
		width : 150,

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
	const rowData = [ {
		budgetId : "1",
		budgetCategory : "Manpower",
		item : "Painting",
		unit : "Mandays",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "2",
		budgetCategory : "Manpower",
		item : "Fabrication",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "3",
		budgetCategory : "Manpower",
		item : "Erection",
		unit : "Manday",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "4",
		budgetCategory : "Manpower",
		item : "Stud Welding",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "5",
		budgetCategory : "Manpower",
		item : "Sheeting/Decking",
		unit : "Mandays",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "6",
		budgetCategory : "Manpower",
		item : "Civil Work",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "7",
		budgetCategory : "Manpower",
		item : "Electrical Work",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "8",
		budgetCategory : "Manpower",
		item : "Dismantling",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "9",
		budgetCategory : "Manpower",
		item : "Labour Supply",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "10",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Farana- F-15",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "11",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Farana- F-17",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "12",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Farana- F-20",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "13",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Farana- F-23",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "14",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Crane- 250 MT",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "15",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Crane- 150 MT",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "16",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Crane- 100 MT",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "17",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Crane- 80 MT",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "18",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Crane- 50 MT",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "19",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Boomlift 220 Ft",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "20",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Boomlift 150 Ft",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "21",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Boomlift 120 Ft",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "22",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Boomlift 60/80 Ft",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "23",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Boomlift 45 Ft",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "24",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Trailer 40 Ft",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "25",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Trailer 20 Ft",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "26",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Tractor",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "27",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "DG Set",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "28",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Scaffolding (10 M Ht)",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "29",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Scaffolding (6 M Ht)",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "30",
		budgetCategory : "Hiring of Equipments/Machineries",
		item : "Emergency Vehicle",
		unit : "Months",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "31",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Electrical Torque wrinch (Hudraulic)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "32",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Impact Wrench",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "33",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Torque Wrench",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "34",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Gas Cutting Set",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "35",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Magnetic Drill machine (110V)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "36",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Screw machine (110V)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "37",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Hand Grinder (110V)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "38",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Hand Scissor",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "39",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Drill Machine (110V)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "40",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Hammering Drill Machine (110V)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "41",
		budgetCategory : "Purchase Tools & Tackies",
		item : "Arc Welding Machine",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "42",
		budgetCategory : "Purchase Tools & Tackies",
		item : "DFT Guage meter (Digital)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "43",
		budgetCategory : "Lifting Tools & Tackies",
		item : "Wire Rope (16mm & 12mm)",
		unit : "Mtr",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	},

	{
		budgetId : "44",
		budgetCategory : "Lifting Tools & Tackies",
		item : "Turn buckle (20mm dia)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "45",
		budgetCategory : "Lifting Tools & Tackies",
		item : "Chain block (2MT & 5MT @ 10m Chain)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "46",
		budgetCategory : "Lifting Tools & Tackies",
		item : "PP Rope (1kg=6Rmt) @ 20mm dia",
		unit : "Kg",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "47",
		budgetCategory : "Lifting Tools & Tackies",
		item : "U clamp for wire rope",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "48",
		budgetCategory : "Lifting Tools & Tackies",
		item : "D Sackle for wire rope (5MT/3MT/10MT)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "49",
		budgetCategory : "Web Sling",
		item : "5MT 5 Rmt",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "50",
		budgetCategory : "Web Sling",
		item : "3MT 3Rmt",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "51",
		budgetCategory : "Web Sling",
		item : "5MT 3 Rmt",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "52",
		budgetCategory : "Web Sling",
		item : "10MT 12 Rmt",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "53",
		budgetCategory : "Wire Rope Sling",
		item : "5MT 5 Rmt @ 20mm dia",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "54",
		budgetCategory : "Wire Rope Sling",
		item : "3MT 3 Rmt @ 20mm dia",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "55",
		budgetCategory : "Wire Rope Sling",
		item : "10MT 12 Rmt @ 25mm dia",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "56",
		budgetCategory : "Wire Rope Sling",
		item : "Rope Pully",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "57",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Spanners 18mm to 65mm",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "58",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Hammer(5kg)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "59",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Hammer(3kg)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "60",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Life Line Post",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "61",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Line doori",
		unit : "LS",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "62",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Water Cooler",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "63",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Water jet Spray Machine ",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "64",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Diesel Hand Pump",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "65",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Welding Electrode",
		unit : "LS",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "66",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Cutting wheel",
		unit : "LS",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "67",
		budgetCategory : "Purchase Consumables(Hardware)",
		item : "Grinding wheel",
		unit : "LS",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "68",
		budgetCategory : "Purchase Electrical Accessories",
		item : "Cables 2.5 Sqmm 3C Cu",
		unit : "Mtr",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "69",
		budgetCategory : "Purchase Electrical Accessories",
		item : "Cables 6.5 Sqmm 4C Alu Armoured",
		unit : "Mtr",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "70",
		budgetCategory : "Purchase Electrical Accessories",
		item : "Cables 10 Sqmm 4C Alu Armoured",
		unit : "Mtr",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "71",
		budgetCategory : "Purchase Electrical Accessories",
		item : "Distribution/Extension Board with RCCB",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "72",
		budgetCategory : "Purchase Electrical Accessories",
		item : "LED Flood light  IP65 @ 100w",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "73",
		budgetCategory : "Purchase Electrical Accessories",
		item : "Earthing",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "74",
		budgetCategory : "Purchase Electrical Accessories",
		item : "Male & Female Plug Scoket for Extention (16 Amps)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "75",
		budgetCategory : "Purchase Electrical Accessories",
		item : "Male & Female Plug Scoket for Extention (32 Amps)",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "76",
		budgetCategory : "Purchase Safety Accessories",
		item : "Safety Helmet",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}, {
		budgetId : "77",
		budgetCategory : "Purchase Safety Accessories",
		item : "Safety Shoes",
		unit : "Nos",
		qty : 10,
		rate : 50,
		nos : 5,
		mob : "Mob 1",
		amount : 500,
		projected : 1000,
		actual : 800,
		createdBy : "John",
		createdOn : "2023-05-01",
		updatedBy : "Jane",
		updatedOn : "2023-05-10",
	}

	];

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});

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
		onSelectionChanged : onSelectionChanged
	};

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
		onSelectionChanged : onSelectionChanged
	};

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});
	var columnDefsNew = [  {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	} , {

		headerName : "Sl No",
		field : "budgetId",
		hide :  true,
		width : 100,
	}, {

		headerName : "Budget Category",
		field : "budgetCategory",
	}, {
		headerName : 'Budget Sub-Category',
		field : "item"
	}, ];
	const rowdataNew = [ {
		//budgetId : 1,
		budgetCategory : "Purchase Civil Material",
		item : "Tiles/Mables/Chips",
	}, {
		//budgetId : 2,
		budgetCategory : "Purchase Civil Material",
		item : "Sand",
	}, {
		//budgetId : 3,
		budgetCategory : "Manpower",
		item : "Painting",
	}, ];
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
	/* APPLY FOR REQUISITION ENDS */

	// setup the grid after the page has finished loading
	$(document).ready(function() {

		$("#cancelFridTrial").attr('disabled', true);
		$("#transportvalue").hide();

		$('#delete').attr('disabled', true);
		$('#delete1').attr('disabled', true);
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

		var gridDiv1 = document.querySelector('#myGrid1');
		new agGrid.Grid(gridDiv1, gridOptions1)

		var gridDiv2 = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv2, gridOptionsNew)

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

	function cancelbtn() {
		$('#reqDltBtn').attr('disabled', true);
		$("#myGrid").show();
		$("#btn1").hide();
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
		} else {
			$('#delete').attr('disabled', true);
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
		document.getElementById("mySidenav").style.cssText = "width: 350px; position: absolute; right:-20px; overflow: hidden; height:auto; top:275px;";
		document.getElementById("main").style.width = "73%";
		$("#addId").hide();
		$("#demo").show();

	}

	function cancel() {
		location.reload();
	}

	/* function saveTableData() {
		var event = {};
		var valid = true;
		event.budgetId = $("#budgetId").val();
		event.item = $("#item").val();
		event.budgetCategory = $("#budgetCategory").val();
		event.uoms = $("#uoms").val();
		event.unit = $("#unit").val();

		console.log(event)
		if (valid) {
			$.ajax({
				type : "POST",
				url : "manage-budget-add",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(event),
				success : function(response) {
					if (response.message == "Success") {
						//$('.loader').hide();
						$("#messageParagraph").text("Data Saved Successfully");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						location.reload();

					}

				},
				error : function(response) {
					console.log(response);

				}
			});
		}

	} */

	function saveTableData() {

		$("#demo").hide();
		$("#myGrid").show();
		$("#addId").show();
		var item = {};
		var data = 1;
		var rowEdit = $("#rowEdit").val();
		gridOptions.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});

		item.budgetId = $("#budgetId").val();
		item.budgetCategory = $("#budgetCategory").val();
		item.item = $("#item").val();
		item.unit = $("#unit").val();
		item.qty = $("#qty").val();
		item.rate = $("#rate").val();
		item.nos = $("#nos").val();
		item.mob = $("#mob").val();
		item.amount = $("#amount").val();
		item.projected = $("#projected").val();
		item.actual = $("#actual").val();

		item.budgetId = data;

		var datas = [];
		if (rowEdit) {
			var rowNode = gridOptions.api.getRowNode(rowEdit);
			rowNode.setData(item);
		} else {

			gridOptions.api.forEachNode(function(rowNode, index) {
				datas.push(rowNode.data);
				if (!rowEdit) {
					data = data + 1;
				}
			});

			datas.push(item)
			gridOptions.api.setRowData(datas);
		}

	}

	function search() {
		$('#deleteid').modal('show');
	}
	function cancelR() {
		$('#deleteid').modal('hide');
	}
	function copy() {

		/* const selectedRows1 = gridOptionsNew.api.getSelectedRows();
		console.log(selectedRows1);

		var datas = [];
		gridOptionsNew.api.forEachNode(function(rowNode, index) {
			datas.push(rowNode.data);

		});

		datas.push(selectedRows1)

		gridOptions.api.setRowData(datas);
		 */
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
		var budgetId = 1; // Increment the slnoId value

		// Update the slnoId field in gridOptions
		gridOptions.api.forEachNode(function(rowNode) {
			rowNode.setDataValue('budgetId', budgetId++);
		});

		// Reset the function state
		copy.isRunning = false;

		cancelR();

	}