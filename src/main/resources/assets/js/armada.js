/*
 * The MIT License (MIT)
 * 
 * Copyright (c) <year> <copyright holders> 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/*
 * Holds functionality of the armada dashboard
 */
$(document).ready(function() {

	$(barChart).ready(loadBarChartData);
	$(pieChart).ready(loadPieChartData);
	$(containerCountChart).ready(loadContainerCountChartData);
	
	Chart.defaults.global.animation = true;
	Chart.defaults.global.animationSteps = 1;
	
	const PERIOD = 24;
	const UNRESPONSIVE_THRESHOLD = 120000;	// 1 minutes
	
	var currentDate = new Date().toDateString();
	
	var cpuThreshold;
	var diskThreshold;
	var memoryThreshold;
	var refreshVar;
	var tableRefresh = 3000;

	var cName;
	var cId;
	
	// colors
	var red = "rgba(247, 70, 74, 1)";
	var redTrans = "rgba(247, 70, 74, 0.7)";
	var green = "rgba(92, 184, 92, 1)";
	var greenTrans = "rgba(92, 184, 92, 0.7)";
	var orange = "rgba(240, 173, 78, 1)";
	var orangeTrans = "rgba(240, 173, 78, 0.7)";
	var blue = "rgba(176,224,230, 1)";
	var blueTrans = "rgba(176,224,230, 0.7)";
	
	var preferencesREST = "/preferences/";
	var metricREST = "/metrics/thresholdStats/";
	var containerCountREST = "/metrics/containerCounts/";
	
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
				tableRefresh = refreshVar;
				clearInterval(tableInterval);
				clearInterval(chartInterval);
				tableInterval = setInterval(function() {
					table.api().ajax.reload(null, false); // user paging is not reset on reload
				}, tableRefresh);
				chartInterval = setInterval(function() {
					loadPieChartData();
					loadBarChartData();
					loadContainerCountChartData();
				}, tableRefresh);
			}
		});
	});

	function loadPieChartData(){
		$.get(metricREST + 1).done(function(data) {
			
			var pieChartData = [];
			
			$('#pieChart').remove(); // this is my <canvas> element
			$('#pie-chart').append('<canvas id="pieChart" width="200" height="200"></canvas>');
			
			var pieChartContext = document.getElementById("pieChart").getContext("2d");
			var pieChart = new Chart(pieChartContext).Pie(pieChartData, 
					{segmentShowStroke : true, 
					 segmentStrokeColor : "rgba(255, 255, 255, 0.1)",
					 animationSteps : 1,
					 animationEasing : "easeOutBounce",
					 animateRotate : true,
					 animateScale : false
					});
			
			var temp = data[0];
			pieChart.addData({value: temp.error,
							  color : redTrans,
							  highlight : red,	// // rgb 247 70 74
							  label : "ERROR"}, 0);
			pieChart.addData({value: temp.warn,
							  color : orangeTrans,
							  highlight : orange,	// // rgb 247 70 74
							  label : "WARN"}, 1);
			pieChart.addData({value: temp.ok,
							  color : greenTrans,
							  highlight : green,	// // rgb 247 70 74
							  label : "OK"}, 2);
		});
	}
		
	function loadContainerCountChartData(){		
		$.get(containerCountREST + PERIOD).done(function(data) {
			
			var containerCountChartData = {
				    labels: [],
				    datasets: [
				        {
				            label: "COUNT",
				            fillColor: greenTrans,
				            strokeColor: green,
				            data: []
				        }
				    ]
				};
			
			$('#containerCountChart').remove(); // this is my <canvas> element
			$('#container-count-chart').append('<canvas id="containerCountChart" width="400" height="220"></canvas>');
			
			var containerCountChartContext = document.getElementById("containerCountChart").getContext("2d");
			var containerCountChart = new Chart(containerCountChartContext).Bar(containerCountChartData, {barStrokeWidth : 1, barValueSpacing : 1, barDatasetSpacing : 1});
			
			$.each(data, function(index) {
				var temp = data[index];
				containerCountChart.addData([temp.value], temp.hour + "h");
			});
		});
	}

	function loadBarChartData(){
		$.get(metricREST + PERIOD).done(function(data) {
			
			$('#barChart').remove(); // this is my <canvas> element
			$('#bar-chart').append('<canvas id="barChart" width="400" height="220"></canvas>');
			
			var barChartData = {
				    labels: [],
				    datasets: [
				        {
				            label: "ERROR",
				            fillColor: redTrans,
				            strokeColor: red,
				            data: []
				        },
				        {
				            label: "WARN",
				            fillColor: orangeTrans,
				            strokeColor: orange,
				            data: []
				        },
				        {
				            label: "OK",
				            fillColor: greenTrans,
				            strokeColor: green,
				            data: []
				        }
				    ]
				};
			
			var barChartContext = document.getElementById("barChart").getContext("2d");
			var barChart = new Chart(barChartContext).StackedBar(barChartData, {barStrokeWidth : 1, barValueSpacing : 1, barDatasetSpacing : 1});
			
			$.each(data, function(index) {
				var temp = data[index];
				barChart.addData([temp.error, temp.warn, temp.ok], temp.period + "h");
			});
		});
	}
	
	var backgroundColor = '#F7464A';

	var successCount = 0;
	var warningCount = 0;
	var errorCount = 0;
	var WARN_THRESHOLD = 0.75;
	
	var table = $('#containers').dataTable({
		"cache":false,
		"white-space":"wrap",
		"lengthMenu":[[ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ]],
		"ajax":{ url:"/containers/", dataSrc:"" },
		"columns":[{"data":"name"}, 
		           {"data":"cpuUsed"}, 
		           {"data":"cpuTotal"}, 
		           {"data":"memUsed"}, 
		           {"data":"memTotal"}, 
		           {"data":"diskUsed"}, 
		           {"data":"diskTotal"},
		           {"data":"timestamp"}],
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
		$(row).attr("cId", data.containerId);
		
		if(index == 0){
			successCount = warningCount = errorCount = 0;
		}
		
		if(data.cpuUsed < 0){
			$('td:eq(1)', row).html(0);
		}
		
		if(data.memTotal < 0){
			$('td:eq(4)', row).html('UNLIMITED'); 
		}
		
		// convert cpu used to percent
		var n = data.cpuUsed;
		n = (n < 0)? 0 : n;
		n = n/100.00;
		n = n.toFixed(2);
		$('td:eq(1)', row).html(n);
		
		var currentDate = new Date().getTime();
		var heartbeatDate = new Date(data.timestamp);
		var millis = heartbeatDate.getTime();
		var diff = (currentDate - millis);

		if(diff > UNRESPONSIVE_THRESHOLD){
			$('td:eq(1)', row).html('');
			$('td:eq(2)', row).html('');
			$('td:eq(3)', row).html('');
			$('td:eq(4)', row).html('No heartbeat since: ' + heartbeatDate.format(dateFormat.masks.isoDateTime));
			$('td:eq(5)', row).html('');
			$('td:eq(6)', row).html('');
			$('td:eq(7)', row).html('');
			$(row).css('background', redTrans);
		}
		
		if(data.cpuUsed >= cpuThreshold || 
		   data.diskUsed >= diskThreshold || 
		   data.memUsed >= memoryThreshold ||
		   (diff > UNRESPONSIVE_THRESHOLD)) {
			$(row).css('background', redTrans);
			errorCount++;
			return;
		}
		
		if(data.cpuUsed >= (cpuThreshold*WARN_THRESHOLD) || 
				data.diskUsed >= (diskThreshold*WARN_THRESHOLD) || 
				data.memUsed >= (memoryThreshold*WARN_THRESHOLD)){
			$(row).css('background', orangeTrans);
			warningCount++;
			return;
		}
		
		$(row).css('background', null);
		successCount++;
	}
	
	$.fn.dataTable.ext.errMode = 'throw';

	/* 
	 * Sets the refresh interval for the table
	 */
	var tableInterval = setInterval(function() {
		table.api().ajax.reload(null, false); // user paging is not reset on reload
	}, tableRefresh);
	
	/*
	 * Sets the refresh interval for the charts
	 */
	var chartInterval = setInterval(function() {
		loadPieChartData();
		loadBarChartData();
		loadContainerCountChartData();
	}, tableRefresh);
	
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
	$('#containers tbody').on('click', 'tr', function() {
		var tds = $('td', this);
		cName = $(tds[0]).text();
		cId = table.$(this).attr('cid');
		
		$("#detailsTitle").text("Details for: " + cName);
		
		$('#cpuChart').remove(); // this is my <canvas> element
		$('#cpuCanvasHolder').append('<canvas id="cpuChart" width="550" height="200"><canvas>');
		$('#memChart').remove(); // this is my <canvas> element
		$('#memCanvasHolder').append('<canvas id="memChart" width="550" height="200"><canvas>');
		$('#diskChart').remove(); // this is my <canvas> element
		$('#diskCanvasHolder').append('<canvas id="diskChart" width="550" height="200"><canvas>');
		
		var options = {datasetFill : false, pointDot : true, pointDotRadius : 1, datasetStrokeWidth : 1, bezierCurve : false};
		
		var cpuChartData = {labels: [], datasets: [{label: "CPU", strokeColor: blue, pointColor: blue, data: []}]};
		var cpuChartContext = document.getElementById("cpuChart").getContext("2d");
		var cpuChart = new Chart(cpuChartContext).Line(cpuChartData, options);

		var memChartData = {labels: [], datasets: [{label: "MEM", strokeColor: blue, pointColor: blue, data: []}]};
		var memChartContext = document.getElementById("memChart").getContext("2d");
		var memChart = new Chart(memChartContext).Line(memChartData, options);
		
		var diskChartData = {labels: [], datasets: [{label: "DISK", strokeColor: blue, pointColor: blue, data: []}]};
		var diskChartContext = document.getElementById("diskChart").getContext("2d");
		var diskChart = new Chart(diskChartContext).Line(diskChartData, options);
		
		var logsREST = "/logs/" + cId;
		$.get(logsREST).done(function(data) {
			$.each(data, function(index) {
				var temp = data[index];
				cpuChart.addData([temp.cpu], temp.hour + "h");
				memChart.addData([temp.mem], temp.hour + "h");
				diskChart.addData([temp.disk], temp.hour + "h");
			});
		});

		$('#cDetails').modal('show');
	});

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
				url : "/preferences/setAll/",
				data : JSON.stringify($('#settingsForm').serializeArray()),
					   dataType : 'json',
					   contentType : 'application/json; charset=utf-8',
					   error : function(e) {
					   console.log(e);}
		});
		location.reload();
	});

});