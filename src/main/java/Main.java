public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.setSecretCodeLength();
        gameManager.setNumberOfSymbols();
        gameManager.setSecretCode();

        gameManager.guessTheCode();
    }
}
