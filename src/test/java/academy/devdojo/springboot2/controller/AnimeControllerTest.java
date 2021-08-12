package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.service.AnimeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static academy.devdojo.springboot2.util.AnimeCreator.*;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks //quando queremos testar a classe em si
    private AnimeController animeController;

    @Mock //Para todas as classes que estao sendo utilizadas dentro de AnimeController
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp(){

        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(List.of(createValidAnime())));

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(createValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(createValidAnime()));


    }
    @Test
    @DisplayName("List returns list of animes inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = createValidAnime().getName();

        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll returns list of animes when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = createValidAnime().getName();

        List<Anime> animeList = animeController.listAll().getBody();

        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        Long expectedId = createValidAnime().getId();

        Anime anime = animeController.findById(1).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("FindByName returns a list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        String expectedName = createValidAnime().getName();

        List<Anime> anime = animeController.findByName("anime").getBody();

        Assertions.assertThat(anime)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(anime.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindByName returns an empty list of anime when animes is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
        String expectedName = createValidAnime().getName();

        List<Anime> anime = animeController.findByName("anime").getBody();

        Assertions.assertThat(anime)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(anime.get(0).getName()).isEqualTo(expectedName);
    }
}