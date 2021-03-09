package com.kcedro.musictracker.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mt_playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private int playlistId;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "playlist",cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Song> songs;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    public Playlist() {
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlist_id){
        this.playlistId = playlist_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlist_id=" + playlistId +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
