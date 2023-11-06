package gr.forth.ics.storyboard.storyboard.resources;

import gr.forth.ics.storyboard.storyboard.model.Story;
import org.apache.commons.lang3.RandomStringUtils;
import gr.forth.ics.storyboard.storyboard.service.StoryService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
//@AllArgsConstructor
@Log4j2
public class SampleLoader {
    private static void loadSampleData(int numberOfStories){
        Set<Story> storiesCol=new HashSet<>();
        for(int i=1;i<=numberOfStories;i++){
            storiesCol.add(new Story(generateSentence(3, 6, 4, 12),
                                     generateSentence(10, 40, 4, 12),
                                     Date.valueOf(LocalDate.now().minusDays((long)randomNumber(1, 365))),
                                     randomNumber(1, 500)));
        }
        new StoryService().addStories(storiesCol);
    }
    
    private static String generateSentence(int minWords, int maxWords, int minWordsLength, int maxWordLength){
        StringBuilder sentenceBuilder=new StringBuilder();
        for(int i=0;i<randomNumber(minWords, maxWords);i++){
            sentenceBuilder.append(RandomStringUtils.randomAlphabetic(minWordsLength,maxWordLength)).append(" ");
        }
        sentenceBuilder.setCharAt(0, Character.toUpperCase(sentenceBuilder.charAt(0)));
        return sentenceBuilder.toString().trim();
    }
    
    private static int randomNumber(int min, int max){
        return min+(int)(Math.random()*(max-min+1));
    }
       
    public static void main(String[] args){
        loadSampleData(15000);
    }
}
