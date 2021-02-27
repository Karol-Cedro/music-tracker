package com.kcedro.musictracker.services;

import com.kcedro.musictracker.entities.User;
import com.kcedro.musictracker.exceptions.AuthorizationException;

public interface UserService {

    User validateUser(String email,String password) throws AuthorizationException;

    User registerUser(String firstName,String lastName,String email,String password) throws AuthorizationException;

}
