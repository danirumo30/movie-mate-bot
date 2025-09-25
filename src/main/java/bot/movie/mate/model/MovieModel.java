package bot.movie.mate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieModel {

    private Integer id;
    private String title;
    private String description;
    private String posterPath;
    private String releaseDate;
    private CastModel director;
    private List<CastModel> cast;
    private List<String> genres;

}
