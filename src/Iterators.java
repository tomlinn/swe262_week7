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
        stopWords = new LinkedList<>(Arrays.asList(Files.readString(Paths.get("stop_words.txt")).toLowerCase()));
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
            if(!stopWords.contains(word)){
                return word;
            }
        }
        return null;
    }

    public void remove() {

    }
}

class FilterDiv7 implements Iterator<Integer> {
    private Integer last = 0;
    private Iterator<Integer> prior;

    public FilterDiv7(Iterator<Integer> p)
    {
        prior = p;
    }

    public boolean hasNext() {
        return true;
    }

    public Integer next() {
        int n = 0;
        do {
            n = prior.next();
        } while (n % 7 != 0 || Math.abs(n-last) < 1000);
        last = n;
        return n;
    }

    public void remove() {
    }
}

public class Iterators {
    public static void main(String[] args) throws IOException {
        Iterator<String> words = new ReadWordsFromFile(args[0]);
        Iterator<String> filtered_words = new FilterStopWords(words);
        while(filtered_words.hasNext()){
            System.out.println(filtered_words.next());
        }

    }
}