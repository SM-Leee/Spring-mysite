<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
#dialog-delete-form p {
	padding: 10px;
	font-weight: bold;
	font-size: 1.0em;
}

#dialog-delete-form input[type="password"] {
	padding: 5px;
	outline: none;
	width: 180px;
	border: 1px solid #888;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	var page = 0;
	var isEnd = false;
	var no = 0;

	var render = function(vo, mode) {
		var htmls = "<li data-no='" + vo.no+"'>" + "<strong>" + vo.name
				+ "</strong>" + "<p>" + vo.message.replace(/\n/g, "<br>")
				+ "</p>" + "<strong></strong>" + "<a href='' data-no='"+vo.no+"'>삭제</a>"
				+ "</li>";

		if (mode == false) {
			$("#list-guestbook").append(htmls);
		} else {
			$("#list-guestbook").prepend(htmls);
		}
	}

	var messageBox = function(title, message) {
		$("#dialog-message").attr("title", title);
		$("#dialog-message p").text(message);
		$("#dialog-message").dialog({
			modal: true, // true = doModalDialog / false = modalDialog
			buttons: {
				"확인": function() {
					$(this).dialog("close");
				}
			}
		});
	}
	
	var fetchList = function() {
		if (isEnd == true) {
			return;
		}

		++page;

		$.ajax({
			url : "/mysite3/guestbook/api/ajax-list?page=" + page,
			type : "get",
			dataType : "json",
			data : "",
			success : function(response) {

				if (response.result == "fail") {
					console.warn(response.data);
					return;
				}
				console.log(response.data);
				// 페이지 끝을 검출
				if (response.data.length < 5) {
					isEnd = true;
					$("#btn-next").prop("disabled", true);
				}
				// rendering
				$.each(response.data, function(index, vo) {
					render(vo, false);
				});
			},
			error : function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});
	}
		
	$(function() {
		
		var dialogDelete = $("#dialog-delete-form").dialog({
			autoOpen:false,
			modal:true,
			buttons:{
				"삭제":function(){
					var password = $("#password-delete").val();
					
					console.log(no);
					console.log(password);
					
					$.ajax({
						url:"/mysite3/guestbook/api/ajax-delete",
						type:"post",
						dataType:"json",
						data : "no="+no+"&password="+password,
						success: function(response){
														
							if(response.data){ //true
								console.log("삭제성공");
								$("#password-delete").val("");
								dialogDelete.dialog("close");
								$("#list-guestbook li[data-no="+no+"]").remove();
							} else {
								console.log("삭제실패");
								
								$(".validateTips-error").show();
								
								// dialog clear
								$("#password-delete").val("");
							}
						}, 
						error: function(xhr, status, e) {
							console.error(status + " : " + e);
						}					
					});						
				},
				"취소": function() {
					dialogDelete.dialog("close");
					$('.validateTips-error').hide();
					$("#password-delete").val("");
				}				
			},
			close: function(){
				//close됐을 때 실행해야할 코드
				console.log("close시 뒤처리..");
			}
		});
		
		//live event
		$(document).on("click", "#list-guestbook li a", function() {
			event.preventDefault();
			
			no = $(this).data("no");
			console.log("click" + no);	
				
			$('#hidden-no').val(no);	
			dialogDelete.dialog("open");
		});

		$("#add-form").submit(
				function(event) {
					//submit의 기본동작(post)
					// 막아야한다.
					event.preventDefault();

					var name = $("#input-name").val();
					var password = $("#input-password").val();
					var text = $("#tx-content").val();
					
					if(name==""){
						messageBox("글남기기", "이름은 필수 항목입니다.")
						return;
					}
					if(password==""){
						messageBox("글남기기", "비밀번호은 필수 항목입니다.")
						return;
					}
					if(text==""){
						messageBox("글남기기", "메세지는 필수 항목입니다.")
						return;
					}

					$.ajax({
						url : "/mysite3/guestbook/api/ajax-insert",
						type : "post",
						dataType : "json",
						data : "name="+name+"&password="+password+"&text="+text,
						success : function(response) {

							if (response.result == "fail") {
								console.warn(response.data);
								return;
							}
							console.log(response.data);
							render(response.data, true);
							
							//fetchList();
							$( "#add-form" )[0].reset();
							
						},
						error : function(xhr, status, e) {
							console.error(status + ":" + e);
						}

					});
					
				});

		$("#btn-next").click(function() {
			fetchList();
		});

		$(window).scroll(function() {
			var $window = $(this);
			var scrollTop = $window.scrollTop();
			var windowHeight = $window.height();
			var documentHeight = $(document).height();

			if (scrollTop + windowHeight + 10 > documentHeight) {
				console.log("fetch ajax start...");
				fetchList();
			}
		});

		//최초 리스트 가져오기
		fetchList();
	});
</script>

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>


				<ul id="list-guestbook">

				</ul>

				<!-- 수정사항 -->
				<button id="btn-next">추가</button>

			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips-normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips-error" style="color:red;display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>