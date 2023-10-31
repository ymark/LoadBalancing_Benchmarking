package gr.forth.ics.storyboard.storyboard.resources;

import gr.forth.ics.storyboard.storyboard.model.Story;
import gr.forth.ics.storyboard.storyboard.model.User;
import gr.forth.ics.storyboard.storyboard.service.StoryService;
import gr.forth.ics.storyboard.storyboard.service.UserService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
//@AllArgsConstructor
@Log4j2
public class SampleLoader {
    private static final String STORY_DEFAULT_TEXT="This is my super great story, I just posted in my StoryBoard. My ID is ";
    
    private static void loadSampleData(int numberOfUsers, int numberOfStories){
//        Set<User> usersCol=new HashSet<>();
//        Set<Story> storiesCol=new HashSet<>();
//        for(int i=1;i<=numberOfUsers;i++){
//            usersCol.add(new User(i,"username-"+i));
//        }
//        log.info("Created "+usersCol.size()+" users");
//        for(int i=1;i<=numberOfStories;i++){
//            storiesCol.add(new Story(i, STORY_DEFAULT_TEXT+i, Calendar.getInstance().getTime(), 0, usersCol.stream().findAny().get()));
//        }
//        log.info("Created "+storiesCol.size()+" stories");
//        
//        log.info("load users in database");
//        new UserService().addUsers(usersCol);
//        
//        log.info("load stories in database");
//        new StoryService().addStories(storiesCol);
        User user=new User(15,"marketak");
        Story story=new Story(15,"My story",Date.valueOf(LocalDate.now()),3,user);
        new UserService().addUser(user);
        new StoryService().addStory(story);
    }
       
    public static void main(String[] args){
        loadSampleData(10,10);
    }

}
