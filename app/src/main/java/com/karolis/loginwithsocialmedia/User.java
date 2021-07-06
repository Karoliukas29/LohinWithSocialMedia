package com.karolis.loginwithsocialmedia;


public class User {
    public String userId;
    public String userName;
    public String userEmail;



    public User(String userId, String userName, String userEmail) {

        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;

    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(){

    }
}
