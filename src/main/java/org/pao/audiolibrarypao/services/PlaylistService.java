package org.pao.audiolibrarypao.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.pao.audiolibrarypao.entities.Playlist;
import org.pao.audiolibrarypao.entities.Track;
import org.pao.audiolibrarypao.entities.User;
import org.pao.audiolibrarypao.repositories.PlaylistRepository;
import org.pao.audiolibrarypao.repositories.TrackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;

    public PlaylistService(PlaylistRepository playlistRepository, TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
    }

    public Page<Playlist> listPlaylists(Pageable pageable) {
        return playlistRepository.findAll(pageable);
    }

    public Playlist createPlaylist(String name, String description, User user) {
        System.out.println(user.getEmail());
        if (playlistRepository.existsByNameAndUser(name, user)) {
            throw new RuntimeException(
                    "Playlist with this name already exists for the user");
        }

        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setUser(user);
        return playlistRepository.save(playlist);
    }

    public Playlist addTrackToPlaylist(Long playlistId, Long trackId) {
        Playlist playlist =
                playlistRepository
                        .findById(playlistId)
                        .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Track track =
                trackRepository
                        .findById(trackId)
                        .orElseThrow(() -> new RuntimeException("Track not found"));

        if (!playlist.getTracks().contains(track)) {
            playlist.getTracks().add(track);
            return playlistRepository.save(playlist);
        } else {
            throw new RuntimeException("Track already exists in the playlist");
        }
    }

    public Playlist addTracksToPlaylist(Long playlistId, List<Long> trackIds) {
        Playlist playlist =
                playlistRepository
                        .findById(playlistId)
                        .orElseThrow(() -> new RuntimeException("Playlist not found"));

        List<Track> tracksToAdd = trackRepository.findAllById(trackIds);

        Set<Track> uniqueTracks = new HashSet<>(playlist.getTracks());
        uniqueTracks.addAll(tracksToAdd);
        playlist.setTracks(new ArrayList<>(uniqueTracks));

        return playlistRepository.save(playlist);
    }
}
