import java.util.Scanner;

public class GameManager {

    CodeGenerator generator = new CodeGenerator();
    Scanner scanner = new Scanner(System.in);
    private int secretCodeLength = 0;
    private int numberOfSymbols;
    private String secretCode;

    public void setSecretCodeLength() {
        System.out.println("Please, enter the secret code's length:");
        String secretCodeLengthStr = scanner.nextLine();

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
    }

    public void setNumberOfSymbols() {
        System.out.println("Input the number of possible symbols in the code:");
        numberOfSymbols = scanner.nextInt();

        if(secretCodeLength > numberOfSymbols) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", secretCodeLength, numberOfSymbols);
            System.exit(0);
        }

        if (numberOfSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }
    }

    public void setSecretCode() {
        secretCode = generator.getSecretCode(secretCodeLength, numberOfSymbols);
    }

    private void printGrade(int cows, int bulls) {
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

    private String getCattle(int cattleCount, String cattleName) {
        if(cattleCount > 1) {
            return cattleCount + " " + cattleName;
        } else {
            return cattleCount + " " + cattleName + "s";
        }
    }

    public void guessTheCode() {
        char[] secretCodeCharArr = secretCode.toCharArray();
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
}
