package dev.shoangenes.expensetracker.command;

import dev.shoangenes.expensetracker.ExpenseCategory;
import dev.shoangenes.expensetracker.ExpenseTracker;

public class AddExpenseCommand implements Command {
    private ExpenseTracker expenseTracker;
    private String description;
    private double amount;
    private ExpenseCategory category;

    public AddExpenseCommand(ExpenseTracker expenseTracker, String description, double amount, ExpenseCategory category) {
        this.expenseTracker = expenseTracker;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    @Override
    public void execute() {
        expenseTracker.addExpense(description, amount, category);
    }
}
