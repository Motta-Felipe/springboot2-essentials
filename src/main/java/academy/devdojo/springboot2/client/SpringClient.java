package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        //#######################GET BY ID 12 ENTITY##################################
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 12);
        log.info(" ENTITY : {}", entity);

        //#######################GET BY ID 12 OBJECT##################################
        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 12);
        log.info("OBJECT", object);


        //#######################GET ALL ARRAY##################################
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));


        //#######################GET ALL ENTITY LIST##################################
        //@formatter:off
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        //@formatter:on
        log.info(exchange.getBody());

//        Anime kingdom = Anime.builder().name("kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
//        log.info("saved anime {}", kingdomSaved);


        //#######################POST##################################
        Anime samuraiChampion = Anime.builder().name("Samurai Champion").build();
        ResponseEntity<Anime> samuraiChampionSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChampion, createJsonHeader()),
                Anime.class);
        log.info(" ####################### saved anime {} #######################", samuraiChampionSaved);


        //#######################PUT##################################
        Anime animeToBeUpdated = samuraiChampionSaved.getBody();
        animeToBeUpdated.setName("Samurai Champloo 2");
        ResponseEntity<Void> samuraiChampionUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);
        log.info("####################### PUT {} #######################", samuraiChampionUpdated);


        //#######################DELETE##################################
        ResponseEntity<Void> samuraiChampionDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());
        log.info("####################### DELETE {} #######################", samuraiChampionDeleted);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
