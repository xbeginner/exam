function initWaitForRegistUserInfo(){
	$.getJSON("/index/showNoRegistUserInfo", function(data) {
		  $("#not_regist_tbody").html("");//清空info内容
		  var orgBodyInfo = "";
	        $.each(data, function(i, item) {
	        	orgBodyInfo += "<tr>";
	        	orgBodyInfo += "<td><span>"+item.userName+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.orgName+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.tel+"</span></td>";
	        	orgBodyInfo += "<td><a class='btn_success' onclick='passRegister("+item.id+");'>通过</a>&nbsp;&nbsp;&nbsp;<a class='btn_danger' onclick='noPassRegister("+item.id+");'>拒绝</a></td>";
	        	orgBodyInfo += "</tr>";
	        });
	    
	        $("#not_regist_tbody").html(orgBodyInfo );
  });
};


function initRegistedUserInfo(){
	$.getJSON("/index/showOwnUserInfo", function(data) {
		  $("#has_regist_tbody").html("");//清空info内容
		  var orgBodyInfo = "";
	        $.each(data, function(i, item) {
	        	orgBodyInfo += "<tr>";
	        	orgBodyInfo += "<td><span>"+item.userName+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.orgName+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.tel+"</span></td>";
	        	orgBodyInfo += "<td><a class='btn_success' onclick='alterUserInfo("+item.id+")'>修改</a>&nbsp;&nbsp;&nbsp;<a class='btn_danger' onclick='deleteUserInfo("+item.id+");'>删除</a></td>";
	        	orgBodyInfo += "</tr>";
	        });
	    
	        $("#has_regist_tbody").html(orgBodyInfo );
});
}

/**
 * 初始化机构列表
 * @returns
 */
function initOrg(parentId){
	$.getJSON("/index/initOrgList?parentId="+parentId, function(data) {
		  $("#org_tbody").html("");//清空info内容
		  var orgBodyInfo = "";
	        $.each(data, function(i, item) {
	        	orgBodyInfo += "<tr>";
	        	orgBodyInfo += "<td><span>"+item.orgName+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.tel+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.master+"</span></td>";
	        	if(item.childCount=="0"){
	        		orgBodyInfo += "<td><a onclick='alterOrg("+item.id+");'>修改</a></td>";
	        	}else{
	        		orgBodyInfo += "<td><a onclick='alterOrg("+item.id+");'>修改</a>&nbsp;&nbsp;&nbsp;<a onclick='showOwnOrg("+item.id+");'>查看辖区机构</a></td>";
	        	}
	        	
	        	orgBodyInfo += "</tr>";
	        });
	    
	        $("#org_tbody").html(orgBodyInfo );
    });
}

