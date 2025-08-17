package dev.shoangenes.expensetracker;

import dev.shoangenes.expensetracker.model.Expense;
import dev.shoangenes.expensetracker.model.ExpenseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class ExpenseTest {

    @BeforeEach
    void setLastSavedIdToZero() {
        Expense.setLastIdSaved(0);
    }

    @ParameterizedTest
    @MethodSource("validExpenseFieldsProvider")
    void testConstructorWithValidInputs(String description, double amount, ExpenseCategory category) {
        Expense expense = new Expense(description, amount, category);

        assertThat(expense.getCategory()).isEqualTo(category);
        assertThat(expense.getAmount()).isEqualTo(amount);
        assertThat(expense.getId()).isEqualTo(Expense.getLastIdSaved());

        LocalDate today = LocalDate.now();
        assertThat(expense.getCreationDate()).isEqualTo(today);
    }

    static Stream<Arguments> validExpenseFieldsProvider() {
        return Stream.of(
                Arguments.of("Lunch", 122.0, ExpenseCategory.FOOD),
                Arguments.of("Cena", 50.5, ExpenseCategory.FOOD),
                Arguments.of("Transporte mensual", 300.0, ExpenseCategory.TRANSPORT),
                Arguments.of("Libro universitario", 10.0, ExpenseCategory.EDUCATION),
                Arguments.of("Café premium", 4.99, ExpenseCategory.FOOD)
        );
    }

    @ParameterizedTest
    @MethodSource("edgeCaseProvider")
    void testConstructorWithEdgeCases(String description, double amount, ExpenseCategory category,
                                      String expectedBehavior) {
        if (expectedBehavior.equals("should_work")) {
            assertDoesNotThrow(() -> new Expense(description, amount, category));
        } else {
            assertThrows(IllegalArgumentException.class,
                    () -> new Expense(description, amount, category));
        }
    }

    static Stream<Arguments> edgeCaseProvider() {
        return Stream.of(
                Arguments.of("", 10.0, ExpenseCategory.ENTERTAINMENT, "shouldn't_work"),
                Arguments.of("Normal expense", -5.0, ExpenseCategory.FOOD, "shouldn't_work"),
                Arguments.of("Very long description that might cause display issues", 100.0, ExpenseCategory.MISC, "should_work"),
                Arguments.of("Invalid amount", 0, ExpenseCategory.EDUCATION, "shouldn't_work")
        );
    }

    @Test
    void testUpdateDescription() {
        Expense expense = new Expense("Old Description", 25.0, ExpenseCategory.ENTERTAINMENT);
        expense.updateDescription("New Description");

        assertThat(expense.toString()).contains("New Descr...");
    }

    @Test
    void testIdIncrementSequentially() {
        Expense expense1 = new Expense("First", 10.0, ExpenseCategory.FOOD);
        Expense expense2 = new Expense("Second", 20.0, ExpenseCategory.TRANSPORT);

        assertThat(expense2.getId()).isEqualTo(expense1.getId() + 1);
        assertThat(Expense.getLastIdSaved()).isEqualTo(2);
    }

    @Test
    void testToStringFormat() {
        Expense expense = new Expense("Test expense", 99.99, ExpenseCategory.FOOD);
        String result = expense.toString();

        assertThat(result).contains(String.valueOf(expense.getId()));
        assertThat(result).contains("Test expense");
        assertThat(result).contains("99,99");
    }

    @Test
    void testToStringTruncation() {
        Expense expense = new Expense("This is a very long description that should be truncated",
                50.0, ExpenseCategory.EDUCATION);
        String result = expense.toString();

        assertThat(result).contains("This is a...");
        assertThat(result).doesNotContain("very long description");
    }
}