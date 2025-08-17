package dev.shoangenes.expensetrackercli;

import dev.shoangenes.expensetracker.service.ExpenseTracker;
import picocli.CommandLine.Command;
import dev.shoangenes.expensetrackercli.commands.*;

@Command(name = "expense-tracker", mixinStandardHelpOptions = true, version = "1.0",
        description = "A simple expense tracker CLI application.",
        subcommands = {
            AddCommand.class,
            DeleteCommand.class,
            ListCommand.class
        })
public class ExpenseTrackerCli implements Runnable {
    private final ExpenseTracker expenseTracker = new ExpenseTracker();

    /**
     * Gets the ExpenseTracker instance used by this CLI application.
     *
     * @return the ExpenseTracker instance
     */
    public ExpenseTracker getExpenseTracker() {
        return expenseTracker;
    }

    /**
     * Default run method that provides a message when no subcommand is specified.
     */
    @Override
    public void run() {
        System.out.println("Use a subcommand: add, delete, list...");
    }
}
