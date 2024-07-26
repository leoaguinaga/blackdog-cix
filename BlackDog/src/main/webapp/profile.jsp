<%@ page import="pe.edu.utp.blackdog.model.Client" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% boolean isError = false; %>
<% if(request.getAttribute("isError") != null) {
    isError = (boolean) request.getAttribute("isError");
}%>
<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />
<%Client client = (Client) session.getAttribute("client");%>

<style>
    .container-form {
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>
<section class="products" id="products">
    <jsp:include page="modal.jsp" />
    <jsp:include page="loginError.jsp" />
    <jsp:include page="modal_about.jsp" />
    <jsp:include page="singin.jsp" />
    <br>
    <div class="container-form">
        <div class="col-md-6">
            <form action="updateClient" method="post">
                <div class="form-group">
                    <label for="first_name" style="color: white">Nombres</label>
                    <input type="text" class="form-control" id="first_name" name="first_name" value="<%=client.getFirst_name()%>" required>
                </div>
                <div class="form-group">
                    <label for="last_name" style="color: white">Apellidos</label>
                    <input type="text" class="form-control" id="last_name" name="last_name" value="<%=client.getLast_name()%>" required>
                </div>
                <div class="form-group">
                    <label for="email" style="color: white">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="<%=client.getEmail()%>" required>
                </div>
                <div class="form-group">
                    <label for="phone_number" style="color: white">Teléfono</label>
                    <input type="text" class="form-control" id="phone_number" name="phone_number" value="<%=client.getPhone_number()%>" required>
                </div>
                <button type="submit" class="btn btn-primary btn-block">Actualizar</button>
            </form>
        </div>
    </div>
</section>

<section class="about" id="about">
    <div class="container-boton">
        <a href="${pageContext.request.contextPath}/checkout" class="nav-link">
            <span class="icon-wtsp">
                <i class="fa-solid fa-cart-shopping icon-wtsp"></i>
            </span>
        </a>
    </div>
</section>

<% if(isError == true) { %>
<script>
    $(document).ready(function() {
        $('#myError').modal('show');
    });
</script>
<% } %>
<jsp:include page="components/footer.jsp" />

</body>
</html>
