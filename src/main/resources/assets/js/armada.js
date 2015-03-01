/*
 * Holds functionality of the armada dashboard
 */
$(document).ready(function() {

	var currentDate = new Date().toDateString();
	
	var cpuThreshold;
	var diskThreshold;
	var memoryThreshold;
	var refreshVar;
	var tableRefresh;

	var cName;
	var cId;
	var memArray = [];
	var diskArray = [];
	var cpuArray = [];
	var timestampArray = [];
	
	var cpuData = {
		labels : timestampArray,
		datasets : [ {
			label : "CPU Usage History",
			fillColor : "rgba(220,220,220,0.2)",
			strokeColor : "rgba(220,220,220,1)",
			pointColor : "rgba(220,220,220,1)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(220,220,220,1)",
			data : cpuArray
		} ]
	};
	
	var preferencesREST = "http://localhost:8083/preferences/";
	
	/*
	 * Sets the current data
	 */
	$("#currentDate").text("Today is " + currentDate);
					
	/*
	 * Used to retrieve thresholds from the 'preferences' REST api 
	 */
	$.get(preferencesREST).done(function(data) {
		$.each(data, function(id) {
			if (data[id].name == 'cpu_threshold') {
				cpuThreshold = data[id].value;
			} else if (data[id].name == 'disk_threshold') {
				diskThreshold = data[id].value;
			} else if (data[id].name == 'memory_threshold') {
				memoryThreshold = data[id].value;
			} else if (data[id].name == 'refresh_page') {
				refreshVar = data[id].value;
				tableRefresh = refreshVar * 1000;
			}
		});
	});

	var memData = {
		labels : timestampArray,
		datasets : [ {
			label : "Memory Usage History",
			fillColor : "rgba(220,220,220,0.2)",
			strokeColor : "rgba(220,220,220,1)",
			pointColor : "rgba(220,220,220,1)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(220,220,220,1)",
			data : memArray
		} ]
	};

	var diskData = {
		labels : timestampArray,
		datasets : [ {
			label : "Disk History",
			fillColor : "rgba(220,220,220,0.2)",
			strokeColor : "rgba(220,220,220,1)",
			pointColor : "rgba(220,220,220,1)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(220,220,220,1)",
			data : diskArray
		} ]
	};

	var pieChartData = [ {
		value : 3,
		color : "#F7464A",
		highlight : "#F7464A",
		label : "Inactive"
	}, {
		value : 5,
		color : "#5cb85c",
		highlight : "#5cb85c",
		label : "Active"
	}, {
		value : 1,
		color : "#f0ad4e",
		highlight : "#f0ad4e",
		label : "Active"
	} ];
	
	var lineChartData = {
		    labels: ["", "", "", "", "", "", ""],
		    datasets: [
		        {
		            label: "My First dataset",
		            fillColor: "rgba(220,220,220,0.2)",
		            strokeColor: "rgba(220,220,220,1)",
		            pointColor: "rgba(220,220,220,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [65, 59, 80, 81, 56, 55, 40]
		        },
		        {
		            label: "My Second dataset",
		            fillColor: "rgba(151,187,205,0.2)",
		            strokeColor: "rgba(151,187,205,1)",
		            pointColor: "rgba(151,187,205,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(151,187,205,1)",
		            data: [28, 48, 40, 19, 86, 27, 90]
		        }
		    ]
		};
	
	var barChartData = {
		    labels: ["", "", "", "", "", "", ""],
		    datasets: [
		        {
		            label: "My First dataset",
		            fillColor: "rgba(220,220,220,0.5)",
		            strokeColor: "rgba(220,220,220,0.8)",
		            highlightFill: "rgba(220,220,220,0.75)",
		            highlightStroke: "rgba(220,220,220,1)",
		            data: [65, 59, 80, 81, 56, 55, 40]
		        },
		        {
		            label: "My Second dataset",
		            fillColor: "rgba(151,187,205,0.5)",
		            strokeColor: "rgba(151,187,205,0.8)",
		            highlightFill: "rgba(151,187,205,0.75)",
		            highlightStroke: "rgba(151,187,205,1)",
		            data: [28, 48, 40, 19, 86, 27, 90]
		        }
		    ]
		};

	var pieChartContext = document.getElementById("pieChart").getContext("2d");
	var pieChart = new Chart(pieChartContext).Pie(pieChartData);
	
	var lineChartContext = document.getElementById("lineChart").getContext("2d");
	var lineChart = new Chart(lineChartContext).Line(lineChartData);

	var barChartContext = document.getElementById("barChart").getContext("2d");
	var barChart = new Chart(barChartContext).Bar(barChartData);
	
	var backgroundColor = '#F7464A';

	var successCount = 0;
	var warningCount = 0;
	var errorCount = 0;
	var WARN_THRESHOLD = 1;
	
	var table = $('#containers').dataTable({
		"cache":false,
		"white-space":"wrap",
		"lengthMenu":[[ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ]],
		"ajax":{ url:"http://localhost:8083/containers/", dataSrc:"" },
		"columns":[{"data":"name"}, 
		           {"data":"cpuUsed"}, 
		           {"data":"cpuTotal"}, 
		           {"data":"memUsed"}, 
		           {"data":"memTotal"}, 
		           {"data":"diskUsed"}, 
		           {"data":"diskTotal"}],
		"fnRowCallback":rowCallback
	});	// end datatable
	
	/*
	 * Used for post processing each row in the table
	 * 
	 * row: currently processed row
	 * data: data used to populate current row
	 * index: current index
	 * lastRowIndex: index of the last row in the table
	 */
	function rowCallback(row, data, index, lastRowIndex){
		$(row).attr("cId",data.containerId);
		
		if(index == 0){
			successCount = warningCount = errorCount = 0;
		}
		
		if(data.cpuUsed >= cpuThreshold || data.diskUsed >= diskThreshold || data.memUsed >= memoryThreshold) {
			$(row).css('background', "red");
			errorCount++;
		}
		else if(data.cpuUsed >= (cpuThreshold*WARN_THRESHOLD) || data.diskUsed >= (diskThreshold*WARN_THRESHOLD) || data.memUsed >= (memoryThreshold*WARN_THRESHOLD)){
			$(row).css('background', "orange");
			warningCount++;
		}
		else {
			successCount++;
		}	
		
		if(index >= lastRowIndex){
			$("#successCount").text(successCount);
			$("#warningCount").text(warningCount);
			$("#errorCount").text(errorCount);
		}
	}
	
	$.fn.dataTable.ext.errMode = 'throw';

	/* 
	 * Sets the refresh interval for the table
	 */
	setInterval(function() {
		table.api().ajax.reload(null, false); // user paging is not reset on reload
	}, 3000);

	$('#containers tbody tr').each(function() {
		var title;
		var tds = $('td', this);
		var cpuUsed = $(tds[1]).text();
		var memUsed = $(tds[3]).text();
		var diskUsed = $(tds[5]).text();

		if (cpuUsed >= cpuThreshold) {
			title = " CPU Used > " + cpuThreshold;
		} 
		else if (diskUsed >= diskThreshold) {
			title += " Disk Used > " + diskUsed;
		} 
		else if (memUsed >= memoryThreshold) {
			title += " Memory Used > " + memUsed;
		}

		this.setAttribute('title', title);
	});

	table.$('tr').tooltip({"delay" : 0, "track" : true, "fade" : 250 });

	/*
	 * Sets doubleclick functionality for the rows
	 */
	$('#containers tbody').on('dblclick', 'tr', function() {
		var tds = $('td', this);
		cName = $(tds[0]).text();
		cId = table.$(this).attr('cid');
		
//		memArray = [];
//		diskArray = [];
//		cpuArray = [];
//		timestampArray = [];

		var logsREST = "http://localhost:8083/logs/" + cId;
		$.get(logsREST).done(function(data) {
			$.each(data, function(id) {
				memArray.push(data[id].memUsed);
				diskArray.push(data[id].diskUsed);
				cpuArray.push(data[id].cpuUsed);
				timestampArray.push(data[id].timestamp);
			});
		});

		$('#cDetails').modal('show');
	});

	$('#cDetails').bind('show', function() {
		$(".modal-header").html("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button><h4>" + cName + " - Details</h4>");
		 $('#cDetails').find('.modal-body').append("<canvas id=\"myChart\" width=\"200\" height=\"200\"></canvas>" +
				 								   "<canvas id=\"myChart2\" width=\"200\" height=\"200\"></canvas>" +
				 								   "<canvas id=\"myChart4\" width=\"200\" height=\"200\"></canvas>");
//		$('#cDetails').find('.modal-body').append("<canvas id=\"myChart\" width=\"400\" height=\"400\"></canvas>");

		var ctx = document.getElementById("myChart").getContext("2d");
		var myCpuChart = new Chart(ctx).Line(cpuData);

		 var ctx2 = document.getElementById("myChart2").getContext("2d");
		 var myDiskChart = new Chart(ctx2).Line(diskData);
		 
		 var ctx4 = document.getElementById("myChart4").getContext("2d");
		 var myMemoryChart = new Chart(ctx4).Line(memData);
	});// end of binding

	$('#settings').bind('show', function() {
		$(".modal-body #memory_threshold").val(memoryThreshold);
		$(".modal-body #cpu_threshold").val(cpuThreshold);
		$(".modal-body #disk_threshold").val(diskThreshold);
		$(".modal-body #refresh_page").val(refreshVar);
	});	// end of binding

	$("#saveSettings").click(function(event) {
		event.preventDefault();
		console.log(JSON.stringify($('#settingsForm').serializeArray()));
		$.ajax({type : "POST",
				url : "http://localhost:8083/preferences/setAll/",
				data : JSON.stringify($('#settingsForm').serializeArray()),
					   dataType : 'json',
					   contentType : 'application/json; charset=utf-8',
					   error : function(e) {
					   console.log(e);}
		});
		location.reload();
	});
	
	$('#closeModal').click(function() {
		memArray.length = 0;
		diskArray.length = 0;
		cpuArray.length = 0;
		timestampArray.length = 0; 
	});

});