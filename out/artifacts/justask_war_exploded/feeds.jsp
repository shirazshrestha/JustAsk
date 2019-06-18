<%@ page import="com.mysql.cj.protocol.Resultset" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Feed" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Feeds</title>
    <link href="https://fonts.googleapis.com/css?family=Slabo+27px&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/feeds.css">
</head>
<body>
<%--<h1>List of feeds</h1>
<c:forEach items="${requestScope.get('feeds')}" var="feed">
    <div class="row card">
        <div class="col-md-12">
            <a href="/feeds/${feed.getId()}">
                <h3>${feed.getTitle()}</h3>
            </a>
        </div>
    </div>
</c:forEach>--%>

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
    <form action="question" method="POST">
        <!--<textarea name="questiontitle" id="questiontitle" cols="100" rows="8" placeholder="Post your query"></textarea>-->
        <input type="text" name="questiontitle" id="questiontitle" placeholder="    Post your query">
        <ul class="list-inline">
            <li class="list-inline-item" style="padding-left: 0;">
                <input type="text" name="tags" id="tag" placeholder="Insert tags"
                       style="min-width: 200px;padding-left: 10px;"/>
            </li>
            <li class="list-inline-item" style="float: right;">
                <button id="justaskbtn" type="submit">JustAsk</button>
            </li>
        </ul>
    </form>

    <c:if test="${requestScope.get('feeds') != null}">
        <c:forEach items="${requestScope.get('feeds')}" var="feed">
            <div class="questionAnswerDisplayArea">
                <div class="feed-content">
                    <div class="image">
                        <img src="https://api.adorable.io/avatars/48/abott@adorable" class="img img-responsive avatar"/>
                    </div>
                    <div class="q-title">
                        <h3 class="title">
                            <a href="answer?id=${feed.getId()}">${feed.getTitle()}</a>
                        </h3>
                        <ul class="list-inline">
                            <li class="list-inline-item">
                                <strong class="texteffect">Posted on:</strong> ${feed.getCreatedAt()}
                            </li>
                            <li class="list-inline-item">
                                <strong class="texteffect">By:</strong> ${feed.getQuestionUser()}
                            </li>
                            <li class="list-inline-item">
                                <strong class="texteffect">Tags:</strong> ${feed.getTags()}
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="feed-answer">
                    <div class="image">
                        <img src="https://api.adorable.io/avatars/48/abott@adorable.png"
                             class="img img-responsive avatar"/>
                    </div>
                    <div class="q-title">
                        <h3 class="title">
                                ${feed.getAnswerUser()}
                        </h3>
                        <ul class="list-inline">
                            <li class="list-inline-item">
                                <strong class="texteffect">Posted on:</strong> ${feed.getCreatedAt()}
                            </li>
                        </ul>
                    </div>
                    <h3 class="answer-content">${feed.getAnswer()}</h3>
                </div>
                <div class="feed-action">
                    <ul class="list-inline">
                        <li class="list-inline-item">
                            <a href="#" class="upvoteQuestion" data-id="${feed.getId()}">
                                <img class="vote" src="images/upvote.png" alt="upvotebutton"></a>
                            <span>${feed.getUpVotes()}</span>
                        </li>
                        <li class="list-inline-item">
                            <a href="#" class="downvoteQuestion">
                                <img class="vote" src="images/downvote.png" alt="downvotebutton">
                            </a>
                            <span>${feed.getDownVotes()}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>

</body>
</html>
