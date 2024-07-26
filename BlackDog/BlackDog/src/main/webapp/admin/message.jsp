<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String message = (String) request.getAttribute("message"); %>

<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />
<!-- Content Products -->
<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Register Products</h1>
    </div>
    <div class="card shadow mb-4">
        <div class="card-body">
            <h1><%= message %></h1>
        </div>
        <button href="${pageContext.request.contextPath}/admin/products">Volver</button>
    </div>
    <jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />