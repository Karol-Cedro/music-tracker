package com.kcedro.musictracker.controllers;

import com.kcedro.musictracker.entities.Playlist;
import com.kcedro.musictracker.entities.Song;
import com.kcedro.musictracker.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists(HttpServletRequest request){
        int userId = (int) request.getAttribute("userId");
        List<Playlist> playlists = playlistService.getAllPlaylists(userId);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<Playlist> getPlaylistById(HttpServletRequest request,@PathVariable("playlistId")int playlistId){
        int userId = (int) request.getAttribute("userId");
        Playlist playlist = playlistService.getPlaylistById(userId,playlistId);
        return new ResponseEntity<>(playlist,HttpStatus.OK);
    }

    @GetMapping("/{playlistId}/songs")
    public ResponseEntity<List<Song>> getAllSongsFromPlaylist(HttpServletRequest request, @PathVariable("playlistId") int playlistId){
        int userId = (int) request.getAttribute("userId");
        List<Song> songs = playlistService.getAllSongsFromPlaylist(userId,playlistId);
        return new ResponseEntity<>(songs,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Playlist> addPlaylist(HttpServletRequest request, @RequestBody Map<String,Object> playlistMap){
        int userId = (int) request.getAttribute("userId");
        String title = (String) playlistMap.get("title");
        String description = (String) playlistMap.get("description");
        Playlist playlist = playlistService.create(userId, title, description);
        return new ResponseEntity<>(playlist,HttpStatus.CREATED);
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<Map<String,Boolean>> updatePlaylist(HttpServletRequest request, @PathVariable("playlistId") int playlistId,
                                                              @RequestBody Playlist playlist){
        int userId = (int) request.getAttribute("userId");
        playlistService.update(userId,playlistId,playlist);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Map<String,Boolean>> addSongToPlaylist(HttpServletRequest request, @PathVariable("playlistId") int playlistId,
                                                                 @PathVariable("songId") int songId){
        int userId = (int) request.getAttribute("userId");
        playlistService.addSongToPlaylist(userId, playlistId ,songId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Map<String, Boolean>> removeSongFromPlaylist(HttpServletRequest request, @PathVariable("playlistId") int playlistId,
                                                                       @PathVariable("songId") int songId){
        int userId = (int) request.getAttribute("userId");
        playlistService.removeSongFromPlaylist(userId, playlistId,songId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Map<String,Boolean>> deletePlaylist(HttpServletRequest request,@PathVariable("playlistId") int playlistId){
        int userId = (int) request.getAttribute("userId");
        playlistService.removeById(userId, playlistId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

}
