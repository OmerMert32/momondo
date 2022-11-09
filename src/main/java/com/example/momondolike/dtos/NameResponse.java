package com.example.momondolike.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NameResponse {
    String name;
    String gender;
    double genderProbability;
    int age;
    int agecount;
    String country;
    double countryProbability;




    public void setGenderProbability(double p) {
        genderProbability = p * 100;
    }

    public void setCountryProbability(double p) {
        countryProbability = p * 100;
    }
}


