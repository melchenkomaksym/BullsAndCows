import java.util.Random;

public class CodeGenerator {

    private void printSecretCodePrepared(int codeLength, int startSymbol, int endSymbol) {
        StringBuilder sb = new StringBuilder("The secret is prepared: ");
        sb.append("*".repeat(Math.max(0, codeLength))).append(" (0-9");
        if(startSymbol < endSymbol) {
            sb.append(", ").appendCodePoint(startSymbol).append("-").appendCodePoint(endSymbol);
        }
        sb.append(")");
        System.out.println(sb);
    }

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

    public String setDigitsUnique(String secretCode) {
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
        return setDigitsUnique(rawSecretCode);
    }

    private Character generateUniqueChar(char c, char[] secretCode) {
        int firstSymbol = 97;
        int lastSymbol = 122;
        char result;
        do {
            result = (char) (Math.random() * (lastSymbol - firstSymbol) + firstSymbol);
        } while(result != c && contains(result, secretCode));
        return result;
    }
}
