package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.IngredientCrud;
import pe.edu.utp.blackdog.model.Ingredient;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO implements AutoCloseable, IngredientCrud {
    private final Connection cnn;

    public IngredientDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public void registerIngredient(Ingredient ingredient) throws SQLException {
        String query = "{CALL RegisterIngredient(?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, ingredient.getName());
            cs.setDouble(2, ingredient.getPrice());
            int rowsAffected = cs.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el ingrediente en la base de datos.");
            }
        }
    }

    @Override
    public List<Ingredient> getAllIngredients() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "{CALL GetAllIngredients()}";
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                ingredients.add(Ingredient.createIngredient(
                        rs.getLong("ingredient_id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
            if (ingredients.isEmpty()) {
                throw new SQLException("No se encontraron ingredientes en la base de datos.");
            }
        }
        return ingredients;
    }

    @Override
    public Ingredient getIngredientById(long ingredient_id) throws SQLException {
        String query = "{CALL GetIngredientById(?)}";
        Ingredient ingredient = null;
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, ingredient_id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    ingredient = Ingredient.createIngredient(
                            rs.getLong("ingredient_id"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    );
                } else {
                    throw new SQLException(String.format("No se encontró un ingrediente con el ID %d en la base de datos.", ingredient_id));
                }
            }
        }
        return ingredient;
    }

    @Override
    public void updateIngredient(Ingredient ingredient, long ingredient_id) throws SQLException {
        String query = "{CALL UpdateIngredient(?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, ingredient.getName());
            cs.setDouble(2, ingredient.getPrice());
            cs.setLong(3, ingredient_id);
            cs.executeUpdate();
        }
    }

    @Override
    public void deleteIngredient(long ingredient_id) throws SQLException {
        String query = "{CALL DeleteIngredient(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, ingredient_id);
            cs.executeUpdate();
        }
    }

    @Override
    public String getIngredientNameById(long ingredient_id) throws SQLException {
        String query = "{CALL GetIngredientNameById(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, ingredient_id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                } else {
                    throw new SQLException(String.format("No se encontró un ingrediente con el ID %d en la base de datos.", ingredient_id));
                }
            }
        }
    }
}
