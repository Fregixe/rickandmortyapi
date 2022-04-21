package com.rickandmortyapi.services;

import com.rickandmortyapi.model.Character;
import com.rickandmortyapi.model.Episode;
import com.rickandmortyapi.model.dtos.CharacterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {
    private RestTemplate restTemplate = new RestTemplate();

    public CharacterDto getCharacter(String url){
        ResponseEntity<Character> responseEntity =
                restTemplate.getForEntity(url, Character.class);
        Character character = responseEntity.getBody();
        CharacterDto characterDto = new CharacterDto();
        characterDto.setImage(character.getImage());
        characterDto.setName(character.getName());
        return characterDto;
    }

    public List<CharacterDto> getCharacters(Episode episode){
        List<CharacterDto> characters = new ArrayList<>();
        for(String url : episode.getCharacters()){
            CharacterDto character = getCharacter(url);
            characters.add(character);
        }
        return characters;
    }
}
