package dev.shoangenes.expensetracker.command;

import dev.shoangenes.expensetracker.ExpenseTracker;

public class UpdateExpenseCommand implements Command {
    private ExpenseTracker expenseTracker;
    private int id;
    private String description;

    public UpdateExpenseCommand(ExpenseTracker expenseTracker, int id, String description) {
        this.expenseTracker = expenseTracker;
        this.id = id;
        this.description = description;
    }

    @Override
    public void execute() {
        expenseTracker.updateExpense(id, description);
    }
}
