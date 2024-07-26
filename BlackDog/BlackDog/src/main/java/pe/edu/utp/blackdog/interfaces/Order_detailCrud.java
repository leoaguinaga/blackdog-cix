package pe.edu.utp.blackdog.interfaces;

import pe.edu.utp.blackdog.dao.Customer_orderDAO;
import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.model.Order_detail;

import javax.naming.NamingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface Order_detailCrud {
    void registerOrder_detail(Order_detail orderDetail) throws SQLException, NamingException;

    List<Order_detail> getAllOrder_details() throws SQLException, NamingException;

    List<Order_detail> getOrderDetailsByOrderId(long customer_order_id) throws SQLException, NamingException;

    List<Order_detail> getOrderDetailsByProductId(long product_id) throws SQLException, NamingException;
}
