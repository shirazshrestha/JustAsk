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
    <link rel="stylesheet" href="css/answer.css">
</head>
<body>


<div class="topnav">
    <span id="title"><img src="images/logo.png" alt="logo"></span>
    <a href="logout">Logout</a>
    <a href="profile">Profile</a>
    <a class="active" href="feed">Home</a>

</div>

<div id="category">
    <h4> Categories </h4>
    <ul id="categoryList">
        <c:forEach items="${requestScope.get('tags')}" var="tag">
            <li>
                <a href="${requestScope.getContextPath}feed?tag=${tag.getTag()}">${tag.getTag()}</a>
            </li>
        </c:forEach>
    </ul>
</div>

<div id="feedDisplayArea">
    <form action="" method="POST">
        <!--<textarea name="questiontitle" id="questiontitle" cols="100" rows="8" placeholder="Post your query"></textarea>-->
        <input type="hidden" name="id" value="${requestScope.get("question").getId()}">
        <input type="text" name="answer" id="answer" placeholder="  Post your answer"/>

        <button id="justaskbtn" type="submit">Answer</button>
    </form>
</div>

<c:if test="${requestScope.get('answers') != null}">
    <c:forEach items="${requestScope.get('answers')}" var="answer">
        <div id="questionAnswerDisplayArea">
            <h3 class="answercontent">${answer.getContent()}</h3>
            <h6 class="postedon">Posted on: ${answer.getCreatedAt()}</h6>

            <%--<a href="#" class="upvote" data-id="${answer.getId()}">upvote</a>--%>

            <!-- upvote Question -->
            <a href="#" class="upvote" data-id = "${answer.getId()}">
                <img class="vote" src="images/upvote.png" alt="upvotebutton"></a>
            <span>0</span>

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
