<%@ page import="pe.edu.utp.blackdog.model.Customer_order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>

<%
    if(session.getAttribute("userType") != null){
        if (!session.getAttribute("userType").equals("chef") && !session.getAttribute("userType").equals("admin")) {
            response.sendRedirect("../index.jsp");
            return;
        }
    }

    List<Customer_order> customerOrders = new LinkedList<>();
    if(request.getAttribute("customerOrders") != null){
        customerOrders = (List<Customer_order>) request.getAttribute("customerOrders");
    }
%>

<html>
<head>
    <title>Menu de cocina de BlackDog</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<style>
    body{
        background: url('img/kitchen.png') no-repeat center center fixed;
        background-size: cover;
    }
</style>
<body>

<div class="container-fluid">

    <div class="card shadow mb-4">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <% if (customerOrders != null && !customerOrders.isEmpty()) { %>
                    <thead>
                    <tr>
                        <th>Client</th>
                        <th>Date and time</th>
                        <th>Amount</th>
                        <th>State</th>
                        <th>Details</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Customer_order customerOrder : customerOrders) { %>
                    <tr>
                        <td><%= customerOrder.getClient().getFirst_name() + " " + customerOrder.getClient().getLast_name() %></td>
                        <td><%= customerOrder.getOrderDateTime() %></td>
                        <td><%= customerOrder.getAmount() %></td>
                        <td><%= customerOrder.getState().getDisplayName() %></td>
                        <td><a href="${pageContext.request.contextPath}/admin/chefDetail?id=<%= customerOrder.getCustomer_order_id()%>"> Details </a></td>
                        <td><a href="${pageContext.request.contextPath}/admin/terminateOrder?id=<%= customerOrder.getCustomer_order_id()%>", style="color: green"> Terminate </a></td>
                    </tr>
                    <% } %>
                    <% } else  { %>
                    <h2>No se encontraron ordenes en la base de datos</h2>
                    <% } %>
                    </tbody>
                </table>
                <form action="../logout" method="post" style="display:inline;">
                    <button type="submit" class="dropdown-item">
                        <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> Logout
                    </button>
                </form>
            </div>
        </div>
    </div>

</div>

</body>
</html>