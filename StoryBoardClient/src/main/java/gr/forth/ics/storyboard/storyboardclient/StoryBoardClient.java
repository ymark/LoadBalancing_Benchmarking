package gr.forth.ics.storyboard.storyboardclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class StoryBoardClient {
    private static final String SERVICE_URL = "http://localhost:8080/StoryBoard/resources/stories/";
    private static final String SERVICE_STORY_URL = SERVICE_URL+"single/";
    private static List<Pair<Integer,Long>> resultsList=new ArrayList<>();
    private static int NUMBER_OF_THREADS;
    
    private static void visitStoryBoard(int storyId) {
        try{
            
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(SERVICE_STORY_URL+"/?id="+storyId);
            long time=System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            resultsList.add(Pair.of(
                    response.getStatusLine().getStatusCode(), 
                    (System.currentTimeMillis()-time)));
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
        threads.forEach(thr-> {
            try{
                thr.join();
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
                
        });
    }
    
    private static int randomNumber(int min, int max){
        return min+(int)(Math.random()*(max-min+1));
    }
    
    private static void printResultsDetailed(){
        System.out.println("Response Code\tThroughput time (ms)");
        for(Pair<Integer,Long> triple : resultsList){
            System.out.println(triple);
        }
    }
    
    private static void printResultsAggregated(){
        System.out.println("Number of Threads: "+NUMBER_OF_THREADS);
        for(Integer respCode : resultsList.stream().map(Pair::getKey).collect(Collectors.toSet())){
            long minThroughput=resultsList.stream().filter(pair -> pair.getKey().longValue()==respCode.longValue()).map(Pair::getValue).mapToLong(Long::longValue).min().orElse(-1);
            long maxThroughput=resultsList.stream().filter(pair -> pair.getKey().longValue()==respCode.longValue()).map(Pair::getValue).mapToLong(Long::longValue).max().orElse(-1);
            double avgThroughput=resultsList.stream().filter(pair -> pair.getKey().longValue()==respCode.longValue()).map(Pair::getValue).mapToLong(Long::longValue).average().orElse(-1);
            System.out.println("RESPONSE_CODE: "+respCode+"\tAvg: "+avgThroughput+"\tMin: "+minThroughput+"\tMax: "+maxThroughput);
        }
    }
    
    private static void visitStoryBoardNoBenchmark(String method, String parameterName, String parameterValue) {
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(SERVICE_URL+method+"/?"+parameterName+"="+parameterValue);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            response.getEntity();
            String responseBody = EntityUtils.toString(response.getEntity());
            response.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private static void warmUp(){
        int warmUpRounds=20;
        for(int i=1;i<=warmUpRounds;i++){
            int numOfLikes=randomNumber(1, 500);
            int randomStoryId=randomNumber(1, 20000);
            long roundStartTimeMs=System.currentTimeMillis();
            System.out.println("Warm-up queries (round "+i+"/"+warmUpRounds+") - Fetch story with random ID ("+randomStoryId+"), and collection of stories with random votes (>= "+numOfLikes+")");
            visitStoryBoardNoBenchmark("single", "id", String.valueOf(randomStoryId));
            visitStoryBoardNoBenchmark("many", "num_of_likes", String.valueOf(numOfLikes));
            System.out.println("Warm-up round "+i+" finished in "+(System.currentTimeMillis()-roundStartTimeMs)+" ms.");
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        NUMBER_OF_THREADS=10;
        visitStoryBoardMultiThread(NUMBER_OF_THREADS);
//        printResultsDetailed();
        printResultsAggregated();
//        warmUp();
    }
}
