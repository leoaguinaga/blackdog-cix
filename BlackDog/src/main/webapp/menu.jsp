<%@ page import="pe.edu.utp.blackdog.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    Map<Long, String> productIngredientsMap = (Map<Long, String>) request.getAttribute("productIngredientsMap");
%>

<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />

<section class="products" id="products">
    <div class="middle-text">
        <h4>Nuestros Productos</h4>

        <form style="display:inline-block; margin:0;">
            <input type="text" value="HAMBURGER" hidden name="type">
            <button formaction="menu" formmethod="post" type="submit" class="btn-primary" style="margin:0; padding:10px 15px; border-radius: 25px">Hamburguesas</button>
        </form>
        <form style="display:inline-block; margin:0;">
            <input type="text" value="CHAUFA" hidden name="type">
            <button formaction="menu" formmethod="post" type="submit" class="btn-primary" style="margin:0; padding:10px 15px; border-radius: 25px">Chaufas</button>
        </form>
        <form style="display:inline-block; margin:0;">
            <input type="text" value="SALCHIPAPA" hidden name="type">
            <button formaction="menu" formmethod="post" type="submit" class="btn-primary" style="margin:0; padding:10px 15px; border-radius: 25px">Salchipapas</button>
        </form>
        <form style="display:inline-block; margin:0;">
            <input type="text" value="DRINK" hidden name="type">
            <button formaction="menu" formmethod="post" type="submit" class="btn-primary" style="margin:0; padding:10px 15px; border-radius: 25px">Bebidas</button>
        </form>
    </div>

    <jsp:include page="modal.jsp" />
    <jsp:include page="singin.jsp" />
    <jsp:include page="modal_about.jsp" />

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

        .icon-extra {
            position: absolute;
            top: 10px;
            right: 10px;
            width: 30px;
            height: 30px;
            cursor: pointer;
        }
    </style>

    <section class="list-products" id="list-products">
        <% if (products != null && !products.isEmpty()) {%>
        <% for (Product product : products) {
            String ingredients = productIngredientsMap != null ? productIngredientsMap.get(product.getProduct_id()) : null;
        %>
        <div class="card mb-4 shadow-sm custom-card" style="position: relative;">
            <div class="custom-img-container">
                <img src="image?img=<%= product.getImage() %>" class="custom-img img-fluid" alt="Image" />
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/car" method="post">
                    <input type="hidden" name="action" value="add">
                    <h5 class="card-title"><%= product.getName() %></h5>
                    <h5 class="card-subtitle"><b>Precio: </b><%= product.getPrice() %></h5>
                    <% if (ingredients != null) { %>
                    <p class="card-text"><%= ingredients %></p>
                    <% } %>

                    <div class="quantity-control">
                        <button type="button" class="quantity-btn" onclick="decrement(<%= product.getProduct_id() %>)">-</button>
                        <input type="text" id="quantity-<%= product.getProduct_id() %>" name="quantity" value="1" readonly>
                        <input type="hidden" name="productId" value="<%= product.getProduct_id() %>">
                        <button type="button" class="quantity-btn" onclick="increment(<%= product.getProduct_id() %>)">+</button>
                    </div>
                    <button class="btn btn-primary" type="submit">Agregar al carrito</button>
                </form>

                <a href="addExtrasRedirect?id=<%=product.getProduct_id()%>" class="icon-extra">
                    <img style="height: 40px; width: 40px; margin-left: 10px;" src="img/extra_icon.png" alt="Extra">
                </a>
            </div>
        </div>
        <%} } else { %>
        <h2 style="color: white">No se encontraron productos en esta categoría</h2>
        <% } %>
    </section>

    <div class="container-boton">
        <a href="${pageContext.request.contextPath}/checkout" class="nav-link">
            <span class="icon-wtsp">
            <i class="fa-solid fa-cart-shopping icon-wtsp"></i>
            </span>
        </a>
    </div>
</section>

<jsp:include page="components/footer.jsp" />

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
