package dev.shoangenes.expensetracker;

import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Provides a fluent API to build queries for filtering and sorting {@link Expense} objects.
 * Allows filtering by category, minimum amount, month, and year, as well as sorting by amount.
 */
public class ExpenseQuery {
    /**
     * Predicate used to filter expenses.
     */
    private Predicate<Expense> filter = e -> true;

    /**
     * Comparator used to sort expenses.
     */
    private Comparator<Expense> sorter = Comparator.comparingInt(Expense::getId);

    /**
     * Returns the current filter predicate.
     *
     * @return the filter predicate
     */
    public Predicate<Expense> getFilter() {
        return filter;
    }

    /**
     * Returns the current comparator for sorting.
     *
     * @return the comparator for sorting expenses
     */
    public Comparator<Expense> getSorter() {
        return sorter;
    }

    /**
     * Filters expenses by the specified category.
     *
     * @param category the category as a string
     * @return this query instance for chaining
     */
    public ExpenseQuery byCategory(ExpenseCategory category) {
        filter = filter.and(e -> e.getCategory() == category);
        return this;
    }

    /**
     * Filters expenses by a minimum amount.
     *
     * @param amount the minimum amount
     * @return this query instance for chaining
     */
    public ExpenseQuery byMinAmount(double amount) {
        filter = filter.and(e -> e.getAmount() >= amount);
        return this;
    }

    /**
     * Filters expenses by a specific month.
     *
     * @param yearMonth the month in format yyyy-MM
     * @return this query instance for chaining
     */
    public ExpenseQuery byMonth(YearMonth yearMonth) {
        filter = filter.and(e -> YearMonth.from(e.getCreationDate()).equals(yearMonth));
        return this;
    }

    /**
     * Filters expenses by a specific year.
     *
     * @param year the year in format yyyy
     * @return this query instance for chaining
     */
    public ExpenseQuery byYear(Year year) {
        filter = filter.and(e -> Year.from(e.getCreationDate()).equals(year));
        return this;
    }

    /**
     * Sets the sorting order by amount.
     *
     * @param ascending true for ascending order, false for descending
     * @return this query instance for chaining
     */
    public ExpenseQuery sortByAmount(boolean ascending) {
        sorter = ascending ? Comparator.comparing(Expense::getAmount)
                            : Comparator.comparing(Expense::getAmount).reversed();
        return this;
    }
}
