package com.kcedro.musictracker.services;

import com.kcedro.musictracker.entities.Playlist;
import com.kcedro.musictracker.entities.Song;
import com.kcedro.musictracker.exceptions.BadRequestException;
import com.kcedro.musictracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface PlaylistService {

    List<Playlist> getAllPlaylists(int userId) throws ResourceNotFoundException;

    Playlist getPlaylistById(int userId, int playlistId) throws ResourceNotFoundException;

    List<Song> getAllSongsFromPlaylist(int userId, int playlistId) throws ResourceNotFoundException;

    Playlist create(int userId, String title, String description) throws BadRequestException;

    void update(int userId,int playlistId, Playlist playlist) throws BadRequestException;

    void addSongToPlaylist(int userId, int playlistId, int songId) throws BadRequestException;

    void removeSongFromPlaylist(int userId, int playlistId, int songId) throws BadRequestException;

    void removeById(int userId, int playlistId);

}
