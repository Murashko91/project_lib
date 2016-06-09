package by.murashko.sergey.controllers;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.jdbc.exceptions.*;

import by.murashko.sergey.dao.interfaces.UserDAO;
import by.murashko.sergey.entities.*;

@Controller
@SessionAttributes("user")

public class LoginController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserDAO userDao;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@ModelAttribute
	public User createNewUser() {
		return new User("User");
	}

	private void addAuthentication(HttpSession session) {
		session.setAttribute("go", "true");
	}

	private void removeAuthentication(HttpSession session) {
		session.removeAttribute("go");
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(@ModelAttribute User user, HttpSession session, Locale locale, ModelMap model) {
		logger.info(locale.getDisplayLanguage());

		logger.info(messageSource.getMessage("locale", new String[] { locale.getDisplayName(locale) }, locale));

		return "login";
	}

	@RequestMapping(value = "/check-user", method = RequestMethod.POST)
	public String checkUser(Locale locale, @Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes, HttpSession session) {
		if (user.isGuest() == true) {
			/* modelMap.addAttribute("user", new User("AnonimGuest")); */
			// не работает. после редиректо остается только user

			session.setAttribute("user", new User("AnonimGuest"));
			addAuthentication(session);
			return "redirect:/main";
		}
		if (!bindingResult.hasErrors()) {
			if (userDao.acceptUser(user)) {
				addAuthentication(session);
				return "redirect:/main";
			} else
				return "redirect:regpage";
		}

		return "redirect:/login";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap modelMap,
			RedirectAttributes redirectAttributes, Locale locale) {

		if (!bindingResult.hasErrors()) {

			try {
				userDao.addUser(user);

			} catch (Exception e) {

				redirectAttributes.addFlashAttribute("addUser",
						messageSource.getMessage("addusererror", new String[] {}, locale));
				return "redirect:/regpage";

			}
			return "redirect:/login";
		} else {

			return "redirect:/regpage";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, @Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

		return "login";
	}

	@RequestMapping(value = "/regpage", method = RequestMethod.GET)
	public String regpage(Locale locale, @Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

		return "regpage";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String login(Locale locale, HttpSession session, ModelMap model) {
		removeAuthentication(session);
		return "redirect:/login";
	}

}
