package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.Product_IngredientCrud;
import pe.edu.utp.blackdog.model.Product_ingredient;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
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
        String query = "{CALL RegisterProductIngredient(?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, productIngredient.getProduct_id());
            cs.setLong(2, productIngredient.getIngredient_id());
            cs.setInt(3, productIngredient.getQuantity());
            int rowsAffected = cs.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el ingrediente del producto en la base de datos.");
            }
        }
    }

    @Override
    public List<Product_ingredient> getProductIngredients() throws SQLException, NamingException {
        List<Product_ingredient> productIngredients = new ArrayList<>();
        String query = "{CALL GetAllProductIngredients()}";
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                productIngredients.add(Product_ingredient.createProduct_ingredient(
                        rs.getLong("product_id"),
                        rs.getLong("ingredient_id"),
                        rs.getInt("quantity")
                ));
            }
            if (productIngredients.isEmpty()) {
                throw new SQLException("No se encontraron ingredientes en la base de datos.");
            }
        }
        return productIngredients;
    }

    @Override
    public List<Product_ingredient> getProductIngredientsByProductId(long product_id) throws SQLException, NamingException {
        String query = "{CALL GetProductIngredientsByProductId(?)}";
        List<Product_ingredient> productIngredients = new ArrayList<>();
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, product_id);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    productIngredients.add(Product_ingredient.createProduct_ingredient(
                            rs.getLong("product_id"),
                            rs.getLong("ingredient_id"),
                            rs.getInt("quantity")
                    ));
                }
                if (productIngredients.isEmpty()) {
                    throw new SQLException(String.format("No se encontró una relación de producto ingrediente con el ID %d en la base de datos.", product_id));
                }
            }
        }
        return productIngredients;
    }

    @Override
    public Product_ingredient getProductIngredientByIngredientId(long ingredient_id) throws SQLException, NamingException {
        String query = "{CALL GetProductIngredientByIngredientId(?)}";
        Product_ingredient productIngredient = null;
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, ingredient_id);
            try (ResultSet rs = cs.executeQuery()) {
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
        String query = "{CALL DeleteProductIngredient(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, product_id);
            cs.executeUpdate();
        }
    }

    public Product_ingredient getProductIngredient(long product_id, long ingredient_id) throws SQLException, NamingException {
        String query = "{CALL GetProductIngredient(?, ?)}";
        Product_ingredient productIngredient = null;
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, product_id);
            cs.setLong(2, ingredient_id);
            try (ResultSet rs = cs.executeQuery()) {
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
        String query = "{CALL UpdateProductIngredient(?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setInt(1, newQuantity);
            cs.setLong(2, product_id);
            cs.setLong(3, ingredient_id);
            int rowsAffected = cs.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar la cantidad del ingrediente del producto en la base de datos.");
            }
        }
    }

    public int getQuantity(long product_id, long ingredient_id) throws SQLException, NamingException {
        String query = "{CALL GetQuantity(?, ?)}";
        Product_ingredient productIngredient = null;
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, product_id);
            cs.setLong(2, ingredient_id);
            try (ResultSet rs = cs.executeQuery()) {
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
