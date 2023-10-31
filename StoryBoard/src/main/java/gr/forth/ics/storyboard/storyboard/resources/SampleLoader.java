package gr.forth.ics.storyboard.storyboard.resources;

import gr.forth.ics.storyboard.storyboard.model.User;
import gr.forth.ics.storyboard.storyboard.service.UserService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
//@AllArgsConstructor
@Log4j2
public class SampleLoader {
    
    private static void loadSampleUsers(){
        UserService userService=new UserService();
        userService.addUser(new User(1,"username-1"));
        userService.addUser(new User(2,"username-2"));
        userService.addUser(new User(3,"username-3"));
        userService.addUser(new User(4,"username-4"));
    }
    
    public static void main(String[] args){
        loadSampleUsers();
    }

}
