package com.example.extocia;

public class ReadWriteUserDetails {

    public String userName,BirthdayDate,numberPhone,MFSelectedGender,eemail;
    private String imageURL;
    //Constructor
    public ReadWriteUserDetails(){};
    public ReadWriteUserDetails(String uname,String date,String phone,String selectedGender,String email){
        this.userName = uname;
        this.BirthdayDate = date;
        this.numberPhone = phone;
        this.MFSelectedGender = selectedGender;
        this.eemail = email;

    }
    public ReadWriteUserDetails(String imageURL){
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
