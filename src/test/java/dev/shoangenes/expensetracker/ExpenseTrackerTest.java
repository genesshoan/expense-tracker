package dev.shoangenes.expensetracker;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class ExpenseTrackerTest {
    ExpenseTracker testExpenses;

    @BeforeEach
    void setUp() {
        Expense.setLastIdSaved(0);

        try {
            Files.deleteIfExists(Path.of("expense_tracker.json"));
        } catch (IOException e) {
            // Ignore cleanup errors
        }

        testExpenses = new ExpenseTracker();
        testExpenses.addExpense("Lunch", 12.0, ExpenseCategory.FOOD);
        testExpenses.addExpense("Transporte", 5.75, ExpenseCategory.TRANSPORT);
        testExpenses.addExpense("Café", 2.50, ExpenseCategory.FOOD);
        testExpenses.addExpense("Libro", 20.0, ExpenseCategory.EDUCATION);
        testExpenses.addExpense("Internet", 30.0, ExpenseCategory.ENTERTAINMENT);
    }

    @AfterAll
    static void tearDown() throws IOException {
        try {
            Files.deleteIfExists(Path.of("expense_tracker.json"));
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Cena,15.0,FOOD,true",
            "Taxi,8.5,TRANSPORT,true",
            "Película,10.0,ENTERTAINMENT,true",
            "Cuaderno,3.0,EDUCATION,true",
            "Cena,0.0,FOOD,false",
            "Taxi,-10.5,TRANSPORT,false",
            "'',10.0,ENTERTAINMENT,false",
            "' ',10.0,FOOD,false"  // Espacios en blanco
    })
    void testAddExpense(String description, double amount, ExpenseCategory category, boolean shouldWork) {
        List<Expense> expensesBefore = testExpenses.listExpenses(new ExpenseQuery());
        int sizeBefore = expensesBefore.size();

        if (shouldWork) {
            testExpenses.addExpense(description, amount, category);
            List<Expense> expensesAfter = testExpenses.listExpenses(new ExpenseQuery());
            assertThat(expensesAfter.size()).isEqualTo(sizeBefore + 1);

            Expense lastExpense = expensesAfter.get(expensesAfter.size() - 1);
            assertThat(lastExpense.getAmount()).isEqualTo(amount);
            assertThat(lastExpense.getCategory()).isEqualTo(category);
            assertThat(lastExpense.getCreationDate()).isEqualTo(LocalDate.now());
        } else {
            assertThatThrownBy(() -> testExpenses.addExpense(description, amount, category))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(amount <= 0 ? "Amount must be positive" : "Description cannot be empty");

            List<Expense> expensesAfter = testExpenses.listExpenses(new ExpenseQuery());
            assertThat(expensesAfter.size()).isEqualTo(sizeBefore);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testDeleteExpenseSuccess(int id) {
        List<Expense> expensesBefore = testExpenses.listExpenses(new ExpenseQuery());
        int sizeBefore = expensesBefore.size();

        testExpenses.deleteExpense(id);

        List<Expense> expensesAfter = testExpenses.listExpenses(new ExpenseQuery());
        assertThat(expensesAfter.size()).isEqualTo(sizeBefore - 1);

        assertThat(expensesAfter).noneMatch(expense -> expense.getId() == id);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 99, 100})
    void testDeleteExpenseNotFound(int id) {
        List<Expense> expensesBefore = testExpenses.listExpenses(new ExpenseQuery());
        int sizeBefore = expensesBefore.size();


        assertThatThrownBy(() -> testExpenses.deleteExpense(id))
                .isInstanceOf(NoSuchElementException.class);
        List<Expense> expensesAfter = testExpenses.listExpenses(new ExpenseQuery());
        assertThat(expensesAfter.size()).isEqualTo(sizeBefore);
    }

    @Test
    void testListExpensesWithoutFilter() {
        List<Expense> expenses = testExpenses.listExpenses(new ExpenseQuery());

        assertThat(expenses).hasSize(5);
        assertThat(expenses).extracting(Expense::getId).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void testListExpensesByCategory() {
        ExpenseQuery query = new ExpenseQuery();
        query.byCategory(ExpenseCategory.FOOD);

        List<Expense> foodExpenses = testExpenses.listExpenses(query);

        assertThat(foodExpenses).hasSize(2);
        assertThat(foodExpenses).allMatch(expense -> expense.getCategory() == ExpenseCategory.FOOD);
    }

    @Test
    void testSummaryTotal() {
        double expectedTotal = 12.0 + 5.75 + 2.50 + 20.0 + 30.0;
        double actualTotal = testExpenses.summaryExpenses(new ExpenseQuery());

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    @Test
    void testSummaryByCategory() {
        double foodTotal = testExpenses.summaryExpenses(new ExpenseQuery().byCategory(ExpenseCategory.FOOD));
        double transportTotal = testExpenses.summaryExpenses(new ExpenseQuery().byCategory(ExpenseCategory.TRANSPORT));

        assertThat(foodTotal).isEqualTo(14.5); // 12.0 + 2.50
        assertThat(transportTotal).isEqualTo(5.75);
    }

    @Test
    void testSummaryEmptyCategory() {
        double total = testExpenses.summaryExpenses(new ExpenseQuery().byCategory(ExpenseCategory.MISC));

        assertThat(total).isEqualTo(0.0);
    }

    @Test
    void testUpdateExpenseDescription() {
        int expenseId = 1;
        String newDescription = "Almuerzo actualizado";

        testExpenses.updateExpense(expenseId, newDescription);

        List<Expense> expenses = testExpenses.listExpenses(new ExpenseQuery());
        Expense updatedExpense = expenses.stream()
                .filter(expense -> expense.getId() == expenseId)
                .findFirst()
                .orElse(null);

        assertThat(updatedExpense).isNotNull();
    }

    @Test
    void testUpdateNonExistentExpense() {
        assertThatThrownBy(() -> testExpenses.updateExpense(999, "Descripción nueva"))
                .isInstanceOf(NoSuchElementException.class);
    }
}
