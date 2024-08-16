import java.util.List;
import java.util.Random;

public class LoadBalancer {

    private final List<String> serverList;
    private final Random random;

    public LoadBalancer(List<String> serverList) {
        this.serverList = serverList;
        this.random = new Random();
    }

    public String getServer() {
        return serverList.get(random.nextInt(serverList.size()));
    }
}
