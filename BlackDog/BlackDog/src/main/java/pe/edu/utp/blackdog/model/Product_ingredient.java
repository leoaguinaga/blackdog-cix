package pe.edu.utp.blackdog.model;

public class Product_ingredient {
    private long product_id;
    private long ingredient_id;
    private int quantity;

    public Product_ingredient(Builder builder) {
        this.product_id = builder.product_id;
        this.ingredient_id = builder.ingredient_id;
        this.quantity = builder.quantity;
    }

    public static class Builder{
        private long product_id;
        private long ingredient_id;
        private int quantity;

        public Builder(long product_id, long ingredient_id, int quantity){
            this.product_id = product_id;
            this.ingredient_id = ingredient_id;
            this.quantity = quantity;
        }

        public Product_ingredient build(){ return new Product_ingredient(this);}
    }

    public long getProduct_id() { return product_id;}

    public long getIngredient_id() { return ingredient_id;}

    public int getQuantity() { return quantity;}

    // CREATE PRODUCT INGREDIENT RELATION
    public static Product_ingredient createProduct_ingredient(long product_id, long ingredient_id, int quantity){
        return new Product_ingredient.Builder(product_id, ingredient_id, quantity).build();
    }

}
