package crypto.keysearching.binary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class BinaryKeyFinder {

    public static Map<Integer, Set<Integer>> formKeyMap(int n, int nStart) {
        Map<Integer, Set<Integer>> keyMap = new TreeMap<>();
        Set<Integer> keySet;
        for (int ni = nStart; ni <= n; ni++) {

   //         System.out.println("current n = " + ni);
            keySet = new TreeSet<>();
            int keyStart = (int) Math.pow(2, ni) / 2 + 1;
            int keyEnd = (int) Math.pow(2, ni) - 1;
            for (int key = keyStart; key <= keyEnd; key++) {
                if (Integer.bitCount(key) % 2 == 0 && (keySet.contains(reverse(key, ni)) || isKeyValid(key, ni))) {
                    keySet.add(key);
                }
            }
            keyMap.put(ni, keySet);
        }
        printMap(keyMap);
        return keyMap;
    }

    private static void printMapToFile(Map<Integer, Set<Integer>> keyMap) {
        try {
            FileOutputStream fos = new FileOutputStream("src/crypto/KeyMap.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(keyMap);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printMap(Map<Integer, Set<Integer>> keyMap) {
        for (Map.Entry<Integer, Set<Integer>> entry : keyMap.entrySet()) {
            System.out.printf("n = %s\n keys (%s):[ ", entry.getKey(), entry.getValue().size());
            for (Integer value : entry.getValue()) {
                System.out.printf("%s ", Integer.toBinaryString(value));
            }
            System.out.println("]");
        }
    }

    public static int reverse(int newKey, int ni) {
        newKey = (newKey << 1) + 1;
        int rev = 0;
        for (int i = 0; i < ni; ++i) {
            rev <<= 1;
            rev |= (newKey & 1);
            newKey >>= 1;
        }
        return rev;
    }
    private static boolean isKeyValid(int key, int n) {
        int phase = (int) Math.pow(2, n) - 1;
        int length = phase;
        int code = phase;
        int temp = 0;
        for (int i = 1; i <= length; i++) {
            temp = code & key;
            code *= 2;
            if (Integer.bitCount(temp) % 2 == 1) {
                code++;
            }
            // System.out.println(Long.toBinaryString(code) + " | i=" + i);
            if (i < length && length % i == 0) {
                if ((code & phase) == phase) {
                    return false;
                }
            }
        }
        return (code & phase) == phase;
    }
}