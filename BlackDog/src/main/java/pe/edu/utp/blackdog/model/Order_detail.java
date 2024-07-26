package pe.edu.utp.blackdog.model;

public class Order_detail {
    private Customer_order customerOder;
    private Product product;
    private int quantity;

    public Order_detail(Builder builder) {
        this.customerOder = builder.customerOder;
        this.product = builder.product;
        this.quantity = builder.quantity;
    }

    //INNER CLASS: BUILDER
    public static class Builder {
        private Customer_order customerOder;
        private Product product;
        private int quantity;

        public Builder(Customer_order customerOder, Product product, int quantity) {
            this.customerOder = customerOder;
            this.product = product;
            this.quantity = quantity;
        }

        public Order_detail build() {
            return new Order_detail(this);
        }
    }

    // GETTERS
    public Customer_order getCustomerOder() {
        return customerOder;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {return quantity; }

    // CREATE INGREDIENT
    public static Order_detail createOrderDetail(Customer_order customerOder, Product product, int quantity){
        return new Order_detail.Builder(customerOder, product, quantity).build();
    }

    @Override
    public String toString() {
        return "Order_detail{" +
                "customerOder=" + customerOder +
                ", product=" + product +
                '}';
    }
}
