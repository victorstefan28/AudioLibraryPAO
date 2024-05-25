package org.pao.audiolibrarypao.services;

import java.util.List;
import org.pao.audiolibrarypao.entities.Track;
import org.pao.audiolibrarypao.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing tracks.
 */
@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    /**
     * Saves a track.
     *
     * @param track the track to save
     * @return the saved track
     */
    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    /**
     * Retrieves all tracks.
     *
     * @return a list of all tracks
     */
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    /**
     * Searches tracks by artist and title.
     *
     * @param artist the artist to search for
     * @param title the title to search for
     * @param pageable the pagination information
     * @return a page of tracks matching the search criteria
     */
    public Page<Track> searchTracks(String artist, String title, Pageable pageable) {
        return trackRepository.findByArtistContainingAndTitleContaining(artist, title, pageable);
    }
}