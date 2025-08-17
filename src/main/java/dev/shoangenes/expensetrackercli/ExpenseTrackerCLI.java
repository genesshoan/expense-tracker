package dev.shoangenes.expensetrackercli;

import dev.shoangenes.expensetracker.service.ExpenseTracker;
import picocli.CommandLine.Command;
import dev.shoangenes.expensetrackercli.commands.*;

@Command(name = "expense-tracker", mixinStandardHelpOptions = true, version = "1.0",
        description = "A simple expense tracker CLI application.",
        subcommands = {
            AddCommand.class
            /*dev.shoangenes.expensetrackercli.commands.DeleteCommand.class,
            dev.shoangenes.expensetrackercli.commands.ListCommand.class,
            dev.shoangenes.expensetrackercli.commands.ExportCommand.class,
            dev.shoangenes.expensetrackercli.commands.ImportCommand.class*/
        })
public class ExpenseTrackerCLI implements Runnable {
    private final ExpenseTracker expenseTracker = new ExpenseTracker();

    @Override
    public void run() {
        System.out.println("Use a subcommand: add, delete, list...");
    }
}
