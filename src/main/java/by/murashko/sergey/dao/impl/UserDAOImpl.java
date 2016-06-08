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

import by.murashko.sergey.dao.interfaces.UserDAO;
import by.murashko.sergey.entities.Book;
import by.murashko.sergey.entities.User;
import by.murashko.sergey.entities.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SortingFocusTraversalPolicy;

@Component
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void addUser(User user) {
		Users userDb = new Users(user.getName(), user.getPassword(), user.getMail());
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(userDb);
	}

	@Override
	@Transactional
	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Users p = (Users) session.load(Users.class, new Long(id));
		if (null != p) {
			session.delete(p);
		}
	}

	@Override
	@Transactional
	public Users getUserFromDbByName(String name) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Users.class);
		criteria.add(Restrictions.eq("name", name));
		Users dbUser = (Users) criteria.uniqueResult();
		return dbUser;
	}

	@Override
	@Transactional
	public boolean acceptUser(User user) {
		try {
			Users dbUser = getUserFromDbByName(user.getName());
			// без хеш-кода почему-то не работает. над разбираться в хибернейте
			if (dbUser != null && user.getPassword().hashCode() == dbUser.getPassword().hashCode()) {
				user.setMail(dbUser.getMail());
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	/*
	 * @Override
	 * 
	 * @Transactional public void removeUser(int id) { Session session =
	 * this.sessionFactory.getCurrentSession(); Users p = (Users)
	 * session.load(Users.class, new Long(id)); if (null != p) {
	 * session.delete(p); }
	 */

	@Override
	@Transactional
	public List<Users> getAllUsers() {
		return sessionFactory.getCurrentSession().createCriteria(Users.class).list();

	}
}