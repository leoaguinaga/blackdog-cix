<%@ page import="pe.edu.utp.blackdog.model.Customer_order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Customer_order customerOrder = (Customer_order) request.getAttribute("customerOrder");
%>

<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .order-confirmation {
        background-color: #fff;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
        padding: 20px;
        max-width: 500px;
        width: 100%;
        text-align: center;
    }

    .order-confirmation h2 {
        color: #333;
        font-size: 24px;
        margin-bottom: 20px;
    }

    .order-confirmation p {
        color: #555;
        font-size: 18px;
        margin: 10px 0;
    }

    .order-confirmation p strong {
        color: #333;
    }

    .button-container {
        margin-top: 20px;
    }

    .button {
        background-color: #007bff;
        border: none;
        color: white;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .button:hover {
        background-color: #0056b3;
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
    <div class="button-container">
        <button class="button" onclick="window.location.href='menu';">Volver al inicio</button>
    </div>
</section>

<jsp:include page="components/footer.jsp" />
