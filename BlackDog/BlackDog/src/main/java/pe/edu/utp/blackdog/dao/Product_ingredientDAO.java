package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.Product_IngredientCrud;
import pe.edu.utp.blackdog.model.Product_ingredient;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product_ingredientDAO implements AutoCloseable, Product_IngredientCrud {
    private final Connection cnn;

    public Product_ingredientDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public void registerProductIngredient(Product_ingredient productIngredient) throws SQLException, NamingException {
        String query = "INSERT INTO product_ingredient (product_id, ingredient_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, productIngredient.getProduct_id());
            ps.setLong(2, productIngredient.getIngredient_id());
            ps.setLong(3, productIngredient.getQuantity());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el ingrediente del producto en la base de datos.");
            }
        }
    }

    @Override
    public List<Product_ingredient> getProductIngredients() throws SQLException, NamingException {
        List<Product_ingredient> productIngredients = new ArrayList<>();
        String query = "SELECT * FROM product_ingredient";
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                        productIngredients.add(Product_ingredient.createProduct_ingredient(
                        rs.getLong("product_id"),
                        rs.getLong("ingredient_id"),
                        rs.getInt("quantity")
                ));
            } if (productIngredients.size() == 0) {
                throw new SQLException("No se encontraron ingredientes en la base de datos.");
            }
        }
        return productIngredients;
    }

    @Override
    public List<Product_ingredient> getProductIngredientsByProductId(long product_id) throws SQLException, NamingException {
        String query = "SELECT * FROM product_ingredient WHERE product_id = ?";
        List<Product_ingredient> productIngredients = new ArrayList<>();
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, product_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productIngredients.add(Product_ingredient.createProduct_ingredient(
                            rs.getLong("product_id"),
                            rs.getLong("ingredient_id"),
                            rs.getInt("quantity")
                    ));
                } if (productIngredients.size() == 0) {
                    throw new SQLException(String.format("No se encontró una relación de producto ingrediente con el ID %d en la base de datos.", product_id));
                }
            }
        }
        return productIngredients;
    }

    @Override
    public Product_ingredient getProductIngredientByIngredientId(long ingredient_id) throws SQLException, NamingException {
        String query = "SELECT * FROM product_ingredient WHERE ingredient_id = ?";
        Product_ingredient productIngredient = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, ingredient_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    productIngredient = Product_ingredient.createProduct_ingredient(
                            rs.getLong("product_id"),
                            rs.getLong("ingredient_id"),
                            rs.getInt("quantity")
                    );
                } else {
                    throw new SQLException(String.format("No se encontró una relación de producto ingrediente con el ID %d en la base de datos.", ingredient_id));
                }

            }
        }
        return productIngredient;
    }

    @Override
    public void DeleteProductIngredient(long product_id) throws SQLException, NamingException {
        String query = "DELETE FROM product_ingredient WHERE product_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, product_id);
            ps.executeUpdate();
        }
    }

    public Product_ingredient getProductIngredient(long product_id, long ingredient_id) throws SQLException, NamingException {
        String query = "SELECT * FROM product_ingredient WHERE product_id = ? AND ingredient_id = ?";
        Product_ingredient productIngredient = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, product_id);
            ps.setLong(2, ingredient_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    productIngredient = Product_ingredient.createProduct_ingredient(
                            rs.getLong("product_id"),
                            rs.getLong("ingredient_id"),
                            rs.getInt("quantity")
                    );
                }
            }
        }
        return productIngredient;
    }

    public void updateProductIngredient(long product_id, long ingredient_id, int newQuantity) throws SQLException, NamingException {
        String query = "UPDATE product_ingredient SET quantity = ? WHERE product_id = ? AND ingredient_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setInt(1, newQuantity);
            ps.setLong(2, product_id);
            ps.setLong(3, ingredient_id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar la cantidad del ingrediente del producto en la base de datos.");
            }
        }
    }

    public int getQuantity(long product_id, long ingredient_id) throws SQLException, NamingException {
        String query = "SELECT * FROM product_ingredient WHERE product_id = ? AND ingredient_id = ?";
        Product_ingredient productIngredient = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, product_id);
            ps.setLong(2, ingredient_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    productIngredient = Product_ingredient.createProduct_ingredient(
                            rs.getLong("product_id"),
                            rs.getLong("ingredient_id"),
                            rs.getInt("quantity")
                    );
                }
            }
        }
        return productIngredient.getQuantity();
    }


}
