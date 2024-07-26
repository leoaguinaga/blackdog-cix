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
<%@ page import="pe.edu.utp.blackdog.model.Worker" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Worker> workers = (List<Worker>) request.getAttribute("workers"); %>
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
        <a href="addWorker.jsp" class="btn btn-success btn-icon-split">
            <span class="text">Añadir nuevo trabajador</span>
        </a>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <% if (workers != null && !workers.isEmpty()) { %>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Worker worker : workers) { %>
                    <tr>
                        <td><%= worker.getWorker_id() %></td>
                        <td><%= worker.getFull_name()%></td>
                        <td><%= worker.getEmail() %></td>
                        <td><%= worker.getRole().getDisplayName() %></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/deleteWorker?id=<%= worker.getWorker_id() %>"><img src="img/borrar.png" alt="delete image" height="30px"></a>
                            <a href="${pageContext.request.contextPath}/admin/updateWorkerRedirect?id=<%= worker.getWorker_id() %>"><img src="img/editar.png" alt="update image" height="30px"></a>
                        </td>
                    </tr>
                    <% } %>
                    <% } else  { %>
                    <h2>No se encontraron trabajadores en la base de datos</h2>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />
