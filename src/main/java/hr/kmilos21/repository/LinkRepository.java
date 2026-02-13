package hr.kmilos21.repository;

import hr.kmilos21.entity.Link;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LinkRepository implements PanacheRepository<Link> {
    public Link findByFullLink(String url) {
        return find("fullLink", url).firstResult();
    }

    public Link findByShortLink(String link) {
        return find("shortLink", link).firstResult();
    }
}
