package by.murashko.sergey.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import by.murashko.sergey.dao.interfaces.AuthorDAO;
import by.murashko.sergey.entities.Author;
import scala.annotation.meta.getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SortingFocusTraversalPolicy;

@Component
public class AuthorDAOImpl implements AuthorDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	@Transactional
	public List<Author> getAuthors() {

		return sessionFactory.getCurrentSession().createCriteria(Author.class).list();

	}

}