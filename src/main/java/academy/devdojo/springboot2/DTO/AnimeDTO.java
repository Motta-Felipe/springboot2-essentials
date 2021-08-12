package academy.devdojo.springboot2.DTO;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

@Data
public class AnimeDTO {
    private Long id;

    @NotEmpty
    private String name;

    @URL(message = "The URL is not valid")
    private String url;
}
