package com.example.brint_quizapp;

import com.example.brint_quizapp.dal.dto.UserDTO;

public class UserSingleton {

    UserDTO user;

    private static UserSingleton userSingleton;

    public static UserSingleton getUserSingleton(){
        if(userSingleton == null){
            return userSingleton = new UserSingleton();
        }

        return userSingleton;
    }

    public void reset(){
        user = null;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser(){
        return user;
    }

}