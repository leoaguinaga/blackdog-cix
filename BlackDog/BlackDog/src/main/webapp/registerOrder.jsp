<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% double amount = (double) request.getAttribute("amount"); %>
<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />
<br><br><br><br><br><br>
<img src="img/qr_yape.jpg" alt="qr_yape" height="360" width="330">

Formulario para registrar la orden
<form action="${pageContext.request.contextPath}/registerOrder" method="post" enctype="multipart/form-data">
    <input type="hidden" name="totalPrice" value="<%= amount %>"><br>
    <label for="address"> Dirección</label><br>
    <input type="text" id="address" name="address" placeholder="Dirección para delivery"><br>
    <label for="evidence"> Evidencia (Yape o Plin)</label><br>
    <input type="file" id="evidence" name="evidence" required><br>
    <button class="btn btn-success" type="submit">Registrar Orden</button>
</form>



<!--
<div class="container-reg">
    <img src="img/qr_yape.webp" alt="qr_yape" class="qr-image-reg">
    <h2 class="form-title-reg">Formulario para registrar la orden</h2>
    <form action="${pageContext.request.contextPath}/registerOrder" method="post" enctype="multipart/form-data" class="order-form-reg">
        <input type="hidden" name="totalPrice" value="<%= amount %>">
        <label for="address" class="form-label-reg">Dirección</label>
        <input type="text" id="address" name="address" placeholder="Dirección para delivery" class="form-input-reg">
        <label for="evidence" class="form-label-reg">Evidencia (Yape o Plin)</label>
        <input type="file" id="evidence" name="evidence" required class="form-input-reg">
        <button type="submit" class="btn-reg btn-success-reg">Registrar Orden</button>
    </form>
</div>

-->

<jsp:include page="components/footer.jsp" />




