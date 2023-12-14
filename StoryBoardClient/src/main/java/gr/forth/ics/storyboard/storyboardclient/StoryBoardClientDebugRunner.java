package gr.forth.ics.storyboard.storyboardclient;

import java.io.IOException;
import org.apache.commons.cli.ParseException;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class StoryBoardClientDebugRunner {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException{
        String[] parameters={"-u", "http://0.0.0.0:8099/", 
                             "-t", "5",
                             "-a", "visit",
                             "-v",
        };
        StoryBoardClient.main(parameters);
    }
}