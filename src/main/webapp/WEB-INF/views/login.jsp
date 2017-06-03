<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<body>
<h1>Welcome to login page!</h1>

${error}

<form action="${loginHandler}" method="post">
    login: <input type="text" name="login" placeholder="login here"/></br>
    password: <input type="password" name="password" placeholder="password here"/></br>
    <input type="submit" value="Send"/>
</form>

</body>
</html>