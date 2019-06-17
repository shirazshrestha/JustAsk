<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <title>Login</title>


</head>
<body>

<h1>JustAsk!</h1>

<h5>Post your queries and get answers. <em>Simple as that!</em></h5>
<form action="forgotpassword" method="post" enctype="application/x-www-form-urlencoded">
    <div class="box">
        <div class="container">

            <span id="updatenewpw">Update New Password</span>
            <br />

            <%if (request.getAttribute("message") != null) { %>
            <p style="color: #DD0000;">${requestScope.get("message")}</p>
            <% } %>

            <label for="username"><b>Username</b></label><br/>
            <input type="text" name="username" placeholder="Enter your username" required/> <br/>

            <label for="password"><b>New Password</b></label><br/>
            <input type="password" name="newpassword" placeholder="Enter your new password" required/> <br/>

            <button type="submit">Update PW</button>
            <a id = "registerbtn" href="login">Back to Login</a>
            <br/>


        </div>



    </div>
</form>


</body>
</html>
