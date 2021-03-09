package com.kcedro.musictracker.services;

import com.kcedro.musictracker.entities.Song;
import com.kcedro.musictracker.exceptions.BadRequestException;
import com.kcedro.musictracker.exceptions.ResourceNotFoundException;
import com.kcedro.musictracker.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SongServiceImpl implements SongService {

    @Autowired
    SongRepository songRepository;

    @Override
    public List<Song> getAll(int userId) throws ResourceNotFoundException {
        return songRepository.getAll(userId);
    }

    @Override
    public Song getById(int userId, int songId) throws ResourceNotFoundException {
        return songRepository.getById(userId, songId);
    }

    @Override
    public Song create(int userId, String artist, String title) throws BadRequestException {
        int songId = songRepository.create(userId,artist, title);
        return songRepository.getById(userId, songId);
    }

    @Override
    public void update(int userId, int songId, Song song) throws BadRequestException {
        songRepository.update(userId, songId, song);
    }

    @Override
    public void removeById(int userId, int songId) throws ResourceNotFoundException {
        songRepository.removeById(userId, songId);
    }
}
