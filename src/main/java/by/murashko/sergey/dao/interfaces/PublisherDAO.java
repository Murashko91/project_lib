package by.murashko.sergey.dao.interfaces;



import by.murashko.sergey.entities.Publisher;

import java.util.List;

public interface PublisherDAO {

    List<Publisher> getPublishers();
    public Publisher getPublisherByName(String name);
    public void addPublisher(Publisher publisher);
    public void removePublisher(int id);
}
