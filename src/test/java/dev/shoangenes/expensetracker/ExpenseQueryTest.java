package dev.shoangenes.expensetracker;

import dev.shoangenes.expensetracker.model.Expense;
import dev.shoangenes.expensetracker.model.ExpenseCategory;
import dev.shoangenes.expensetracker.model.ExpenseQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import static org.assertj.core.api.Assertions.*;

public class ExpenseQueryTest {
    private List<Expense> testExpenses;

    @BeforeEach
    void setUp() {
        Expense.setLastIdSaved(0);

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
        ExpenseQuery expenseQuery = ExpenseQuery.makeQuery(List.of(ExpenseCategory.FOOD), null, null, null, null);
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
        ExpenseQuery expenseQuery = ExpenseQuery.makeQuery(null, 10.0, null, null, null);
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
        ExpenseQuery expenseQuery = ExpenseQuery.makeQuery(List.of(ExpenseCategory.FOOD), 10.0, null, null, null);
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
        ExpenseQuery expenseQuery = ExpenseQuery.makeQuery(null, null, YearMonth.from(LocalDate.now()), null, null);
        Predicate<Expense> filter = expenseQuery.getFilter();
        List<Expense> expenses = testExpenses.stream()
                .filter(filter)
                .toList();

        assertThat(expenses).hasSize(testExpenses.size());
    }

    @Test
    void testSortByAmountAscending() {
        ExpenseQuery query = ExpenseQuery.makeQuery(null, null, null, null, true);

        List<Expense> result = testExpenses.stream()
                .sorted(query.getSorter())
                .toList();

        assertThat(result).isSortedAccordingTo(Comparator.comparingDouble(Expense::getAmount));
        assertThat(result.getFirst().getAmount()).isEqualTo(3.50);
        assertThat(result.getLast().getAmount()).isEqualTo(45.30);
    }

    @Test
    void testSortByAmountDescending() {
        ExpenseQuery query = ExpenseQuery.makeQuery(null, null, null, null, false);

        List<Expense> result = testExpenses.stream()
                .sorted(query.getSorter())
                .toList();

        assertThat(result).isSortedAccordingTo((e1, e2) ->
                Double.compare(e2.getAmount(), e1.getAmount()));
        assertThat(result.getFirst().getAmount()).isEqualTo(45.30);
        assertThat(result.getLast().getAmount()).isEqualTo(3.50);
    }

    @Test
    void testComplexQuery() {
        ExpenseQuery query = ExpenseQuery.makeQuery(List.of(ExpenseCategory.FOOD), 5.0, null, null, false);

        List<Expense> result = testExpenses.stream()
                .filter(query.getFilter())
                .sorted(query.getSorter())
                .toList();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getAmount()).isEqualTo(45.30);
        assertThat(result.get(1).getAmount()).isEqualTo(15.0);
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
