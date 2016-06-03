package by.murashko.sergey.controllers;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import by.murashko.sergey.beans.TestBean;
import by.murashko.sergey.objects.User;

@Controller
@SessionAttributes("user")
public class LoginController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	SessionFactory ssessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@ModelAttribute
	public User createNewUser() {
		return new User("usernamevalue");
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(@ModelAttribute User user, HttpSession session, Locale locale ) {
		logger.info(locale.getDisplayLanguage());
		logger.info(messageSource.getMessage("locale", new String[] { locale.getDisplayName(locale) }, locale));
		logger.info("ssessionFactory is =="+ssessionFactory.toString());
	
		return "login";
	}

	@RequestMapping(value = "/check-user", method = RequestMethod.POST)
	public String checkUser(Locale locale, @Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes) {

		if (!bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("locale", messageSource.getMessage("locale", new String[] { locale.getDisplayName(locale) }, locale));
			return "redirect:/mainpage";
		}
		redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
		return "redirect:/login";
	}
	
	
	

	@RequestMapping(value = "/mainpage", method = RequestMethod.GET)
	public String goMainPage(HttpServletRequest request) {

		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		if (map != null) {
			logger.info("redirect!");
		} else {
			logger.info("update!");
		}

		return "main";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, @Valid @ModelAttribute("user") User user, BindingResult bindingResult){
		
		return "login";
		}

	@RequestMapping(value = "/failed", method = RequestMethod.GET)
	public ModelAndView failed() {
		return new ModelAndView("login-failed", "message", "Login failed!");
	}

}
