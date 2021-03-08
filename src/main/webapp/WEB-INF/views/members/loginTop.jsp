<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>


<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.login_wrap {
	text-align: right;
	padding: 10px;
	font-size: 11pt;
}
</style>
<script type="text/javascript"> // jQuery 안가져옴
$(document).ready(function() {
	$("body").on("click", "#loginBtn", function() {
		location.href = "memberLogin";
	});
	
	$("#logoutBtn").on("click", function() {
		location.href = "memberLogout";
	}); // oboard에서 로그인 로그아웃 버튼 잘라오기
});
</script>

	<div class="login_wrap">[${param.test}]
      <c:choose>
         <c:when test="${!empty sMno}">
${sMnm}님 어서오십시오♡.<input type="button" value="로그아웃" id="logoutBtn" />
         </c:when>
         <c:otherwise>
            <input type="button" value="로그인" id="loginBtn" />
         </c:otherwise>
      </c:choose>
   </div>

