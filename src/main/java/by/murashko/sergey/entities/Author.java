package by.murashko.sergey.entities;
// Generated Jun 8, 2016 2:07:14 PM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Author generated by hbm2java
 */
@Entity
@Table(name = "author", catalog = "springlib")
public class Author implements java.io.Serializable {

	private Long id;
	public String fio;
	@DateTimeFormat(pattern = "yyyy/MM/dd" )
	private Date birthday;
	private Set<Book> books = new HashSet<Book>(0);

	public Author() {
	}
	
	
	public Author(String fio) {
		this.fio = fio;
	}

	public Author(String fio, Date birthday) {
		this.fio = fio;
		this.birthday = birthday;
	}

	public Author(String fio, Date birthday, Set<Book> books) {
		this.fio = fio;
		this.birthday = birthday;
		this.books = books;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "fio", nullable = false, length = 300)
	public String getFio() {
		return this.fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", nullable = false, length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
	@Fetch (FetchMode.SELECT)
	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	@Override
	public String toString(){
		return fio;
		
	}

}
