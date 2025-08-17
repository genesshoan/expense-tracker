package dev.shoangenes.expensetrackercli.commands;

import dev.shoangenes.expensetracker.command.Command;
import dev.shoangenes.expensetracker.command.DeleteExpeseCommand;
import dev.shoangenes.expensetrackercli.ExpenseTrackerCli;
import picocli.CommandLine;

@CommandLine.Command(name = "delete", description = "Delete an expense by ID.")
public class DeleteCommand implements Runnable {
    @CommandLine.ParentCommand
    private ExpenseTrackerCli parent;

    @CommandLine.Option(names = {"-i", "--id"}, description = "Expense's id",  required = true)
    private Integer id;

    /**
     * Executes the command to delete an expense by its id
     */
    @Override
    public void run() {
        try {
            Command<Void> command = new DeleteExpeseCommand(parent.getExpenseTracker(), id);
            command.execute();
            System.out.println("Successfully deleted expense by ID: " + id);
        } catch (Exception e) {
            System.out.println("Error deleting expense by ID: " + e.getMessage());
        }
    }
}
