package pe.edu.utp.blackdog.model;

public class Ingredient {
    private long ingredient_id;
    private String name;
    private double price;

    public Ingredient(Builder builder) {
        this.ingredient_id = builder.ingredient_id;
        this.name = builder.name;
        this.price = builder.price;
    }

    //INNER CLASS: BUILDER
    public static class Builder {
        private long ingredient_id;
        private String name;
        private double price;

        public Builder(String name, double price) {
            this.ingredient_id = 0;
            this.name = name;
            this.price = price;
        }

        public Builder withIngredient_id(long ingredient_id){
            this.ingredient_id = ingredient_id;
            return this;
        }

        public Ingredient build() {
            return new Ingredient(this);
        }
    }

    // GETTERS
    public long getIngredient_id() {
        return ingredient_id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {return price;}

    // CREATE INGREDIENT
    public static Ingredient createIngredientWithoutId(String name, double price){
        return new Ingredient.Builder(name, price).build();
    }
    public static Ingredient createIngredient(long ingredient_id, String name, double price){
        return new Ingredient.Builder(name, price).withIngredient_id(ingredient_id).build();
    }


    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredient_id=" + ingredient_id +
                ", name='" + name + '\'' +
                '}';
    }
}
