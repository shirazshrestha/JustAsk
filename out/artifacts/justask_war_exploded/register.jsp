<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <title>Register</title>


</head>
<body>

<h1>JustAsk!</h1>
<h5>Post your queries and get answers. <em>Simple as that!</em></h5>

<form action="register" method="post" enctype="application/x-www-form-urlencoded">
    <div class="box">
        <div class="container">
            <%if (request.getAttribute("message") != null) { %>
            <p style="color: #DD0000;">${requestScope.get("message")}</p>
            <% } %>
            <label for="username"><b>Username</b></label><br/>
            <input type="text" name="username" placeholder="Enter your username" required/> <br/>

            <label for="password"><b>Password</b></label><br/>
            <input type="password" name="password" placeholder="Enter your password" required/> <br/>

            <label for="firstname"><b>First name</b></label><br/>
            <input type="text" name="firstname" placeholder="Enter your firstname" required/> <br/>

            <label for="lastname"><b>Last name</b></label><br/>
            <input type="text" name="lastname" placeholder="Enter your lastname" required/> <br/>

            <label for="email"><b>Email</b></label><br/>
            <input type="text" name="email" placeholder="Enter your email" required/> <br/>


            <button type="submit">Register</button>

            <a id = "registerbtn" href="login">Back to Login</a>
            <br/>

            </div>



    </div>
</form>


</body>
</html>
