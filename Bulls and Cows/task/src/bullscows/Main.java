package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        Scanner scanner = new Scanner(System.in);
        String secretCodeLengthStr = scanner.nextLine();
        int secretCodeLength = 0;
        try {
            secretCodeLength = Integer.parseInt(secretCodeLengthStr);
            if(secretCodeLength <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + secretCodeLengthStr + "\" isn't a valid number.");
            System.exit(0);
        }

        if (secretCodeLength > 36) {
            System.out.println("Error: maximum length of secret code is 36 (0-9, a-z).");
            System.exit(0);
        }

        System.out.println("Input the number of possible symbols in the code:");
        int numberOfSymbols = scanner.nextInt();

        if(secretCodeLength > numberOfSymbols) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", secretCodeLength, numberOfSymbols);
            System.exit(0);
        }

        if (numberOfSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }

        String secretCode = getSecretCode(secretCodeLength, numberOfSymbols);
        secretCode = setDigitsUnique(secretCode);
        System.out.println("secretCode :" + secretCode);
        System.out.println("Okay, let's start a game!");

        char[] secretCodeCharArr = (secretCode).toCharArray();

        guessTheCode(secretCodeCharArr);
    }

    public static void guessTheCode(char[] secretCodeCharArr) {
        boolean isDone = false;
        int counter = 1;

        while(!isDone) {
            int cows = 0;
            int bulls = 0;
            System.out.println("Turn " + counter + ":");
            Scanner scanner = new Scanner(System.in);
            char[] guess = scanner.nextLine().toCharArray();

            for (int i = 0; i < secretCodeCharArr.length; i++) {
                for (int j = 0; j < guess.length; j++) {
                    if(secretCodeCharArr[i] == guess[j]) {
                        if(i == j) {
                            bulls++;
                        } else {
                            cows++;
                        }
                        guess[j] = 'N';
                    }
                }
            }
            printGrade(cows, bulls);
            if(bulls == secretCodeCharArr.length) {
                System.out.println("Congratulations! You guessed the secret code.");
                isDone = true;
            }
            counter++;
        }
    }

    public static String getSecretCode(int codeLength, int possibleSymbols) {
        int leftLimit = 48; // numeral '0'
        int lastDigit = 57; // number '9'
        int firstSymbol = 97; // letter 'a'
        possibleSymbols -= 10;
        int rightLimit = possibleSymbols > 0 ? firstSymbol + possibleSymbols - 1 : lastDigit;

        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= lastDigit || i >= 97))
                .limit(codeLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        printSecretCodePrepared(codeLength, firstSymbol, rightLimit);
        return generatedString;
    }

    public static String setDigitsUnique(String secretCode) {
        char[] codeSplit = secretCode.toCharArray();
        for (int i = 0; i < codeSplit.length; i++) {
            if(Character.isDigit(codeSplit[i]) && (secretCode.indexOf(codeSplit[i]) != secretCode.lastIndexOf(codeSplit[i]))) {
                codeSplit[i] = generateUniqueChar(codeSplit[i], codeSplit);
            }
        }
        return String.valueOf(codeSplit);
    }

    public static void printGrade(int cows, int bulls) {
        if(cows > 0 && bulls > 0) {
            System.out.println("Grade: " + getCattle(bulls, "bull") + " and " + getCattle(cows, "cow"));
        } else if(cows > 0) {
            System.out.println("Grade: " + getCattle(cows, "cow"));
        } else if(bulls > 0) {
            System.out.println("Grade: " + getCattle(bulls, "bull"));
        } else {
            System.out.println("Grade: None");
        }
    }

    private static String getCattle(int cattleCount, String cattleName) {
        if(cattleCount > 1) {
            return cattleCount + " " + cattleName;
        } else {
            return cattleCount + " " + cattleName + "s";
        }
    }

    private static void printSecretCodePrepared(int codeLength, int startSymbol, int endSymbol) {
        StringBuilder sb = new StringBuilder("The secret is prepared: ");
        StringBuilder asterisks = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            asterisks.append("*");
        }
        sb.append(asterisks).append(" (0-9");
        if(startSymbol < endSymbol) {
            sb.append(", ").appendCodePoint(startSymbol).append("-").appendCodePoint(endSymbol);
        }
        sb.append(")");
        System.out.println(sb);
    }

    private static Character generateUniqueChar(char c, char[] secretCode) {
        int firstSymbol = 97;
        int lastSymbol = 122;
        char result;
        do {
            result = (char) (Math.random() * (lastSymbol - firstSymbol) + firstSymbol);
        } while(result != c && contains(result, secretCode));
        return result;
    }

    private static boolean contains(char c, char[] arr) {
        boolean result = false;
        for (char current : arr) {
            if(current == c) {
                result = true;
            }
        }
        return result;
    }
}
