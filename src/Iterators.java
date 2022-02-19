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

class FilterOdd implements Iterator<Integer> {
    private Integer last = 0;
    private Iterator<Integer> prior;

    public FilterOdd(Iterator<Integer> p)
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
        } while (n % 2 != 0);
        return n;
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
        Iterator<String> gen = new ReadWordsFromFile(args[0]);

        while(gen.hasNext()){
            System.out.println(gen.next());
        }

    }
}