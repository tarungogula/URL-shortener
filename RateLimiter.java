import java.util.concurrent.TimeUnit;
import com.google.common.util.concurrent.RateLimiter;

public class RateLimiter {

    private final RateLimiter rateLimiter;

    public RateLimiter(double permitsPerSecond) {
        this.rateLimiter = RateLimiter.create(permitsPerSecond);
    }

    public void acquire() {
        rateLimiter.acquire();
    }
}
