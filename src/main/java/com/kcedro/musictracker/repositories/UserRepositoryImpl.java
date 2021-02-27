package com.kcedro.musictracker.repositories;

import com.kcedro.musictracker.entities.User;
import com.kcedro.musictracker.exceptions.AuthorizationException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public int create(String firstName, String lastName, String email, String password) throws AuthorizationException {
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt(10));
        Session session = entityManager.unwrap(Session.class);
        try {
            User user = new User(firstName, lastName, email, hashedPassword);
            session.save(user);
            return user.getUser_id();
        }catch (Exception e){
            throw new AuthorizationException("Incorrect data. Failed to create account.");
        }
    }

    @Override
    public User findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class,id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws AuthorizationException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Query<User> query = session.createQuery("from User u where u.email = :email", User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new AuthorizationException(" Invalid email or password");
            }
            return user;
        }catch(EmptyResultDataAccessException e){
            throw new AuthorizationException("invalid email or password");
        }
    }

    @Override
    public boolean checkIfEmailExist(String email) {
        Query query = entityManager.unwrap(Session.class).createQuery("from User u where u.email = :email");
        query.setParameter("email",email);
        return (query.uniqueResult() !=null);
    }
}
