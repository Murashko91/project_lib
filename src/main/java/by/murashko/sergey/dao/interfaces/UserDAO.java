package by.murashko.sergey.dao.interfaces;

import java.util.List;

import by.murashko.sergey.entities.*;

public interface UserDAO {

	public void addUser(User user);

	public void removeUser(int id);

	public List<Users> getAllUsers();
	public boolean acceptUser(User user);
	
	public Users getUserFromDbByName(String name);
}