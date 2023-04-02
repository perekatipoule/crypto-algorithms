package crypto.keysearching.multilevel;


import crypto.sequencesforming.SequenceFormer;

import java.util.*;

public class MultilevelKeyFinder {

    public static Map<Integer, Map<Integer, Set<List<Integer>>>> formKeysMap(Map<Integer, TreeMap<Integer, TreeSet<Pair>>> startPairMap) {
        Map<Integer, Map<Integer, Set<List<Integer>>>> keysMap = new TreeMap<>();
        Map<Integer, Set<List<Integer>>> lengthMap;
        for (Integer base : startPairMap.keySet()) {
            lengthMap = new TreeMap<>();
            for (Integer sequanceLength : startPairMap.get(base).keySet()) {
                lengthMap.put(sequanceLength, formKeySet(base, sequanceLength, startPairMap.get(base).get(sequanceLength)));
            }
            keysMap.put(base, lengthMap);
        }
        return keysMap;
    }


    private static Set<List<Integer>> formKeySet(int base, Integer sequenceLength, Set<Pair> startPairs) {
        Set<List<Integer>> keySet = new HashSet<>();
        int power = (int) (Math.log(sequenceLength + 1) / Math.log(base));
        // Form phase
        List<Integer> phase = new ArrayList<>();
        int phaseElement = base - 1;
        for (int i = 0; i < power; i++) {
            phase.add(phaseElement);
        }
        // Form all possible key numbers
        int[] keyElements = new int[base];
        for (int i = 0; i < base; i++) {
            keyElements[i] = i;
        }
        // Form keys by joining start element with all possible key fragments without first element and add it to keySet
        int keyElementsLength = keyElements.length;
        int keyLengthWithoutStartElem = power - 1;
        int possiblePerm = (int) Math.pow(keyElementsLength, keyLengthWithoutStartElem);
        List<Integer> keyFragment;
        List<Integer> key;
        for (int i = 0; i < possiblePerm; i++) {
            keyFragment = formKeyFragment(i, keyElements, keyElementsLength, keyLengthWithoutStartElem);
            for (Pair startPair: startPairs) {
                key = new ArrayList<>();
                key.add(startPair.getX1());
                key.addAll(keyFragment);
                // Check resulted key and put it to keySet
                if (isKeyValid(key, phase, sequenceLength, base, power)) {
                    keySet.add(key);
                    // If first key is valid - form pair key, check it and put to keySet
                    key = formPairKey(key, startPair.getX2(), base);
                    if (isKeyValid(key, phase, sequenceLength, base, power)) {
                        keySet.add(key);
                    }
                }
            }
        }
        return keySet;
    }

    private static List<Integer> formKeyFragment(int n, int[] elements, int elementsLength, int fragmentLength) {
        List<Integer> keyFragment = new ArrayList<>();
        for (int i = 0; i < fragmentLength; i++) {
            keyFragment.add(elements[n % elementsLength]);
            n /= elementsLength;
        }
        return keyFragment;
    }


    private static boolean isKeyValid(List<Integer> key, List<Integer> phase, int sequenceLength, int base, int power) {
        // Initial key testing
        if (key.size() != power) {
            return false;
        }
        for (int keyNumber : key) {
            if (keyNumber >= base) {
                return false;
            }
        }
        // Main key testing
        int count = 0;
        List<Integer> fragment = new ArrayList<>(phase);
        int keyLength = key.size();
        while (count != sequenceLength - 1) {
            fragment = SequenceFormer.formNextFragment(key, fragment, base, keyLength);
            count++;
            if (phase.equals(fragment)) {
                return false;
            }
        }
        return phase.equals(SequenceFormer.formNextFragment(key, fragment, base, keyLength));
    }

    private static List<Integer> formPairKey(List<Integer> key, int secondStartElem, int base) {
        List<Integer> pairKey = new ArrayList<>();
        int keyLength = key.size();
        int k = base - secondStartElem;
        pairKey.add(secondStartElem);
        for (int i = 1; i < keyLength; i++) {
            pairKey.add((key.get(keyLength - i) * k) % base);
        }
        return pairKey;
    }
}
