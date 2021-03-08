<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" 
		src="resources/script/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#listBtn").on("click", function(){
		$("#actionForm").attr("action", "test1")
		$("#actionForm").submit();
		/* location.href = "test1"; */
	});
	$("#updateBtn").on("click",function(){
		$("#actionForm").attr("action", "test1Update")
		$("#actionForm").submit();
	});
	$("#deleteBtn").on("click",function(){
		if(confirm("삭제하시겠습니까?")) {
			$("#actionForm").attr("action", "test1Delete") /* test1Delete라는 주소가 새롭게 생긴것이다. 동작시 저 주소로 갈것이다.  */
			$("#actionForm").submit();			
		}
	});
});
</script>
</head>
<body>
<form action="#" method="post" id="actionForm"><!-- # : 아무동작도 하지 않는다는 뜻  -->
	<input type="hidden" name="memno" value="${data.MEMNO}"/>
	<input type="hidden" name="searchGbn" value="${param.searchGbn}" />
	<input type="hidden" name="searchTxt" value="${param.searchTxt}" />
	<input type="hidden" name="page"  value="${param.page}" /> <!-- 넘어오는 값을 받는거니까 param. -->
</form>
회원번호 : ${data.MEMNO}<br/>
이름 : ${data.MEMNAME}<br/>




생년월일 : ${data.MEMBIRTH}<br/>
전화번호 : ${data.MEMPHONE}<br/>
<input type="button" value="수정" id= "updateBtn" />
<input type="button" value="삭제" id= "deleteBtn" />
<input type="button" value="목록" id= "listBtn" />
</body>
</html>