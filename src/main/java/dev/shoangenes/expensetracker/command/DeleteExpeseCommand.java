package dev.shoangenes.expensetracker.command;

import dev.shoangenes.expensetracker.service.ExpenseTracker;

/**
 * Command to delete an expense by its ID.
 */
public class DeleteExpeseCommand implements Command {
    /** Expense tracker instance to operate on. */
    private ExpenseTracker expenseTracker;
    /** ID of the expense to delete. */
    private int id;

    /**
     * Constructs a DeleteExpeseCommand.
     *
     * @param expenseTracker the expense tracker instance
     * @param id the ID of the expense to delete
     */
    public DeleteExpeseCommand(ExpenseTracker expenseTracker, int id) {
        this.expenseTracker = expenseTracker;
        this.id = id;
    }

    /**
     * Executes the command to delete the expense.
     */
    @Override
    public void execute() {
        expenseTracker.deleteExpense(id);
    }
}
