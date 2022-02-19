import java.util.Iterator;
import java.util.Random;

// templete from class
class IntGen implements Iterator<Integer> {
    private Random rand = new Random();

    public boolean hasNext() {
        return true;
    }
    public Integer next() {
        return rand.nextInt(10000);
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
    public static void main(String[] args) {
        Iterator<Integer> gen = new IntGen();
        Iterator<Integer> f1 = new FilterOdd(gen);
        Iterator<Integer> f2 = new FilterDiv7(f1);

        while (f2.hasNext()) {
            System.out.println(f2.next());
        }

    }
}