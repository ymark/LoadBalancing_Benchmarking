package gr.forth.ics.storyboard.storyboardclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class StoryBoardClient {
    private static final String SERVICE_URL = "http://localhost:8080/StoryBoard/resources/stories/";
    private static final String SERVICE_STORY_URL = SERVICE_URL+"single/";
    private static final String SERVICE_VOTE_URL = SERVICE_URL+"vote/";
    private static final String SERVICE_POST_URL = SERVICE_URL+"new/";
    private static List<Triple<Integer,String,Long>> resultsList=new ArrayList<>();
    private static int NUMBER_OF_THREADS;
    
    private static void visitStoryBoard(int storyId) {
        try{
            
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(SERVICE_STORY_URL+"/?id="+storyId);
            long time=System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            resultsList.add(Triple.of(
                    response.getStatusLine().getStatusCode(), 
                    responseBody,
                    (System.currentTimeMillis()-time)));
            response.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private static void voteForStory(int storyId) {
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(SERVICE_VOTE_URL+"/?id="+storyId);
            long time=System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            resultsList.add(Triple.of(
                    response.getStatusLine().getStatusCode(), 
                    responseBody,
                    (System.currentTimeMillis()-time)));
            response.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private static void postNewStory(String storyTitle, String storyContents) {
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(SERVICE_POST_URL+"/?title="+storyTitle+"&story_content="+storyContents);
            long time=System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            resultsList.add(Triple.of(
                    response.getStatusLine().getStatusCode(), 
                    responseBody,
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

    private static void voteForStoryMultiThread(int numOfThreads){
        List<Thread> threads=new ArrayList<>();
        for(int i=0;i<numOfThreads;i++){
            Thread thread=new Thread(){
                @Override
                public void run(){
                    voteForStory(randomNumber(1, 20000));
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

    private static void postNewStoryMultiThread(int numOfThreads){
        List<Thread> threads=new ArrayList<>();
        for(int i=0;i<numOfThreads;i++){
            Thread thread=new Thread(){
                @Override
                public void run(){
                    postNewStory(generateSentence(3, 6, 4, 12).replaceAll(" ", "%20"),generateSentence(10, 40, 4, 12).replace(" ","%20"));
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
        for(Triple<Integer,String,Long> triple : resultsList){
            System.out.println(triple);
        }
    }
    
    private static void printResultsAggregated(){
        System.out.println("Number of Threads: "+NUMBER_OF_THREADS);
        for(Integer respCode : resultsList.stream().map(Triple::getLeft).collect(Collectors.toSet())){
            long minThroughput=resultsList.stream().filter(triple -> triple.getLeft().longValue()==respCode.longValue()).map(Triple::getRight).mapToLong(Long::longValue).min().orElse(-1);
            long maxThroughput=resultsList.stream().filter(triple -> triple.getLeft().longValue()==respCode.longValue()).map(Triple::getRight).mapToLong(Long::longValue).max().orElse(-1);
            double avgThroughput=resultsList.stream().filter(triple -> triple.getLeft().longValue()==respCode.longValue()).map(Triple::getRight).mapToLong(Long::longValue).average().orElse(-1);
            System.out.println("RESPONSE_CODE: "+respCode+"\tAvg: "+avgThroughput+"\tMin: "+minThroughput+"\tMax: "+maxThroughput);
        }
        System.out.println("---------------------------");
        Map<String,Integer> serverUtilization=new HashMap<>();
        for(Triple<Integer,String,Long> triple : resultsList){
            if(triple.getLeft().intValue()==200){
                JSONObject resultJson = new JSONObject(triple.getMiddle());
                String serverUuid=resultJson.get("served_by").toString();
                if(serverUuid!=null){
                    if(serverUtilization.containsKey(serverUuid)){
                        serverUtilization.put(serverUuid, serverUtilization.get(serverUuid).intValue()+1);
                    }else{
                        serverUtilization.put(serverUuid, 1);
                    }
                }else{
                    System.out.println("Cannot identify server UUID from JSON response "+triple.getMiddle());
                }
            }
        }
        System.out.println("SERVER Utilization ");
        System.out.println("Server UUID\tNum of Responses");
        serverUtilization.forEach((serverUuid,servedNum) -> System.out.println(serverUuid+"\t"+servedNum));
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
    
    private static String generateSentence(int minWords, int maxWords, int minWordsLength, int maxWordLength){
        StringBuilder sentenceBuilder=new StringBuilder();
        for(int i=0;i<randomNumber(minWords, maxWords);i++){
            sentenceBuilder.append(RandomStringUtils.randomAlphabetic(minWordsLength,maxWordLength)).append(" ");
        }
        sentenceBuilder.setCharAt(0, Character.toUpperCase(sentenceBuilder.charAt(0)));
        return sentenceBuilder.toString().trim();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        NUMBER_OF_THREADS=10;
//        visitStoryBoardMultiThread(NUMBER_OF_THREADS);
//        voteForStoryMultiThread(NUMBER_OF_THREADS);
        postNewStoryMultiThread(NUMBER_OF_THREADS);
        printResultsDetailed();
        printResultsAggregated();
//        warmUp();
    }
}
