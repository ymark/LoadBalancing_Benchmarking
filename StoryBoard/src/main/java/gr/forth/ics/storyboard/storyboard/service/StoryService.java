package gr.forth.ics.storyboard.storyboard.service;

import gr.forth.ics.storyboard.storyboard.model.Story;
import java.util.Collection;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Log4j2
public class StoryService {
    private final Configuration configuration;
    
    public StoryService(){
        this.configuration=new Configuration();
        this.configuration.configure("hibernate.cfg.xml");
        this.configuration.addAnnotatedClass(Story.class);
    }
   
    public Collection<Story> getAll(){   
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Story> persons=session.createQuery("from Story", Story.class).list();
        log.debug("Found "+persons.size()+" stories");
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return persons;
    }
        
    public Collection<Story> getStoriesWithAtLeastNumberOfLikes(int numberOfLikes){
        log.debug("search for stories with at least "+numberOfLikes+" likes");
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Story s WHERE s.numberOfLikes >= :numlikes", Story.class);
        query.setParameter("numlikes", numberOfLikes);
        List<Story> retStories=query.list();
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return retStories;
    }
    
    public Story getStoryWithId(int id){
        log.debug("search for story with id "+id);
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Story s WHERE s.id = :id", Story.class);
        query.setParameter("id", id);
        List<Story> retStories=query.list();
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return (!retStories.isEmpty()?retStories.get(0):null);
    }

    public void addStory(Story newStory){
        log.debug("Adding new story "+newStory);
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();
        Transaction transaction=session.beginTransaction();
        session.persist(newStory);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
    
    public void addStories(Collection<Story> newStories){
        log.debug("Adding "+newStories.size()+" stories");
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();
        Transaction transaction=session.beginTransaction();
        for(Story newStory : newStories){
            System.out.println(newStory);
            session.persist(newStory);
        }
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    
    public static void main(String[] args){
        StoryService storyService=new StoryService();
//        storyService.getAll();
//        Collection<Story> stories=storyService.getStoriesWithAtLeastNumberOfLikes(450);
//        System.out.println(stories.size());
//        Collection<Person> persons=personService.getAll();
//        for(Person person : persons){
//            System.out.println(person);
//        }
//        Collection<Person> results=personService.search(-1, "", "", "", false);
//        System.out.println(personService.getNewPersonIdReadable());
    }
}
