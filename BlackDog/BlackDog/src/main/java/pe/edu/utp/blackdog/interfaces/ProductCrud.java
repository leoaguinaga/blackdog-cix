package pe.edu.utp.blackdog.interfaces;

import pe.edu.utp.blackdog.model.Product;
import pe.edu.utp.blackdog.model.Product_Type;

import java.sql.SQLException;
import java.util.List;

public interface ProductCrud {
    void registerProduct(Product product) throws SQLException;

    List<Product> getAllProducts() throws SQLException;

    List<Product> getProductsByType(Product_Type productType) throws SQLException;

    Product getProductById(long product_id) throws SQLException;

    void updateProduct(Product product, long product_id) throws SQLException;

    void deleteProduct(long product_id) throws SQLException;

    Product getLastProduct() throws SQLException;
}
