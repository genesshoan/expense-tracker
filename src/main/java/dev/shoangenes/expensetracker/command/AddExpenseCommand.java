package dev.shoangenes.expensetracker.command;

import dev.shoangenes.expensetracker.model.ExpenseCategory;
import dev.shoangenes.expensetracker.service.ExpenseTracker;

/**
 * Command to add a new expense to the ExpenseTracker.
 */
public class AddExpenseCommand implements Command {
    /**
     * The ExpenseTracker instance where the expense will be added.
     */
    private ExpenseTracker expenseTracker;

    /**
     * Description of the expense.
     */
    private String description;

    /**
     * Amount of the expense.
     */
    private double amount;

    /**
     * Category of the expense.
     */
    private ExpenseCategory category;

    /**
     * Constructs an AddExpenseCommand with the specified parameters.
     *
     * @param expenseTracker the ExpenseTracker to add the expense to
     * @param description the description of the expense
     * @param amount the amount of the expense
     * @param category the category of the expense
     */
    public AddExpenseCommand(ExpenseTracker expenseTracker, String description, double amount, ExpenseCategory category) {
        this.expenseTracker = expenseTracker;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    /**
     * Executes the command to add an expense to the ExpenseTracker.
     */
    @Override
    public void execute() {
        expenseTracker.addExpense(description, amount, category);
    }
}
