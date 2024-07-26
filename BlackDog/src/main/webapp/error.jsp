<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <a href="index.jsp" class="btn btn-primary">Volver</a>
    </div>
</div>
</body>
</html>
