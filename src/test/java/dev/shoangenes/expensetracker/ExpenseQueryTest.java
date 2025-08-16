package dev.shoangenes.expensetracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class ExpenseQueryTest {
    private List<Expense> testExpenses;

    @BeforeEach
    void setUp() {
        Expense.setLastIdSaved(0);

        // Crear gastos de prueba con diferentes características
        testExpenses = List.of(
                createExpense("Lunch", 15.0, ExpenseCategory.FOOD),
                createExpense("Bus ticket", 3.50, ExpenseCategory.TRANSPORT),
                createExpense("Movie", 12.99, ExpenseCategory.ENTERTAINMENT),
                createExpense("Groceries", 45.30, ExpenseCategory.FOOD),
                createExpense("Coffee", 4.50, ExpenseCategory.FOOD)
        );
    }

    private Expense createExpense(String desc, double amount, ExpenseCategory category) {
        return new Expense(desc, amount, category);
    }

    @Test
    void testByCategoryFilter() {
        ExpenseQuery expenseQuery = new ExpenseQuery().byCategory("FOOD");
        Predicate<Expense> filter = expenseQuery.getFilter();

        List<Expense> expenses = testExpenses.stream()
                .filter(filter)
                .toList();
        assertThat(expenses).hasSize(3);
        assertThat(expenses).allSatisfy(
                expense -> assertThat(expense.getCategory()).isEqualTo(ExpenseCategory.FOOD)
        );
    }

    @Test
    void testByAmountFilter() {
        ExpenseQuery expenseQuery = new ExpenseQuery().byMinAmount(10.0);
        Predicate<Expense> filter = expenseQuery.getFilter();

        List<Expense> expenses = testExpenses.stream()
                .filter(filter)
                .toList();
        assertThat(expenses).hasSize(3);
        assertThat(expenses).allSatisfy(
                expense -> assertThat(expense.getAmount()).isGreaterThanOrEqualTo(10.0)
        );
    }

    @Test
    void testChainedFilters() {
        ExpenseQuery expenseQuery = new ExpenseQuery()
                                        .byCategory("FOOD")
                                        .byMinAmount(10.0);
        Predicate<Expense> filter = expenseQuery.getFilter();

        List<Expense> expenses = testExpenses.stream()
                .filter(filter)
                .toList();
        assertThat(expenses).hasSize(2);
        assertThat(expenses).allSatisfy( expense -> {
                    assertThat(expense.getAmount()).isGreaterThanOrEqualTo(10.0);
                    assertThat(expense.getCategory()).isEqualTo(ExpenseCategory.FOOD);
                }
        );
    }

    @Test
    void testByMonthFilter() {
        ExpenseQuery expenseQuery = new ExpenseQuery().byMonth(String.valueOf(YearMonth.from(LocalDate.now())));
        Predicate<Expense> filter = expenseQuery.getFilter();
        List<Expense> expenses = testExpenses.stream()
                .filter(filter)
                .toList();

        assertThat(expenses).hasSize(testExpenses.size());
    }

    @Test
    void testSortByAmountAscending() {
        ExpenseQuery query = new ExpenseQuery().sortByAmount(true);

        List<Expense> result = testExpenses.stream()
                .sorted(query.getSorter())
                .toList();

        assertThat(result).isSortedAccordingTo((e1, e2) ->
                Double.compare(e1.getAmount(), e2.getAmount()));
        assertThat(result.get(0).getAmount()).isEqualTo(3.50);
        assertThat(result.get(result.size()-1).getAmount()).isEqualTo(45.30);
    }

    @Test
    void testSortByAmountDescending() {
        ExpenseQuery query = new ExpenseQuery().sortByAmount(false);

        List<Expense> result = testExpenses.stream()
                .sorted(query.getSorter())
                .toList();

        assertThat(result).isSortedAccordingTo((e1, e2) ->
                Double.compare(e2.getAmount(), e1.getAmount()));
        assertThat(result.get(0).getAmount()).isEqualTo(45.30);
        assertThat(result.get(result.size()-1).getAmount()).isEqualTo(3.50);
    }

    @Test
    void testComplexQuery() {
        ExpenseQuery query = new ExpenseQuery()
                .byCategory("FOOD")
                .byMinAmount(5.0)
                .sortByAmount(false);

        List<Expense> result = testExpenses.stream()
                .filter(query.getFilter())
                .sorted(query.getSorter())
                .toList();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getAmount()).isEqualTo(45.30);
        assertThat(result.get(1).getAmount()).isEqualTo(15.0);
    }

    @Test
    void testInvalidCategory() {
        assertThatThrownBy(() -> new ExpenseQuery().byCategory("INVALID"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid ExpenseCategory");
    }

    @Test
    void testInvalidMonth() {
        assertThatThrownBy(() -> new ExpenseQuery().byMonth("invalid-month"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid year month");
    }

    @Test
    void testDefaultFilter() {
        ExpenseQuery query = new ExpenseQuery();
        Predicate<Expense> filter = query.getFilter();

        List<Expense> result = testExpenses.stream()
                .filter(filter)
                .toList();

        assertThat(result).hasSize(testExpenses.size());
    }

    @Test
    void testDefaultSorter() {
        ExpenseQuery query = new ExpenseQuery();

        List<Expense> result = testExpenses.stream()
                .sorted(query.getSorter())
                .toList();

        for (int i = 1; i < result.size(); i++) {
            assertThat(result.get(i).getId()).isGreaterThan(result.get(i-1).getId());
        }
    }
}
