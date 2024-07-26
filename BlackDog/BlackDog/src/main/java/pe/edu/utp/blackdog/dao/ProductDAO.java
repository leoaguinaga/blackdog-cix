package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.ProductCrud;
import pe.edu.utp.blackdog.model.Product;
import pe.edu.utp.blackdog.model.Product_Type;
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
        String query = "INSERT INTO product (name, image, price, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getImage());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getProduct_type().toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo registrar el producto en la base de datos.");
            }
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
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
        String query = "SELECT * FROM product WHERE type = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, productType.toString());
            try (ResultSet rs = ps.executeQuery()) {
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
        String query = "SELECT * FROM product WHERE product_id = ?";
        Product product = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, product_id);
            try (ResultSet rs = ps.executeQuery()) {
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
        String query = "UPDATE product SET name = ?, image = ?, price = ?, type = ? WHERE product_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getImage());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getProduct_type().toString());
            ps.setLong(5, product_id);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteProduct(long product_id) throws SQLException {
        String query = "DELETE FROM product WHERE product_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, product_id);
            ps.executeUpdate();
        }
    }

    @Override
    public Product getLastProduct() throws SQLException {
        String query = "SELECT * FROM product ORDER BY product_id DESC LIMIT 1";
        Product product = null;
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
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
