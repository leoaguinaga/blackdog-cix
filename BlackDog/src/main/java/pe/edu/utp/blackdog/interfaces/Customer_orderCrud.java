package pe.edu.utp.blackdog.interfaces;

import pe.edu.utp.blackdog.model.Customer_order;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Stack;

public interface Customer_orderCrud {
    void registerOrder(Customer_order customerOrder) throws SQLException;

    Stack<Customer_order> getAllOrders() throws SQLException, NamingException;

    Customer_order getOrderById(long customer_order_id) throws SQLException, NamingException;

    void updateOrder(Customer_order customerOrder, long customer_order_id) throws SQLException;

    void deleteOrder(long customer_order_id) throws SQLException;

    Customer_order getLastCustomer_order() throws SQLException, NamingException;
}
