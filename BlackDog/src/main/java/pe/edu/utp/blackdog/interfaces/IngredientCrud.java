package pe.edu.utp.blackdog.interfaces;

import pe.edu.utp.blackdog.model.Ingredient;

import java.sql.SQLException;
import java.util.List;

public interface IngredientCrud {
    void registerIngredient(Ingredient ingredient) throws SQLException;

    List<Ingredient> getAllIngredients() throws SQLException;

    Ingredient getIngredientById(long ingredient_id) throws SQLException;

    void updateIngredient(Ingredient ingredient, long ingredient_id) throws SQLException;

    void deleteIngredient(long ingredient_id) throws SQLException;

    String getIngredientNameById(long ingredient_id) throws SQLException;
}
