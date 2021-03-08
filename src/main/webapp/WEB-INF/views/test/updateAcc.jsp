<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
		src="resources/script/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#cancelBtn").on("click", function() {
		history.back();
	});
	
	$("#updateBtn").on("click", function() {
		if($.trim($("#accTitle").val()) == "") {
			alert("입출금내역을 입력해 주세요.");
			$("#accTitle").focus();
		} else if($.trim($("#accPrice").val()) == "") {
			alert("입출금금액을 입력해 주세요.");
			$("#accPrice").focus();
		} else if($.trim($("#accDate").val()) == "") {
			alert("입출금일을 입력해 주세요.");
			$("#accDate").focus();
		} else {
			$("#actionForm").submit();
		}
	});
	
	$("#actionForm").on("keypress", "input", function(e) {
		if(e.keyCode == 13) {
			return false;
		}
	});
});
</script>
</head>
<body>
<form action="updateAccs" method="post" id="actionForm">
<input type="hidden" name="page" value="${param.page}" />
<input type="hidden" name="searchGbn" value="${param.searchGbn}" />
<input type="hidden" name="searchTxt" value="${param.searchTxt}" />
<input type="hidden" name="accNo" value="${data.ACC_NO}" />
입출금구분 
<c:choose>
	<c:when test="${data.ACC_TYPE eq 0}">
		<input type="radio" name="accType" value="0" checked="checked" />입금
	</c:when>
	<c:otherwise>
		<input type="radio" name="accType" value="0" />입금
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${data.ACC_TYPE eq 1}">
		<input type="radio" name="accType" value="1" checked="checked" />출금<br/>
	</c:when>
	<c:otherwise>
		<input type="radio" name="accType" value="1" />출금<br/>
	</c:otherwise>
</c:choose>
입출금내역 <input type="text" name="accTitle" id="accTitle" value="${data.ACC_TITLE}" /><br/>
입출금금액 <input type="number" name="accPrice" id="accPrice" value="${data.ACC_PRICE}" /><br/>
입출금일 <input type="date" name="accDate" id="accDate" value="${data.ACC_DATE}" /><br/>
</form>
<input type="button" value="수정" id="updateBtn" />
<input type="button" value="취소" id="cancelBtn" />
</body>
</html>









