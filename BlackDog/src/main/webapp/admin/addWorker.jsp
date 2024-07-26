<%@ page import="pe.edu.utp.blackdog.model.enums.Role" %>
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
<% List<Role> roles = Role.getRoles();%>
<jsp:include page="components/header.jsp" />
<jsp:include page="components/sidebar.jsp" />
<jsp:include page="components/topbar.jsp" />

<div class="container-fluid">

  <!-- Page Heading -->
  <div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">Añadir trabajador</h1>
  </div>
  <div class="container mt-5">
    <form action="addWorker" method="post">
      <div class="form-group">
        <label for="full_name">Ingresa el nombre completo del trabajador</label>
        <input type="text" class="form-control" id="full_name" name="full_name" placeholder="Ingresa el nombre del trabajador">
      </div>
      <div class="form-group">
        <div class="form-group">
          <label for="role">Selecciona el rol del trabajador</label>
          <select name="role" id="role">
            <% for (Role role : roles) { %>
            <option value="<%= role.toString() %>"> <%= role.getDisplayName() %> </option>
            <% } %>
          </select>
        </div>
        <label for="email">Ingresa el email del trabajador</label>
        <input type="text" class="form-control" id="email" name="email" placeholder="Introduce the ingredients name">
      </div>
      <div class="form-group">
        <label for="password" >Ingresa la contraseña del trabajador</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Introduce the ingredients name">
      </div>
      <button type="submit" class="btn btn-primary">Add</button>
    </form>
  </div>
</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />