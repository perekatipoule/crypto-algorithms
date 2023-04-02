package crypto.keysearching.multilevel;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class StartPairFinder{
    public static TreeMap<Integer, TreeMap<Integer, TreeSet<Pair>>> makeBasePairMap (int maxBase, int maxDegree) {
        TreeMap<Integer, TreeMap<Integer, TreeSet<Pair>>> basePairMap = new TreeMap<>();
        ArrayList<Integer> primeNumbers = findPrimeNumbers(maxBase);
        for (Integer i : primeNumbers) {
            basePairMap.put(i, makeLengthPairMap(i, maxDegree));
        }
        return basePairMap;
    }


    public static TreeMap<Integer, TreeSet<Pair>> makeLengthPairMap(int base, int maxDegree) {
        TreeMap<Integer, TreeSet<Pair>> lengthPairMap = new TreeMap<>();
        Integer length = 0;
        for (int i = 2; i < maxDegree + 1; i++) {
            length = (int) Math.pow(base, i) - 1;
            lengthPairMap.put(length, findPair(base));
        }
        return lengthPairMap;
    }

    public static TreeSet<Pair> findPair(int base) {
        TreeSet<Pair> pair = new TreeSet<>();
        for (int a0 = 1; a0 < base; a0++) {
            for (int a1 = base - 1; a1 >= a0; a1--) {
                if ((a0 * a1) % base == 1 && a0 !=1) {
                    pair.add(new Pair(a0, a1));
                    break;
                }
            }
        }
        return pair;
    }

    public static ArrayList<Integer> findPrimeNumbers(int number) {
        ArrayList<Integer> numbers = new ArrayList<>();
        boolean isPrime = false;
        numbers.add(3);
        for (int i = 3; i < number + 1; i += 2) {
            isPrime = false;
            for (int j : numbers) {
                if (i % j == 0) {
                    break;
                }
                if (j > i / 3) {
                    isPrime = true;
                }
            }
            if (isPrime) {
                numbers.add(i);
            }
        }
        return numbers;
    }
}
