package com.example.momondolike.controllers;

import com.example.momondolike.dtos.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class NameController {


    @RequestMapping ("/name-info")
    public NameResponse getDetails (@RequestParam String name){

        Mono<Age> age = getAge(name);
        Mono<Gender> gender = getGender(name);
        Mono<Nationality> nationality = getNationality(name);

        var resMono = Mono.zip(age, gender, nationality).map(t -> {

            NameResponse ns = new NameResponse();
            ns.setAge(t.getT1().getAge());
            ns.setAgecount(t.getT1().getCount());


            ns.setGender(t.getT2().getGender());
            ns.setGenderProbability(t.getT2().getProbability());

            // Lav et forloop hvis den skal igennem alle i listen
            for (int i = 0; i < t.getT3().getCountry().size(); i++){
                ns.setCountry(t.getT3().getCountry().get(i).getCountry_id());
                ns.setCountryProbability(t.getT3().getCountry().get(i).getProbability());

            }
            return ns;

        });
        NameResponse res = resMono.block();
        res.setName(name);

        return res;
    }

    Mono<Age> getAge (String name){
        WebClient client = WebClient.create();
        Mono<Age> age = client.get()
                .uri("https://api.agify.io/?name="+name)
                .retrieve()
                .bodyToMono(Age.class);
        return age;
    }

    Mono<Gender> getGender (String name){
        WebClient client = WebClient.create();
        Mono<Gender> gender = client.get()
                .uri("https://api.genderize.io/?name=" +name)
                .retrieve()
                .bodyToMono(Gender.class);
        return gender;

    }

    Mono<Nationality> getNationality (String name){
        WebClient client = WebClient.create();
        Mono<Nationality> nationality = client.get()
                .uri("https://api.nationalize.io/?name="+name)
                .retrieve()
                .bodyToMono(Nationality.class);
        return nationality;
    }
}
