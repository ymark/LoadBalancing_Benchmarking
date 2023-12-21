package gr.forth.ics.storyboard.storyboard.request;

import gr.forth.ics.storyboard.storyboard.model.Story;
import gr.forth.ics.storyboard.storyboard.resources.Resources;
import gr.forth.ics.storyboard.storyboard.service.StoryService;
import java.util.Collection;
import java.sql.Date;
import java.time.LocalDate;
//import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
/**
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Path("stories")
@Log4j2
public class StoryRequest {
    private StoryService storyService;
    
    public StoryRequest(){
        this.storyService=new StoryService();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/many/")
    public Response getRequestMany(@QueryParam("num_of_likes") String numOfLikes){
        log.info("Received incoming request for searching stories with number of likes: "+numOfLikes);
        
        long timeStart=System.currentTimeMillis();
        Response.Status status=Response.Status.OK;
        Collection<Story> storiesResults=this.storyService.getStoriesWithAtLeastNumberOfLikes(Integer.valueOf(numOfLikes));
//        storiesResults.forEach(aStory -> {
//            aStory.setServerUuid(Resources.SERVER_INSTANCE_UUID);
//            aStory.setThroughputNet((System.currentTimeMillis()-timeStart));
//        });
        
        Story retStory=new Story();
        retStory.setId(storiesResults.size());
        retStory.setNumberOfLikes(Integer.valueOf(numOfLikes));
        retStory.setTitle("Collection of "+storiesResults.size()+" Stories");
        retStory.setContents(storiesResults.toString());
        retStory.setServerUuid(Resources.SERVER_INSTANCE_UUID);
        retStory.setThroughputNet((System.currentTimeMillis()-timeStart));

        log.debug("Return values in JSON format");
        return Response.status(status)
                       .type(MediaType.APPLICATION_JSON)
//                       .entity(storiesResults)
                       .entity(retStory)
                       .build();
   
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/single/")
    public Response getRequestSingle(@QueryParam("id") String id){
        log.info("Received incoming request for searching story with id: "+id);
        long timeStart=System.currentTimeMillis();
        
        Response.Status status=Response.Status.OK;
        Story story=this.storyService.getStoryWithId(Integer.valueOf(id));
        if(story==null){
            status=Response.Status.NOT_FOUND;
        }else{
            story.setServerUuid(Resources.SERVER_INSTANCE_UUID);
            story.setThroughputNet((System.currentTimeMillis()-timeStart));
        }
        log.debug("Return values in JSON format");
        return Response.status(status)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(story)
                       .build();
   
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/new/")
    public Response getRequestAdd(@QueryParam("title") String title,
                                  @QueryParam("story_content") String storyContents){
        log.info("Received incoming request for searching adding a new story with title: "+title+" and contents: "+storyContents);
        long timeStart=System.currentTimeMillis();
        
        Response.Status status=Response.Status.OK;
        Story newStory=new Story(title, storyContents, Date.valueOf(LocalDate.now()), 0);
        this.storyService.addStory(newStory);
        newStory.setServerUuid(Resources.SERVER_INSTANCE_UUID);
        newStory.setThroughputNet((System.currentTimeMillis()-timeStart));
        return Response.status(status)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(newStory)
                       .build();
   
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/vote/")
    public Response getRequestVote(@QueryParam("id") String id){
        log.info("Received incoming request for votinh a story with id: "+id);
        long timeStart=System.currentTimeMillis();
        Response.Status status=Response.Status.OK;
        this.storyService.voteForStoryWithId(Integer.valueOf(id));

        return Response.status(status)
                       .type(MediaType.APPLICATION_JSON)
                       .entity("{\"id\":"+id+", "
                              +"\"served_by\": \""+Resources.SERVER_INSTANCE_UUID.toString()+"\", "
                              +"\"througput_net\": "+(System.currentTimeMillis()-timeStart)
                            +"}")
                       .build();
   
    }
}
