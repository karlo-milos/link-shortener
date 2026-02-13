package hr.kmilos21.service;

import hr.kmilos21.entity.Link;
import hr.kmilos21.entity.LinkMessage;
import hr.kmilos21.repository.LinkRepository;
import hr.kmilos21.util.LinkShorteningSystem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Transactional
public class LinkService {

    @ConfigProperty(name = "app.redirect.base-url")
    String baseUrl;

    @Inject
    LinkShorteningSystem shorteningSystem;

    @Inject
    LinkRepository linkRepository;

    public Link getRedirectionLink(String shortLink){
        return linkRepository.findByShortLink(shortLink);
    }

    public LinkMessage getShortLink(String url){
        if (url == null || url.isEmpty())
            return createLinkMessage("URL not found", false);

        Link shortLink = linkRepository.findByFullLink(url);
        if (shortLink != null){
            return createLinkMessage(shortLink.getShortLink());
        }

        String generatedLink = generateShortLink(url);

        if (generatedLink == null){
            return createLinkMessage("Short link generation failed", false);
        }

        Boolean saved = persistLink(url, generatedLink);

        if (saved)
            return createLinkMessage(generatedLink);
        else
            return null;
    }

    private String generateShortLink(String url) {
        String shortLink = shorteningSystem.createShortLink(url);
        int attempts = 0;

        while (linkRepository.findByShortLink(shortLink) != null && attempts < 10) {
            Link existing = linkRepository.findByShortLink(shortLink);
            if (existing.getFullLink().equals(url)) {
                return shortLink;
            }
            shortLink = shorteningSystem.createShortLink(url + System.nanoTime());
            attempts++;
        }
        return shortLink;
    }

    private Boolean persistLink(String url, String shortLink) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        Link noviLink = new Link(url, shortLink);
        linkRepository.persist(noviLink);
        return true;
    }

    private LinkMessage createLinkMessage(String link) {
        String newURL = baseUrl + "/" + link;

        return  new LinkMessage.LinkMessageBuilder()
                .message(newURL)
                .success(true)
                .build();
    }

    private LinkMessage createLinkMessage(String msg, Boolean success) {
        return  new LinkMessage.LinkMessageBuilder()
                .message(msg)
                .success(success)
                .build();
    }
}
