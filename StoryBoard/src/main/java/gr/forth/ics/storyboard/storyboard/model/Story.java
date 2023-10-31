package gr.forth.ics.storyboard.storyboard.model;

import gr.forth.ics.storyboard.storyboard.resources.Resources;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Data @NoArgsConstructor @AllArgsConstructor @Entity
@Table(name=Resources.STORY_TABLE)
public class Story implements Serializable{
    @Id
    @Column(name=Resources.STORY_ID_COLUMN)
    private Integer id;
    
    @Column(name=Resources.STORY_CONTENTS_COLUMN)
    private String contents;
    
    @Column(name=Resources.STORY_POST_DATE_COLUMN)
    private Date postDate;
    
    @Column(name=Resources.STORY_BY_USER_COLUMN)
    private User byUser;
}
