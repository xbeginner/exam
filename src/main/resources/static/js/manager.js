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


function initRegistedUserInfo(pageNum){
	$.getJSON("/index/showOwnUserInfo?pageNum="+pageNum, function(data) {
		  $("#has_regist_tbody").html("");//清空info内容
		  var orgBodyInfo = "";
	        $.each(data.users, function(i, item) {
	        	orgBodyInfo += "<tr>";
	        	orgBodyInfo += "<td><span>"+item.userName+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.orgName+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.level+"</span></td>";
	        	orgBodyInfo += "<td><span>"+item.idcard+"</span></td>";
	        	orgBodyInfo += "<td><a class='btn_success' onclick='alterUserInfo("+item.id+")'>修改</a>&nbsp;&nbsp;&nbsp;<a class='btn_danger' onclick='deleteUserInfo("+item.id+");'>删除</a></td>";
	        	orgBodyInfo += "</tr>";
	        });
	        $("#has_regist_tbody").html(orgBodyInfo );
	        var userPageInfo = "";
	     	   if(pageNum==0||pageNum==1){
	     		  userPageInfo += "<a class='page_prev' href='javascript:initRegistedUserInfo(1)'><span>‹</span></a>";   
	    	   }else{
	    		   userPageInfo += "<a class='page_prev' href='javascript:initRegistedUserInfo("+(pageNum-1)+")'><span>‹</span></a>";   
	    	   }
	           for(var index=1;index<data.page+1;index++){
	        	   userPageInfo += "<a href='javascript:initRegistedUserInfo("+index+")' class='page-numbers'>"+index+"</a>";
	           }
	           if(pageNum>=data.page){
	        	   userPageInfo += "<a class='page_next' href='javascript:initRegistedUserInfo("+data.page+")'><span>›</span></a>";
	           }else{
	        	   userPageInfo += "<a class='page_next' href='javascript:initRegistedUserInfo("+(pageNum+1)+")'><span>›</span></a>";
	           }
	        	   $("#registed_userinfo_page").html(userPageInfo );
});
}

/**
 * 初始化机构列表
 * @returns
 */
