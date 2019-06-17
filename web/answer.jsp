<%@ page import="com.mysql.cj.protocol.Resultset" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Feed" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Answer</title>
    <link rel="stylesheet" href="css/feeds.css">
</head>
<body>


<div class="topnav">
    <span id="title">JustAsk!</span>
    <a href="logout">Logout</a>
    <a href="profile">Profile</a>
    <a class="active" href="feed">Home</a>

</div>


<div id="feedDisplayArea">
    <form action="" method="POST">
        <!--<textarea name="questiontitle" id="questiontitle" cols="100" rows="8" placeholder="Post your query"></textarea>-->
        <input type="hidden" name="id" value="${requestScope.get("question").getId()}">
        <input type="text" name="answer" id="answer" placeholder="Post your answer"/>

        <button id="justaskbtn" type="submit">JustAsk</button>
    </form>
</div>

<c:if test="${requestScope.get('answers') != null}">
    <c:forEach items="${requestScope.get('answers')}" var="answer">
        <div class="questionAnswerDisplayArea">
            <h3>${answer.getContent()}</h3>
            <h6>Posted on: ${answer.getCreatedAt()}</h6>
            <a href="#" class="upvote" data-id="${answer.getId()}">upvote</a>
        </div>
    </c:forEach>
</c:if>

<script src="https://code.jquery.com/jquery-3.4.1.min.js" type="text/javascript"></script>
<script>
    $(function () {
        $('.upvote').click(function (e) {
            e.preventDefault();
            const id = this.attributes['data-id'].value;
            $.ajax({
                url: 'answer-upvote',
                type: 'post',
                dataType: 'json',
                data: {id},
                success: (res) => {
                    console.log(res);
                }
            });
        })
    })
</script>
</body>
</html>
