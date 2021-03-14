package com.kcedro.musictracker.repositories;
import com.kcedro.musictracker.entities.Song;
import com.kcedro.musictracker.exceptions.BadRequestException;
import com.kcedro.musictracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface SongRepository {

    List<Song> getAllSongs(int userId)throws ResourceNotFoundException;

    Song getSongById(int userId, int songId) throws ResourceNotFoundException;

    int create(int userId,String artist, String title) throws BadRequestException;

    void update(int userId, int songId, Song song) throws BadRequestException;

    void removeById(int userId, int songId) throws ResourceNotFoundException;
}
