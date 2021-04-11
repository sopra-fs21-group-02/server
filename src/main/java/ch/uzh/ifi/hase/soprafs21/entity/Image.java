package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;

@Entity
@Table(name = "IMAGES", schema="soprafs21")
public class Image {

    @Id
    @GeneratedValue
    Long id;

    @Lob
    byte[] content;

    String name;
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}