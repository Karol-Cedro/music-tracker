package com.kcedro.musictracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "mt_songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private int songId;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="playlist_id")
    private Playlist playlist;

    @JsonIgnore
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "artist")
    private String artist;

    @Column(name = "title")
    private String title;

    public Song() {
    }

    public Song(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int song_Id) {
        this.songId = song_Id;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Song{" +
                "song_Id=" + songId +
                ", playlist=" + playlist +
                ", user=" + user +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
