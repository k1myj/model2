<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>게시글 목록</title>
    <!-- 기존 코드 상단에 추가 -->
    <div style="text-align: right; margin: 10px;">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                안녕하세요, ${sessionScope.userName}님!
                <a href="front?command=logout">로그아웃</a>
            </c:when>
            <c:otherwise>
                <a href="front?command=login">로그인</a> |
                <a href="front?command=signup">회원가입</a>
            </c:otherwise>
        </c:choose>
    </div>
</head>
<body>
<h1>게시글 목록</h1>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<c:if test="${not empty sessionScope.user}">
    <a href="front?command=boardWrite" class="write-btn">글쓰기</a>
</c:if>

<table border="1">
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
    </tr>
    <c:forEach var="board" items="${boardList}">
        <tr>
            <td>${board.id}</td>
            <td><a href="front?command=boardView&id=${board.id}">${board.title}</a></td>
            <td>${board.authorName}</td>
            <td>${board.createdAt}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>