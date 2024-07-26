<%@ page import="pe.edu.utp.blackdog.model.enums.Product_Type" %>
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
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%List<Product_Type> productTypeList = Product_Type.getProductTypes();%>
<%double price = 0.0;%>
<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />
<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Add Products</h1>
    </div>
    <div class="container mt-5">
        <form action="registerProduct" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Introduce the product name">
            </div>
            <div class="form-group">
                <label for="image">Image</label>
                <input type="file" class="form-control" id="image" name="image" placeholder="Introduce the image product">
            </div>
            <div class="form-group">
                <label for="type">Type</label>
                <select class="form-control" id="type" name="type">
                    <%for (Product_Type productType : productTypeList) { %>
                    <option value="<%=productType.toString()%>"><%=productType.getDisplayName()%></option>
                    <%}%>
                </select>
            </div>
            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" class="form-control" id="price" name="price" placeholder="Introduce the ingredients price">
            </div>
            <button type="submit" class="btn btn-primary">Add</button>
        </form>
    </div>
</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />