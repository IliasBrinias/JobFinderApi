package com.unipi.msc.jobfinderapi.Model.Link;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;

    public Optional<List<Link>> getLinksByIds(List<Long> id){
        return linkRepository.findByIdIn(id);
    }

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public Optional<Link> getLinkById(Long id) {
        return linkRepository.findById(id);
    }
}
