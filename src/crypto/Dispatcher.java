package crypto;

import crypto.keysearching.multilevel.MultilevelKeyFinder;
import crypto.keysearching.multilevel.Pair;
import crypto.keysearching.multilevel.StartPairFinder;
import crypto.sequencesforming.SequenceFormer;

import java.util.*;

public class Dispatcher {
    public static void main(String[] args) {

        //Form Multilevel key map
        int maxBase = 3;
        int maxDegree = 3;
        TreeMap<Integer, TreeMap<Integer, TreeSet<Pair>>> startPairMap = StartPairFinder.makeBasePairMap(maxBase, maxDegree);
        Map<Integer, Map<Integer, Set<List<Integer>>>> multilevelKeys = MultilevelKeyFinder.formKeysMap(startPairMap);

        // Form multilevel sequences list
        int multilevelSeqLength = (int)Math.pow(maxBase, maxDegree) - 1;
        List<Integer[]> multilevelSequences = SequenceFormer.formSequenceCodeList(multilevelKeys, maxBase, maxDegree, multilevelSeqLength);
         multilevelSequences.stream().forEach(s -> System.out.println(Arrays.asList(s)));


    }
}
