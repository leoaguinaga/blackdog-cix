package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.Customer_orderCrud;
import pe.edu.utp.blackdog.model.Customer_order;
import pe.edu.utp.blackdog.model.State;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Customer_orderDAO implements AutoCloseable, Customer_orderCrud {
    private final Connection cnn;

    public Customer_orderDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public void registerOrder(Customer_order customerOrder) throws SQLException {
        String query = "INSERT INTO customer_order (client_id, order_date, address, amount, state, evidence_image) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, customerOrder.getClient().getClient_id());
            ps.setTimestamp(2, Timestamp.valueOf(customerOrder.getOrder_date()));
            ps.setString(3, customerOrder.getAddress());
            ps.setDouble(4, customerOrder.getAmount());
            ps.setString(5, String.valueOf(customerOrder.getState()));
            ps.setString(6, customerOrder.getEvidence_image());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo registrar la orden en la base de datos.");
            }
        }
    }

    @Override
    public Stack<Customer_order> getAllOrders() throws SQLException, NamingException {
        Stack<Customer_order> customer_orders = new Stack<>();
        String query = "SELECT * FROM customer_order ORDER BY order_date DESC";
        ClientDAO clientDAO = new ClientDAO();
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                customer_orders.push(Customer_order.createOrder(
                        rs.getLong("customer_order_id"),
                        clientDAO.getClientById(rs.getLong("client_id")),
                        rs.getTimestamp("order_date").toLocalDateTime(), // Conversión de Timestamp a LocalDateTime
                        rs.getString("address"),
                        rs.getDouble("amount"),
                        State.valueOf(rs.getString("state")),
                        rs.getString("evidence_image")
                ));
            }
            clientDAO.close();
            if (customer_orders.isEmpty()) {
                throw new SQLException("No se encontraron órdenes en la base de datos.");
            }
        }
        return customer_orders;
    }


    @Override
    public Customer_order getOrderById(long customer_order_id) throws SQLException, NamingException {
        String query = "SELECT * FROM customer_order WHERE customer_order_id = ?";
        Customer_order customer_order = null;
        ClientDAO clientDAO = new ClientDAO();
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, customer_order_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer_order = Customer_order.createOrder(
                            rs.getLong("customer_order_id"),
                            clientDAO.getClientById(rs.getLong("client_id")),
                            rs.getTimestamp("order_date").toLocalDateTime(), // Conversión de Timestamp a LocalDateTime
                            rs.getString("address"),
                            rs.getDouble("amount"),
                            State.valueOf(rs.getString("state")),
                            rs.getString("evidence_image")
                    );
                    clientDAO.close();
                } else {
                    throw new SQLException(String.format("No se encontró una orden con el ID %d en la base de datos.", customer_order_id));
                }
            }
        }
        return customer_order;
    }

    @Override
    public void updateOrder(Customer_order customerOrder, long customer_order_id) throws SQLException {
        String query = "UPDATE customer_order SET client_id = ?, order_date = ?, address = ?, amount = ?, state = ?, evidence_image = ? WHERE customer_order_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, customerOrder.getClient().getClient_id());
            ps.setTimestamp(2, Timestamp.valueOf(customerOrder.getOrder_date())); // Conversión a Timestamp
            ps.setString(3, customerOrder.getAddress());
            ps.setDouble(4, customerOrder.getAmount());
            ps.setString(5, String.valueOf(customerOrder.getState()));
            ps.setString(6, customerOrder.getEvidence_image());
            ps.setLong(7, customer_order_id);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteOrder(long customer_order_id) throws SQLException {
        String query = "DELETE FROM customer_order WHERE customer_order_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, customer_order_id);
            ps.executeUpdate();
        }
    }

    @Override
    public Customer_order getLastCustomer_order() throws SQLException, NamingException {
        String query = "SELECT * FROM customer_order ORDER BY customer_order_id DESC LIMIT 1";
        Customer_order customer_order = null;
        ClientDAO clientDAO = new ClientDAO();
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                customer_order = Customer_order.createOrder(
                        rs.getLong("customer_order_id"),
                        clientDAO.getClientById(rs.getLong("client_id")),
                        rs.getTimestamp("order_date").toLocalDateTime(), // Conversión de Timestamp a LocalDateTime
                        rs.getString("address"),
                        rs.getDouble("amount"),
                        State.valueOf(rs.getString("state")),
                        rs.getString("evidence_image")
                );
            } else {
                throw new SQLException("No se encontró el último producto en la base de datos.");
            }
        }
        return customer_order;
    }

    public Stack<Customer_order> getOrdersByState(State state) throws SQLException, NamingException {
        String stateStr = state.toString();
        Stack<Customer_order> customer_orders = new Stack<>();
        String query = "SELECT * FROM customer_order WHERE state = ?";
        ClientDAO clientDAO = new ClientDAO();
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, stateStr);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    customer_orders.add(Customer_order.createOrder(
                            rs.getLong("customer_order_id"),
                            clientDAO.getClientById(rs.getLong("client_id")),
                            rs.getTimestamp("order_date").toLocalDateTime(), // Conversión de Timestamp a LocalDateTime
                            rs.getString("address"),
                            rs.getDouble("amount"),
                            State.valueOf(rs.getString("state")),
                            rs.getString("evidence_image")
                    ));
                }
                clientDAO.close();
                if (customer_orders.isEmpty()) {
                    throw new SQLException("No se encontraron órdenes de este tipo en la base de datos.");
                }
            }
        }
        return customer_orders;
    }
}
