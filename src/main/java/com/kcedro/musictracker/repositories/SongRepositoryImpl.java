package com.kcedro.musictracker.repositories;

import com.kcedro.musictracker.entities.Song;
import com.kcedro.musictracker.entities.User;
import com.kcedro.musictracker.exceptions.BadRequestException;
import com.kcedro.musictracker.exceptions.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class SongRepositoryImpl implements SongRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Song> getAll(int userId) throws ResourceNotFoundException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Query query = session.createQuery("select s from Song s where s.user.id = :userId");
            query.setParameter("userId", userId);
            return query.list();
        }catch (Exception e){
            throw new ResourceNotFoundException("This user doesn't have any songs");
        }
    }

    @Override
    public Song getById(int userId, int songId) throws ResourceNotFoundException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Query query = session.createQuery("select s from Song s where s.user.id = :userId and s.id = :songId");
            query.setParameter("userId", userId).setParameter("songId",songId);
            return (Song) query.getSingleResult();
        }catch (Exception e){
            throw new ResourceNotFoundException("Song not found");
        }
    }

    @Override
    public int create(int userId, String artist, String title) throws BadRequestException {
        Session session = entityManager.unwrap(Session.class);
        try{
            User user = session.get(User.class,userId);
            Song song = new Song(artist,title);
            user.addSong(song);
            session.save(song);
            return song.getSongId();
        }catch (Exception e){
            throw new BadRequestException("Invalid request. Failed to add song.");
        }
    }

    @Override
    public void update(int userId, int songId, Song song) throws BadRequestException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Song song1 = getById(userId, songId);
            song1.setArtist(song.getArtist());
            song1.setTitle(song.getTitle());
            session.update(song1);
        }catch (Exception e){
            throw new BadRequestException("Invalid request. Failed to update song.");
        }
    }

    @Override
    public void removeById(int userId, int songId) throws ResourceNotFoundException {
        Session session = entityManager.unwrap(Session.class);
        try{
            Song song = session.get(Song.class,songId);
            User user = session.get(User.class,userId);
            user.getSongs().remove(song);
            session.delete(song);
        }catch(Exception e){
            throw  new ResourceNotFoundException("Song not found.");
        }
    }
}
