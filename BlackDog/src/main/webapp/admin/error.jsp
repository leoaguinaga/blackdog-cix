<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HttpSession session1 = request.getSession(false);
    if (session1 == null || session1.getAttribute("userType") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String userType = (String) session1.getAttribute("userType");
    if (!"admin".equals(userType)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<% String message = (String) request.getAttribute("message");%>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<div class="container">
    <div class="text-center">
        <h1>Oops! Ha ocurrido un error</h1>
        <img src="img/monito.jpg" alt="No tienes la dicha de ver la imagen del monito enfermo" class="img-fluid">
        <h2> <%=message%> </h2>
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-primary">Volver</a>
    </div>
</div>
</body>
</html>
