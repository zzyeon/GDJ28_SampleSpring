<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>한 줄 게시판</title>
<style type="text/css">
body {
	margin: 0px;
	font-size: 0pt;
}

.login_wrap {
	text-align: right;
	padding: 10px;
}

.ob_wrap {
	width: 800px;
	margin: 0px auto;
	border: 1px solid #444444;
}

.write_area {
	height: 100px;
}

.user_info {
	display: inline-table;
	width: 100px;
	height: 100px;
	vertical-align: top;
}

.user_name {
	display: table-cell;
	width: 100px;
	height: 100px;
	vertical-align: middle;
	text-align: center;
	font-size: 12pt;
}

.write_con_wrap {
	display: inline-block;
	width: 550px;
	height: 100px;
	vertical-align: top;
}

.write_con {
	width: 540px;
	height: 90px;
	resize: none;
	margin: 2px;
}

.btn_wrap {
	display: inline-block;
	width: 150px;
	height: 100px;
	vertical-align: top;
}
.addBtn,#update2Btn {
	width: 146px;
	height: 96px;
	margin: 2px;
}

#updateBtn,#deleteBtn {
	width: 71px;
	height: 96px;
	margin: 2px;
}
.action_btn {
	width: 146px;
	height: 96px;
	margin: 2px;
}

.action2_btn {
	width: 71px;
	height: 96px;
	margin: 2px;
}

.login_req_wrap {
	display: inline-table;
	width: 800px;
	height: 100px;
}

.login_req {
	display: table-cell;
	width: 800px;
	height: 100px;
	vertical-align: middle;
	text-align: center;
	font-size: 12pt;
}

.data_req_wrap {
	display: inline-table;
	width: 800px;
	height: 100px;
	border-top: 1px solid #444444;
	margin-bottom: 5px;
}

.data_req {
	display: table-cell;
	width: 800px;
	height: 100px;
	vertical-align: middle;
	text-align: center;
	font-size: 12pt;
}

.ob_data {
	border-top: 1px solid #444444;
	margin-bottom: 5px;
}

.con_info {
	display: inline-table;
	width: 550px;
	height: 100px;
	vertical-align: top;
}

.con {
	display: table-cell;
	width: 100px;
	height: 100px;
	vertical-align: middle;
	text-align: left;
	font-size: 12pt;
}

.paging_wrap {
	margin: 5px 0px;
	padding: 5px 0px;
	border-top: 1px solid #444444;
	text-align: center;
}

.paging_wrap span {
	margin: 0px 5px;
	font-size: 12pt;
	cursor: pointer;
}
</style>
<script type="text/javascript"
	src="resources/script/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
		reloadList();
	$("#addBtn").on("click", function() {
			
		if($.trim($("#write_con").val()) == "") {
			alert("내용을 입력해 주세요.");
			$("#write_con").focus();
		} else {
		var params = $("#actionForm").serialize();
		
		$.ajax({
			type : "post",
			url : "oBoardAddAjax",
			dataType : "json", // {키:값, 키:값}
			data : params, // 보낼데이터
			success : function(res) { // 성공 시 해당 함수 실행, 결과는 result로 받겠다.
				if(res.res == "SUCCESS") {
					location.href = "oBoardList";
				} else {
					$("#alert").attr("title", "알림");
					$("#alert p").html("저장에 실패하였습니다.");
					$("#alert").dialog({
						buttons: {
							Ok: function() {
								$(this).dialog("close");
							}
						}
					});
				}
			},
			error : function(req, status, error) { // 결과, 상태값, 에러
				console.log("code : " + req.status);
				console.log("message : " + req.responseText);
			}
		});
			}
		});
	
		$("body").on("click","#deleteBtn", function() {
			$("#obNo").val($(this).attr("name"));
			console.log($("#obNo").val());
			if(confirm("삭제하시겠습니까?")) {
				var params = $("#actionForm").serialize();
			
				$.ajax({
					type : "post",
					url : "oBoardDeleteAjax",
					dataType : "json", // {키:값, 키:값}
					data : params, // 보낼데이터
					success : function(res) { // 성공 시 해당 함수 실행, 결과는 result로 받겠다.
						if(res.res == "SUCCESS") {
							location.href = "oBoardList";
						} else {
							$("#alert").attr("title", "알림");
							$("#alert p").html("삭제에 실패하였습니다.");
							$("#alert").dialog({
								buttons: {
									Ok: function() {
										$(this).dialog("close");
									}
								}
							});
					}
				},
				error : function(req, status, error) { 
					console.log("code : " + req.status);
					console.log("message : " + req.responseText);
				}
			});
		}
	});
		$(".ob_list_wrap").on("click","#updateBtn", function() { //.ob_data는 항상있는게 아니라서?
			var reply=$(this).parent();
			var no=reply.attr("no"); 
			var con=reply.attr("con");
			console.log(no,con);
			$(".write_con").html(con);
			$("#addBtn").hide();
			$("#update2Btn").show();
			$("#update2Btn").on("click", function() {
				$("#obNo").val(no);
				console.log($("#obNo").val());
				if($.trim($(".write_con").val()) == "") {
					alert("내용을 입력해 주세요.");
					$(".write_con").focus();
				} else {
					var params=$("#actionForm").serialize();
					$.ajax({
						type : "post",
						url : "oBoardUpdateAjax",
						dataType : "json", // {키:값, 키:값}
						data : params, //보낼데이터
						success : function(res) { // 성공 시 해당 함수 실행. 결과는 result로받겠다.
							if(res.res=="SUCCESS"){
								location.href="oBoardList";
							}else{
								$("#alert").attr("title","알림");
								$("#alert p").html("수정에 실패하였습니다.");
								$("#alert").dialog({
									buttons: {
										Ok: function(){
											$(this).dialog("close");
										}
									}
								});
							}
						},
						error : function(req, status, error) {
							console.log("code : " + req.status);
							console.log("message : " + req.responseText);
						}
					});
				}
			});
		});
		
	$(".paging_wrap").on("click", "span", function() {
		$("#page").val($(this).attr("name"));
			reloadList();
		});



