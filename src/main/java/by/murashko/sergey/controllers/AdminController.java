package by.murashko.sergey.controllers;

import java.awt.Image;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
@SessionAttributes({ "genreList", "publisherList", "usersList" })

public class AdminController {

	@Autowired
	private MessageSource messageSource;


	@Autowired
	private GenreDAO genreDao;
	@Autowired
	private AuthorDAO authorDao;

	@Autowired
	private BookDAO bookDao;
	@Autowired
	private PublisherDAO publisherDao;

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@ModelAttribute
	public Book createNewBook() {
		return new Book();
	}

	@ModelAttribute
	public Author createNewAuthor() {
		return new Author();
	}

	@ModelAttribute
	public Genre createNewGenre() {
		return new Genre();
	}

	@ModelAttribute
	public Publisher createNewPublisher() {
		return new Publisher();
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap) {
		modelMap.addAttribute("authorList", authorDao.getAuthors());
		modelMap.addAttribute("publisherList", publisherDao.getPublishers());
		modelMap.addAttribute("genreList", genreDao.getGenres());
		modelMap.addAttribute("bookList", bookDao.getBooks());

		return "admin";
	}

	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addbook(@ModelAttribute Book book, @RequestParam("contentFile") MultipartFile contentFile,
			@RequestParam("genre") String genre, @RequestParam("author") String author,
			@RequestParam("publisher") String publisher, @RequestParam("imageFile") MultipartFile imageFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap,
			RedirectAttributes redirectAttributes) throws IOException {
		String s = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
		for(int i = 0;i<5; i++){s += s; }
		logger.info(s);
		System.out.println("SSSS"+s);
		System.out.println("2222"+s);
		try {
			book.setContent(contentFile.getBytes());
			book.setImage(imageFile.getBytes());
			book.setAuthor(authorDao.getAuthorByName(author));
			book.setPublisher(publisherDao.getPublisherByName(publisher));
			book.setGenre(genreDao.getGenreByName(genre));
			if (request.getParameter("errorPage") != null) {
				throw new Exception("Error in form add book");
			}
			bookDao.addBook(book);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL",
					"Возникли проблемы при добавлении книги. " + "Скорее всего неправильно заполнена форма.");
			return "redirect:/admin?add_book=true";
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Книга успешно добавлена.");
		return "redirect:/admin?add_book=true";
	}

	@RequestMapping(value = "/addauthor", method = RequestMethod.POST)
	public String addauthor(@ModelAttribute Author author, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws IOException {
		try {
			logger.info("Created Author befor add to Database:" + author.getFio());

			if (request.getParameter("errorPage").length() > 1) {
				throw new Exception("Error in form add author");
			}
			authorDao.addAuthor(author);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL",
					"Возникли проблемы при добавлении автора. " + "Скорее всего неправильно заполнена форма.");
			return "redirect:/admin?add_author=true";
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Автор успешно добавлен.");

		return "redirect:/admin?add_author=true";
	}

	@RequestMapping(value = "/addgenre", method = RequestMethod.POST)
	public String addgenre(@ModelAttribute Genre genre, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws IOException {
		try {
			logger.info("Created Author befor add to Database:" + genre.getName());

			if (request.getParameter("errorPage").length() > 1) {
				throw new Exception("Error in form add author");
			}
			genreDao.addGenre(genre);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL",
					"Возникли проблемы при добавлении жанра. " + "Скорее всего неправильно заполнена форма.");
			return "redirect:/admin?add_genre=true";
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Жанр успешно добавлен.");

		return "redirect:/admin?add_genre=true";
	}

	@RequestMapping(value = "/addpublisher", method = RequestMethod.POST)
	public String addpublisher(@ModelAttribute Publisher publisher, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws IOException {
		try {
			logger.info("Created Publisher befor add to Database:" + publisher.getName());

			if (request.getParameter("errorPage").length() > 1) {
				throw new Exception("Error in form add author");
			}
			publisherDao.addPublisher(publisher);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL",
					"Возникли проблемы при добавлении жанра. " + "Скорее всего неправильно заполнена форма.");
			return "redirect:/admin?add_publisher=true";
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Издание успешно добавлено.");

		return "redirect:/admin?add_publisher=true";
	}
	
	
	@RequestMapping(value = "/removebook", method = RequestMethod.POST)
	public String removebook(
			HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap,
			RedirectAttributes redirectAttributes) throws Exception {
		String s = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
		for(int i = 0;i<5; i++){s += s; }
		logger.info(s);
		System.out.println(s);
		System.out.println(s);
		
		return "redirect:/admin";
	}

}
