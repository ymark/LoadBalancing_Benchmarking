package gr.forth.ics.storyboard.storyboardclient;

import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class StoryBoardClient {
    private static final String SERVICE_URL = "http://localhost:8080/StoryBoard/resources/stories/single/";
    
    private static void visitStoryBoard(int storyId) throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(SERVICE_URL+"/?id="+storyId);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println("HTTP Status Code: " + response.getStatusLine().getStatusCode());
        System.out.println("Response Body: " + responseBody);
        response.close();
    }

    public static void main(String[] args) throws IOException {
        visitStoryBoard(567);
    }
}
