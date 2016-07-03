package by.murashko.sergey.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import by.murashko.sergey.dao.interfaces.MessageDAO;
import by.murashko.sergey.entities.Messages;
import java.util.List;


@Component
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	
	@Override
	@Transactional
	public List<Messages> getMessages() {

		return sessionFactory.getCurrentSession().createCriteria(Messages.class).addOrder(Order.asc("id")).list();

	}

	@Transactional
	@Override
	public void addMessage(Messages message) { 
		Session session = this.sessionFactory.getCurrentSession();
		session.save(message);
	}

	@Transactional
	@Override
	public void removeMessage(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Messages message = (Messages) session.load(Messages.class, id);
		if (null != message) {
			session.delete(message);
		} else
			throw new NullPointerException(" WARN! genre = NULL in session.delete(message);");

	}


	

}