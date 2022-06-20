import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class AppTests {

    CodeGenerator generator = new CodeGenerator();
    String basicCode = "aa11";
    int codeLength = 8;
    int possibleSymbols = 8;


    @Test
    public void getRawSecretCodeTest() {

        String code = generator.getRawSecretCode(codeLength, possibleSymbols);

        assertEquals(code.length(), codeLength);
    }

    @Test
    public void setUniqueCharsTest() {

        String updatedCode = generator.setUniqueChars(basicCode);

        assertNotEquals(basicCode, updatedCode);
        assertEquals(basicCode.length(), updatedCode.length());
    }

    @Test
    public void setSecretCodeTest() {

        String secretCode = generator.getSecretCode(codeLength, possibleSymbols);

        Set<Character> secretCodeSet = secretCode.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());

        assertEquals(secretCode.length(), secretCodeSet.size());
    }
}
