<%@ page import="java.util.List" %>
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
<%@ page import="pe.edu.utp.blackdog.model.Ingredient" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients"); %>

<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />
<!-- Content Products -->
<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Administrate Ingredients</h1>
    </div>
    <div class="card shadow mb-4">
        <a href="addIngredient.jsp" class="btn btn-success btn-icon-split">
            <span class="text">Añadir nuevo ingrediente</span>
        </a>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <% if (ingredients != null && !ingredients.isEmpty()) { %>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Ingredient ingredient : ingredients) { %>
                    <tr>
                        <td><%= ingredient.getIngredient_id() %></td>
                        <td><%= ingredient.getName()%></td>
                        <td><%= ingredient.getPrice() %></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/deleteIngredient?id=<%= ingredient.getIngredient_id() %>"><img src="img/borrar.png" alt="delete image" height="30px"></a>
                            <a href="${pageContext.request.contextPath}/admin/updateIngredientRedirect?id=<%= ingredient.getIngredient_id() %>"><img src="img/editar.png" alt="update image" height="30px"></a>
                        </td>
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
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />
