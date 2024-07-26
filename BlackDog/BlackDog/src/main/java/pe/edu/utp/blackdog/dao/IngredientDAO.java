package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.IngredientCrud;
import pe.edu.utp.blackdog.model.Client;
import pe.edu.utp.blackdog.model.Ingredient;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "INSERT INTO ingredient (name, price) VALUES (?, ?)";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, ingredient.getName());
            ps.setDouble(2, ingredient.getPrice());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el ingrediente en la base de datos.");
            }
        }
    }

    @Override
    public List<Ingredient> getAllIngredients() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredient";
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ingredients.add(Ingredient.createIngredient(
                        rs.getLong("ingredient_id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            } if (ingredients.size() == 0) {
                throw new SQLException("No se encontraron ingredientes en la base de datos.");
            }
        }
        return ingredients;
    }

    @Override
    public Ingredient getIngredientById(long ingredient_id) throws SQLException {
        String query = "SELECT * FROM ingredient WHERE ingredient_id = ?";
        Ingredient ingredient = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, ingredient_id);
            try (ResultSet rs = ps.executeQuery()) {
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
        String query = "UPDATE ingredient SET name = ?, price = ? WHERE ingredient_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, ingredient.getName());
            ps.setDouble(2, ingredient.getPrice());
            ps.setLong(3, ingredient_id);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteIngredient(long ingredient_id) throws SQLException {
        String query = "DELETE FROM ingredient WHERE ingredient_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, ingredient_id);
            ps.executeUpdate();
        }
    }

    @Override
    public String getIngredientNameById(long ingredient_id) throws SQLException {
        String query = "SELECT name FROM ingredient WHERE ingredient_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, ingredient_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                } else {
                    throw new SQLException(String.format("No se encontró un ingrediente con el ID %d en la base de datos.", ingredient_id));
                }
            }
        }
    }
}
