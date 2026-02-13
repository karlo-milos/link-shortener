package hr.kmilos21;

import hr.kmilos21.util.LinkShorteningSystem;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class LinkShorteningSystemTests {

    @Inject
    LinkShorteningSystem shorteningSystem;

    String url1 = "https://www.google.com";
    String url2 = "https://bing.com";

    @Test
    void testCreateShortLink() {
        String shortLink = shorteningSystem.createShortLink(url1);

        Assertions.assertNotNull(shortLink);
        Assertions.assertFalse(shortLink.isEmpty());
    }

    @Test
    void testIdenticalHashing() {
        String hash1 = shorteningSystem.createShortLink(url1);
        String hash2 = shorteningSystem.createShortLink(url1);

        Assertions.assertEquals(hash1, hash2);
    }

    @Test
    void testDifferentUrlsProduceDifferentHashes() {
        String hash1 = shorteningSystem.createShortLink(url1);
        String hash2 = shorteningSystem.createShortLink(url2);

        Assertions.assertNotEquals(hash1, hash2);
    }

    @Test
    void testEmptyUrl() {
        String hash1 = shorteningSystem.createShortLink("");

        Assertions.assertNull(hash1);
    }

    @Test
    void testNullUrl() {
        String hash1 = shorteningSystem.createShortLink(null);

        Assertions.assertNull(hash1);
    }
}
