package com.rickandmortyapi.controllers;

import com.rickandmortyapi.model.Episode;
import com.rickandmortyapi.model.dtos.CharacterDto;
import com.rickandmortyapi.model.dtos.SeasonInfoDto;
import com.rickandmortyapi.services.CharacterService;
import com.rickandmortyapi.services.EpisodeService;
import com.rickandmortyapi.model.dtos.EpisodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;
    private final CharacterService characterService;


    @CrossOrigin
    @GetMapping("/episodes/{season}")
    public List<EpisodeDto> getAllEpisodes(@PathVariable int season){
        return episodeService.getSeason(season);
    }

    @CrossOrigin
    @GetMapping("/seasons")
    public List<SeasonInfoDto> getSeasonInfo(){
        return episodeService.getSeasonsInfo();
    }

}
