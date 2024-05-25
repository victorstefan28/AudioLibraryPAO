package org.pao.audiolibrarypao.services;

import java.util.*;

import org.pao.audiolibrarypao.entities.Playlist;
import org.pao.audiolibrarypao.entities.Track;
import org.pao.audiolibrarypao.entities.User;
import org.pao.audiolibrarypao.repositories.PlaylistRepository;
import org.pao.audiolibrarypao.repositories.TrackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing playlists.
 */
@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;

    /**
     * Constructs a new PlaylistService with the specified repositories.
     *
     * @param playlistRepository the repository for playlists
     * @param trackRepository the repository for tracks
     */
    public PlaylistService(PlaylistRepository playlistRepository, TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
    }

    /**
     * Retrieves a paginated list of playlists.
     *
     * @param pageable the pagination information
     * @return a paginated list of playlists
     */
    public Page<Playlist> listPlaylists(Pageable pageable) {
        return playlistRepository.findAll(pageable);
    }

    /**
     * Creates a new playlist with the specified name, description, and user.
     *
     * @param name the name of the playlist
     * @param description the description of the playlist
     * @param user the user creating the playlist
     * @return the created playlist
     * @throws RuntimeException if a playlist with the same name already exists for the user
     */
    public Playlist createPlaylist(String name, String description, User user) {
        System.out.println(user.getEmail());
        if (playlistRepository.existsByNameAndUser(name, user)) {
            throw new RuntimeException("Playlist with this name already exists for the user");
        }

        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setUser(user);
        return playlistRepository.save(playlist);
    }

    /**
     * Adds a track to the specified playlist if the user has permission.
     *
     * @param playlistId the ID of the playlist
     * @param trackId the ID of the track
     * @param user the user adding the track
     * @return the updated playlist
     * @throws RuntimeException if the playlist or track is not found, the user does not have permission,
     *                          or the track already exists in the playlist
     */
    public Playlist addTrackToPlaylist(Long playlistId, Long trackId, User user) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Track not found"));
        if (!Objects.equals(playlist.getUser().getId(), user.getId())) {
            throw new RuntimeException("User does not have permission to add track to playlist");
        }
        if (!playlist.getTracks().contains(track)) {
            playlist.getTracks().add(track);
            return playlistRepository.save(playlist);
        } else {
            throw new RuntimeException("Track already exists in the playlist");
        }
    }

    /**
     * Adds multiple tracks to the specified playlist if the user has permission.
     *
     * @param playlistId the ID of the playlist
     * @param trackIds the IDs of the tracks to add
     * @param user the user adding the tracks
     * @return the updated playlist
     * @throws RuntimeException if the playlist is not found, the user does not have permission,
     *                          or the tracks already exist in the playlist
     */
    public Playlist addTracksToPlaylist(Long playlistId, List<Long> trackIds, User user) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        List<Track> tracksToAdd = trackRepository.findAllById(trackIds);
        if (!Objects.equals(playlist.getUser().getId(), user.getId())) {
            throw new RuntimeException("User does not have permission to add track to playlist");
        }
        Set<Track> uniqueTracks = new HashSet<>(playlist.getTracks());
        uniqueTracks.addAll(tracksToAdd);
        playlist.setTracks(new ArrayList<>(uniqueTracks));

        return playlistRepository.save(playlist);
    }
}
