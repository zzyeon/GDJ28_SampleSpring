<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
div {
	cursor: pointer;
}

div:hover{
	background-color: orange;
}
</style>
<script type="text/javascript" 
		src="resources/script/jquery/jquery-1.12.4.min.js">
</script>
<script type="text/javascript">
$(document).ready(function() {
	$("body").on("click", "div", function(){
		$("#memno").val($(this).attr("name")); // 여기서 this는 선택 된 div이다.
		
		$("#actionForm").submit();
	});
	$("#addBtn").on("click", function() {
		location.href = "test1Add";
	});
	
	$("#searchBtn").on("click", function() {
		$("#page").val("1");// 페이지 2에서 검색버튼을 눌렀을때? 페이지가 다시 1로 나와줘야지.
		$("#searchForm").submit();
	});
	
	$("#p").on("click", "span",function(){
		$("#page").val($(this).attr("name")); // 클릭했을 때 그 번호
		$("#searchForm").submit();		
	});
});
</script>
</head>
<body><!-- form의 액션이 각각 다르기 때문에 name은 중복해서 여러개 있어도 상관없다. 값 전달용이기때문에. id는 중복지양하라.  -->
<form action="test1Detail" method="post" id="actionForm">
	<input type="hidden" name="memno" id="memno" />
	<input type="hidden" name="searchGbn" value="${param.searchGbn}" />
	<input type="hidden" name="searchTxt" value="${param.searchTxt}" />
<!-- 요기 건듬 --> <input type="hidden" name="page" value="${page}" />
</form>
잘보이는지 확인용입니다.<br/>
<form action="test1" method="post" id="searchForm"> <!-- 해당 폼을 실행하고 다시 test1을 띄운다. -->
	<input type="hidden" name="page" id="page" value="${page}" />
	<select name="searchGbn">
		<c:choose>
			<c:when test="${param.searchGbn eq 0}">
				<option value="0" selected="selected">번호</option>
			</c:when>
			<c:otherwise>			
				<option value="0">번호</option>
			</c:otherwise>
		</c:choose>
				<c:choose>
			<c:when test="${param.searchGbn eq 1}">
				<option value="1" selected="selected">이름</option>
			</c:when>
			<c:otherwise>			
				<option value="1">이름</option>
			</c:otherwise>
		</c:choose>
		</select>
		<input type="text" name="searchTxt" value="${param.searchTxt}" />
		<input type="button" value="검색" id="searchBtn" />
		<input type="button" value="추가" id="addBtn" />
</form>
<c:forEach var="data" items="${list}">
<div name="${data.MEMNO}">${data.MEMNO}, ${data.MEMNAME}</div>
</c:forEach>

<span id="p">
<c:choose>
<c:when test="${page eq 1}">
			<span name="1">이전</span>
		</c:when>
		<c:otherwise>
			<span name="${page -1}">이전</span>
		</c:otherwise>
</c:choose>
<c:forEach var="i" begin="${pb.startPcount}" end="${pb.endPcount}" step="1">
	<c:choose>
		<c:when test="${page eq i}">
			<span name="${i}"><b>${i}</b></span>
		</c:when>
		<c:otherwise>
			<span name="${i}">${i}</span>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:choose>
	<c:when test="${page eq pb.maxPcount}">
		<span name="${pb.maxPcount}">다음</span>
	</c:when>
	<c:otherwise>
		<span name="${page + 1}">다음</span>		
	</c:otherwise>
</c:choose>
</span>
</body>
</html>                              