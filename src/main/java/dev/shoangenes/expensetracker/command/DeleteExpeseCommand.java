package dev.shoangenes.expensetracker.command;

import dev.shoangenes.expensetracker.ExpenseTracker;

public class DeleteExpeseCommand implements Command {
    private ExpenseTracker expenseTracker;
    private int id;

    public DeleteExpeseCommand(ExpenseTracker expenseTracker, int id) {
        this.expenseTracker = expenseTracker;
        this.id = id;
    }

    @Override
    public void execute() {
        expenseTracker.deleteExpense(id);
    }
}
