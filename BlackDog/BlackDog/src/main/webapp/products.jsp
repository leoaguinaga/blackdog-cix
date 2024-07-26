<%@page import="pe.edu.utp.blackdog.model.Product"%>
<%@page import="java.util.List"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Productos</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
            text-align: left;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .actions {
            text-align: center;
        }
        .actions button {
            margin: 0 5px;
            padding: 5px 10px;
            background-color: #f44336;
            color: white;
            border: none;
            cursor: pointer;
        }
        .actions button:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
<h1>Black Dog - Night Food</h1>
<nav>
    <a href="showAllOrders">Pedidos</a>
    <a href="showProducts">Productos</a>
    <a href="logOut">Cerrar Sesión</a>
</nav>

<h2>PRODUCTOS</h2>
<a href="showAllOrders">Registrar Hamburguesa</a>
<a href="showAllOrders">Registrar Bebida</a>
<a href="showAllOrders">Registrar Salchipapa</a>
<a href="showAllOrders">Registrar Chaufa</a>

<% List<Product> products = (List<Product>) request.getAttribute("products");%>
<%if (!products.isEmpty()) {%>
<% for( Product product : (List<Product>) request.getAttribute("products")) {%>
<table>
    <tr>
        <th>CÓDIGO</th>
        <th>NOMBRE</th>
        <th>TIPO</th>
        <th>PRECIO</th>
        <th>ACCIONES</th>
    </tr>
    <tr>
        <td><%=product.getProduct_id()%></td>
        <td><%=product.getName()%></td>
        <td><%=product.getProduct_type().toString()%></td>
        <td>S/. <%=product.getPrice()%></td>
        <td class="actions">
            <button>Actualizar</button>
            <button>Eliminar</button>
        </td>
    </tr>
</table>
<%}%>
<%} else {%>
<h2>Aún no se registraron productos.</h2>
<%}%>
</body>
</html>

