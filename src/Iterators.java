import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// templete from class
class ReadWordsFromFile implements Iterator<String> {
    private Random rand = new Random();
    private List<String> wordsList;
    private Integer index = 0;

    public ReadWordsFromFile (String filename) throws IOException {
        wordsList = new LinkedList<>(Arrays.asList(Files.readString(Paths.get(filename)).toLowerCase().split("[^a-z]+")));
    }
    public boolean hasNext() {
        if (index < wordsList.size()) {
            return true;
        }
        return false;
    }
    public String next() {
        if (index < wordsList.size()) {
            String nextWord = wordsList.get(index);
            index += 1;
            return nextWord;
        }
        return null;
    }

    public void remove() {

    }

}

class FilterStopWords implements Iterator<String> {
    private Integer last = 0;
    private Iterator<String> prior;
    private LinkedList<String> stopWords;
    public FilterStopWords(Iterator<String> p) throws IOException {
        stopWords = new LinkedList<>(Arrays.asList(Files.readString(Paths.get("stop_words.txt")).toLowerCase().split(",")));
        prior = p;
    }

    public boolean hasNext() {
        if(prior.hasNext()){
            return true;
        }
        return false;
    }

    public String next() {
        while(prior.hasNext()){
            String word = prior.next();
            if(!stopWords.contains(word) && word.length() >= 2){
                return word;
            }
        }
        return null;
    }

    public void remove() {

    }
}

class GetFreqs implements Iterator<Map<String, Integer>> {
    private Integer last = 0;
    private Iterator<String> prior;
    private Map<String, Integer> freqs = new HashMap<>();

    public GetFreqs(Iterator<String> p)
    {
        prior = p;
    }

    public boolean hasNext() {
        if(prior.hasNext()){
            return true;
        }
        return false;
    }

    public Map<String, Integer> next() {

        Integer wordCount = 0;
        while(prior.hasNext() && wordCount < 5000){
            String word = prior.next();
            freqs.put(word, freqs.get(word)!=null ? freqs.get(word) + 1: 1);
            wordCount ++;
        }

        // ref: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(freqs.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> sorted = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> item : list) {
            sorted.put(item.getKey(), item.getValue());
        }

        return sorted;
    }

    public void remove() {
    }
}

public class Iterators {
    public static void main(String[] args) throws IOException {
        Iterator<String> words = new ReadWordsFromFile(args[0]);
        Iterator<String> filtered_words = new FilterStopWords(words);
        Iterator<Map<String, Integer>> sorted_Freqs = new GetFreqs(filtered_words);
        while(filtered_words.hasNext()){
            System.out.println(sorted_Freqs.next());
        }

    }
}