package com.example.extocia;

public class User {
    private String phone;
    private String uname;
    private String email;
    private String pass;
    private String selectedGender;

    public User() {}

    public User(String uname, String email) {
        this.uname = uname;
        this.email = email;
    }
    public User(String phone,String uname, String email,String pass,String selectedGender) {
        this.phone = phone;
        this.uname = uname;
        this.email = email;
        this.pass=pass;
        this.selectedGender=selectedGender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedGender(String selectedGender) {
        this.selectedGender = selectedGender;
    }
}
