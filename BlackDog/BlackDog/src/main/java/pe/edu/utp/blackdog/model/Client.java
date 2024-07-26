package pe.edu.utp.blackdog.model;

import java.util.List;

public class Client {
    private long client_id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String pwd;

    public Client(Builder builder) {
        this.client_id = builder.client_id;
        this.first_name = builder.first_name;
        this.last_name = builder.last_name;
        this.phone_number = builder.phone_number;
        this.email = builder.email;
        this.pwd = builder.pwd;
    }

    //INNER CLASS: BUILDER
    public static class Builder{
        private long client_id;
        private String first_name;
        private String last_name;
        private String phone_number;
        private String email;
        private String pwd;

        public Builder(String email){
            this.client_id = 0;
            this.first_name = "user";
            this.last_name = "black_dog";
            this.phone_number = "phone_number";
            this.email = email;
            this.pwd = "pwd";
        }

        public Builder withPwd(String pwd){
            this.pwd = pwd;
            return this;
        }

        public Builder withAll(long client_id, String first_name, String last_name, String phone_number){
            this.client_id = client_id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.phone_number = phone_number;
            return this;
        }

        public Builder forUpdate(String first_name, String last_name, String phone_number){
            this.first_name = first_name;
            this.last_name = last_name;
            this.phone_number = phone_number;
            return this;
        }

        public Client build(){return new Client(this);}
    }

    // GETTERS
    public long getClient_id() {
        return client_id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public String getEmail() {
        return email;
    }
    public String getPwd() {
        return pwd;
    }

    // CREATE CLIENT
    public static Client createClient(long client_id, String first_name, String last_name, String phone_number, String email, String pwd){
        return new Client.Builder(email).withPwd(pwd).withAll(client_id, first_name, last_name, phone_number).build();
    }
    public static Client createClientWithoutId(String email, String pwd){
        return new Client.Builder(email).withPwd(pwd).build();
    }

    public static Client createClientUpdate(String first_name, String last_name, String phone_number, String email){
        return new Client.Builder(email).forUpdate(first_name, last_name, phone_number).build();
    }

    @Override
    public String toString() {
        return "Client{" +
                "client_id=" + client_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
