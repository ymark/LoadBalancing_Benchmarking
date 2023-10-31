package gr.forth.ics.storyboard.storyboard.model;

import gr.forth.ics.storyboard.storyboard.resources.Resources;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Data @NoArgsConstructor @AllArgsConstructor @Entity
@Table(name=Resources.USER_TABLE)
public class User implements Serializable{
    @Id
    @Column(name=Resources.USER_ID_COLUMN)
    private Integer id;
    
    @Column(name=Resources.USER_USERNAME_COLUMN)
    private String username;
}
