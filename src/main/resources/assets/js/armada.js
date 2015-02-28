/*
 * Holds functionality of the armada dashboard
 */
$(document).ready(function() {

	var cpuThreshold;
	var diskThreshold;
	var memoryThreshold;
	var refreshVar;
	var tableRefresh;

	var cName;
	var memArray = new Array();
	var diskArray = new Array();
	var cpuArray = new Array();
	var timestampArray = new Array();
	
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

	var data3 = [ {
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

	var ctx3 = document.getElementById("myChart3").getContext("2d");
	var myPieChart = new Chart(ctx3).Pie(data3);
	
	var backgroundColor = '#F7464A';
		
	var table = $('#containers').DataTable({
		"cache":false,
		"white-space":"nowrap",
		"lengthMenu":[[ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ]],
		"ajax":{ url:"http://localhost:8083/containers/", dataSrc:"" },
		"columns":[{"data":"name"}, 
		           {"data":"containerUniqueId"},
		           {"data":"cAdvisorURL"}, 
		           {"data":"cpuUsed"}, 
		           {"data":"cpuTotal"}, 
		           {"data":"memUsed"}, 
		           {"data":"memTotal"}, 
		           {"data":"diskUsed"}, 
		           {"data":"diskTotal"}, 
		           {"data":"containerId"}],
		"columnDefs":[{"targets":[ 9 ], "visible":false, "searchable":false}],
		"fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
							$(nRow).children().each(function(index, td) {
								if (aData.cpuUsed >= cpuThreshold) {
									$(nRow).css('background', backgroundColor);
								} 
								else if(aData.diskUsed >= diskThreshold) {
									$(nRow).css('background', backgroundColor);
								} 
								else if(aData.memUsed >= memoryThreshold) {
									$(nRow).css('background', backgroundColor);
								}
							});
						}
	});	// end datatable
	
	
	$.fn.dataTable.ext.errMode = 'throw';

	/* 
	 * Sets the refresh interval 
	 */
	setInterval(function() {
		table.ajax.reload(null, false); // user paging is not reset on reload
	}, 40000);

	$('#containers tbody tr').each(function() {
		var title;
		var tds = $('td', this);
		var cpuUsed = $(tds[3]).text();
		var memUsed = $(tds[5]).text();
		var diskUsed = $(tds[7]).text();

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
		cName = table.cell(this, 0).data();
		var cId = table.cell(this, 9).data();

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
									// $('#cDetails').find('.modal-body').append("<canvas
									// id=\"myChart\" width=\"200\"
									// height=\"200\"></canvas><canvas
									// id=\"myChart2\" width=\"200\"
									// height=\"200\"></canvas><canvas
									// id=\"myChart4\" width=\"200\"
									// height=\"200\"></canvas>");
									$('#cDetails').find('.modal-body').append("<canvas id=\"myChart\" width=\"400\" height=\"400\"></canvas>");

									var ctx = document.getElementById("myChart").getContext("2d");
									var myCpuChart = new Chart(ctx).Line(cpuData);

									// var ctx2 =
									// document.getElementById("myChart2").getContext("2d");
									// var myDiskChart = new
									// Chart(ctx2).Line(diskData);
									// var ctx4 =
									// document.getElementById("myChart4").getContext("2d");
									// var myMemoryChart = new
									// Chart(ctx4).Line(memData);
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

});