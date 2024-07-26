<%@ page import="pe.edu.utp.blackdog.model.Customer_order" %>
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
<%@ page import="pe.edu.utp.blackdog.model.Order_detail" %>
<%@ page import="pe.edu.utp.blackdog.model.enums.State" %>
<%@ page import="pe.edu.utp.blackdog.dao.Product_ingredientDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="pe.edu.utp.blackdog.model.Product_ingredient" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pe.edu.utp.blackdog.dao.IngredientDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Order_detail> order_detailList = (List<Order_detail>) request.getAttribute("order_detailList"); %>
<% Customer_order customer_order = (Customer_order) request.getAttribute("customer_order"); %>
<% try {
    Product_ingredientDAO product_ingredientDAO = new Product_ingredientDAO();
    IngredientDAO ingredientDAO = new IngredientDAO();
    List<Product_ingredient> product_ingredients = new ArrayList<Product_ingredient>();
%>

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

<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Order detail</h1>
    </div>

    <div class="card shadow mb-4">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <% if (order_detailList != null && !order_detailList.isEmpty()) {%>

                    El pedido #<%=customer_order.getCustomer_order_id()%> se realizó el <%=customer_order.getOrderDateOnly()%> a las <%= customer_order.getOrderTimeOnly()%> y se encuentra <%=customer_order.getState().getDisplayName()%>.
                    <br>
                    <strong>Nombre del cliente: </strong><%=customer_order.getClient().getFirst_name()%> <%=customer_order.getClient().getLast_name()%>.
                    <br>
                    <strong>Dirección: </strong><%=customer_order.getAddress()%>.
                    <br>
                    <tbody>
                    <thead>
                    <tr>
                        <th colspan="2"><strong>Producto</strong></th>
                        <th><strong>Ingredientes</strong></th>
                        <th><strong>Cantidad</strong></th>
                    </tr>
                    </thead>
                    <% for (Order_detail orderDetail : order_detailList) { %>
                    <tr>
                        <td><img src="${pageContext.request.contextPath}/image?img=<%= orderDetail.getProduct().getImage() %>" class="img-fluid custom-img" alt="Image" height="100px"/></td>
                        <td><%= orderDetail.getProduct().getName()%></td>
                        <td>
                            <%
                                product_ingredients = product_ingredientDAO.getProductIngredientsByProductId(orderDetail.getProduct().getProduct_id());
                                StringBuilder ingredientsList = new StringBuilder();
                                for (Product_ingredient product_ingredient : product_ingredients) {
                                    ingredientsList.append(product_ingredient.getQuantity())
                                            .append(" ")
                                            .append(ingredientDAO.getIngredientNameById(product_ingredient.getIngredient_id()))
                                            .append(", ");
                                }
                                if (ingredientsList.length() > 0) {
                                    ingredientsList.setLength(ingredientsList.length() - 2); // Eliminar la última coma y espacio
                                }
                                out.print(ingredientsList.toString());
                            %>
                        </td>
                        <td><%= orderDetail.getQuantity()%></td>
                    </tr>
                    <% } %>
                    <% } else  { %>
                    <h2>No se encontraron ordenes en la base de datos</h2>
                    <% } %>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th colspan="3"><strong>Total</strong></th>
                        <td><%=customer_order.getAmount()%></td>
                    </tr>
                    </tfoot>
                </table>

                <%
                    State currentState = customer_order.getState();
                    if (currentState == State.ON_HOLD) {
                %>
                <a href="${pageContext.request.contextPath}/admin/alterStateOrder?state=ON_PROCESS&id=<%= customer_order.getCustomer_order_id() %>">Preparar pedido</a>
                <br>
                <a href="${pageContext.request.contextPath}/admin/alterStateOrder?state=CANCELED&id=<%= customer_order.getCustomer_order_id() %>">Cancelar pedido</a>
                <br>
                <%
                } else if (currentState == State.ON_PROCESS) {
                %>
                <a href="${pageContext.request.contextPath}/admin/alterStateOrder?state=ON_THE_WAY&id=<%= customer_order.getCustomer_order_id() %>">Pedido en camino</a>
                <br>
                <a href="${pageContext.request.contextPath}/admin/alterStateOrder?state=CANCELED&id=<%= customer_order.getCustomer_order_id() %>">Cancelar pedido</a>
                <br>
                <%
                } else if (currentState == State.ON_THE_WAY) {
                %>
                <a href="${pageContext.request.contextPath}/admin/alterStateOrder?state=FINISHED&id=<%= customer_order.getCustomer_order_id() %>">Entregado/Finalizar</a>
                <br>
                <%
                    }
                %>
                <strong>Evidencia de pago: </strong>
                <img src="${pageContext.request.contextPath}/image?img=<%= customer_order.getEvidence_image() %>" class="img-fluid custom-img" alt="Image" height="150px" width="80px"/>
            </div>
        </div>
    </div>

</div>
<jsp:include page="components/footer.jsp" />
<jsp:include page="components/scripts.jsp" />
<%} catch (SQLException e) {
    throw new RuntimeException(e);
} catch (NamingException e) {
    throw new RuntimeException(e);
}%>
