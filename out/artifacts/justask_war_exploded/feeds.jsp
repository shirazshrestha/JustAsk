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
</head>
<body>
<h1>List of feeds</h1>
<%
//    List<Feed> feeds = (List<Feed>) request.getAttribute("feeds");
%>
<c:forEach items="${requestScope.get('feeds')}" var="feed">
    <div class="row card">
        <div class="col-md-12">
            <a href="/feeds/${feed.getId()}">
                <h3>${feed.getTitle()}</h3>
            </a>
        </div>
    </div>
</c:forEach>
</body>
</html>
