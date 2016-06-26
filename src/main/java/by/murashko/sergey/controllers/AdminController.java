package by.murashko.sergey.controllers;

import java.awt.Image;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import by.murashko.sergey.dao.impl.*;
import by.murashko.sergey.dao.impl.BookHome;
import by.murashko.sergey.dao.interfaces.*;
import by.murashko.sergey.entities.*;

@Controller
@SessionAttributes({ "genreList", "bookList", "publisherList", "usersList" })

public class AdminController {

	@Autowired
	private MessageSource messageSource;

	/*
	 * private List<Book> bookList; private List<Author> authorList; private
	 * List<Publisher> publisherList; private List<User> usersList;
	 */

	@Autowired
	private GenreDAO genreDao;
	@Autowired
	private AuthorDAO authorDao;

	@Autowired
	private BookDAO bookDao;
	@Autowired
	private PublisherDAO publisherDao;

	private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

	@ModelAttribute
	public Book createNewBook() {
		return new Book();
	}

	@ModelAttribute("genreList")
	public List<Genre> createNewGenreList() {

		return genreDao.getGenres();
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap) {
		modelMap.addAttribute("authorList", authorDao.getAuthors());
		modelMap.addAttribute("publisherList", publisherDao.getPublishers());
		return "admin";
	}

	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addbook(@ModelAttribute Book book, @RequestParam("contentFile") MultipartFile contentFile,@RequestParam("genre") String genre,@RequestParam("author") String author, @RequestParam("publisher") String publisher, @RequestParam("imageFile") MultipartFile imageFile, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap modelMap) throws IOException {
	book.setContent(contentFile.getBytes());
	book.setImage(imageFile.getBytes());
	book.setAuthor(authorDao.getAuthorByName(author));
	book.setPublisher(publisherDao.getPublisherByName(publisher));
	book.setGenre(genreDao.getGenreByName(genre));
	logger.info("!!!!!!!!!!!!!!SSSS!!!!!!!!!!!!!!!!!!"+book+" "+book.getPublishYear()+book.getPageCount()+""+book.getIsbn()); 
	bookDao.addBook(book);
			return "redirect:/admin";
	}

}
