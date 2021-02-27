package com.kcedro.musictracker.services;

import com.kcedro.musictracker.controllers.UserController;
import com.kcedro.musictracker.entities.User;
import com.kcedro.musictracker.exceptions.AuthorizationException;
import com.kcedro.musictracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws AuthorizationException {
        if (email!=null) email=email.toLowerCase();
        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws AuthorizationException {
        Pattern pattern=  Pattern.compile( "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");//RFC 5322
        if (email!=null)email=email.toLowerCase();
        if (!pattern.matcher(email).matches()){
            throw new AuthorizationException("Invalid email format");
        }

        if (userRepository.checkIfEmailExist(email)){
            throw new AuthorizationException("Email already in use");
        }
        int userId=userRepository.create(firstName,lastName,email,password);
        return userRepository.findById(userId);
    }
}
