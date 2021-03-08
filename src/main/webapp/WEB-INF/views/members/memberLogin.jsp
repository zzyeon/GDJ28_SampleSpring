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
function emptyCheck(sel) {
	if($.trim($(sel).val()) == '') {
		return true;
	} else {
		return false;
	}
}

$(document).ready(function() {
	$("#loginBtn").on("click", function() {
		if(emptyCheck("#mid")) {
			alert("아이디를 입력해주세요.");
			$("#mid").focus();
		} else if(emptyCheck("#mpw")) {
			alert("비밀번호를 입력해주세요.");
			$("#mpw").focus();
		} else {
			var params = $("#loginForm").serialize();
			console.log(params);
			
			$.ajax({
				type : "post",
				url : "memberLoginAjax", // 결과를 받을 주소
				dataType : "json", // {키 : 값, 키 : 값}
				data : params, // 보낼데이터
				success : function(res) { // 성공 시 해당함수 실행. 결과는 result로 받겠다.
					if(res.res == "0") {
						alert("아이디가 없습니다.");
					} else if(res.res == "1"){
						alert("비밀번호가 틀립니다.");
					} else {
						location.href = "aBoardList";
					}
				}, 
				error : function(req, status, error) {
					console.log("code : " + req.status);
					console.log("message : " + req.responseText);
					console.log("error : " + error);
				}
			});
		}
	});

	$("#loginForm").on("keypress", "input", function(e) {
		if(e.keyCode == 13) {
			return false;
		}	
	});
});


</script>
</head>
<body>
로그인
<form action="#" method="post" id="loginForm"> <!-- 비동기 방식을 보기 위해서 #으로 바꾸면서 이제 직접적인 연결이 없어졌다. -->
아이디<input type="text" name="mid" id="mid" /><br/>
비밀번호<input type="password" name="mpw" id="mpw" /><br/>
</form>
<input type="button" value="로그인" id="loginBtn" />
</body>
</html>