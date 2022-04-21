package com.rickandmortyapi.services;

import com.rickandmortyapi.model.Episodes;
import com.rickandmortyapi.model.Episode;
import com.rickandmortyapi.model.dtos.EpisodeDto;
import com.rickandmortyapi.model.dtos.SeasonInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class EpisodeService {


    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private CharacterService characterService;

    public List<Episode> getAllEpisodes(){
        String URL = "https://rickandmortyapi.com/api/episode";
        ResponseEntity<Episodes> responseEntity =
                restTemplate.getForEntity(URL, Episodes.class);
        Episodes allEpisodesDto = responseEntity.getBody();
        List<Episode> episodes = allEpisodesDto.getResults();

        while (allEpisodesDto.getInfo().getNext() != null){
            URL = allEpisodesDto.getInfo().getNext();
            responseEntity = restTemplate.getForEntity(URL, Episodes.class);
            allEpisodesDto = responseEntity.getBody();
            episodes.addAll(allEpisodesDto.getResults());
        }

        return episodes;
    }

    public List<List<Episode>> divideIntoSeasons(List<Episode> episodes){
        List<List<Episode>> seasonedEpisodes = new ArrayList<>();
        seasonedEpisodes.add(new ArrayList<>());
        Pattern p = Pattern.compile("\\d+");
        int currentSeason = 1;
        int season = 1;
        for(Episode e : episodes){
            Matcher m = p.matcher(e.getEpisode());
            if(m.find()) season = Integer.parseInt(m.group());
            if(season != currentSeason){
                seasonedEpisodes.add(new ArrayList<>());
                currentSeason = season;
            }
            seasonedEpisodes.get(season - 1).add(e);
        }
        return seasonedEpisodes;
    }

    public List<SeasonInfoDto> getSeasonsInfo(){
        List<List<Episode>> seasonedEpisodes = divideIntoSeasons(getAllEpisodes());
        List<SeasonInfoDto> seasonInfoDtos = new ArrayList<>();
        for(int i = 0; i < seasonedEpisodes.size(); i++) {
            SeasonInfoDto seasonInfoDto = new SeasonInfoDto();
            seasonInfoDto.setSeason(i+1);
            seasonInfoDto.setEpisodesNumber(seasonedEpisodes.get(i).size());
            seasonInfoDtos.add(seasonInfoDto);
        }
        return seasonInfoDtos;
    }
    public List<EpisodeDto> getSeason(int season){
        List<Episode> episodes = divideIntoSeasons(getAllEpisodes()).get(season - 1);
        List<EpisodeDto> episodesDto = new ArrayList<>();
        for(Episode e : episodes){
            EpisodeDto episodeDto = new EpisodeDto();
            episodeDto.setEpisode(e.getEpisode());
            episodeDto.setName(e.getName());
            episodeDto.setAir_date(e.getAir_date());
            episodeDto.setUrl(e.getUrl());
            episodeDto.setCharacters(characterService.getCharacters(e));
            episodesDto.add(episodeDto);
        }
        return episodesDto;
    }

}

