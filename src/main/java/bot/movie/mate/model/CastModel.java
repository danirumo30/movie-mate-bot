package bot.movie.mate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CastModel {

    private Integer id;
    private String name;
    private String character;
    private String birthDate;
    private String deathDate;
    private String birthPlace;
    private String biography;
    private String photoUrl;
    private List<MovieModel> movies;

}
