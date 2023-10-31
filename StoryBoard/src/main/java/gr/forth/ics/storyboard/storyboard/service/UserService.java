package gr.forth.ics.storyboard.storyboard.service;

import gr.forth.ics.storyboard.storyboard.model.User;
import java.util.Collection;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Log4j2
public class UserService {
    private final Configuration configuration;
    
    public UserService(){
        this.configuration=new Configuration();
        this.configuration.configure("hibernate.cfg.xml");
        this.configuration.addAnnotatedClass(User.class);
    }
   
    public Collection<User> getAll(){   
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.openSession();
        List<User> persons=session.createQuery("from User", User.class).list();
        log.debug("Found "+persons.size()+" users");
        session.close();
        return persons;
    }
        
//    public Collection<Person> search(int id, String firstName, String lastName, String phoneNumber, boolean deletedMember){
//        log.debug("Search persons using id: "+id+", firstName: "+firstName+", lastName: "+lastName+", phoneNumber: "+phoneNumber+", deletedMember: "+deletedMember);
//        SessionFactory sessionFactory=configuration.buildSessionFactory();
//        Session session=sessionFactory.openSession();
//        Predicate<Person> idPredicate=(id<0)?p -> true:p -> p.getPersonIdReadable()==id;
//        Predicate<Person> firstNamePredicate=(firstName.isBlank())?p -> true:p -> p.getFirstName().toLowerCase().contains(firstName.toLowerCase());
//        Predicate<Person> lastNamePredicate=(lastName.isBlank())?p -> true:p -> p.getLastName().toLowerCase().contains(lastName.toLowerCase());
//        Predicate<Person> phonePredicate=(phoneNumber.isBlank())?p -> true:p -> p.getPhone().toLowerCase().contains(phoneNumber.toLowerCase());
//        List<Person> persons=session.createQuery("from Person", Person.class).getResultStream()
//                .filter(idPredicate)
//                .filter(firstNamePredicate)
//                .filter(lastNamePredicate)
//                .filter(phonePredicate)
//                .filter(p -> p.isDeleted()==deletedMember)
//                .sorted(Comparator.comparingInt(Person::getPersonIdReadable))
//                .collect(Collectors.toList());
//        log.debug("Found "+persons.size()+" persons");
//        session.close();
//        return persons;
//    }
    
//    public int getNewPersonIdReadable(){
//        log.debug("retrieve new person ID");
//        SessionFactory sessionFactory=configuration.buildSessionFactory();
//        Session session=sessionFactory.openSession();
//        int result=(int)session.createQuery("select max(personIdReadable) from Person").uniqueResult();
//        session.close();
//        return result+1;
//    }
//    
    public void addUser(User newUser){
        log.debug("Adding new user "+newUser);
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        session.persist(newUser);
        transaction.commit();
        session.close();
    }
    
//    public void updatePerson(Person person){
//        log.debug("update person "+person);
//        SessionFactory sessionFactory=configuration.buildSessionFactory();
//        Session session=sessionFactory.openSession();
//        Transaction transaction=session.beginTransaction();
//        session.update(person);
//        transaction.commit();
//        session.close();
//    }
//    
//    public void deletePerson(Person person){
//        log.debug("delete person "+person);
//        SessionFactory sessionFactory=configuration.buildSessionFactory();
//        Session session=sessionFactory.openSession();
//        person.setDeleted(true);
//        Transaction transaction=session.beginTransaction();
//        session.update(person);
//        transaction.commit();
//        session.close();
//    }
//    
//    public void restorePerson(Person person){
//        log.debug("restore person "+person);
//        SessionFactory sessionFactory=configuration.buildSessionFactory();
//        Session session=sessionFactory.openSession();
//        person.setDeleted(false);
//        Transaction transaction=session.beginTransaction();
//        session.update(person);
//        transaction.commit();
//        session.close();
//    }
    
    public static void main(String[] args){
        UserService userService=new UserService();
        userService.getAll();
//        Collection<Person> persons=personService.getAll();
//        for(Person person : persons){
//            System.out.println(person);
//        }
//        Collection<Person> results=personService.search(-1, "", "", "", false);
//        System.out.println(personService.getNewPersonIdReadable());
    }
}
