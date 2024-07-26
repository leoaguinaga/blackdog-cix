<%@ page import="java.util.List" %>
<%@ page import="pe.edu.utp.blackdog.model.Ingredient" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
    Long product_id = (Long) request.getAttribute("product_id");
%>

<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />

<section class="products" id="products">
    <div class="middle-text">
        <h4>Nuestros Extras</h4>
    </div>

    <jsp:include page="modal.jsp" />
    <jsp:include page="singin.jsp" />
    <jsp:include page="modal_about.jsp" />

    <style>
        .extras-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
            padding: 20px;
        }
        .extra-card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            padding: 20px;
            width: 250px;
        }
        .extra-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .extra-price {
            font-size: 16px;
            color: #666;
            margin-bottom: 15px;
        }
        .quantity-control {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 10px;
        }
        .quantity-btn {
            width: 30px;
            height: 30px;
            border: none;
            background-color: #f0f0f0;
            cursor: pointer;
            font-size: 18px;
        }
        .quantity-input {
            width: 40px;
            text-align: center;
            border: 1px solid #ddd;
            margin: 0 5px;
        }
        .add-to-cart {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            margin-top: 20px;
            cursor: pointer;
            width: 100%;
            border-radius: 4px;
        }
    </style>

    <form action="addExtra" method="post">
        <input type="hidden" name="product_id" value="<%=product_id%>">
        <div class="extras-container">
            <% if (ingredients != null && !ingredients.isEmpty()) {
                for (Ingredient ingredient : ingredients) { %>
            <div class="extra-card">
                <div class="extra-title"><%= ingredient.getName() %></div>
                <div class="extra-price">Precio: <%= ingredient.getPrice() %></div>
                <div class="quantity-control">
                    <input type="checkbox" id="ingredient-<%= ingredient.getIngredient_id() %>" name="ingredient_id" value="<%= ingredient.getIngredient_id() %>">
                    <button type="button" class="quantity-btn" onclick="decrement(<%= ingredient.getIngredient_id() %>)">-</button>
                    <input type="text" id="quantity-<%= ingredient.getIngredient_id() %>" name="quantity-<%= ingredient.getIngredient_id() %>" value="0" class="quantity-input" readonly>
                    <button type="button" class="quantity-btn" onclick="increment(<%= ingredient.getIngredient_id() %>)">+</button>
                </div>
            </div>
            <%  }
            } else { %>
            <h2 style="color: white">No se encontraron extras</h2>
            <% } %>
        </div>
        <button type="submit" class="add-to-cart">Agregar al Carrito</button>
    </form>

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
    function increment(ingredient_id) {
        var quantityInput = document.getElementById('quantity-' + ingredient_id);
        var checkBox = document.getElementById('ingredient-' + ingredient_id);
        var currentValue = parseInt(quantityInput.value);
        quantityInput.value = currentValue + 1;
        checkBox.checked = true;
    }

    function decrement(ingredient_id) {
        var quantityInput = document.getElementById('quantity-' + ingredient_id);
        var checkBox = document.getElementById('ingredient-' + ingredient_id);
        var currentValue = parseInt(quantityInput.value);
        if (currentValue > 0) {
            quantityInput.value = currentValue - 1;
            if (currentValue - 1 === 0) {
                checkBox.checked = false;
            }
        }
    }
</script>