/**
 * 打开添加机构界面
 * @returns
 */
 function openAddOrgPage(){
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
     
	 window.open("/manage/toAddOrg","","top="+top+"px,left="+left+"px,width=700,height=500");
 }
 

 
 
 function alterOrg(orgId){
	 
       $("#addOwnOrgModal").modal('show');
	    
	    $.getJSON("/index/showOrgInfo?orgId="+orgId, function(data) {
	    	  $('#orgId').val(data.id);
	          $('#orgName').val(data.orgName);
	          $('#tel').val(data.tel);
	          $('#address').val(data.address);
	          $('#master').val(data.master);
	          $('#masterTel').val(data.masterTel);
	     });
	    
	    
	    $("#orgForm").validate({
	    	rules:{
	    		orgName:{
	    			required:true
	    		},
	    		tel:{
	    			required:true
	    		},
	    		address:{
	    			required:true
	    		},
	    		master:{
	    			required:true
	    		}
	    	},
	    	messages:{
	    		orgName:{
	    			required:'请输入名称'
	    		},
	    		tel:{
	    			required:'请输入电话号码'
	    		},
	    		address:{
	    			required:'请输入地址'
	    		},
	    		master:{
	    			required:'请输入联系人'
	    		}
	    	},
	    	submitHandler:function(form){
		    	 $('#orgForm').ajaxSubmit({
			   			url:'/index/alterOrg',
			   			dataType:'text',
			   			success:function(data){
			   				$("#addOwnOrgModal").modal('hide');
			   				$('#orgForm')[0].reset();
			                alert(data);
			                window.location.reload();//刷新当前页面.
			   		    }
			   	     });
	             }    
	        });
 
 };
 
 
 function showOwnOrg(orgId){
		$.getJSON("/index/showOwnOrgList?parentId="+orgId, function(data) {
			  $("#org_tbody").html("");//清空info内容
			  var orgBodyInfo = "";
		        $.each(data, function(i, item) {
		        	orgBodyInfo += "<tr>";
		        	orgBodyInfo += "<td><span>"+item.orgName+"</span></td>";
		        	orgBodyInfo += "<td><span>"+item.tel+"</span></td>";
		        	orgBodyInfo += "<td><span>"+item.master+"</span></td>";
	                orgBodyInfo += "<td><a onclick='alterOrg("+item.id+");'>修改</a>&nbsp;&nbsp;&nbsp;<a onclick='delOrg("+item.id+","+orgId+");'>删除</a></td>";
		        	orgBodyInfo += "</tr>";
		        });
		    
		        $("#org_tbody").html(orgBodyInfo );
	    });

 }
 
 function addOwnOrg(){
	   $("#addOwnOrgModal").modal('show');
	   $('#orgForm')[0].reset();
	   
	   $("#orgForm").validate({
	    	rules:{
	    		orgName:{
	    			required:true
	    		},
	    		tel:{
	    			required:true
	    		},
	    		address:{
	    			required:true
	    		},
	    		master:{
	    			required:true
	    		}
	    	},
	    	messages:{
	    		orgName:{
	    			required:'请输入名称'
	    		},
	    		tel:{
	    			required:'请输入电话号码'
	    		},
	    		address:{
	    			required:'请输入地址'
	    		},
	    		master:{
	    			required:'请输入联系人'
	    		}
	    	},
	    	submitHandler:function(form){
	    		 $('#orgForm').ajaxSubmit({
			  			url:'/index/addOwnOrg',
			  			dataType:'text',
			  			success:function(data){
			  				$("#addOwnOrgModal").modal('hide');
		                    alert(data);
		                    window.location.reload();//刷新当前页面.
			  		    }
			  	     });
	    	   }    
	        });
 
 };
 
 
 function delOrg(orgId,parentId){
	  var delete_org_options = {
				url:"/index/deleteOrg?id="+orgId,
				dataType:'text',	
				success:function(data){
					alert(data);
					showOwnOrg(parentId);
				}
			 }
			  $.ajax(delete_org_options);
 };
 
 
 function initRegisterOrgs(){
	 $.getJSON("/manage/getAllRegisteOrgs", function(data) {
		  $("#org_select").html("");//清空info内容
		  var orgBodyInfo = "";
	        $.each(data, function(i, item) {
	        	orgBodyInfo += "<option value="+item.id+">"+item.orgName+"</option>";
	        });
	    
	        $("#org_select").html(orgBodyInfo );
   });
 };
 
 
 
 function passRegister(userId){
	  var pass_register_options = {
				url:"/index/passRegister?id="+userId,
				dataType:'text',	
				success:function(data){
					initWaitForRegistUserInfo();
					initRegistedUserInfo();
					alert(data);
				}
			 }
			  $.ajax(pass_register_options);
 };
 
 
 function noPassRegister(userId){
	 var nopass_register_options = {
				url:"/index/noPassRegister?id="+userId,
				dataType:'text',	
				success:function(data){
					initWaitForRegistUserInfo();
					initRegistedUserInfo();
					alert(data);
				}
			 }
			  $.ajax(nopass_register_options);
 };
 
 
 
 function alterUserInfo(userId){
	 
	 $("#alterUserInfoModal").modal('show');
	    
	    $.getJSON("/index/showUserInfo?id="+userId, function(data) {
	    	  $('#userId').val(data.id);
	          $('#userName').val(data.userName);
	          $('#userTel').val(data.tel);
	          $('#idcard').val(data.idcard);
	     });
	    
	 
	 $("#alterUserInfoForm").validate({
			rules:{
				userName:{
					required:true
				},
				userTel:{
					required:true
				},
				idcard:{
					required:true
				}
			},
			messages:{
				userName:{
					required:'请输入姓名'
				},
				userTel:{
					required:'请输入电话号码'
				},
				idcard:{
					required:'请输入身份证号'
				}
			},
	    	submitHandler:function(form){
	    		 $('#alterUserInfoForm').ajaxSubmit({
			  			url:'/index/alterUserInfo',
			  			dataType:'text',
			  			success:function(data){
			  				$("#alterUserInfoModal").modal('hide');
							initRegistedUserInfo();
							alert(data);
			  		    }
			  	     });
	    	   }    
	        });
	 
 };
 
 
 function deleteUserInfo(userId){
	  var delete_userInfo_options = {
				url:"/index/deleteUserInfo?id="+userId,
				dataType:'text',	
				success:function(data){
					initRegistedUserInfo();
					alert(data);
				}
			 }
			  $.ajax(delete_userInfo_options);
 };
 
 
 
 function initRole(){
		$.getJSON("/index/initRole", function(data) {
			  $("#role_tbody").html("");//清空info内容
			  var roleBodyInfo = "";
		        $.each(data, function(i, item) {
		        	roleBodyInfo += "<tr>";
		        	roleBodyInfo += "<td><span>"+item.name+"</span></td>";
		        	roleBodyInfo += "<td><span>"+item.info+"</span></td>";
		        	roleBodyInfo += "<td><a onclick='alterRole("+item.id+");'>修改</a>" +
		        			"&nbsp;&nbsp;&nbsp;<a onclick='deleteRole("+item.id+");'>删除</a>";
		        			if(item.manageLog=="1"){
		        				roleBodyInfo+="&nbsp;&nbsp;&nbsp;<a onclick='setRoleMenu("+item.id+")'>设置菜单权限</a></td>";
		        			}
		        	roleBodyInfo += "</tr>";
		        });
		        $("#role_tbody").html(roleBodyInfo);
	    });
	};
	
	
	function addRole(){
		 $("#roleManageModal").modal('show');
		   $('#roleForm')[0].reset();
		   $('#roleForm').attr('action','/index/addRole');
		   $("#roleForm").validate({
		    	rules:{
		    		name:{
		    			required:true
		    		}
		    	},
		    	messages:{
		    		name:{
		    			required:'请输入名称'
		    		}
		    	},
		    	submitHandler:function(form){
		    		$(form).ajaxSubmit({
				  			dataType:'text',
				  			success:function(data){
				  				$('#roleForm')[0].reset();
				  				$("#roleManageModal").modal('hide');
			                    alert(data);
			                    initRole();
				  		    }
				  	     });
		    	     }    
		        });
	};
	
	
	
	
	
	function alterRole(roleId){
		 
		 $("#roleManageModal").modal('show');
		 $('#roleForm')[0].reset();
		 $('#roleForm').attr('action','/index/alterRole');
		 $("#manage_log_div").hide();
		    
		    $.getJSON("/index/showRoleInfo?id="+roleId, function(data) {
		    	  $('#roleId').val(data.id);
		          $('#name').val(data.name);
		          $('#info').val(data.info);
		     });
		    
		 
		 $("#roleForm").validate({
				rules:{
					name:{
						required:true
					}
				},
				messages:{
					name:{
						required:'请输入名称'
					}
				},
		    	submitHandler:function(form){
		    		$(form).ajaxSubmit({
				  			dataType:'text',
				  			success:function(data){
				  				$('#roleForm')[0].reset();
				  				$("#roleManageModal").modal('hide');
				  				initRole();
								alert(data);
				  		    }
				  	     });
		    	    }    
		        });
		 
	 };
	 
	 
	 
	 function deleteRole(roleId){
		  var delete_role_options = {
					url:"/index/deleteRole?id="+roleId,
					dataType:'text',	
					success:function(data){
							initRole();
							alert(data);
					}
				 }
				  $.ajax(delete_role_options);
	 };
	 
	 
	 
	 function setRoleMenu(roleId){
 
		 $("#roleMenuManageModal").modal('show');
		 
		    
	     $.getJSON("/index/showRoleMenuInfo?roleId="+roleId, function(data) {
	    	  $("#role_menu_div").html("");//清空info内容
			  var divbody = "";
		        $.each(data, function(i, item) {
		        	 divbody += "<div class='rowCheckbox'>";
		        	divbody += "<input name='menus' type='checkbox' id='checkbox"+item.id+"'  value='"+item.id+"' " ;
		        	if(item.checklog==true){
		        		divbody += "checked>";
		        	}else{
		        		divbody += ">"
		        	}
		        	divbody += "<label for='checkbox"+item.id+"'>"+item.name+"</label>";
		        	 divbody += "</div>";
		        });
		        $("#role_menu_div").html(divbody );
	     });
	     
		    
		 
		 $("#roleMenuForm").validate({
		    	submitHandler:function(form){
		    		 $('#roleMenuForm').ajaxSubmit({
				  			url:'/index/setRoleMenu?roleId='+roleId,
				  			dataType:'text',
				  			success:function(data){
				  				$("#roleMenuManageModal").modal('hide');
				  				$("#role_menu_div").html("");
								alert(data);
				  		    }
				  	     });
		    	   }    
		        });
	 };
	 
	 
	 
	 function initMessage(){
			$.getJSON("/index/initMessage", function(data) {
				  $("#message_tbody").html("");//清空info内容
				  var messageBodyInfo = "";
			        $.each(data, function(i, item) {
			        	messageBodyInfo += "<tr>";
			        	messageBodyInfo += "<td><span>"+item.name+"</span></td>";
			        	messageBodyInfo += "<td><span>"+item.createTime+"</span></td>";
			        	messageBodyInfo += "<td><a onclick='alterMessage("+item.id+");'>修改</a>" +
			        			"&nbsp;&nbsp;&nbsp;<a onclick='deleteMessage("+item.id+");'>删除</a>";
			        	messageBodyInfo += "</tr>";
			        });
			        $("#message_tbody").html(messageBodyInfo);
		    });
		};
	
		
		
		function addMessage(){
			   $("#addMessageManageModal").modal('show');
			   $('#addMessageForm')[0].reset();
			   
			   $("#addMessageForm").validate({
					rules:{
						name:{
							required:true
						},
						content:{
							required:true
						}
					},
					messages:{
						name:{
							required:'请输入名称'
						},
						content:{
							required:'内容不能为空'
						}
					},
			    	submitHandler:function(form){
			    		$(form).ajaxSubmit({
					  			url:'/index/addMessage',
					  			dataType:'text',
					  			success:function(data){
					  				$('#addMessageForm')[0].reset();
					  				$("#addMessageManageModal").modal('hide');
					  				initMessage();
				                    alert(data);
					  		    }
					  	     });
			    	     }    
			        });
		};
		
		
		
		
		
		function alterMessage(id){
			 $("#alterMessageManageModal").modal('show');
			 $('#alterMessageForm')[0].reset();
			 $('#alterMessageForm').attr('action','/index/alterMessage?id='+id);
			    $.getJSON("/index/showMessageInfo?id="+id, function(data) {
			          $('#message_name').val(data.name);
			          $('#message_content').val(data.content);
			     });
			    
			 
			 $("#alterMessageForm").validate({
					rules:{
						name:{
							required:true
						},
						content:{
							required:true
						}
					},
					messages:{
						name:{
							required:'请输入名称'
						},
						content:{
							required:'内容不能为空'
						}
					},
			    	submitHandler:function(form){
			    		
			    		$(form).ajaxSubmit({
					  			resetForm:true,
					  			dataType:'text',
					  			success:function(data){
					  				$("#alterMessageManageModal").modal('hide');
					  				initMessage();
									alert(data);
					  		    }
					  	     });
			    	    }    
			        });
			 
		 };
		 
		 
		 
		 function deleteMessage(messageId){
			  var delete_message_options = {
						url:"/index/deleteMessage?id="+messageId,
						dataType:'text',	
						success:function(data){
								initMessage();
								alert(data);
						}
					 }
					  $.ajax(delete_message_options);
		 };
		 
		 
		 
		 
		 /**
		  * 打开文件导入界面
		  * @returns
		  */
		 function toImportUserInfo(){
			 $("#importUserInfoModal").modal('show');
			 $("#import_userInfo_form")[0].reset();
			};
				
 
			function setUserInfoFileText(){
				$('input[id=userInfofile]').click();
				$('input[id=userInfofile]').change(function() {
					$('#userInfofiletext').val($(this).val());
				});
				
				
				 $.validator.addMethod("checkExcel",function(value,element,params){ 
				      var checkExcel = /\.xl.{1,2}$/; 
				      return this.optional(element)||(checkExcel.test(value)); 
				    },"必须为excel类型文件！"); 
				
				 $("#import_userInfo_form").validate({
				 		rules:{
				 			userInfofiletext:{
				 				required:true,
				 				checkExcel:true
				 			}
				 		},
				 		messages:{
				 			userInfofiletext:{
									required:'不能为空'
								}
						},
						 submitHandler:function() {
 
						    	var import_userInfo_option={
						    			url:'/index/importUserInfo',
						    			dataType:'text',
						    			async: true,
						    			success:function(data){
						      				$('#import_userInfo_form')[0].reset();
							    			alert(data);
						      				$("#importUserInfoModal").modal('hide');
						      				initRegistedUserInfo();
						    		    }
						    	};
						  	    $('#import_userInfo_form').ajaxSubmit(import_userInfo_option);
						 }
				   });
			};