function reloadList() {
	var params = $("#actionForm").serialize();

		$.ajax({
			type : "post",
			url : "oBoardListAjax",
			dataType : "json", 
			data : params, 
			success : function(res) { 
				drawList(res.list, res.cnt);
				drawPaging(res.pb);
			},
			error : function(req, status, error) { // 결과, 상태값, 에러
				console.log("code : " + req.status);
				console.log("message : " + req.responseText);
			}
		});
	}
	
function drawList(list, cnt) {
		var html = "";

		if (cnt == 0) {
			html += "<span>";
			html += "<span\">데이터가 없습니다.</span>";
			html += "</span>";
		} else {
			for (var i = 0; i < list.length; i++) {
				html += "<div class= \"ob_data\">";
				html +="<div class=\"user_info\"><div class=\"user_name\">"+list[i].MNM+ "</div></div>";
				html += "<div class= \"con_info\">";
				html += "<div class= \"con\" name=\"" + list[i].OB_CON + "\">";
				html += list[i].OB_CON+ "</div>";
				html += "</div>";
				html += "<div class=\"btn_wrap\" no=\"" + list[i].OB_NO + "\" con=\"" + list[i].OB_CON + "\">";
				if(list[i].MNM == "${sMnm}") { 
					html +="<input type=\"button\" value=\"수정\" name=\""+list[i].OB_NO+"\" id=\"updateBtn\" />";
					html += "<input type=\"button\" name=\"" + list[i].OB_NO + "\" value=\"삭제\" id=\"deleteBtn\" class=\"action2_btn\">";
				}
				html += "</div>";
			}
		}
		$(".ob_list_wrap").html(html);

	}
	
function drawPaging(pb) {
	      var html = "";

	      if ($("#page").val() == 1) {
	         html += "<span name=\"1\">이전</span>";
	      } else {
	         html += "<span name=\"" + ($("#page").val() - 1) + "\">이전</span>";
	      }

	      for (var i = pb.startPcount; i <= pb.endPcount; i++) {
	         if ($("#page").val() == i) {
	            html += "<span name=\"" + i + "\"<b>" + i + "</b></span>";
	         } else {
	            html += "<span name=\"" + i + "\">" + i + "</span>";
	         }
	      }

	      if ($("#page").val() == pb.maxPcount) {
	         html += "<span name=\"" + pb.maxPcount + "\">다음</span>";
	      } else {
	         html += "<span name=\"" + ($("#page").val() * 1 + 1)
	               + "\">다음</span>";
	      }

	      $(".paging_wrap").html(html);
	   }

</script>
</head>
<body>

<div id="alert" style="display: none;"><p></p></div>
 <form action="#" method="post" id="actionForm">
      <input type="hidden" name="obNo" id="obNo" /> 
      <input type="hidden" name="page" id="page" value="${page}" />
      <input type="hidden" name="mno" value="${sMno}" />
	<div class="login_wrap">
      <c:choose>
         <c:when test="${!empty sMno}">
${sMnm}님 어서오십시오.<input type="button" value="로그아웃" id="logoutBtn" />
         </c:when>
         <c:otherwise>
            <input type="button" value="로그인" id="loginBtn2" />
         </c:otherwise>
      </c:choose>
   </div>
  
   <div class="ob_wrap">
      <!-- Write -->
      <div class="write_area">
         <!-- 로그인 시 추가 -->
         <c:choose>
            <c:when test="${!empty sMno}">
            <div class="user_info">
               <div class="user_name">${sMnm}</div>
            </div>
            <div class="write_con_wrap">
               <textarea class="write_con" id="write_con" name="obcon"></textarea>
            </div>
            <div class="btn_wrap">
               <input type="button" value="저장" class="action_btn" id="addBtn"/>
               <input type="button" style="display:none;" value="수정" class="update2Btn" id="update2Btn" />
						
            </div>
            </c:when>
            <c:otherwise>
            <div class="login_req_wrap">
            <div class="login_req">
               작성 시 로그인이 필요합니다. <input type="button" value="로그인" id="loginBtn"/>
            </div>
         </div>
            </c:otherwise>
         </c:choose>
      </div>
      <!-- List -->
      <div class="ob_list_wrap">
	  </div>
      <div class="paging_wrap">
      </div>
   </div>
   </form>
</body>
</html>