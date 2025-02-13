package tests;

import org.junit.jupiter.api.Test;
import org.kurodev.fields.FiniteField;

public class FiniteFieldsTest {

    @Test
    public void testFiniteFields() {
        FiniteField a = FiniteField.ofBase(5);
        System.out.println(a.computeAdditionTable());
    }
}
