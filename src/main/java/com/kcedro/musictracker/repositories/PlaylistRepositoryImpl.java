package com.kcedro.musictracker.repositories;

import com.kcedro.musictracker.entities.Playlist;
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
public class PlaylistRepositoryImpl implements PlaylistRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Playlist> getAllPlaylists(int userId) throws ResourceNotFoundException {
        Session session = entityManager.unwrap(Session.class);

        try{
            Query query = session.createQuery("select p from Playlist p where p.user.id = :userId");
            query.setParameter("userId", userId);
            return query.list();
        }catch (Exception e){
            throw new ResourceNotFoundException("This user doesn't have any playlists");
        }
    }

    @Override
    public Playlist getPlaylistById(int userId, int playlistId) throws ResourceNotFoundException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Query query = session.createQuery("select p from Playlist p where p.user.id = :userId and p.id = :playlistId");
            query.setParameter("userId", userId).setParameter("playlistId",playlistId);
            return (Playlist) query.getSingleResult();
        }catch (Exception e){
            throw new ResourceNotFoundException("Playlist not found");
        }
    }

    @Override
    public List<Song> getAllSongsFromPlaylist(int userId, int playlistId) throws ResourceNotFoundException {
        try{
            return this.getPlaylistById(userId, playlistId).getSongs();
        }catch (Exception e){
            throw new ResourceNotFoundException("This playlist is empty.");
        }
    }

    @Override
    public Integer create(int userId, String title, String description) throws BadRequestException {
        Session session = entityManager.unwrap(Session.class);
        try{
            User user = session.get(User.class,userId);
            Playlist playlist = new Playlist(title,description);
            user.addPlaylist(playlist);
            session.save(playlist);
            return playlist.getPlaylistId();
        }catch (Exception e){
            throw new BadRequestException("Invalid request. Failed to add Playlist.");
        }
    }

    @Override
    public void update(int userId, int playlistId, Playlist playlist) throws BadRequestException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Playlist playlist1 = getPlaylistById(userId, playlistId);
            playlist1.setTitle(playlist.getTitle());
            playlist1.setDescription(playlist.getDescription());
            session.update(playlist1);
        }catch (Exception e){
            throw new BadRequestException("Invalid request. Failed to update Playlist.");
        }
    }

    @Override
    public void addSongToPlaylist(int userId, int playlistId, int songId) throws BadRequestException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Song song = session.get(Song.class,songId);
            this.getPlaylistById(userId, playlistId).addSong(song);
        }catch (Exception e){
            throw new BadRequestException("Invalid request. Failed to add a song to the playlist.");
        }
    }

    @Override
    public void removeSongFromPlaylist(int userId, int playlistId, int songId) throws BadRequestException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Song song = session.get(Song.class,songId);
            song.getPlaylists().remove(this.getPlaylistById(userId, playlistId));
        }catch (Exception e){
            throw new BadRequestException("Invalid request. Failed to remove a song from the playlist.");
        }
    }

    @Override
    public void removeById(int userId, int playlistId) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Playlist playlist = session.get(Playlist.class,playlistId);
            User user = session.get(User.class,userId);
            this.removePlaylistConnectionsWithSongs(user, playlistId);
            user.getPlaylists().remove(playlist);
            session.delete(playlist);
        }catch(Exception e){
            throw  new ResourceNotFoundException("Playlist not found.");
        }
    }

    public void removePlaylistConnectionsWithSongs(User user,int playlistId){
        List<Song> songs = user.getSongs();
        for (Song song : songs) {
            song.getPlaylists().removeIf(playlist -> playlist.getPlaylistId() == playlistId);
        }
    }
}
