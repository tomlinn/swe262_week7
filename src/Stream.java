import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;

public class Stream {
    public static void main(String[] args) throws IOException {
            List<String> stopWords = new LinkedList<>(Arrays.asList(Files.readString(Paths.get("stop_words.txt")).toLowerCase().split(",")));

            Map<String , Long> freqs =
                    Arrays.asList(Files.readString(Paths.get(args[0])).toLowerCase().split("[^a-z]+")).stream()
                    .filter(word -> word.length() >= 2 && !stopWords.contains(word))
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

            freqs.entrySet().stream()
                    .sorted(comparingByValue(reverseOrder()))
                    .limit(25)
                    .forEach(word -> System.out.println(word.getKey() + " - " + word.getValue()));

    }
}
