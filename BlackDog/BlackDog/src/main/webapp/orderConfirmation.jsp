<%@ page import="pe.edu.utp.blackdog.model.Customer_order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Customer_order customerOrder = (Customer_order) request.getAttribute("customerOrder");
%>

<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />

<style>
    .order-confirmation p,
    .order-confirmation h2 {
        color: white;
    }
</style>

<section class="order-confirmation" id="order-confirmation">
    <br><br>
        <div class="container">
            <h2>¡Orden Registrada!</h2>
            <p>Su orden ha sido registrada exitosamente.</p>
            <p><strong>ID de la Orden:</strong> <%= customerOrder.getCustomer_order_id() %></p>
            <p><strong>Cliente:</strong> <%= customerOrder.getClient().getFirst_name() %> <%= customerOrder.getClient().getLast_name() %></p>
            <p><strong>Fecha y hora:</strong> <%= customerOrder.getOrderDateTime() %></p>
            <p><strong>Dirección:</strong> <%= customerOrder.getAddress() %></p>
        <p><strong>Total:</strong> <%= customerOrder.getAmount() %></p>
    </div>
</section>

<jsp:include page="components/footer.jsp" />
