package com.example.myautocare.User;

public class User {

    String first_name,last_name,id_number,email;

    public User(){

    }

    public User(String first_name,String last_name,String id_number,String email){
        this.first_name = first_name;
        this.last_name = last_name;
        this.id_number = id_number;
        this.email = email;

    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
