package dev.shoangenes.expensetracker.command;

import dev.shoangenes.expensetracker.model.ExpenseQuery;
import dev.shoangenes.expensetracker.service.ExpenseTracker;

/**
 * Command to summarize expenses based on a given query.
 */
public class SummaryExpensesCommand implements Command {
    /** Expense tracker instance to perform the summary on. */
    private ExpenseTracker expenseTracker;
    /** Query defining the criteria for summarizing expenses. */
    private ExpenseQuery expenseQuery;
    /** Result of the summary operation. */
    private double result;

    /** Constructor to initialize the command with the expense tracker and query.
     * @param expenseTracker The expense tracker instance.
     * @param expenseQuery The query defining the criteria for summarizing expenses.
     */
    public SummaryExpensesCommand(ExpenseTracker expenseTracker, ExpenseQuery expenseQuery) {
        this.expenseTracker = expenseTracker;
        this.expenseQuery = expenseQuery;
    }

    /** Getter for the result of the summary operation.
     * @return The total amount of expenses matching the query.
     */
    public double getResult() {
        return result;
    }

    /**
     * Executes the command to summarize expenses.
     */
    @Override
    public void execute() {
        this.result = expenseTracker.summaryExpenses(expenseQuery);
    }
}
