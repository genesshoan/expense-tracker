package dev.shoangenes.expensetracker.command;

import dev.shoangenes.expensetracker.service.ExpenseTracker;

/**
 * Command to update an existing expense's description in the ExpenseTracker.
 */
public class UpdateExpenseCommand implements Command {
    /** ExpenseTracker instance to operate on */
    private ExpenseTracker expenseTracker;
    /** ID of the expense to update */
    private int id;
    /** New description for the expense */
    private String description;

    /**
     * Constructs an UpdateExpenseCommand.
     *
     * @param expenseTracker the ExpenseTracker instance
     * @param id             the ID of the expense to update
     * @param description    the new description for the expense
     */
    public UpdateExpenseCommand(ExpenseTracker expenseTracker, int id, String description) {
        this.expenseTracker = expenseTracker;
        this.id = id;
        this.description = description;
    }

    /**
     * Executes the command to update the expense's description.
     */
    @Override
    public void execute() {
        expenseTracker.updateExpense(id, description);
    }
}
