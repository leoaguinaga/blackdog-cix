package pe.edu.utp.blackdog.model;

public class Administrator {
    private long admin_id;
    private String full_name;
    private String email;
    private String pwd;

    public Administrator(Builder builder) {
        this.admin_id = builder.admin_id;
        this.full_name = builder.full_name;
        this.email = builder.email;
        this.pwd = builder.pwd;
    }

    //INNER CLASS: BUILDER
    public static class Builder{
        private long admin_id;
        private String full_name;
        private String email;
        private String pwd;

        public Builder(long admin_id, String full_name, String email, String pwd){
            this.admin_id = admin_id;
            this.full_name = full_name;
            this.email = email;
            this.pwd = pwd;
        }

        public Administrator build(){return new Administrator(this);}
    }

    // GETTERS
    public long getAdmin_id() {
        return admin_id;
    }
    public String getFull_name() {
        return full_name;
    }
    public String getEmail() {
        return email;
    }
    public String getPwd() {
        return pwd;
    }

    // CREATE ADMINISTRATOR
    public static Administrator createAdministrator(long admin_id, String full_name, String email, String pwd){
        return new Builder(admin_id, full_name, email, pwd).build();
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "admin_id=" + admin_id +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
