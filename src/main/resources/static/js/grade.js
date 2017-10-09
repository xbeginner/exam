function initGrade(){
	 
	$.getJSON("/index/showGrade", function(data) {
		  $("#table_head").html("");//清空info内容
		  $("#table_tbody").html("");//清空info内容
		  var headInfo = "";
		  var bodyInfo = "";
		  if(data.type=="0"){
			  headInfo += "<tr>";
			  headInfo += "<th><span>试卷名称</span></th>";
			  headInfo += "<th><span>分数</span></th>";
			  headInfo += "</tr>";
			
		  }else{
			  headInfo += "<tr>";
			  headInfo += "<th><span>试卷名称</span></th>";
			  headInfo += "<th><span>操作</span></th>";
			  headInfo += "</tr>";
		  }
		  $("#table_head").html(headInfo);
		  
		  
	        $.each(data.paper, function(i, item) {
	        	bodyInfo += "<tr>";
	        	bodyInfo += "<td><span>"+item.paperName+"</span></td>";
	        	if(data.type!="0"){
	        		bodyInfo +=  "<td><a onclick='showPaperGrade("+item.id+");'>查看成绩</a>"
	        	}else{
	        		bodyInfo += "<td><span>"+item.grade+"</span></td>";
	        	}
	        	bodyInfo += "</tr>";
	        });
	        $("#table_tbody").html(bodyInfo);
});
};
	
	function showPaperGrade(paperId){
		$.getJSON("/index/showPaperGrade?paperId="+paperId, function(data) {
			  $("#table_head").html("");//清空info内容
			  $("#table_tbody").html("");//清空info内容
			  var headInfo = "";
			  var bodyInfo = "";
			  headInfo += "<tr>";
			  headInfo += "<th><span>参考人员</span></th>";
			  headInfo += "<th><span>分数</span></th>";
			  headInfo += "</tr>";
			  $("#table_head").html(headInfo);
			  
			  
		        $.each(data, function(i, item) {
		        	bodyInfo += "<tr>";
		        	bodyInfo += "<td><span>"+item.userName+"</span></td>";
		        	bodyInfo += "<td><span>"+item.grade+"</span></td>";
		        	bodyInfo += "</tr>";
		        });
		        $("#table_tbody").html(bodyInfo);
	    });
	};
	
 