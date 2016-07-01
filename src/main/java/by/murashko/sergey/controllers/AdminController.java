package by.murashko.sergey.controllers;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
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
	private UserDAO userDao;

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

	@ModelAttribute
	public List createNewGenreList() {
		return genreDao.getGenres();
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap, RedirectAttributes redirectAttributes) {

		modelMap.addAttribute("authorList", authorDao.getAuthors());
		modelMap.addAttribute("publisherList", publisherDao.getPublishers());
		modelMap.addAttribute("genreList", genreDao.getGenres());
		modelMap.addAttribute("bookList", bookDao.getBooks());
		modelMap.addAttribute("userList", userDao.getAllUsers());
		System.out.println("!!!!!!!!!!!!!!! GenrelistSIZE" + genreDao.getGenres().size() + "to string"
				+ genreDao.getGenres().toString());

		/*
		 * if(request.getParameter("edit_book")!=null&&request.getAttribute(
		 * "editBook")==null){ return "redirect:/admin?edit_bookList=true"; }
		 */
		return "admin";
	}

	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addbook(@ModelAttribute Book book, @RequestParam("contentFile") MultipartFile contentFile,
			@RequestParam("genre") String genre, @RequestParam("author") String author,
			@RequestParam("publisher") String publisher, @RequestParam("imageFile") MultipartFile imageFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap,
			RedirectAttributes redirectAttributes) throws IOException {

		try {
			book.setContent(contentFile.getBytes());
			book.setImage(imageFile.getBytes());
			book.setAuthor(authorDao.getAuthorByName(author));
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + author);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + genre);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + publisher);
			book.setPublisher(publisherDao.getPublisherByName(publisher));
			book.setGenre(genreDao.getGenreByName(genre));
			if (Integer.parseInt(request.getParameter("errorPage")) != 0) {
				// throw new Exception("Error in form add book");
				request.getParameter("errorPage").length();
				System.out
						.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + request.getParameter("errorPage"));
				System.out
						.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + request.getParameter("errorPage"));
				System.out.println(
						"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + request.getParameter("errorPage").length());
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
	public String removebook(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap, RedirectAttributes redirectAttributes) throws Exception {

		logger.info("IN   '/removebook' method");
		try {
			Map<String, String[]> mapParamerts = request.getParameterMap();
			Iterator<String> keySetIterator = mapParamerts.keySet().iterator();
			int haveСhoiceDelMeter = 0;
			while (keySetIterator.hasNext()) {

				String key = keySetIterator.next();
				if (key.contains("bookID_")) {
					logger.info("key_delBook: " + key + "ID: " + (mapParamerts.get(key))[0]);
					haveСhoiceDelMeter++;
					bookDao.removeBook(Long.parseLong((mapParamerts.get(key)[0])));
				}
			}
			if (haveСhoiceDelMeter == 0) {
				redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", "Не выбрана ни одна книга. ");
				return "redirect:/admin?remove_book=true";
			}
		} catch (Exception e) {
			logger.warn("Exceptiont in /removebook' method");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", "Возникли проблемы при удалении книги. ");
			return "redirect:/admin?remove_book=true";
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Выбранные книги успешно удалены. ");
		return "redirect:/admin?remove_book=true";
	}

	@RequestMapping(value = "/removeauthor", method = RequestMethod.POST)
	public String removeauthor(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap, RedirectAttributes redirectAttributes) throws Exception {
		String redirect = "redirect:/admin?remove_author=true";
		logger.info("IN   '/removeauthor' method");
		try {
			Map<String, String[]> mapParamerts = request.getParameterMap();
			Iterator<String> keySetIterator = mapParamerts.keySet().iterator();
			int haveСhoiceDelMeter = 0;
			while (keySetIterator.hasNext()) {

				String key = keySetIterator.next();
				if (key.contains("authorID_")) {
					logger.info("key_delAuthor: " + key + "ID: " + (mapParamerts.get(key))[0]);
					haveСhoiceDelMeter++;
					authorDao.removeAuthor(Integer.parseInt(mapParamerts.get(key)[0]));
				}
			}
			if (haveСhoiceDelMeter == 0) {
				redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", "Не выбран автор. ");
				return redirect;
			}
		} catch (Exception e) {
			logger.warn("Exceptiont in /removeauthor' method");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", "Возникли проблемы при удалении автора ");
			return redirect;
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Выбранные авторы успешно удалены. ");
		return redirect;
	}

	@RequestMapping(value = "/removegenre", method = RequestMethod.POST)
	public String removegenre(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap, RedirectAttributes redirectAttributes) throws Exception {

		logger.info("IN   '/removegenre' method");
		int haveСhoiceDelMeter = 0;
		String redirect = "redirect:/admin?remove_genre=true";
		try {
			Map<String, String[]> mapParamerts = request.getParameterMap();
			Iterator<String> keySetIterator = mapParamerts.keySet().iterator();

			while (keySetIterator.hasNext()) {

				String key = keySetIterator.next();
				if (key.contains("genreID_")) {
					logger.info("key_delGenre: " + key + "ID: " + (mapParamerts.get(key))[0]);
					haveСhoiceDelMeter++;
					genreDao.removeGenre(Integer.parseInt(mapParamerts.get(key)[0]));
				}
			}
			if (haveСhoiceDelMeter == 0) {
				redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", ":Жанр не выбран. ");
				return redirect;
			}
		} catch (Exception e) {
			logger.warn("Exceptiont in /removegenre' method");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", "Возникли проблемы при удалении жанра. ");
			return redirect;
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Выбранные жанры успешно удалены. ");
		return redirect;
	}

	@RequestMapping(value = "/removepublisher", method = RequestMethod.POST)
	public String removepublisher(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap, RedirectAttributes redirectAttributes) throws Exception {
		int haveСhoiceDelMeter = 0;
		String redirect = "redirect:/admin?remove_publisher=true";
		logger.info("IN   '/removepublisher' method");
		try {
			Map<String, String[]> mapParamerts = request.getParameterMap();
			Iterator<String> keySetIterator = mapParamerts.keySet().iterator();
			while (keySetIterator.hasNext()) {

				String key = keySetIterator.next();
				if (key.contains("publisherID_")) {
					logger.info("key_delPublisher: " + key + "ID: " + (mapParamerts.get(key))[0]);
					haveСhoiceDelMeter++;
					publisherDao.removePublisher(Integer.parseInt(mapParamerts.get(key)[0]));
				}
			}
			if (haveСhoiceDelMeter == 0) {
				redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", ": Издание не выбрано. ");
				return redirect;
			}
		} catch (Exception e) {
			logger.warn("Exceptiont in /removepublisher' method");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", "Возникли проблемы при удалении издания. ");
			return redirect;
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Выбранные издания успешно удалены. ");
		return redirect;
	}

	@RequestMapping(value = "/removeuser", method = RequestMethod.POST)
	public String removeuser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap, RedirectAttributes redirectAttributes) throws Exception {
		int haveСhoiceDelMeter = 0;
		String redirect = "redirect:/admin?remove_user=true";
		logger.info("IN   '/removeuser' method");
		try {
			Map<String, String[]> mapParamerts = request.getParameterMap();
			Iterator<String> keySetIterator = mapParamerts.keySet().iterator();
			while (keySetIterator.hasNext()) {

				String key = keySetIterator.next();
				if (key.contains("userID_")) {
					logger.info("key_delUser: " + key + "ID: " + (mapParamerts.get(key))[0]);
					haveСhoiceDelMeter++;
					userDao.removeUser(Integer.parseInt(mapParamerts.get(key)[0]));
				}
			}
			if (haveСhoiceDelMeter == 0) {
				redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL", ": Пользователь не выбран. ");
				return redirect;
			}
		} catch (Exception e) {
			logger.warn("Exceptiont in /removeuser' method");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL",
					"Возникли проблемы при удалении пользователя. ");
			return redirect;
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Выбранные пользователи успешно удалены. ");
		return redirect;
	}

	@RequestMapping(value = "/editbook", method = RequestMethod.POST)
	public String editbook(@ModelAttribute Book book, @RequestParam("contentFile") MultipartFile contentFile,
			@RequestParam("genre") String genre, @RequestParam("author") String author,
			@RequestParam("publisher") String publisher, @RequestParam("imageFile") MultipartFile imageFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap,
			RedirectAttributes redirectAttributes) throws IOException {

		String redidect = "redirect:/admin?edit_bookList=true";
		try {
			book.setId(Long.parseLong(request.getParameter("id")));
			Book editBook = bookDao.getBookById(Long.parseLong(request.getParameter("id")));

			if (contentFile.getSize() < 1) {
				book.setContent(editBook.getImage());
			} else {
				book.setContent(contentFile.getBytes());
			}
			if (imageFile.getSize() < 1) {
				book.setImage(editBook.getImage());
			} else {
				book.setImage(imageFile.getBytes());
			}

			if (author == null || publisher == null || genre == null) {
				throw new Exception("Error in form add book");
			}

			book.setAuthor(authorDao.getAuthorByName(author));
			book.setPublisher(publisherDao.getPublisherByName(publisher));
			book.setGenre(genreDao.getGenreByName(genre));
			// if (request.getParameter("errorPage") != "0") {
			// throw new Exception("Error in form add book");
			// }

			bookDao.upgdateBook(book);

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL",
					"Возникли проблемы при добавлении книги. " + "Скорее всего неправильно заполнена форма.");
			return redidect;
		}
		redirectAttributes.addFlashAttribute("adminInfoPreAction_OK", "Книга успешно изменена.");
		return redidect;
	}

	@RequestMapping(value = "/redirect_editbook", method = RequestMethod.POST)
	public String redirect_editbook(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap modelMap, RedirectAttributes redirectAttributes) throws Exception {

		logger.info("IN   '/redirect_editbook' method");
		try {
			String bookID = request.getParameter("editBookID");

			if (bookID != null) {
				redirectAttributes.addFlashAttribute("editBook",
						bookDao.getBookById(Long.parseLong(request.getParameter("editBookID"))));

				return "redirect:/admin?edit_book=true";
			} else {
				throw new Exception("Error in form editbooklist");
			}
		} catch (Exception e) {
			logger.warn("Exceptiont in /redirect_editbook' method");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("adminInfoPreAction_FALL",
					"Возникли проблемы. Скорее всего книга не выбрана. ");
			return "redirect:/admin?edit_bookList=true";
		}

	}

}
