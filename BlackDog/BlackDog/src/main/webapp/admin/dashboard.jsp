<%@ page import="pe.edu.utp.blackdog.model.Customer_order" %>
<%@ page import="java.util.List" %>
<% List<Customer_order> customerOrders = (List<Customer_order>) request.getAttribute("customerOrders"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Administrate Products</h1>
    </div>

    <div class="card shadow mb-4">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <% if (customerOrders != null && !customerOrders.isEmpty()) { %>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Client</th>
                        <th>Date</th>
                        <th>Address</th>
                        <th>Amount</th>
                        <th>State</th>
                        <th>Image</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Customer_order customerOrder : customerOrders) { %>
                    <tr>
                        <td><%= customerOrder.getCustomer_order_id() %></td>
                        <td><%= customerOrder.getClient().getFirst_name() + " " + customerOrder.getClient().getLast_name() %></td>
                        <td><%= customerOrder.getOrder_date() %></td>
                        <td><%= customerOrder.getAddress() %></td>
                        <td><%= customerOrder.getState() %></td>
                        <td><img src="img/products/<%= customerOrder.getEvidence_image() %>" alt="" height="70px"></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/cancelOrder?id=<%= customerOrder.getCustomer_order_id() %>"><img src="img/borrar.png" alt="delete image" height="30px"></a>
                            <a href="${pageContext.request.contextPath}/admin/acceptOrder?id=<%= customerOrder.getCustomer_order_id() %>"><img src="img/editar.png" alt="update image" height="30px"></a>
                        </td>
                    </tr>
                    <% } %>
                    <% } else  { %>
                    <h2>No se encontraron ordenes pendientes</h2>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />