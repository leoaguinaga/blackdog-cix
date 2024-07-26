package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.Customer_orderCrud;
import pe.edu.utp.blackdog.model.Customer_order;
import pe.edu.utp.blackdog.model.enums.State;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
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
        String query = "{CALL RegisterOrder(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, customerOrder.getClient().getClient_id());
            cs.setTimestamp(2, Timestamp.valueOf(customerOrder.getOrder_date()));
            cs.setString(3, customerOrder.getAddress());
            cs.setDouble(4, customerOrder.getAmount());
            cs.setString(5, String.valueOf(customerOrder.getState()));
            cs.setString(6, customerOrder.getEvidence_image());
            cs.executeUpdate();
        }
    }

    @Override
    public Stack<Customer_order> getAllOrders() throws SQLException, NamingException {
        Stack<Customer_order> customer_orders = new Stack<>();
        String query = "{CALL GetAllOrders()}";
        ClientDAO clientDAO = new ClientDAO();
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                customer_orders.push(Customer_order.createOrder(
                        rs.getLong("customer_order_id"),
                        clientDAO.getClientById(rs.getLong("client_id")),
                        rs.getTimestamp("order_date").toLocalDateTime(),
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
        String query = "{CALL GetOrderById(?)}";
        Customer_order customer_order = null;
        ClientDAO clientDAO = new ClientDAO();
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, customer_order_id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    customer_order = Customer_order.createOrder(
                            rs.getLong("customer_order_id"),
                            clientDAO.getClientById(rs.getLong("client_id")),
                            rs.getTimestamp("order_date").toLocalDateTime(),
                            rs.getString("address"),
                            rs.getDouble("amount"),
                            State.valueOf(rs.getString("state")),
                            rs.getString("evidence_image")
                    );
                } else {
                    throw new SQLException(String.format("No se encontró una orden con el ID %d en la base de datos.", customer_order_id));
                }
            }
        }
        return customer_order;
    }

    @Override
    public void updateOrder(Customer_order customerOrder, long customer_order_id) throws SQLException {
        String query = "{CALL UpdateOrder(?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, customerOrder.getClient().getClient_id());
            cs.setTimestamp(2, Timestamp.valueOf(customerOrder.getOrder_date()));
            cs.setString(3, customerOrder.getAddress());
            cs.setDouble(4, customerOrder.getAmount());
            cs.setString(5, String.valueOf(customerOrder.getState()));
            cs.setString(6, customerOrder.getEvidence_image());
            cs.setLong(7, customer_order_id);
            cs.executeUpdate();
        }
    }

    @Override
    public void deleteOrder(long customer_order_id) throws SQLException {
        String query = "{CALL DeleteOrder(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, customer_order_id);
            cs.executeUpdate();
        }
    }

    @Override
    public Customer_order getLastCustomer_order() throws SQLException, NamingException {
        String query = "{CALL GetLastCustomerOrder()}";
        Customer_order customer_order = null;
        ClientDAO clientDAO = new ClientDAO();
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            if (rs.next()) {
                customer_order = Customer_order.createOrder(
                        rs.getLong("customer_order_id"),
                        clientDAO.getClientById(rs.getLong("client_id")),
                        rs.getTimestamp("order_date").toLocalDateTime(),
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
        String query = "{CALL GetOrdersByState(?)}";
        ClientDAO clientDAO = new ClientDAO();
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, stateStr);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    customer_orders.add(Customer_order.createOrder(
                            rs.getLong("customer_order_id"),
                            clientDAO.getClientById(rs.getLong("client_id")),
                            rs.getTimestamp("order_date").toLocalDateTime(),
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
