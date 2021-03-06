package by.murashko.sergey.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import by.murashko.sergey.dao.interfaces.BookDAO;
import by.murashko.sergey.entities.Author;
import by.murashko.sergey.entities.Book;
import by.murashko.sergey.entities.Genre;

import by.murashko.sergey.entities.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

@Component
public class BookDAOImpl implements BookDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private ProjectionList bookProjection;
	private ProjectionList miniBookProjection;

	public BookDAOImpl() {
		bookProjection = Projections.projectionList();
		bookProjection.add(Projections.property("id"), "id");
		bookProjection.add(Projections.property("name"), "name");
		bookProjection.add(Projections.property("genre"), "genre");
		bookProjection.add(Projections.property("pageCount"), "pageCount");
		bookProjection.add(Projections.property("isbn"), "isbn");
		bookProjection.add(Projections.property("publisher"), "publisher");
		bookProjection.add(Projections.property("author"), "author");
		bookProjection.add(Projections.property("publishYear"), "publishYear");
		bookProjection.add(Projections.property("descr"), "descr");
		bookProjection.add(Projections.property("rating"), "rating");

		miniBookProjection = Projections.projectionList();
		miniBookProjection.add(Projections.property("id"), "id");
		miniBookProjection.add(Projections.property("name"), "name");

	}

	/*
	 * @Transactional
	 * 
	 * @Override public byte[] getImage(Long id) { List<Book> books =
	 * createBookList(createBookCriteria().add(Restrictions.eq("b.id", id)));
	 * byte[] image = books.get(0).getImage(); return image; }
	 */

	@Transactional
	@Override
	public List<Book> getBooks() {

		List<Book> books = createBookList(createBookCriteria());
		return books;
	}

	@Transactional
	@Override
	public List<Book> getLiteBookList() {

		Criteria criteria = DetachedCriteria.forClass(Book.class)
				.getExecutableCriteria(sessionFactory.getCurrentSession());
		criteria.addOrder(Order.asc("name")).setProjection(miniBookProjection)
				.setResultTransformer(Transformers.aliasToBean(Book.class));

		List<Book> books = criteria.list();
		return books;
	}

	@Transactional
	@Override
	public TreeSet<Character> getListFirstLetterBooks() {
		List<Book> books = getLiteBookList();

		TreeSet<Character> letters = new TreeSet<Character>();
		for (Book book : books) {
			String bookName = book.getName().toUpperCase();

			while (bookName.charAt(0) == ' ') {
				bookName = bookName.substring(1);
			}
			letters.add(bookName.charAt(0));

		}

		return letters;
	}

	@Transactional
	@Override
	public List<Book> getBooks(Author author) {
		List<Book> books = createBookList(
				createBookCriteria().add(Restrictions.ilike("author.fio", author.getFio(), MatchMode.ANYWHERE)));
		return books;
	}

	@Transactional
	@Override
	public List<Book> getBooks(String bookName) {
		List<Book> books = createBookList(
				createBookCriteria().add(Restrictions.ilike("b.name", bookName, MatchMode.ANYWHERE)));
		return books;
	}

	@Transactional
	@Override
	public List<Book> getBooks(Genre genre) {
		List<Book> books = createBookList(createBookCriteria().add(Restrictions.eq("genre.id", genre.getId())));
		return books;
	}

	@Transactional
	@Override
	public List<Book> getBooks(Character letter) {
		List<Book> books = createBookList(
				createBookCriteria().add(Restrictions.ilike("b.name", letter.toString(), MatchMode.START)));
		return books;

	}

	private DetachedCriteria createBookCriteria() {
		DetachedCriteria bookListCriteria = DetachedCriteria.forClass(Book.class, "b");
		// если убрать алиасы то можно убрать. попробывать
		createAliases(bookListCriteria);

		return bookListCriteria;
	}

	/// можно попробывать убрать алиасы
	private void createAliases(DetachedCriteria criteria) {
		criteria.createAlias("b.author", "author");
		criteria.createAlias("b.genre", "genre");
		criteria.createAlias("b.publisher", "publisher");
	}

	private List<Book> createBookList(DetachedCriteria bookListCriteria) {
		Criteria criteria = bookListCriteria.getExecutableCriteria(sessionFactory.getCurrentSession());
		criteria.addOrder(Order.asc("b.name")).setProjection(bookProjection)
				.setResultTransformer(Transformers.aliasToBean(Book.class));
		return criteria.list();
	}
	/*
	 * @Transactional
	 * 
	 * @Override public Book getBookById(Long id){ return (Book)
	 * sessionFactory.getCurrentSession().createCriteria(Book.class).add(
	 * Restrictions.eq("id", id)).uniqueResult();
	 * 
	 * }
	 */

	@Transactional
	@Override
	public Book getBookById(Long id) {

		return (Book) sessionFactory.getCurrentSession().get(Book.class, id);
	}

	@Transactional
	@Override
	public byte[] getImage(Long id) {

		byte[] image = getBookById(id).getImage();
		return image;
	}

	@Transactional
	@Override
	public byte[] getContent(Long id) {
		byte[] content = getBookById(id).getContent();
		return content;

	}

	@Transactional
	@Override
	public void addBook(Book book) {
		Session session = this.sessionFactory.getCurrentSession();

		session.save(book);
	}

	@Override
	@Transactional
	public void removeBook(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Book book = (Book) session.load(Book.class, new Long(id));
		if (null != book) {
			session.delete(book);
		}
	}

	@Override
	@Transactional
	public void upgdateBook(Book book) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(book);
	}

}
