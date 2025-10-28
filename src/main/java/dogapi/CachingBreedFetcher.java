package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private final BreedFetcher fetcher;
    private int callsMade = 0;
    private Map<String, List<String>> cachedSubBreeds = new HashMap<>();

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;

    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if  (cachedSubBreeds.containsKey(breed)) {
            return cachedSubBreeds.get(breed);
        }

        callsMade++;

        try {
            List<String> subBreeds = fetcher.getSubBreeds(breed);
            cachedSubBreeds.put(breed, subBreeds);
            return subBreeds;
        } catch (Exception e) {
            // Do NOT cache failed results
            throw new BreedNotFoundException(breed);
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}