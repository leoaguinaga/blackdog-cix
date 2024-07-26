<%@ page import="java.util.List" %>
<%@ page import="pe.edu.utp.blackdog.model.Product" %>
<%@ page import="pe.edu.utp.blackdog.model.Ingredient" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Ingredient> ingredients =  ( List<Ingredient>) request.getAttribute("ingredients"); %>
<% Product product = (Product) request.getAttribute("product"); %>
<% HashMap<Long, Integer> quantityIngredient = (HashMap<Long, Integer>) request.getAttribute("quantityIngredient"); %>
<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Add Ingredients</h1>
    </div>
    <div class="container mt-5">

        <div class="form-group">
            <label>Name: <%=product.getName()%></label>
        </div>
        <div class="form-group">
            <label>Price: <%=product.getPrice()%></label>
        </div>

        <div class="card shadow mb-4">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <% if (ingredients != null && !ingredients.isEmpty()) { %>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Quantity</th>
                        </tr>
                        </thead>
                        <tbody>

                        <% for (Ingredient ingredient : ingredients) { %>
                        <tr>

                            <td><%= ingredient.getName() %></td>
                            <td><%= quantityIngredient.get(ingredient.getIngredient_id()) %> </td>
                        </tr>
                        <% } %>

                        <% } else  { %>
                        <h2>No se encontraron ingredientes en la base de datos</h2>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />
