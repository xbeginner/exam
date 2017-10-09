 


 function login(){
	 if($("#name").val()==null||$("#name").val()==""){
		 $("#username_log").html(" <font color='red'>请输入用户名</font>");
	 }else if($("#password").val()==null||$("#password").val()==""){
		 $("#password_log").html(" <font color='red'>请输入密码</font>");
	 }else{
			$.ajax({
					cache: false,
					type: "POST",
					url:"/login/checkLogin", 
                    data:$('#loginForm').serialize(), 
					async: false,
					error: function(request) {
						 alert("输入错误，请重新输入！");
					},
					success: function(data) {
				         if(data=='username'){
				        	 $("#username_log").html(" <font color='red'>用户名错误</font>");
				         }else if(data=='password'){
				        	 $("#password_log").html(" <font color='red'>密码错误</font>");
				         }else if(data=='tomuch'){
				        	 alert("您输入错误次数过多，请10分钟后尝试");
				         } else{
                              StandardPost('/login/openMainPage',{userId:data});
				         } 
					}
			});
	}
	 

 };
 
 
 function changeLog(type){
	 
  switch (type) {
	case 0:
	   $("#username_log").html("");
		break;
	case 1:
	   $("#password_log").html("");
		break;
	default:
		break;
	}
	 
 };
 
 

 function StandardPost(url,args){
     var form = $("<form method='post'></form>");
     var  input;
     form.attr({"action":url});
     $.each(args,function(key,value){
         input = $("<input type='hidden'>");
         input.attr({"name":key});
         input.val(value);
         form.append(input);
     });
     form.appendTo("body").submit();
     form.remove();  
 };
 
 
 function openRegisteUserPage(){
	 var $browser=$(window);
	 
	    var winWidth;
	    var winHeight;
	   
	    var scrollLeft;
	    var scrollTop;
	   
	    var left=0,top=0;
	    var $currentWin=$("div.win");//弹出窗口
	    var clientWidth;
	    var clientHeight;
	    
	 winWidth=$browser.width();
     winHeight=$browser.height();
    
     scrollLeft=$browser.scrollLeft();
     scrollTop=$browser.scrollTop();
    
     clientWidth=$currentWin.outerWidth();
     clientHeight=$currentWin.outerHeight();

     left=scrollLeft+(winWidth-clientWidth)/2;
     top=scrollTop+(winHeight-clientHeight)/2;   
     
	 window.open("/manage/toRegisteUser","","top="+top+"px,left="+left+"px,width=700,height=500");
 };

 
 function remeberUserName(){

		if($("#rememberLog").is(':checked')){
			var userName = $("#name").val();
			$.cookie('login_username',userName, { expires: 7 });
		}else{
			$.cookie('login_username', null);
		}
 }
 
 