package org.pao.audiolibrarypao.services;


import org.pao.audiolibrarypao.entities.Track;
import org.pao.audiolibrarypao.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {
    @Autowired
    private TrackRepository trackRepository;

    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }
}