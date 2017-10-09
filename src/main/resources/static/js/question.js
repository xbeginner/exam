function initQuestionBank(){
	$.getJSON("/index/initQuestionBank", function(data) {
		  $("#questionbank_tbody").html("");//清空info内容
		  var questionBodyInfo = "";
	        $.each(data, function(i, item) {
	        	questionBodyInfo += "<tr>";
	        	questionBodyInfo += "<td><span>"+item.name+"</span></td>";
	        	questionBodyInfo += "<td><span>"+item.info+"</span></td>";
	        	questionBodyInfo += "<td><a onclick='alterQuestionBank("+item.id+");'>修改</a>" +
	        			"&nbsp;&nbsp;&nbsp;<a onclick='deleteQuestionBank("+item.id+");'>删除</a>";
	        	questionBodyInfo+="&nbsp;&nbsp;&nbsp;<a  href='/index/manageQuestion?id="+item.id+"' target='_blank'>查看题目</a></td>";
	        	questionBodyInfo += "</tr>";
	        });
	        $("#questionbank_tbody").html(questionBodyInfo);
  });
};




function addQuestionBank(){
		   $("#addQuestionBankModal").modal('show');
		   $('#addQuestionBankForm')[0].reset();
		   $('#addQuestionBankForm').attr('action','/index/addQuestionBank');
		   $("#addQuestionBankForm").validate({
		    	rules:{
		    		name:{
		    			required:true
		    		}
		    	},
		    	messages:{
		    		name:{
		    			required:'请输入题库名称'
		    		}
		    	},
		    	submitHandler:function(form){
		    		$(form).ajaxSubmit({
				  			dataType:'text',
				  			success:function(data){
				  				$('#addQuestionBankForm')[0].reset();
				  				$("#addQuestionBankModal").modal('hide');
			                    alert(data);
			                    initQuestionBank();
				  		    }
				  	     });
		    	     }    
		        });
	};
	
	
	function  alterQuestionBank(bankId){
		 $("#addQuestionBankModal").modal('show');
		 $('#addQuestionBankForm')[0].reset();
		 $('#addQuestionBankForm').attr('action','/index/alterQuestionBank?id='+bankId);
		    $.getJSON("/index/showQuestionBankInfo?id="+bankId, function(data) {
		          $('#questionBank_name').val(data.name);
		          $('#questionBank_info').val(data.info);
		     });
		 
		 $("#addQuestionBankForm").validate({
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
				  			resetForm:true,
				  			dataType:'text',
				  			success:function(data){
				  				$("#addQuestionBankModal").modal('hide');
				  				initQuestionBank();
								alert(data);
				  		    }
				  	     });
		    	    }    
		        });
	};
	
	
	
	 function deleteQuestionBank(bankId){
		  var delete_questionBank_options = {
					url:"/index/deleteQuestionBank?id="+bankId,
					dataType:'text',	
					success:function(data){
						initQuestionBank();
							alert(data);
					}
				 }
				  $.ajax(delete_questionBank_options);
	 };
	 
	 
	 function initQuestionByBank(){
		var bankId = $("#bankid_text").val();
		$.getJSON("/index/initQuestionByBank?bankId="+bankId, function(data) {
			  $("#question_tbody").html("");//清空info内容
			  var questionBodyInfo = "";
		        $.each(data, function(i, item) {
		        	questionBodyInfo += "<tr>";
		        	questionBodyInfo += "<td><span>"+item.title+"</span></td>";
		        	questionBodyInfo += "<td><span>"+item.type+"</span></td>";
		        	questionBodyInfo += "<td><span>"+item.banks+"</span></td>";
		        	questionBodyInfo += "<td><a onclick='alterQuestion("+item.id+");'>修改</a>" +
		        			"&nbsp;&nbsp;&nbsp;<a onclick='deleteQuestion("+item.id+");'>删除</a>";
		        	questionBodyInfo += "</tr>";
		        });
		        $("#question_tbody").html(questionBodyInfo);
	     });
	 };
	 
	 
	 function addQuestion(){
		   $("#addQuestionModal").modal('show');
		   $('#addQuestionForm')[0].reset();
		   $('#addQuestionForm').attr('action','/index/addQuestion');
		   
		   
			$.getJSON("/index/initQuestionBank", function(data) {
				  $("#bank_labels").html("");//清空info内容
				  var questionLabelInfo = "";
			        $.each(data, function(i, item) {
			        	questionLabelInfo += "<input name='questionLabels' type='checkbox' id='checkbox"+item.id+"'  value='"+item.id+"'>" ;
			            questionLabelInfo += " <label for='checkbox"+item.id+"'>"+item.name+"</label>"
			        });
			        $("#bank_labels").html(questionLabelInfo);
		     });
		   
		   
		   $("#addQuestionForm").validate({
		    	rules:{
		    		title:{
		    			required:true
		    		},
		    		answer:{
		    			required:true
		    		}
		    	},
		    	messages:{
		    		title:{
		    			required:'请输入题干'
		    		},
		    		answer:{
		    			required:'请输入答案'
		    		}
		    	},
		    	submitHandler:function(form){
		    		$(form).ajaxSubmit({
				  			dataType:'text',
				  			success:function(data){
				  				$('#addQuestionForm')[0].reset();
				  				$("#addQuestionModal").modal('hide');
			                    alert(data);
			                    initQuestionByBank();
				  		    }
				  	     });
		    	     }    
		        });
	 };
	 
	 
	 function selectChangeFunc(){
		 if($("#questionType_select").val()==1||$("#questionType_select").val()==2){
			 $("#questionContent_textarea").show();
		 }else{
			 $("#questionContent_textarea").hide();
		 }
	 };
	
	 
	 
	 
	 function  alterQuestion(id){
		 var labels = new Array();
		 $("#alterQuestionModal").modal('show');
		 $('#alterQuestionForm')[0].reset();
		 $('#alterQuestionForm').attr('action','/index/alterQuestion?id='+id);
		 $.getJSON("/index/showQuestionInfo?id="+id, function(data) {

	          $('#question_title').val(data.title);
	          $('#question_answer').val(data.answer);
	          if(data.type=='单选题'||data.type=='多选题'){
	        	  $("#alter_questionContent_textarea").show();
	        	  $('#question_content').val(data.content);
	          }else{
	        	  $("#alter_questionContent_textarea").hide();
	          }
	          labels = data.banks.split(",");
	 		 $.getJSON("/index/initQuestionBank", function(data) {
				  $("#alter_bank_labels").html("");//清空info内容
				  var questionLabelInfo = "";
			        $.each(data, function(i, item) {
			        	questionLabelInfo += "<input name='questionLabels' type='checkbox' id='checkbox"+item.id+"'  value='"+item.id+"'" ;
			        	if($.inArray(item.name, labels)>=0){
			        		questionLabelInfo += " checked >";
			        	}else{
			        		questionLabelInfo += ">"
			        	}
			            questionLabelInfo += " <label for='checkbox"+item.id+"'>"+item.name+"</label>"
			        });
			        $("#alter_bank_labels").html(questionLabelInfo);
		     });
	    });
		 

		 
		 $("#alterQuestionForm").validate({
				rules:{
					title:{
						required:true
					},
					answer:{
						required:true
					}
				},
				messages:{
					title:{
						required:'请输入题干'
					},
					answer:{
						required:'请输入答案'
					}
				},
		    	submitHandler:function(form){
		    		$(form).ajaxSubmit({
				  			resetForm:true,
				  			dataType:'text',
				  			success:function(data){
				  				$("#alterQuestionModal").modal('hide');
				  				alert(data);
				  				initQuestionByBank();
				  		    }
				  	     });
		    	    }    
		        });
	};
	
	
	
	 function deleteQuestion(id){
		  var delete_question_options = {
					url:"/index/deleteQuestion?id="+id,
					dataType:'text',	
					success:function(data){
						initQuestionByBank();
						alert(data);
					}
				 }
				  $.ajax(delete_question_options);
	 };
	 
	 
	 /**
	  * 打开文件导入界面
	  * @returns
	  */
	 function openImportQuestionFile(){
		 $("#importQuestionModal").modal('show');
		 $("#import_question_form")[0].reset();
		};
			
		 
		 

		function setFileText(){
			$('input[id=questionfile]').click();
			$('input[id=questionfile]').change(function() {
				$('#questionfiletext').val($(this).val());
			});
			
			
			 $.validator.addMethod("checkExcel",function(value,element,params){ 
			      var checkExcel = /\.xl.{1,2}$/; 
			      return this.optional(element)||(checkExcel.test(value)); 
			    },"必须为excel类型文件！"); 
			
			 $("#import_question_form").validate({
			 		rules:{
			 			questionfiletext:{
			 				required:true,
			 				checkExcel:true
			 			}
			 		},
			 		messages:{
			 			questionfiletext:{
								required:'不能为空'
							}
					},
					 submitHandler:function() {
 
					    	var import_question_option={
					    			url:'/index/importQuestion',
					    			dataType:'text',
					    			async: true,
					    			success:function(data){
					      				$('#import_question_form')[0].reset();
						    			alert(data);
					      				$("#importQuestionModal").modal('hide');
					      				initQuestionBank();
					    		    }
					    	};
					  	    $('#import_question_form').ajaxSubmit(import_question_option);
					 }
			   });
		};

	 