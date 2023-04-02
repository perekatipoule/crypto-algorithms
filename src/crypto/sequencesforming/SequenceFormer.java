package crypto.sequencesforming;

import java.util.*;

public class SequenceFormer {

    public static List<Integer[]> formSequenceCodeList(Map<Integer, Map<Integer, Set<List<Integer>>>> keyMap, int base, int power, int codeLength) {
        List<Integer[]> sequenceCodeList = new ArrayList<>();
        int sequenceLength = (int) Math.pow(base, power) - 1;
        Set<List<Integer>> keySet = keyMap.get(base).get(sequenceLength);
        for (List<Integer> key : keySet) {
            Integer[] sequenceCode = formSequenceCode(key, power, base, codeLength);
            sequenceCodeList.add(sequenceCode);
            //          System.out.print(("key ") + key + " - ");
            //          System.out.println("code " + Arrays.toString(sequenceCode));
        }
        return sequenceCodeList;
    }

    public static Integer[] formSequenceCode(List<Integer> key, int power, int base, int codeLength) {
        Integer[] sequenceCode = new Integer[codeLength];
        // Form phase
        int phaseElement = base - 1;
        Integer[] phase = new Integer[power];
        for (int i = 0; i < power; i++) {
            phase[i] = phaseElement;
        }
        System.arraycopy(phase, 0, sequenceCode, 0, power);
        List<Integer> fragment = new ArrayList<>(Arrays.asList(phase));
        int fragmentIndex = power - 1;
        for (int i = power; i < codeLength; i++) {
            fragment = formNextFragment(key, fragment, base, power);
            sequenceCode[i] = fragment.get(fragmentIndex);
        }
        return sequenceCode;
    }

    public static List<Integer> formNextFragment(List<Integer> key, List<Integer> fragment, int base, int keyLength) {
        List<Integer> nextFragment = new ArrayList<>();
        int newValue;
        int counter = 0;
        for (int i = 0; i < keyLength; i++) {
            counter += fragment.get(i) * key.get(i);
        }
        newValue = counter % base;
        for (int i = 0; i < keyLength - 1; i++) {
            nextFragment.add(fragment.get(i + 1));
        }
        nextFragment.add(newValue);
        return nextFragment;
    }
}
