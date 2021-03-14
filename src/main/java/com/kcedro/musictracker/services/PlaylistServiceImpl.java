package com.kcedro.musictracker.services;

import com.kcedro.musictracker.entities.Playlist;
import com.kcedro.musictracker.entities.Song;
import com.kcedro.musictracker.exceptions.BadRequestException;
import com.kcedro.musictracker.exceptions.ResourceNotFoundException;
import com.kcedro.musictracker.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService{

    @Autowired
    PlaylistRepository playlistRepository;

    @Override
    public List<Playlist> getAllPlaylists(int userId) throws ResourceNotFoundException {
        return playlistRepository.getAllPlaylists(userId);
    }

    @Override
    public Playlist getPlaylistById(int userId, int playlistId) throws ResourceNotFoundException {
        return playlistRepository.getPlaylistById(userId, playlistId);
    }

    @Override
    public List<Song> getAllSongsFromPlaylist(int userId, int playlistId) throws ResourceNotFoundException {
        return playlistRepository.getAllSongsFromPlaylist(userId, playlistId);
    }

    @Override
    public Playlist create(int userId, String title, String description) throws BadRequestException {
        int playlistId = playlistRepository.create(userId, title, description);
        return playlistRepository.getPlaylistById(userId,playlistId);
    }

    @Override
    public void update(int userId, int playlistId, Playlist playlist) throws BadRequestException {
        playlistRepository.update(userId, playlistId, playlist);
    }

    @Override
    public void addSongToPlaylist(int userId, int playlistId, int songId) throws BadRequestException {
        playlistRepository.addSongToPlaylist(userId, playlistId, songId);
    }

    @Override
    public void removeSongFromPlaylist(int userId, int playlistId, int songId) throws BadRequestException {
        playlistRepository.removeSongFromPlaylist(userId, playlistId, songId);
    }

    @Override
    public void removeById(int userId, int playlistId) {
        playlistRepository.removeById(userId, playlistId);
    }
}
