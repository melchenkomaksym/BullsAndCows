package generator;

import java.util.Random;

/**
 * Generator of a secret code.
 */

public class CodeGenerator {

    /**
     * Transforms secret code with sequence of '*' symbols according to its size.
     *
     * @param codeLength secret code size
     * @param startSymbol code of the starting symbol
     * @param endSymbol code of the ending symbol
     */
    private void printSecretCodePrepared(int codeLength, int startSymbol, int endSymbol) {
        StringBuilder sb = new StringBuilder("The secret is prepared: ");
        sb.append("*".repeat(Math.max(0, codeLength))).append(" (0-9");
        if(startSymbol < endSymbol) {
            sb.append(", ").appendCodePoint(startSymbol).append("-").appendCodePoint(endSymbol);
        }
        sb.append(")");
        System.out.println(sb);
    }

    /**
     * Check if symbol is present in the array.
     *
     * @param c searched symbol
     * @param arr an array where the symbol should be searched
     * @return searching result
     */

    private boolean contains(char c, char[] arr) {
        boolean result = false;
        for (char current : arr) {
            if (current == c) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Method is used to get rid of duplicates.
     * @param c character
     * @param secretCode raw secret code with possible duplicates
     * @return secret code without duplicates
     */

    private Character generateUniqueChar(char c, char[] secretCode) {
        int firstSymbol = 97;
        int lastSymbol = 122;
        char result;
        do {
            result = (char) (Math.random() * (lastSymbol - firstSymbol) + firstSymbol);
        } while(result != c && contains(result, secretCode));
        return result;
    }

    /**
     * Method generates raw secret code. It can contain duplicates.
     *
     * @param codeLength length of the secret code
     * @param possibleSymbols character code
     * @return raw secret code
     */

    public String getRawSecretCode(int codeLength, int possibleSymbols) {
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

    public String setUniqueChars(String secretCode) {
        char[] codeSplit = secretCode.toCharArray();
        for (int i = 0; i < codeSplit.length; i++) {
            if(Character.isDigit(codeSplit[i]) && (secretCode.indexOf(codeSplit[i]) != secretCode.lastIndexOf(codeSplit[i]))) {
                codeSplit[i] = generateUniqueChar(codeSplit[i], codeSplit);
            }
        }
        return String.valueOf(codeSplit);
    }

    public String getSecretCode(int codeLength, int numberOfSymbols) {
        String rawSecretCode = getRawSecretCode(codeLength, numberOfSymbols);
        return setUniqueChars(rawSecretCode);
    }

}
