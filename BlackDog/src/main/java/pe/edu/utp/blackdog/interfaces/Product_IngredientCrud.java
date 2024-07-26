package pe.edu.utp.blackdog.interfaces;

import pe.edu.utp.blackdog.model.Product_ingredient;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface Product_IngredientCrud {
    void registerProductIngredient(Product_ingredient productIngredient) throws SQLException, NamingException;

    List<Product_ingredient> getProductIngredients() throws SQLException, NamingException;

    List<Product_ingredient> getProductIngredientsByProductId(long product_id) throws SQLException, NamingException;

    Product_ingredient getProductIngredientByIngredientId(long ingredient_id) throws SQLException, NamingException;

    void DeleteProductIngredient(long product_id) throws SQLException, NamingException;
}
