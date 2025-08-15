package dev.shoangenes.expensetracker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExpenseCategoryTest {
    @ParameterizedTest
    @CsvSource({
            "ENTERTAINMENT, ENTERTAINMENT, true",
            "Health & Medical, HEALTH, true",
            "education, EDUCATION, true",
            "Home & Utilities, HOME, true",
            "Miscellaneous, MISC, true",
            "food & dining, FOOD, true",
            "unknown, MISC, false",
            "' ', MISC, false",
            "'', MISC, false",
            "FOODIE, MISC, false"
    })
    void testParseCategory(String value, ExpenseCategory expected,boolean shouldWork) {
        if (shouldWork) {
            assertThat(ExpenseCategory.parseCategory(value)).isEqualTo(expected);
        } else {
            assertThrows(IllegalArgumentException.class, () -> ExpenseCategory.parseCategory(value));
        }
    }
}
