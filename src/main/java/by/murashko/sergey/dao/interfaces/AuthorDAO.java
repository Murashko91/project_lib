package by.murashko.sergey.dao.interfaces;


import by.murashko.sergey.entities.Author;


import java.util.List;


public interface AuthorDAO {

	  List<Author> getAuthors();
	  public Author getAuthorByName(String name);

}
