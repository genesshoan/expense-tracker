package dev.shoangenes.expensetrackercli.commands;

import dev.shoangenes.expensetracker.command.Command;
import dev.shoangenes.expensetracker.command.UpdateExpenseCommand;
import dev.shoangenes.expensetrackercli.ExpenseTrackerCli;
import picocli.CommandLine;

public class UpdateCommand implements Runnable {
    @CommandLine.ParentCommand
    private ExpenseTrackerCli parent;

    @CommandLine.Option(names = {"-i", "-id"}, description = "Expense's id", required = true)
    private Integer id;

    @CommandLine.Option(names = {"-d", "--description"}, description = "Expense's description", required = true)
    private String description;

    @Override
    public void run() {
        try {
            Command<Void> command = new UpdateExpenseCommand(parent.getExpenseTracker(), id, description);
            command.execute();
        } catch (Exception e) {
            System.err.println("Error, cannot update expense tracker: " + e.getMessage());
        }
    }
}