function initOrg(){
	$.getJSON("/index/initOrgList", function(data) {
		  
		  $("#org_tbody").html("");//清空info内容
		  var orgBodyInfo = "";
		  orgBodyInfo += "<tr>";
	      	if(data.ouType=='0'){
	      		orgBodyInfo += "<td><span><a onclick='showOwnOrg("+data.id+");'>"+data.name+"</a></span></td>";
	      	}else{
	      		orgBodyInfo += "<td><span>"+data.name+"</span></td>";
	      	}
	      	orgBodyInfo += "<td><span>"+data.pbcOrder+"</span></td>";
	      //	orgBodyInfo += "<td><a onclick='alterOrg("+data.id+");'>修改</a></td>";
	      	orgBodyInfo += "</tr>";
	        $("#org_tbody").html(orgBodyInfo);
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
 

 
 
 function alterOrg(orgId,parentId){
       $("#addOwnOrgModal").modal('show');
       $('#orgForm').attr('action','/index/alterOrg');
	    $.getJSON("/index/showOrgInfo?orgId="+orgId, function(data) {
	    	  $('#orgId').val(data.id);
	          $('#displayName').val(data.displayName);
	          $('#pbcOrder').val(data.pbcOrder);
	          if(data.ouType=='0'){
	        	  $('input:radio[name=ouType]')[0].checked = true;
	          }else{
	        	  $('input:radio[name=ouType]')[1].checked = true;
	          }
	     });
	    
	    
	    $("#orgForm").validate({
	    	rules:{
	    		displayName:{
	    			required:true
	    		}
	    	},
	    	messages:{
	    		displayName:{
	    			required:'请输入名称'
	    		}
	    	},
	    	submitHandler:function(form){
		    	 $('#orgForm').ajaxSubmit({
			   			dataType:'text',
			   			success:function(data){
			   				$("#addOwnOrgModal").modal('hide');
			   				$('#orgForm')[0].reset();
			                alert(data);
			                showOwnOrg(parentId);
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
		        	if(item.ouType=='0'){
			      		orgBodyInfo += "<td><span><a onclick='showOwnOrg("+item.id+");'>"+item.name+"</a></span></td>";
			      	}else{
			      		orgBodyInfo += "<td><span>"+item.name+"</span></td>";
			      	}
		        	orgBodyInfo += "<td><span>"+item.pbcOrder+"</span></td>";
	                orgBodyInfo += "<td><a onclick='alterOrg("+item.id+","+orgId+");'>修改</a>&nbsp;&nbsp;&nbsp;<a onclick='delOrg("+item.id+","+orgId+");'>删除</a>";
	                if(item.ouType=='0'){
	                	orgBodyInfo += "&nbsp;&nbsp;&nbsp;<a onclick='addOwnOrg("+item.id+");'>添加下级机构</a>"
	                }
	                orgBodyInfo += "</td>";
		        	orgBodyInfo += "</tr>";
		        });
		    
		        $("#org_tbody").html(orgBodyInfo);
	    });

 }
 
 function addOwnOrg(parentId){
	   $("#addOwnOrgModal").modal('show');
	   $('#orgForm')[0].reset();
	   $('#orgForm').attr('action','/index/addOwnOrg?parentId='+parentId);
	   $('input:radio[name=ouType]')[0].checked = true;
	   $("#orgForm").validate({
	    	rules:{
	    		displayName:{
	    			required:true
	    		},
	    		ouType:{
	    			required:true
	    		}
	    	},
	    	messages:{
	    		displayName:{
	    			required:'请输入名称'
	    		},
	    		ouType:{
	    			required:'请选择机构类型'
	    		}
	    	},
	    	submitHandler:function(form){
	    		 $('#orgForm').ajaxSubmit({
			  			dataType:'text',
			  			success:function(data){
			  				$("#addOwnOrgModal").modal('hide');
		                    alert(data);
		                    showOwnOrg(parentId);
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
	  
	  if(window.confirm('确定删除吗？')){
		  $.ajax(delete_org_options);
		  return true;
		}else{
			return false;
		} 
 
			 
 };
 
 
 function initRegisterOrgs(){
	 $.getJSON("/manage/getAllRegisteOrgs", function(data) {
		  $("#first_org_level").show();
		  $("#first_org_level").html("");//清空info内容
		  var orgBodyInfo = "<label>机构选择</label>";
		  orgBodyInfo += "<select name='firstOrgId'  class='form-control' onchange='getChildOrgByFirstLevelOrg(2)'>";
	        $.each(data, function(i, item) {
	        	orgBodyInfo += "<option value="+item.id+">"+item.displayName+"</option>";
	        });
	        orgBodyInfo += "</select>";
	        $("#first_org_level").html(orgBodyInfo );
	        getChildOrgByFirstLevelOrg(2);
   });
 };
 
 
 
 function passRegister(userId){
	  var pass_register_options = {
				url:"/index/passRegister?id="+userId,
				dataType:'text',	
				success:function(data){
					initWaitForRegistUserInfo();
					initRegistedUserInfo(1);
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
					initRegistedUserInfo(1);
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
			        			"&nbsp;&nbsp;&nbsp;<a onclick='deleteMessage("+item.id+");'>删除</a>"+
			        			"&nbsp;&nbsp;&nbsp;<a onclick='showMessageInfo("+item.id+");'>查看</a>";
			        	messageBodyInfo += "</tr>";
			        });
			        $("#message_tbody").html(messageBodyInfo);
		    });
		};
	
		
		
		function addMessage(){
 
			     $("#addMessageManageModal").modal('show');
			     $('#addMessageForm')[0].reset();
			     myNicEditor.removeInstance('nicedit-message');
	             $('#nicedit-message').val('');
	             myNicEditor.panelInstance('nicedit-message');
			     jQuery("#contact-name").trigger("chosen:updated");
		         $.getJSON("/index/showAllTargetPaper", function(data) {
		        	  $("#field_select_id").html("");//清空info内容
					  var messageBodyInfo = "<label for='contact-name' class='label_title'>选择考试方案</label>";
					  messageBodyInfo += "<select name='contact-name' id='contact-name' multiple data-placeholder='选择考试方案'>";
				        $.each(data, function(i, item) {
				        	messageBodyInfo += "<option value='"+item.id+"'>"+item.name+"</option>";
				        });
				        messageBodyInfo += "</select>";
				       $("#field_select_id").html(messageBodyInfo);
				       $('#contact-name').chosen({ width: "100%" });
			     });
			   
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
			 
			 $.validator.addMethod("checkFile",function(value,element,params){ 
			      var checkExcel = /\.xl.{1,2}$/; 
			      var checkXml = /\.xml$/; 
			      return this.optional(element)||(checkExcel.test(value))||(checkXml.test(value)); 
			    },"必须为xls或者xml格式文件！"); 
			
			 $("#import_userInfo_form").validate({
			 		rules:{
			 			userInfofiletext:{
			 				required:true,
			 				checkFile:true
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
				
 
			function setUserInfoFileText(){
				$('input[id=userInfofile]').click();
				$('input[id=userInfofile]').change(function() {
					$('#userInfofiletext').val($(this).val());
				});
 
			};
			
 
			
			 function openLdapFile(){
				 $("#importOrgInfoModal").modal('show');
				 $("#import_org_form")[0].reset();
				 
				 $.validator.addMethod("checkXML",function(value,element,params){ 
				      var checkXML = /\.xml$/; 
				      return this.optional(element)||(checkXML.test(value)); 
				    },"必须为xml类型LDAP文件！"); 
				
				 $("#import_org_form").validate({
				 		rules:{
				 			orgInfofiletext:{
				 				required:true,
				 				checkXML:true
				 			}
				 		},
				 		messages:{
				 			orgInfofiletext:{
									required:'不能为空'
								}
						},
						 submitHandler:function() {
							 
						    	var import_orgInfo_option={
						    			url:'/index/importOrgInfo',
						    			dataType:'text',
						    			async: true,
						    			beforeSend: function(){
						    				$('#import_org_form')[0].reset();
						    				$("#importOrgInfoModal").modal('hide');
						    				$("#myAlertModal").modal('show');
						    				$("#alert_content").html("正在处理,请稍后");
						    			},
						    			success:function(data){
						    				$("#myAlertModal").modal('hide');
							    			alert(data);
						      				initOrg();
						    		    }
						    	};
						  	    $('#import_org_form').ajaxSubmit(import_orgInfo_option);
						 }
				   });
		     };
				
		     
		     
		     function setOrgInfoFileText(){
					$('input[id=orgInfofile]').click();
					$('input[id=orgInfofile]').change(function() {
						$('#orgInfofiletext').val($(this).val());
					});
				};
				
				
				
		function getChildOrgByFirstLevelOrg(index){
			switch (index) {
			case 2:
				var parentId = $("#first_org_level").find("option:selected").val();
				$.getJSON("/manage/getAllRegisteOrgsByParentId?parentId="+parentId, function(data) {
					  $("#second_org_level").show();
					  $("#second_org_level").html("");//清空info内容
					  var orgBodyInfo = "";
					  orgBodyInfo += "<select name='secondOrgId' class='form-control' onchange='getChildOrgByFirstLevelOrg(3)'>";
				        $.each(data, function(i, item) {
				        	orgBodyInfo += "<option value="+item.id+">"+item.displayName+"</option>";
				        });
				        orgBodyInfo += "</select>";
				        $("#second_org_level").html(orgBodyInfo);
			    });
				break;
			case 3:
				var parentId = $("#second_org_level").find("option:selected").val();
				$.getJSON("/manage/getAllRegisteOrgsByParentId?parentId="+parentId, function(data) {
					if(data.length!=0){
						  $("#third_org_level").show();
						  $("#third_org_level").html("");//清空info内容
						  var orgBodyInfo = "";
						  orgBodyInfo += "<select name='thirdOrgId' class='form-control' onchange='getChildOrgByFirstLevelOrg(4)'>";
					        $.each(data, function(i, item) {
					        	orgBodyInfo += "<option value="+item.id+">"+item.displayName+"</option>";
					        });
					        orgBodyInfo += "</select>";
					        $("#third_org_level").html(orgBodyInfo);
					}
			    });
				break;
			case 4:
				var parentId = $("#third_org_level").find("option:selected").val();
				$.getJSON("/manage/getAllRegisteOrgsByParentId?parentId="+parentId, function(data) {
					if(data.length!=0){
						  $("#forth_org_level").show();
						  $("#forth_org_level").html("");//清空info内容
						  var orgBodyInfo = "";
						  orgBodyInfo += "<select name='forthOrgId'  class='form-control' onchange='getChildOrgByFirstLevelOrg(5)'>";
					        $.each(data, function(i, item) {
					        	orgBodyInfo += "<option value="+item.id+">"+item.displayName+"</option>";
					        });
					        orgBodyInfo += "</select>";
					        $("#forth_org_level").html(orgBodyInfo);
					}
			    });
				break;
			default:
				break;
			}
		};
