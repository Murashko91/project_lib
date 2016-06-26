package by.murashko.sergey.dao.interfaces;


import by.murashko.sergey.entities.Author;
import by.murashko.sergey.entities.Genre;

import java.util.List;

public interface GenreDAO {

    List<Genre> getGenres();
    public Genre getGenreByName(String name);

}
