package by.murashko.sergey.controllers;

import java.awt.Image;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import by.murashko.sergey.dao.impl.*;
import by.murashko.sergey.dao.interfaces.*;
import by.murashko.sergey.entities.*;

@Controller
@SessionAttributes("genreList")

public class LibraryController {

	@Autowired
	private MessageSource messageSource;

	private List<Book> bookList;
	@Autowired
	private GenreDAO genreDao;

	@Autowired
	private BookDAO bookDao;

	private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

	@ModelAttribute
	public User createNewUser() {
		return new User("Guest");
	}

	@ModelAttribute("genreList")
	public List<Genre> createNewGenreList() {

		return genreDao.getGenres();
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String gomain(@ModelAttribute("genreList") List<Genre> genreList, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		logger.info("go to main-page on library");

		return "main";
	}

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String golibrary(@ModelAttribute("genreList") List<Genre> genreList, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model, Locale locale) {
		logger.info("go books-page on library");

		// Search by Genre
		if (request.getParameter("genre_id") != null) {
			long genreId = Long.valueOf(request.getParameter("genre_id"));

			for (Genre genre : genreList) {
				if (genre.getId() == genreId) {
					bookList = bookDao.getBooks(genre);
				}
			}
		}else{bookList = bookDao.getBooks();}

		// Search by Author(author) and by string name book
		if (request.getParameter("search_string") != null) {
			String search_string = request.getParameter("search_string");
			if (request.getParameter("search_type") == messageSource.getMessage("search_author", new String[] {},
					locale)) {
				bookList = bookDao.getBooks(new Author(search_string));
			} else {
				bookList = bookDao.getBooks(search_string);
			}
		}

		// Search by letter
		if (request.getParameter("letter") != null) {
			Character letter = ((request.getParameter("letter")).toLowerCase()).charAt(0);
			bookList = bookDao.getBooks(letter);
		}
		model.addAttribute("bookList", bookList);
		return "books";

	}
	
	
	@RequestMapping(value = "/imageController/{bookId}")
	@ResponseBody
	public byte[] getImage(@PathVariable long bookId)  {
		byte[] image = bookDao.getImage(bookId);
	  return image;
	}
	
	@RequestMapping(value = "/contentController/{bookId}")
	@ResponseBody
	public byte[] getContent(@PathVariable long bookId)  {
		byte[] content = bookDao.getContent(bookId);
	  return content;
	}
	

}
