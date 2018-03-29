<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="page/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="page/js/bootstrap.js"></script>
<script type="text/javascript" src="page/js/jquery-2.2.2.js"></script>
<link href="page/css/bootstrap.css" rel="stylesheet">
<script src="page/js/jquery-2.2.2.min.js"></script>

<script src="page/js/highcharts.js"></script> 
</head>
<body>
<div class="panel-body"  style="width: 99%; height: 90%; position: absolute; overflow:auto;">
<div id="top">
			<label for="xqNameId">选择小区</label> 
			<select id="xqNameId" name="xqName" class="input-text" style="width: 150px; margin-right: 15px;">
				
				<c:if test="${!empty xqInfos }">
				<option>--选择小区名称--</option>
					<c:forEach items="${xqInfos}" var="xqInfos">
						<option value="${xqInfos.xqName}">${xqInfos.xqName}</option>
					</c:forEach>
				</c:if>
			</select> 
		&nbsp;&nbsp;&nbsp;
		<input onclick="chaxun()" type="button" class="btn btn-success" value="搜索" /> &nbsp;&nbsp;
		</div><hr />

		<div >
			<table id="shuju" align="center"  style="height: 30px; width: 50%; border:0px;"  >
		 
			</table> 
		</div>
		<div id="container" style="width: 1000px; height: 400px; margin: 0 auto;"></div>
</div>


<script type="text/javascript">
var chart;
 $(function () {
	 
	 $(document).ready(function() {
	 chart = new Highcharts.Chart({
	        chart:{
	            renderTo:'container',
	            type:'scatter',//显示类型 散点图
	            zoomType: 'xy'
	        },
	        title:{
	            text:'传感器温度散点图' //图表的标题
	        },
	        xAxis:{
	            title: {
	                enabled: true,
	                text: '传感器Id'
	                },
	            startOnTick: true,
	            endOnTick: true,
	            showLastLabel: true,
	            allowDecimals:false,
	       
	        },
	        yAxis:{
	            title:{
	                text:'温度' //Y轴的名称
	            },

	        },
	        legend: {
	            layout: 'vertical',
	            align: 'left',
	            verticalAlign: 'top',
	            x: 100,
	            y: 90,
	            floating: true,
	            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
	            borderWidth: 1
	        },
	        credits:{
	            enabled:false
	        },
	        plotOptions: {
	        	series: {
	                cursor: 'pointer',
	                events: {
	                    click: function(e) {
	                   var x = event.point.x
	        		ShowMaxSubValues(x);
	                   
	        	    }
	        	},
	            },
                    
	            scatter: {
	                marker: {
	                    radius: 3,
	                    states: {
	                        hover: {
	                            enabled: true,
	                            lineColor: 'rgb(100,100,100)'
	                        }
	                    }
	                },

	                credits: {  
	                    enabled:false  
	                }, 
	                states: {
	                    hover: {
	                        marker: {
	                            enabled: false
	                        }
	                    }
	                },
	                tooltip: {
	                    headerFormat: '<b>{series.name}</b><br>',
	                    pointFormat: '{point.x}, {point.y}',
	                }
	            }
	        },
	        series: [{
	            name: '温度',
	            color: 'rgba(223, 83, 83, .5)',
	        }]
	    });
	   });
	 //调用查询，加载数据
	    chaxun();

    });
 
 
 function ShowMaxSubValues(x) {
	 var html ="";
	 $.ajax({
			url:"WDSD/findSd.action?id="+x,
			async:false,
			dataType:"json",
			
			success:function(data){
				$("#shuju").empty();
				var cgqId=data.cgqId;
				var xqName=data.xqName;
				var buildNO=data.buildNO;
				var cellNO=data.cellNO;
				var houseNO=data.houseNO;
				var wd=data.wd;
				
			    html+="<tr>";
			    html+="<td> 传感器ID："+cgqId+"</td>";
			    html+="<td> 小区名字："+xqName+"</td>";
			    html+="<td> 楼栋号："+buildNO+"</td>";
			    html+="<td> 单元号："+cellNO+"</td>";
			    html+="<td> 户号："+houseNO+"</td>";
			    html+="<td> 温度："+wd+"</td>";
			    html+="<td class='text-center'> <button type='button' id='btnsearch' class='btn btn-success' >历史数据</td>";
				html+="</tr>";
				html+="</tr>";
				$("#shuju").append(html);
				$("#btnsearch").click(function(){
					window.location.href="/UpPerMonitor/SdChart/goHistoryLine.action?cgqId="+cgqId+"&xqName="+xqName+"&buildNO="+buildNO+"&cellNO="+cellNO+"&houseNO="+houseNO;
				});
			}
			
		})
 }
 function chaxun(){
	    var arr = [];
	    var xqName = $('#xqNameId').val();
	  
	    $.ajax({
	        type:'get',
	        url:"WDSD/chartSearchSd.action?xqName="+xqName,//请求数据的地址
	        beforeSend:function(XMLHttpRequest){ 
	               $('#loading').show();
	               $('#contentDIV').hide();
	           }, 
	         success: function(data){
	            $('#contentDIV').show();
	            $('#loading').hide();
	           chart.series[0].setData(data.data);//数据填充到highcharts上面 
	           chart.yAxis[0].addPlotLine({           //在x轴上增加
	        	    value:28,                           //在值为2的地方
	        	    width:2,                           //标示线的宽度为2px
	        	    color: 'green',                  //标示线的颜色
	        	    id: 'plot-line-1'                  //标示线的id，在删除该标示线的时候需要该id标示
	        	});
	           chart.yAxis[0].addPlotLine({           //在x轴上增加
	        	    value:20,                           //在值为2的地方
	        	    width:2,                           //标示线的宽度为2px
	        	    color: 'green',                  //标示线的颜色
	        	    id: 'plot-line-2'                  //标示线的id，在删除该标示线的时候需要该id标示
	        	});
	        },
	        error:function(e){
	            alert("不好意思，请要访问的图标跑到火星去了。。。。");
	        } 
	    });
	};

 
</script>

</div>
</body>
</html>