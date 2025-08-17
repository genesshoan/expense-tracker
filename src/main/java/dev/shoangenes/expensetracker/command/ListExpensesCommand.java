package dev.shoangenes.expensetracker.command;

import dev.shoangenes.expensetracker.model.Expense;
import dev.shoangenes.expensetracker.model.ExpenseQuery;
import dev.shoangenes.expensetracker.service.ExpenseTracker;
import java.util.List;

/**
 * Command to list expenses based on a given query.
 */
public class ListExpensesCommand implements Command {
    /** Expense tracker instance to operate on. */
    private ExpenseTracker expenseTracker;
    /** Query to filter expenses. */
    private ExpenseQuery expenseQuery;
    /** Resulting list of expenses after execution. */
    private List<Expense> result;

    /**
     * Constructs a ListExpensesCommand with the specified expense tracker and query.
     *
     * @param expenseTracker the expense tracker to list expenses from
     * @param expenseQuery the query to filter expenses
     */
    public ListExpensesCommand(ExpenseTracker expenseTracker, ExpenseQuery expenseQuery) {
        this.expenseTracker = expenseTracker;
        this.expenseQuery = expenseQuery;
    }

    /**
     * Gets the list of expenses resulting from the command execution.
     *
     * @return the list of expenses
     */
    public List<Expense> getResult() {
        return result;
    }

    /**
     * Executes the command to list expenses based on the provided query.
     */
    @Override
    public void execute() {
        this.result = expenseTracker.listExpenses(expenseQuery);
    }
}
