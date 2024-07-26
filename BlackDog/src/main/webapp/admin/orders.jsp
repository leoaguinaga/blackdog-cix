<%@ page import="pe.edu.utp.blackdog.model.Customer_order" %>
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
<%@ page import="java.util.List" %>
<%@ page import="pe.edu.utp.blackdog.model.enums.State" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Customer_order> customerOrders = (List<Customer_order>) request.getAttribute("customerOrders"); %>
<% List<State> states= State.getStates(); %>
<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Orders</h1>
    </div>

    <div>
        <a href="ordersHistory"><button class="btn-primary">Todos</button></a>
        <% for (State state : states) { %>
        <form style="display:inline-block; margin:0;">
            <input type="hidden" value="<%=state%>" hidden name="state">
            <button formaction="filterOrders" formmethod="post" type="submit" class="btn-primary"><%=state.getDisplayName()%></button>
        </form>
        <% } %>
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
                        <th>Date and time</th>
                        <th>Amount</th>
                        <th>State</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Customer_order customerOrder : customerOrders) { %>
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/admin/orderDetail?id=<%= customerOrder.getCustomer_order_id()%>"><%= customerOrder.getCustomer_order_id()%></a></td>
                        <td><%= customerOrder.getClient().getFirst_name() + " " + customerOrder.getClient().getLast_name() %></td>
                        <td><%= customerOrder.getOrderDateTime() %></td>
                        <td><%= customerOrder.getAmount() %></td>
                        <td><%= customerOrder.getState().getDisplayName() %></td>
                    </tr>
                    <% } %>
                    <% } else  { %>
                    <h2>No se encontraron ordenes en la base de datos</h2>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />