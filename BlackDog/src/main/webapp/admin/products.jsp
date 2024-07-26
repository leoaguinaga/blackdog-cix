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
<%@ page import="pe.edu.utp.blackdog.model.Product" %>
<%@ page import="pe.edu.utp.blackdog.model.enums.Product_Type" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Product> products = (List<Product>) request.getAttribute("products"); %>
<% List<Product_Type> productTypes = (List<Product_Type>) Product_Type.getProductTypes();%>
<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />

<style>
    .custom-img {
        max-width: 100px;
        max-height: 100px;
        width: auto;
        height: auto;
    }
</style>

<!-- Content Products -->
<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Administrate Products</h1>
    </div>
    <div class="card shadow mb-4">

        <div>
            <a href="products"><button class="btn-primary">Todos</button></a>
            <% for (Product_Type product_type : productTypes) { %>
            <form style="display:inline-block; margin:0;">
                <input type="hidden" value="<%=product_type%>" hidden name="product_type">
                <button formaction="filterProducts" formmethod="post" type="submit" class="btn-primary"><%=product_type.getDisplayName()%></button>
            </form>
            <% } %>
        </div>

        <a href="addProduct.jsp" class="btn btn-success btn-icon-split">
            <span class="text">Añadir nuevo producto</span>
        </a>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <% if (products != null && !products.isEmpty()) {%>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Type</th>
                        <th>Image</th>
                        <th colspan="2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Product product : products) {%>
                    <tr>
                        <td><%= product.getProduct_id() %></td>
                        <td><%= product.getName()%></td>
                        <td><%= product.getPrice() %></td>
                        <td><%= product.getProduct_type().getDisplayName() %></td>
                        <td><img src="${pageContext.request.contextPath}/image?img=<%= product.getImage() %>" class="img-fluid custom-img" alt="Image"/></td>
                        <% if(product.getProduct_type().equals(Product_Type.HAMBURGER)) {%>
                        <td> <a href="${pageContext.request.contextPath}/admin/seeProductIngredients?id=<%= product.getProduct_id() %>"> Ingredientes</a> </td>
                        <% } %>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/deleteProduct?id=<%= product.getProduct_id() %>"><img src="img/borrar.png" alt="delete image" height="30px"></a>
                        </td>
                    </tr>
                    <% } %>
                    <% } else  { %>
                    <h2>No se encontraron productos en la base de datos</h2>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />
