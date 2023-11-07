package gr.forth.ics.storyboard.storyboard.model;

import gr.forth.ics.storyboard.storyboard.resources.Resources;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import java.util.UUID;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Data @NoArgsConstructor @AllArgsConstructor @Entity
@Table(name=Resources.STORY_TABLE)
public class Story implements Serializable{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=Resources.STORY_ID_COLUMN) @JsonProperty("id") 
    private Integer id;
    
    @Column(name=Resources.STORY_TITLE_COLUMN) @JsonProperty("title") 
    private String title;
    
    @Column(name=Resources.STORY_CONTENTS_COLUMN) @JsonProperty("contents") 
    private String contents;
    
    @Column(name=Resources.STORY_POST_DATE_COLUMN) @JsonProperty("post_date") 
    private Date postDate;
    
    @Column(name=Resources.STORY_NUMBER_OF_LIKES_COLUMN) @JsonProperty("number_of_likes") 
    private Integer numberOfLikes;
    
    @Transient @JsonProperty("served_by") 
    private UUID serverUuid;
    
    public Story(String title, String contents, Date postDate, int numOfLikes){
        this.title=title;
        this.contents=contents;
        this.postDate=postDate;
        this.numberOfLikes=numOfLikes;
    }
}
