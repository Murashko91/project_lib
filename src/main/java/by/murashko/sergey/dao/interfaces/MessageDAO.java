package by.murashko.sergey.dao.interfaces;


import by.murashko.sergey.entities.Messages;

import java.util.List;




public interface MessageDAO {


	public List<Messages> getMessages();

	public void addMessage(Messages message); 


	void removeMessage(int id); 
}
