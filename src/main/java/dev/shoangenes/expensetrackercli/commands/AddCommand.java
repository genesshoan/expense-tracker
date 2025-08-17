package dev.shoangenes.expensetrackercli.commands;

import dev.shoangenes.expensetracker.command.AddExpenseCommand;
import dev.shoangenes.expensetracker.command.Command;
import dev.shoangenes.expensetracker.model.ExpenseCategory;
import dev.shoangenes.expensetracker.service.ExpenseTracker;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "add", description = "Add a new expense")
public class AddCommand implements Runnable {
    @Option(names = {"-d", "--description"}, description = "Expense's description", required = true)
    private String description;
    @Option(names = {"-a", "--amount"}, description = "Expense's amount", required = true)
    private double amount;
    @Option(names = {"-c", "--category"}, description = "Expense's category", required = true)
    private ExpenseCategory category;
    private ExpenseTracker expenseTracker;

    @Override
    public void run() {
        try {
            Command command = new AddExpenseCommand(expenseTracker, description, amount, category);
            command.execute();
            System.out.println("Expense added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding expense: " + e.getMessage());
        }
    }
}
