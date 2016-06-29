package by.murashko.sergey.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import by.murashko.sergey.dao.interfaces.GenreDAO;
import by.murashko.sergey.entities.Author;
import by.murashko.sergey.entities.Book;
import by.murashko.sergey.entities.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SortingFocusTraversalPolicy;

@Component
public class GenreDAOImpl implements GenreDAO{

		@Autowired
	private SessionFactory sessionFactory;
	
/*	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}*/

    @Override
    @Transactional
    public List<Genre> getGenres() {
    	 
       return sessionFactory.getCurrentSession().createCriteria(Genre.class).list();}
     
      
    @Override
    @Transactional
    public Genre getGenreByName(String name){
    	   
    	   return (Genre)sessionFactory.getCurrentSession().createCriteria(Genre.class).add(Restrictions.eq("name", name)).uniqueResult();
       
       
    }
    @Override
    @Transactional
    public void addGenre(Genre genre){
    	Session session = this.sessionFactory.getCurrentSession();
		session.save(genre);
    };
    @Override
    @Transactional
    public void removeGenre(int id){
    	Session session = this.sessionFactory.getCurrentSession();
		Genre genre = (Genre) session.load(Genre.class, new Long(id));
		if (null != genre) {
			session.delete(genre);
		}else throw new NullPointerException(" WARN! genre = NULL in session.delete(genre);");
    	
    	
    }





}
