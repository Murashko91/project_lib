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
import by.murashko.sergey.dao.interfaces.PublisherDAO;
import by.murashko.sergey.entities.Genre;
import by.murashko.sergey.entities.Publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SortingFocusTraversalPolicy;

@Component
public class PublisherDAOImpl implements PublisherDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	@Transactional
	public List<Publisher> getPublishers() {

		return sessionFactory.getCurrentSession().createCriteria(Publisher.class).list();

	}
	@Override
	@Transactional
	  public Publisher getPublisherByName(String name){
   	   
   	   return (Publisher)sessionFactory.getCurrentSession().createCriteria(Publisher.class).add(Restrictions.eq("name", name)).uniqueResult();
      
      
   }
	@Override
	@Transactional
	 public void addPublisher(Publisher publisher){Session session = this.sessionFactory.getCurrentSession();
		session.save(publisher);};
	 @Override
		@Transactional
	    public void removePublisher(int id){
		 Session session = this.sessionFactory.getCurrentSession();
		 Publisher publisher = (Publisher) session.load(Publisher.class, new Long(id));
		if (null != publisher) {
			
			session.delete(publisher);
		}else throw new NullPointerException(" WARN! genre = NULL in session.delete(publisher);");};
	

}
