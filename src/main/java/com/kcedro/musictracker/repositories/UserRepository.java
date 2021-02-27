package com.kcedro.musictracker.repositories;

import com.kcedro.musictracker.entities.User;
import com.kcedro.musictracker.exceptions.AuthorizationException;

public interface UserRepository {

    int create(String firstName,String lastName, String email,String password) throws AuthorizationException;

    User findById(int id);

    User findByEmailAndPassword(String email, String password) throws AuthorizationException;

    boolean checkIfEmailExist(String email);
}
