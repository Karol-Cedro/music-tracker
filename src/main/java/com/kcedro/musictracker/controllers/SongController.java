package com.kcedro.musictracker.controllers;

import com.kcedro.musictracker.entities.Song;
import com.kcedro.musictracker.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs(HttpServletRequest request){
        int userId = (int) request.getAttribute("userId");
        List<Song> songs = songService.getAllSongs(userId);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<Song> getSongsById(HttpServletRequest request, @PathVariable("songId") int songId){
        int userId = (int) request.getAttribute("userId");
        Song song = songService.getSongById(userId,songId);
        return new ResponseEntity<>(song,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Song> addSong(HttpServletRequest request, @RequestBody Map<String,Object> songMap){
        int userId = (int) request.getAttribute("userId");
        String artist = (String) songMap.get("artist");
        String title = (String) songMap.get("title");
        Song song = songService.create(userId, artist, title);
       return new ResponseEntity<>(song,HttpStatus.CREATED);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<Map<String,Boolean>> updateSong(HttpServletRequest request, @PathVariable("songId") int songId,
                                                          @RequestBody Song song){
        int userId = (int) request.getAttribute("userId");
        songService.update(userId,songId,song);
        Map<String, Boolean>map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Map<String,Boolean>> deleteSong(HttpServletRequest request, @PathVariable("songId") int songId){
        int userId = (int) request.getAttribute("userId");
        songService.removeById(userId,songId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

}
