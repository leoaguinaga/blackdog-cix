package pe.edu.utp.blackdog.model;

import pe.edu.utp.blackdog.model.enums.Role;

public class Worker {
    private long worker_id;
    private String full_name;
    private String email;
    private String pwd;
    private Role role;

    public Worker(Builder builder) {
        this.worker_id = builder.worker_id;
        this.full_name = builder.full_name;
        this.email = builder.email;
        this.pwd = builder.pwd;
        this.role = builder.role;
    }

    //INNER CLASS: BUILDER
    public static class Builder{

        private long worker_id;
        private String full_name;
        private String email;
        private String pwd;
        private Role role;
        public Builder(String full_name, String email, String pwd, Role role){
            this.worker_id = 0;
            this.full_name = full_name;
            this.email = email;
            this.pwd = pwd;
            this.role = role;
        }

        public Builder withWorker_id(long worker_id){
            this.worker_id = worker_id;
            return this;
        }

        public Worker build(){return new Worker(this);}

    }
    // GETTERS

    public long getWorker_id() {
        return worker_id;
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
    public Role getRole() {return role;}
    // CREATE WORKER

    public static Worker createWorkerWithoutId(String full_name, String email, String pwd, Role role){
        return new Builder(full_name, email, pwd, role).build();
    }
    public static Worker createWorker(long worker_id, String full_name, String email, String pwd, Role role){
        return new Builder(full_name, email, pwd, role).withWorker_id(worker_id).build();
    }
    @Override
    public String toString() {
        return "Administrator{" +
                "admin_id=" + worker_id +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public Worker() {}
}
