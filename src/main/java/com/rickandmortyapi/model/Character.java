package com.rickandmortyapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Character {

    private int	id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private Place origin;
    private Place location;
    private String image;
    private String[] episode;
    private String url;
    private String created;
}
