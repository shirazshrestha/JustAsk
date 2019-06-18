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
    <link href="https://fonts.googleapis.com/css?family=Slabo+27px&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/feeds.css">
    <%--<link rel="stylesheet" href="css/answer.css">--%>
</head>
<body>


<div class="topnav">
    <span id="title"><img src="images/logo.png" alt="logo"></span>
    <a href="logout">Logout</a>
    <a href="profile">Profile</a>
    <a class="active" href="feed">Feed</a>

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

    <div class="questionAnswerDisplayArea">
        <div class="feed-content">
            <div class="image">
                <img src="https://api.adorable.io/avatars/48/abott@adorable" class="img img-responsive avatar"/>
            </div>
            <div class="q-title">
                <h3 class="title">
                    <a href="answer?id=${requestScope.get('question').getId()}">${requestScope.get('question').getTitle()}</a>
                </h3>
                <ul class="list-inline">
                    <li class="list-inline-item">
                        <strong class="texteffect">Posted on:</strong> ${requestScope.get('question').getCreatedAt()}
                    </li>
                    <li class="list-inline-item">
                        <strong class="texteffect">By:</strong> ${requestScope.get('question').getQuestionUser()}
                    </li>
                    <li class="list-inline-item">
                        <strong class="texteffect">Tags:</strong> ${requestScope.get('question').getTags()}
                    </li>
                </ul>
            </div>
        </div>
        <div class="feed-action">
            <ul class="list-inline">
                <li class="list-inline-item">
                    <a href="#" class="upvoteQuestion" data-id="${requestScope.get('question').getId()}">
                        <img class="vote" src="images/upvote.png" alt="upvotebutton">
                    </a>
                    <span class="count-upvotes">${requestScope.get('question').getUpVotes()}</span>
                </li>
                <li class="list-inline-item">
                    <a href="#" class="downvoteQuestion" data-id="${requestScope.get('question').getId()}">
                        <img class="vote" src="images/downvote.png" alt="downvotebutton">
                    </a>
                    <span class="count-downvotes">${requestScope.get('question').getDownVotes()}</span>
                </li>
            </ul>
        </div>
    </div>

    <form action="" method="POST">
        <!--<textarea name="questiontitle" id="questiontitle" cols="100" rows="8" placeholder="Post your query"></textarea>-->
        <input type="hidden" name="id" value="${requestScope.get("question").getId()}">
        <input type="text" name="answer" id="answer" placeholder="  Post your answer"/>
        <p style="text-align: right;">
            <button id="justaskbtn" type="submit">Answer</button>
        </p>
    </form>

    <c:if test="${requestScope.get('answers') != null}">
        <c:forEach items="${requestScope.get('answers')}" var="answer">
            <div class="questionAnswerDisplayArea">
                <div class="feed-content">
                    <div class="image">
                        <img src="https://api.adorable.io/avatars/48/abott@adorable.png"
                             class="img img-responsive avatar"/>
                    </div>
                    <div class="q-title">
                        <h3 class="title">
                            <a href="#">${answer.getAnswerUser()}</a>
                        </h3>
                        <ul class="list-inline">
                            <li class="list-inline-item">
                                <strong class="texteffect">Posted on:</strong> ${answer.getCreatedAt()}
                            </li>
                        </ul>
                    </div>
                    <h3 class="answer-content">${answer.getContent()}</h3>
                </div>
                <div class="clearfix"></div>
                <div class="feed-action">
                    <ul class="list-inline">
                        <li class="list-inline-item">
                            <a href="#" class="upvote" data-id="${answer.getId()}">
                                <img class="vote" src="images/upvote.png" alt="upvotebutton">
                            </a>
                            <span class="count">${answer.getUpVotes()}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>

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
                    $(this).parent().find('span.count').html(res);
                }
            })
        });
        $('.upvoteQuestion').click(function (e) {
            e.preventDefault();
            const id = this.attributes['data-id'].value;
            $.ajax({
                url: 'question-upvote',
                type: 'post',
                dataType: 'json',
                data: {id},
                success: (res) => {
                    $(this).parents('.feed-action').find(".count-upvotes").html(res.upvotes);
                    $(this).parents('.feed-action').find(".count-downvotes").html(res.downvotes);
                }
            });
        });
        $('.downvoteQuestion').click(function (e) {
            e.preventDefault();
            const id = this.attributes['data-id'].value;
            $.ajax({
                url: 'question-downvote',
                type: 'post',
                dataType: 'json',
                data: {id},
                success: (res) => {
                    $(this).parents('.feed-action').find(".count-upvotes").html(res.upvotes);
                    $(this).parents('.feed-action').find(".count-downvotes").html(res.downvotes);
                }
            });
        });
    });
</script>
</body>
</html>
