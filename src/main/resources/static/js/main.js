function initMainMenu(){
   var size = 0;
   $.getJSON("/index/initMainMenu", function(data) {
	        $("#menuContentUl").html("");//清空info内容
	        var menuInfo="" ;
	        $.each(data, function(i, item) {
	        		menuInfo+="<li class='menu_li'><a href='"+item.url+"'>"+item.name+"</a></li>";
	        		size += 1;
	        });
	        $("#menuContentUl").html(menuInfo);
	        var pxSize = 1080/size-30;
	        $("li.menu_li").css({"width":pxSize+"px"});
	     });
 };
 
 
 function   initExamInfo(pageNum){
	  
	 $.getJSON("/index/initExamInfo?page="+pageNum, function(data) {
	        $("#exam_tbody").html("");//清空info内容
	        $("#exam_page").html("");
	        var examBodyInfo = "";
	        var examPageInfo = "";
	        $.each(data.info, function(i, item) {
	        	examBodyInfo += "<tr>";
	        	examBodyInfo += "<td><span>"+item.paperName+"</span></td>";
	        	examBodyInfo += "<td><span>"+item.doTime+"</span></td>";
	        	if(item.doLog=='0'){
	        		examBodyInfo += "<td><a href='/openExamPage?paperId="+item.id+"'>参加</a></td>";
	        	}else{
	        		examBodyInfo += "<td><a href='/index/checkGrade'>查看成绩</a></td>";
	        	}
	        	examBodyInfo += "</tr>";
	        });
	        $("#exam_tbody").html(examBodyInfo );
	        
     	   if(pageNum==0||pageNum==1){
    		   examPageInfo += "<a class='page_prev' href='javascript:initExamInfo(1)'><span>‹</span></a>";   
    	   }else{
    		   examPageInfo += "<a class='page_prev' href='javascript:initExamInfo("+(pageNum-1)+")'><span>‹</span></a>";   
    	   }
           for(var index=1;index<data.page+1;index++){
        	   examPageInfo += "<a href='javascript:initExamInfo("+index+")' class='page-numbers'>"+index+"</a>";
           }
           if(pageNum>=data.page){
        	   examPageInfo += "<a class='page_next' href='javascript:initExamInfo("+data.page+")'><span>›</span></a>";
           }else{
        	   examPageInfo += "<a class='page_next' href='javascript:initExamInfo("+(pageNum+1)+")'><span>›</span></a>";
           }
        	   $("#exam_page").html(examPageInfo );
	     });
 };
 
 
 
 function initMessageInfo(){
 
	   $.getJSON("/index/initMessageInfo", function(data) {
		        $("#message_tbody").html("");//清空info内容
		        var messageInfo="" ;
		        $.each(data, function(i, item) {
		        	messageInfo += "<tr>";
		        	messageInfo += "<td class='messageContent'><a onclick='checkMessage("+item.id+");'>"+item.name+"</a></td>";
		        	messageInfo += "<td class='messageTime'> <span>"+item.createTime+"</span></td>";
		        	messageInfo += "</tr>";
		        });
		        $("#message_tbody").html(messageInfo);
		     });
	 };
	 
	 
	 function checkMessage(id){
		    $("#showMessageModal").modal('show');
		    $("#message_info_div").html("");
		    var messageContent = "<table>";
		    $.getJSON("/index/showMessageInfo?id="+id, function(data) {
		    	messageContent += "<tr align='center' ><td>"; 
		    	messageContent += "<font size='6'>"
		    	messageContent += data.name;
		    	messageContent += "</font>";
		    	messageContent += "</td>";
		    	messageContent += "</tr>";
		    	messageContent += "<tr><td>";
		    	messageContent += "<font size='5'>"
		    	messageContent += data.content;
		    	messageContent += "</font>";
		    	messageContent += "</td>";
		    	messageContent += "</tr>";
		    	messageContent += "</table>";
			    $("#message_info_div").html(messageContent);
		     });
		   
	 };
	 
	 
	 function initMessage(){
		  
	 }
	 
	 
 