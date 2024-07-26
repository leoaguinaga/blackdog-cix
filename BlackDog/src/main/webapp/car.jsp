<%@ page import="pe.edu.utp.blackdog.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Obtener la lista de productos y las cantidades
    List<Product> products = (List<Product>) request.getAttribute("products");
    List<Integer> quantities = (List<Integer>) request.getAttribute("quantities");
    double totalPrice = (double) request.getAttribute("totalPrice");
    String modal;
    String name = (String) session.getAttribute("name");

    if(name==null){
        modal = "myModal";
    } else {
        modal = "modal_car";
    }
%>

<style>
    .quantity-control {
        display: flex;
        align-items: center;
        margin-bottom: 10px;
    }

    .quantity-btn {
        width: 30px;
        height: 30px;
        text-align: center;
        line-height: 30px;
        border: 1px solid #ccc;
        background-color: #f9f9f9;
        cursor: pointer;
    }

    .quantity-btn:hover {
        background-color: #e9e9e9;
    }

    .quantity-control input {
        width: 50px;
        text-align: center;
        border: 1px solid #ccc;
        margin: 0 5px;
    }

    .quantity-control .btn-modify {
        margin-left: 10px;
    }
</style>

<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />

<section class="products" id="products">
    <jsp:include page="modal.jsp" />
    <jsp:include page="modal_car.jsp" />
    <jsp:include page="singin.jsp" />

    <div class="container">
        <br><br>
        <h3 class="text-white">¡Carrito de compra!</h3>
        <br>

        <div class="row">
            <%
                if (products != null && quantities != null && !products.isEmpty()) {
                    for (int i = 0; i < products.size(); i++) {
                        Product product = products.get(i);
                        int quantity = quantities.get(i);
                        double productTotal = product.getPrice() * quantity;
            %>
            <div class="col-md-4">
                <div class="card mb-4 shadow-sm custom-card">
                    <div class="custom-img-container">
                        <img src="${pageContext.request.contextPath}/image?img=<%= product.getImage() %>" class="custom-img img-fluid" alt="<%= product.getName() %>">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title"><%= product.getName() %></h5>
                        <form action="${pageContext.request.contextPath}/car" method="post">
                            <input type="hidden" name="action" value="modify">
                            <p class="card-text">Cantidad:</p>
                            <div class="quantity-control">
                                <button type="button" class="quantity-btn" onclick="decrement(<%= product.getProduct_id() %>)">-</button>
                                <input type="text" id="quantity-<%= product.getProduct_id() %>" name="quantity" value="<%=quantity%>" readonly>
                                <input type="hidden" name="productId" value="<%= product.getProduct_id() %>">
                                <button type="button" class="quantity-btn" onclick="increment(<%= product.getProduct_id() %>)">+</button>
                                <button class="btn btn-primary btn-modify" type="submit">Modificar</button>
                            </div>
                        </form>

                        <p class="card-text">Precio unitario: $<%= product.getPrice() %></p>
                        <p class="card-text">Subtotal: $<%= productTotal %></p>
                        <form action="${pageContext.request.contextPath}/car" method="post">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="productId" value="<%= product.getProduct_id() %>">
                            <div class="text-right">
                                <button type="submit" class="btn btn-danger">Eliminar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>

        <div class="row">
            <div class="col-12 text-right">
                <h4 style="color: white"><strong>Total:</strong> $<%= totalPrice %></h4>
            </div>
        </div>

        <a href="#" data-toggle="modal" data-target="#<%=modal%>" class="btn btn-primary nav-link">Registrar compra!</a>
        <% } else { %>
        <div class="row">
            <div class="col-12 text-center">
                <p class="text-white">No se encontraron productos agregados al carrito.</p>
            </div>
        </div>
        <% } %>
    </div>

    <jsp:include page="components/footer.jsp" />
</section>

<script>
    function increment(productId) {
        var quantityInput = document.getElementById('quantity-' + productId);
        var currentValue = parseInt(quantityInput.value);
        quantityInput.value = currentValue + 1;
    }

    function decrement(productId) {
        var quantityInput = document.getElementById('quantity-' + productId);
        var currentValue = parseInt(quantityInput.value);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
        }
    }
</script>

</body>
</html>
