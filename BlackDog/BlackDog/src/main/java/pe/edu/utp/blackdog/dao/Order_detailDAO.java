package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.Order_detailCrud;
import pe.edu.utp.blackdog.model.Ingredient;
import pe.edu.utp.blackdog.model.Order_detail;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order_detailDAO implements AutoCloseable, Order_detailCrud {
    private final Connection cnn;

    public Order_detailDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public void registerOrder_detail(Order_detail orderDetail) throws SQLException, NamingException {
        String query = "INSERT INTO order_detail (customer_order, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, orderDetail.getCustomerOder().getCustomer_order_id());
            ps.setLong(2, orderDetail.getProduct().getProduct_id());
            ps.setInt(3, orderDetail.getQuantity());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el detalle de la orden en la base de datos.");
            }
        }
    }

    @Override
    public List<Order_detail> getAllOrder_details() throws SQLException, NamingException {
        List<Order_detail> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM order_detail";
        Customer_orderDAO customerOrderDAO = new Customer_orderDAO();
        ProductDAO productDAO = new ProductDAO();
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orderDetails.add(Order_detail.createOrderDetail(
                        customerOrderDAO.getOrderById(rs.getLong("customer_order")),
                        productDAO.getProductById(rs.getLong("product_id")),
                        rs.getInt("quantity")
                ));
                customerOrderDAO.close();
                productDAO.close();
            } if (orderDetails.isEmpty()) {
                throw new SQLException("No se encontraron los detalles del pedido en la base de datos.");
            }
        }
        return orderDetails;
    }

    @Override
    public List<Order_detail> getOrderDetailsByOrderId(long customer_order_id) throws SQLException, NamingException {
        String query = "SELECT * FROM order_detail WHERE customer_order = ?";
        List<Order_detail> order_details = new ArrayList<>();
        Customer_orderDAO customerOrderDAO = new Customer_orderDAO();
        ProductDAO productDAO = new ProductDAO();
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, customer_order_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                order_details.add(Order_detail.createOrderDetail(
                        customerOrderDAO.getOrderById(rs.getLong("customer_order")),
                        productDAO.getProductById(rs.getLong("product_id")),
                        rs.getInt("quantity")
                ));
            } if (order_details.isEmpty()) {
                throw new SQLException("No se encontraron los detalles del pedido en la base de datos.");
            }
        }
        customerOrderDAO.close();
        productDAO.close();
        return order_details;
    }

    @Override
    public List<Order_detail> getOrderDetailsByProductId(long product_id) throws SQLException, NamingException {
        String query = "SELECT * FROM product WHERE product_id = ?";
        List<Order_detail> order_details = new ArrayList<>();
        Customer_orderDAO customerOrderDAO = new Customer_orderDAO();
        ProductDAO productDAO = new ProductDAO();
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, product_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    order_details.add(Order_detail.createOrderDetail(
                            customerOrderDAO.getOrderById(rs.getLong("customer_order")),
                            productDAO.getProductById(rs.getLong("product_id")),
                            rs.getInt("quantity")
                    ));
                    customerOrderDAO.close();
                    productDAO.close();
                } if (order_details.isEmpty()) {
                    throw new SQLException("No se encontraron los detalles del pedido en la base de datos.");
                }
            }
        }
        return order_details;
    }

}
