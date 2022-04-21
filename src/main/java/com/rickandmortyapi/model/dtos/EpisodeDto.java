package com.rickandmortyapi.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDto {

    private String name;
    private String air_date;
    private String episode;
    private List<CharacterDto> characters;
    private String url;

}