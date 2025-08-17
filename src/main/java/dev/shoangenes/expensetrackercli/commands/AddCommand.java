package dev.shoangenes.expensetrackercli.commands;

import dev.shoangenes.expensetracker.command.AddExpenseCommand;
import dev.shoangenes.expensetracker.command.Command;
import dev.shoangenes.expensetracker.model.ExpenseCategory;
import dev.shoangenes.expensetrackercli.ExpenseTrackerCli;
import picocli.CommandLine;
import picocli.CommandLine.Option;

/**
 * Command to add a new expense.
 * This command requires a description, amount, and category for the expense.
 */
@CommandLine.Command(name = "add", description = "Add a new expense")
public class AddCommand implements Runnable {
    @CommandLine.ParentCommand
    private ExpenseTrackerCli parent;

    @Option(names = {"-d", "--description"}, description = "Expense's description", required = true)
    private String description;

    @Option(names = {"-a", "--amount"}, description = "Expense's amount", required = true)
    private double amount;

    @Option(names = {"-c", "--category"}, description = "Expense's category", required = true)
    private ExpenseCategory category;


    /**
     * Executes the command to add a new expense.
     * It creates an instance of AddExpenseCommand with the provided parameters
     * and calls its execute method to perform the operation.
     * If an error occurs, it prints an error message to the console.
     */
    @Override
    public void run() {
        try {
            Command<Void> command = new AddExpenseCommand(
                    parent.getExpenseTracker(),
                    description,
                    amount,
                    category);
            command.execute();
            System.out.println("Expense added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding expense: " + e.getMessage());
        }
    }
}
