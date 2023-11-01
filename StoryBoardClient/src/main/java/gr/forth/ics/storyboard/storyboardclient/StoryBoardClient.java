package gr.forth.ics.storyboard.storyboardclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    
    private static void visitStoryBoard(int storyId) {
        try{
            long time=System.currentTimeMillis();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(SERVICE_URL+"/?id="+storyId);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("HTTP Status Code: " + response.getStatusLine().getStatusCode());
            System.out.println("Response Body: " + responseBody);
            System.out.println("TIME: "+(System.currentTimeMillis()-time));
            response.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private static void visitStoryBoardMultiThread(int numOfThreads){
        List<Thread> threads=new ArrayList<>();
        for(int i=0;i<numOfThreads;i++){
            Thread thread=new Thread(){
                @Override
                public void run(){
                    visitStoryBoard(randomNumber(1, 20000));
                }
            };
            threads.add(thread);
        }
        threads.forEach(thr -> thr.start());
    }
    
    private static int randomNumber(int min, int max){
        return min+(int)(Math.random()*(max-min+1));
    }
    
    public static void main(String[] args) throws IOException {
//        visitStoryBoard(567);
        visitStoryBoardMultiThread(5);
    }
}
