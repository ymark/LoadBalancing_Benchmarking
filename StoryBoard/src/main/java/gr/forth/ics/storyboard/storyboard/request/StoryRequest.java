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
    @Path("/")
    public Response getRequest(@QueryParam("num_of_likes") String numOfLikes){
        log.info("Received incoming request for searching stories with number of likes: "+numOfLikes);
        
        Response.Status status=Response.Status.OK;
        Collection<Story> storiesResults=this.storyService.getStoriesWithAtLeastNumberOfLikes(Integer.valueOf(numOfLikes));

        log.debug("Return values in JSON format");
        return Response.status(status)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(storiesResults)
                       .build();
   
    }
}
