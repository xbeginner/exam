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
 
 
 function initExamInfo(pageNum){
	  
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
		        $("#message_div").html("");//清空info内容
		        var messageInfo="" ;
		        if(data=="1"){
		        	messageInfo += "<div class='inner'>";
		        	messageInfo += "<div class='tagcloud clearfix'>";
		        	messageInfo += "<a href='/index/showMessage'><i class='menu-icon menu-icon-6'></i>查看消息列表</a>";
		        	messageInfo += "</div>";
		        	messageInfo += "</div>";
		        }
 		        $("#message_div").html(messageInfo);
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
	 
	 
	 
	 function initMessageList(){
		 
		   $.getJSON("/index/initMessageList", function(data) {
			   $("#message_tbody").html("");//清空info内容
				  var messageBodyInfo = "";
			        $.each(data, function(i, item) {
			        	messageBodyInfo += "<tr>";
			        	messageBodyInfo += "<td><span>"+item.name+"</span></td>";
			        	messageBodyInfo += "<td><span>"+item.createTime+"</span></td>";
			        	messageBodyInfo += "<td><a onclick='showMessageInfo("+item.id+");'>查看</a>";
			        	messageBodyInfo += "</tr>";
			        });
			        $("#message_tbody").html(messageBodyInfo);
			     });
		 };
		 
		 
	 /**
	  * 查看信息
	  * @returns
	  */
	 function showMessageInfo(id){
		   $("#showMessageInfoModal").modal('show');
		   $.getJSON("/index/getMessageContent?id="+id, function(data) {
			   $("#show_message_div_id").html("");//清空info内容
				  var messageBodyInfo = "";
				  	messageBodyInfo += "<h3>"+data.name+"<i></i></h3><br>";
				  	messageBodyInfo += data.content;
			        $("#show_message_div_id").html(messageBodyInfo);
			        var messageInfo = "";
			        $.each(data.infos, function(i, item) {
			        	messageInfo += "<div class='message-field'>";
			        	messageInfo += "<ol>";
			        	messageInfo += "<li class='comment'>";    
			        	messageInfo += "<div class='comment-body boxed blue-line'>";             
			        	messageInfo += "<div class='comment-text'>";
			        	messageInfo += "<div class='comment-author'>";
			        	messageInfo += "<span class='link-author'>"+item.info+"</span></div>";
			        	messageInfo += "<div class='comment-entry pull-right'>"+item.sendUserName+":"+item.sendTime+"</div>";
			        	messageInfo += "</div></div></li></ol></div>";
			        });
			        $("#show_message_info_div_id").html(messageInfo);
			        var footerMsg = "<textarea id='message_info_area_id' placeholder='请输入内容'></textarea>";
			        footerMsg += "<a onclick='sendMessageInfo("+id+")' class='btn btn-follow'><span><em>"+data.infos.length+"</em>发送留言</span></a>";
			        $("#send_message_info_footer").html(footerMsg);
			        
		    });
	  };
	  
	  
	  function sendMessageInfo(messageId){
		  $.post("/index/addMessageInfo",
				{messageId:messageId,info:$("#message_info_area_id").val()},
				function(result){ 
					showMessageInfo(messageId);
				});
	  };
	 
 