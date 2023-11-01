package gr.forth.ics.storyboard.storyboard.request;

import gr.forth.ics.storyboard.storyboard.model.Story;
import gr.forth.ics.storyboard.storyboard.service.StoryService;
import java.util.Collection;
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
        
        Response.Status status=Response.Status.OK;
        Collection<Story> storiesResults=this.storyService.getStoriesWithAtLeastNumberOfLikes(Integer.valueOf(numOfLikes));

        log.debug("Return values in JSON format");
        return Response.status(status)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(storiesResults)
                       .build();
   
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/single/")
    public Response getRequestSingle(@QueryParam("id") String id){
        log.info("Received incoming request for searching sotry with id: "+id);
        
        Response.Status status=Response.Status.OK;
        Story story=this.storyService.getStoryWithId(Integer.valueOf(id));
        if(story==null){
            status=Response.Status.NOT_FOUND;
        }
        log.debug("Return values in JSON format");
        return Response.status(status)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(story)
                       .build();
   
    }
}
