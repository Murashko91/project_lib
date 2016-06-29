package by.murashko.sergey.dao.interfaces;


import by.murashko.sergey.entities.Author;
import by.murashko.sergey.entities.Book;
import by.murashko.sergey.entities.Genre;

import java.util.List;


public interface BookDAO {

    List<Book> getBooks();
    List<Book> getBooks(Author author);
    List<Book> getBooks(String bookName);
    List<Book> getBooks(Genre genre);
    List<Book> getBooks(Character letter);
    Book getBookById(Long id);
    byte[] getImage(Long id);
    public byte[] getContent(Long id);
    public void addBook(Book book);
    public void removeBook(Long id);
	void upgdateBook(Book book);
 

}
