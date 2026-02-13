package hr.kmilos21.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "links")
public class Link extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_link", nullable = false, length = 2048)
    private String fullLink;

    @Column(name = "short_link", nullable = false, length = 50)
    private String shortLink;

    public Link() {}

    public Link(String fullLink, String shortLink){
        this.fullLink = fullLink;
        this.shortLink = shortLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullLink() {
        return fullLink;
    }

    public void setFullLink(String fullLink) {
        this.fullLink = fullLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }
}
