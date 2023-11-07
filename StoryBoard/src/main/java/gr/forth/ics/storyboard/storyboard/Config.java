package gr.forth.ics.storyboard.storyboard;

import gr.forth.ics.storyboard.storyboard.resources.Resources;
import java.util.UUID;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@WebListener @Log4j2
public class Config implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Assigning UUID for current server intance");
        Resources.SERVER_INSTANCE_UUID=UUID.randomUUID();
        log.info("Server Instance UUID: "+Resources.SERVER_INSTANCE_UUID.toString());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //
    }

}
