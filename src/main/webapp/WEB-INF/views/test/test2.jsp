<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type=text/css>

table{
	border-collapse: collapse;
}

tr{ 
	border-top: 1px solid #444444;
	border-bottom: 1px solid #444444;
}

tbody tr{
	cursor : pointer;
}

tbody tr:hover {
	background-color : orange;
}

tbody td{
	padding : 5px 10px;
	text-aling : center;
} 

</style>

<script type="text/javascript" 
		src="resources/script/jquery/jquery-1.12.4.min.js">
</script>
<script type="text/javascript">
$(document).ready(function() {
	$("tbody").on("click", "tr", function(){ // on 뒤에 오는 순서대로 행위, 행위구역
		$("#b_no").val($(this).attr("name")); // 여기서 this는 선택 된 tr이다.
		
		$("#actionForm").submit();
	});
	
	$("#addBtn").on("click", function() {
		location.href = "test2Add";
	});
	$("#searchBtn").on("click", function(){
		$("#page").val("1");
		$("#searchForm").submit();
	});
	$("#p").on("click", "span",function(){
		$("#page").val($(this).attr("name")); // 클릭했을 때 그 번호
		$("#searchForm").submit();		
	});
});
</script>
</head>

<body>
<form action="test2Detail" method="post" id="actionForm"> <!-- test2Detail 이라는 주소로 보내겠다.  -->
	<input type="hidden" name="b_no" id="b_no" />
	<input type="hidden" name="searchGbn" value="${param.searchGbn}" />
	<input type="hidden" name="searchTxt" value="${param.searchTxt}" />
	<input type="hidden" name="page"  value="${page}" />
</form>

<form action="test2" method="post" id="searchForm"> <!-- 해당 폼을 실행하고 다시 test1을 띄운다. -->
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
				<option value="1" selected="selected">제목</option>
			</c:when>
			<c:otherwise>			
				<option value="1">제목</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${param.searchGbn eq 2}">
				<option value="2" selected="selected">작성자</option>
			</c:when>
			<c:otherwise>			
				<option value="2">작성자</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${param.searchGbn eq 3}">
				<option value="3" selected="selected">작성일</option>
			</c:when>
			<c:otherwise>			
				<option value="3">작성일</option>
			</c:otherwise>
		</c:choose>
		</select>
		<input type="text" name="searchTxt" value="${param.searchTxt}" />
		<input type="button" value="검색" id="searchBtn" />
		<input type="button" value="추가" id="addBtn" />
</form>


<%-- 두번째 실습 혼자 <c:forEach var="data" items="${list}"> 
<div name="${data.B_NO}">${data.B_TITLE}, ${data.B_CON}, ${data.B_WRITER}, ${data.B_DT}</div>
</c:forEach> --%>


<table>
<thead>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
	</tr>
</thead>
<tbody>
<c:forEach var="data" items="${list}">
	<tr name= "${data.B_NO}">
			<td>${data.B_NO}</td>
			<td>${data.B_TITLE}</td>
			<td>${data.B_WRITER}</td>
			<td>${data.B_DT}</td>
	</tr>
</c:forEach>
</tbody>
</table>

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


<%-- 혼자 <table> 
	<thead>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="data" items="${list}">
			<tr>
			<td>${data.B_NO}</td>
			<td>${data.B_TITLE}</td>
			<td>${data.B_WRITER}</td>
			<td>${data.B_DT}</td>
			</tr>
		</c:forEach>
	</tbody>
</table> --%>
</body>
</html>