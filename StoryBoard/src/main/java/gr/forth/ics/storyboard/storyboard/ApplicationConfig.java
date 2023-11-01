package gr.forth.ics.isl.storyboard.storyboard;

/** API main configuration class
 * 
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
//        resources.add(org.glassfish.jersey.jackson.JacksonFeature.class);
        resources.add(gr.forth.ics.storyboard.storyboard.request.StoryRequest.class);
        return resources;
    }
    
    @Override
    public Set<Object> getSingletons() {
        return Collections.emptySet();
    }
    
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.wadl.disableWadl", true);
        return properties;
    }    
}