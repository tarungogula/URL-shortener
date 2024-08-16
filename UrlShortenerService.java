import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;
    
    @Autowired
    private Cache cache;
    
    private static final String BASE_URL = "http://short.url/";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();
    private static final AtomicLong sequencer = new AtomicLong();

    public String shortenUrl(String longUrl) {
        String shortUrl = cache.get(longUrl);
        if (shortUrl != null) {
            return BASE_URL + shortUrl;
        }

        long id = sequencer.incrementAndGet();
        String encoded = encode(id);
        Url url = new Url(encoded, longUrl);
        urlRepository.save(url);
        cache.put(longUrl, encoded);
        return BASE_URL + encoded;
    }

    public String getOriginalUrl(String shortUrl) {
        Url url = cache.get(shortUrl);
        if (url != null) {
            return url.getLongUrl();
        }
        url = urlRepository.findByShortUrl(shortUrl);
        cache.put(shortUrl, url);
        return url.getLongUrl();
    }

    private String encode(long id) {
        StringBuilder encoded = new StringBuilder();
        while (id > 0) {
            encoded.append(ALPHABET.charAt((int) (id % BASE)));
            id /= BASE;
        }
        return encoded.reverse().toString();
    }
}
