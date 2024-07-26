package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.Order_detailCrud;
import pe.edu.utp.blackdog.model.Order_detail;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
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
        String query = "{CALL RegisterOrderDetail(?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, orderDetail.getCustomerOder().getCustomer_order_id());
            cs.setLong(2, orderDetail.getProduct().getProduct_id());
            cs.setInt(3, orderDetail.getQuantity());
            int rowsAffected = cs.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el detalle de la orden en la base de datos.");
            }
        }
    }

    @Override
    public List<Order_detail> getAllOrder_details() throws SQLException, NamingException {
        List<Order_detail> orderDetails = new ArrayList<>();
        String query = "{CALL GetAllOrderDetails()}";
        Customer_orderDAO customerOrderDAO = new Customer_orderDAO();
        ProductDAO productDAO = new ProductDAO();
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                orderDetails.add(Order_detail.createOrderDetail(
                        customerOrderDAO.getOrderById(rs.getLong("customer_order")),
                        productDAO.getProductById(rs.getLong("product_id")),
                        rs.getInt("quantity")
                ));
            }
            if (orderDetails.isEmpty()) {
                throw new SQLException("No se encontraron los detalles del pedido en la base de datos.");
            }
        } finally {
            customerOrderDAO.close();
            productDAO.close();
        }
        return orderDetails;
    }

    @Override
    public List<Order_detail> getOrderDetailsByOrderId(long customer_order_id) throws SQLException, NamingException {
        String query = "{CALL GetOrderDetailsByOrderId(?)}";
        List<Order_detail> order_details = new ArrayList<>();
        Customer_orderDAO customerOrderDAO = new Customer_orderDAO();
        ProductDAO productDAO = new ProductDAO();
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, customer_order_id);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    order_details.add(Order_detail.createOrderDetail(
                            customerOrderDAO.getOrderById(rs.getLong("customer_order")),
                            productDAO.getProductById(rs.getLong("product_id")),
                            rs.getInt("quantity")
                    ));
                }
                if (order_details.isEmpty()) {
                    throw new SQLException("No se encontraron los detalles del pedido en la base de datos.");
                }
            }
        } finally {
            customerOrderDAO.close();
            productDAO.close();
        }
        return order_details;
    }

    @Override
    public List<Order_detail> getOrderDetailsByProductId(long product_id) throws SQLException, NamingException {
        String query = "{CALL GetOrderDetailsByProductId(?)}";
        List<Order_detail> order_details = new ArrayList<>();
        Customer_orderDAO customerOrderDAO = new Customer_orderDAO();
        ProductDAO productDAO = new ProductDAO();
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, product_id);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    order_details.add(Order_detail.createOrderDetail(
                            customerOrderDAO.getOrderById(rs.getLong("customer_order")),
                            productDAO.getProductById(rs.getLong("product_id")),
                            rs.getInt("quantity")
                    ));
                }
                if (order_details.isEmpty()) {
                    throw new SQLException("No se encontraron los detalles del pedido en la base de datos.");
                }
            }
        } finally {
            customerOrderDAO.close();
            productDAO.close();
        }
        return order_details;
    }
}
