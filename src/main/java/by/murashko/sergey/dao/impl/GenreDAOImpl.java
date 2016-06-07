package by.murashko.sergey.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import by.murashko.sergey.dao.interfaces.GenreDAO;
import by.murashko.sergey.entities.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SortingFocusTraversalPolicy;

@Component
public class GenreDAOImpl implements GenreDAO{

		@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

    @Override
    @Transactional
    public List<Genre> getGenres() {
    	  System.out.println("!!!!!!!!!!!getsession"+sessionFactory.getCurrentSession().toString());
       return sessionFactory.getCurrentSession().createCriteria(Genre.class).list();
     
       
    }





}
