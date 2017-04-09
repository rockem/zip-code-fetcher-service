package e2e.org.rockem.zip.fetcher;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MapUtil {

    public static <K, V> Map<K, V> map(Map.Entry<K, V>... entries) {
        return Collections.unmodifiableMap(Arrays.stream(entries).collect(entriesToMap()));
    }

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private static <K, U> Collector<Map.Entry<K, U>, ?, Map<K, U>> entriesToMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }
}
