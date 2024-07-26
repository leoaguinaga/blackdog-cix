package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.ProductCrud;
import pe.edu.utp.blackdog.model.Product;
import pe.edu.utp.blackdog.model.enums.Product_Type;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements AutoCloseable, ProductCrud {
    private final Connection cnn;

    public ProductDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public void registerProduct(Product product) throws SQLException {
        String query = "{CALL RegisterProduct(?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, product.getName());
            cs.setString(2, product.getImage());
            cs.setDouble(3, product.getPrice());
            cs.setString(4, product.getProduct_type().toString());
            int rowsAffected = cs.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo registrar el producto en la base de datos.");
            }
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "{CALL GetAllProducts()}";
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                products.add(Product.createProduct(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getDouble("price"),
                        Product_Type.valueOf(rs.getString("type"))
                ));
            }
            if (products.isEmpty()) {
                throw new SQLException("No se encontraron productos en la base de datos.");
            }
        }
        return products;
    }

    @Override
    public List<Product> getProductsByType(Product_Type productType) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "{CALL GetProductsByType(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, productType.toString());
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    products.add(Product.createProduct(
                            rs.getLong("product_id"),
                            rs.getString("name"),
                            rs.getString("image"),
                            rs.getDouble("price"),
                            Product_Type.valueOf(rs.getString("type"))
                    ));
                }
            }
        }
        return products;
    }

    @Override
    public Product getProductById(long product_id) throws SQLException {
        String query = "{CALL GetProductById(?)}";
        Product product = null;
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, product_id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    product = Product.createProduct(
                            rs.getLong("product_id"),
                            rs.getString("name"),
                            rs.getString("image"),
                            rs.getDouble("price"),
                            Product_Type.valueOf(rs.getString("type"))
                    );
                } else {
                    throw new SQLException(String.format("No se encontró un producto con el ID %d en la base de datos.", product_id));
                }
            }
        }
        return product;
    }

    @Override
    public void updateProduct(Product product, long product_id) throws SQLException {
        String query = "{CALL UpdateProduct(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, product.getName());
            cs.setString(2, product.getImage());
            cs.setDouble(3, product.getPrice());
            cs.setString(4, product.getProduct_type().toString());
            cs.setLong(5, product_id);
            int rowsAffected = cs.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el producto en la base de datos.");
            }
        }
    }

    @Override
    public void deleteProduct(long product_id) throws SQLException {
        String query = "{CALL DeleteProduct(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, product_id);
            cs.executeUpdate();
        }
    }

    @Override
    public Product getLastProduct() throws SQLException {
        String query = "{CALL GetLastProduct()}";
        Product product = null;
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            if (rs.next()) {
                product = Product.createProduct(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getDouble("price"),
                        Product_Type.valueOf(rs.getString("type"))
                );
            } else {
                throw new SQLException("No se encontró el último producto en la base de datos.");
            }
        }
        return product;
    }
}
