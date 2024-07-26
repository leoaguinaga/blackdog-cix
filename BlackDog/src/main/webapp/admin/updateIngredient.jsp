<%@ page import="pe.edu.utp.blackdog.model.Ingredient" %>
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Ingredient ingredient = (Ingredient) request.getAttribute("ingredient"); %>
<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Update ingredient</h1>
    </div>
    <div class="container mt-5">
        <form action="updateIngredient" method="post">
            <input type="text" id="id" name="id" value="<%= ingredient.getIngredient_id() %>" hidden>
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="<%= ingredient.getName() %>">
            </div>
            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" class="form-control" id="price" name="price" value="<%= ingredient.getPrice() %>">
            </div>
            <button type="submit" class="btn btn-primary">Add</button>
        </form>
    </div>
</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />
