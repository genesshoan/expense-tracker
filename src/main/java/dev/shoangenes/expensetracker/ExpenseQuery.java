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
     * @param categoryStr the category as a string
     * @return this query instance for chaining
     * @throws IllegalArgumentException if the category is invalid
     */
    public ExpenseQuery byCategory(String categoryStr) {
        try {
            ExpenseCategory category = ExpenseCategory.valueOf(categoryStr.toUpperCase());
            filter = filter.and(e -> e.getCategory() == category);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ExpenseCategory: " + categoryStr, e);
        }
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
     * @param monthStr the month in format yyyy-MM
     * @return this query instance for chaining
     * @throws IllegalArgumentException if the month format is invalid
     */
    public ExpenseQuery byMonth(String monthStr) {
        try {
            YearMonth yearMonth = YearMonth.parse(monthStr);
            filter = filter.and(e -> YearMonth.from(e.getCreationDate()).equals(yearMonth));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid year month, the argument must be such as 2007-12", e);
        }
        return this;
    }

    /**
     * Filters expenses by a specific year.
     *
     * @param yearStr the year in format yyyy
     * @return this query instance for chaining
     * @throws IllegalArgumentException if the year format is invalid
     */
    public ExpenseQuery byYear(String yearStr) {
        try {
            Year year = Year.parse(yearStr);
            filter = filter.and(e -> Year.from(e.getCreationDate()).equals(year));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid year, the argument must be such as 2007", e);
        }
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
