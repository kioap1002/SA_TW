package Game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomNum {

	public String ansNum() {
        Set<Integer> generatedNumbers = new HashSet<>();
        Random random = new Random();
        while (generatedNumbers.size() < 4) {
            int num = random.nextInt((9 - 1) + 1) + 1;
            generatedNumbers.add(num);
        }
        return convertSetToString(generatedNumbers);
    }
	
    public static String convertSetToString(Set<Integer> numberSet) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer number : numberSet) {
            stringBuilder.append(number);
        }
        return stringBuilder.toString();
    }
}
