<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<body>
<script type="text/javascript">
function writeValidate(f)
{
	if(f.name.value==""){
		alert("작성자 이름을 입력하세요");
		f.name.focus();
		return false;
	}
	if(f.contents.value==""){
		alert("내용을 입력하세요");
		f.contents.focus(); 
		return false;
	} 
}
</script>
<div class="container">
	<h3>방명록(글쓰기) - 
		<small>연습용입니다.</small></h3>
		
	<!-- JSTL의 url태그는 컨텍스트루트 경로를 자동으로 포함시켜 준다. -->
	<form name="writeFrm" method="post" 
		onsubmit="return writeValidate(this);"
		action="<c:url value="/testboard/writeAction.do" />" >
		
	<table class="table table-bordered">
	<!-- DB에 id을 not null로 줬기때문에 추가함 
		처음에 없어서 에러났엇음  -->
	<input type="hidd en" value="${sessionScope.siteUserInfo.id }" />
	<colgroup>
		<col width="20%"/>
		<col width="*"/>
	</colgroup>
	<tbody>
		<tr>
			<th class="text-center" 
				style="vertical-align:middle;">작성자</th>
			<td>
				<!-- 쓰기 페이지는 로그인 후 접근할 수 있으므로 세션
				영역에 저장한 DTO객체에서 이름을 가져와 삽입한다. -->
				<input type="text" class="form-control" 
					style="width:100px;" name="name" 
						value="${sessionScope.siteUserInfo.name }" />
			</td>
		</tr>
		<!-- DB에 title을 not null로 줬기때문에 추가함 
		처음에 없어서 에러났엇음  -->
		<tr>
			<th class="text-center" 
				style="vertical-align:middle;">제목</th>
			<td>
				<!-- 쓰기 페이지는 로그인 후 접근할 수 있으므로 세션
				영역에 저장한 DTO객체에서 이름을 가져와 삽입한다. -->
				<textarea rows="1" style="width:500px;" class="form-control"
				name="title"></textarea>
			</td>
		</tr>
		<tr>
			<th class="text-center" 
				style="vertical-align:middle;">내용</th>
			<td>
				<textarea rows="10" class="form-control" 
				name="content"></textarea>
			</td>
		</tr>	
	</tbody>
	</table>
	
	<div class="row text-center" style="">
		<!-- 각종 버튼 부분 -->
		
		<button type="submit" class="btn btn-danger">전송하기</button>
		<button type="reset" class="btn">Reset</button>
		<button type="button" class="btn btn-warning" 
			onclick="location.href='list.do';">리스트보기</button>
	</div>
	</form> 
</div>

</body>
</html>