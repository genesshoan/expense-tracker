package dev.shoangenes.expensetrackercli.commands;

import dev.shoangenes.expensetracker.command.Command;
import dev.shoangenes.expensetracker.command.UpdateExpenseCommand;
import dev.shoangenes.expensetrackercli.ExpenseTrackerCli;
import picocli.CommandLine;

@CommandLine.Command(name = "update", description = "Update an expense in the expense tracker.")
public class UpdateCommand implements Runnable {
    @CommandLine.ParentCommand
    private ExpenseTrackerCli parent;

    @CommandLine.Option(names = {"-i", "-id"}, description = "Expense's id", required = true)
    private Integer id;

    @CommandLine.Option(names = {"-d", "--description"}, description = "Expense's description", required = true)
    private String description;

    /**
     * Executes the command to update an expense in the expense tracker.
     * This method is called when the command is run.
     */
    @Override
    public void run() {
        try {
            Command<Void> command = new UpdateExpenseCommand(parent.getExpenseTracker(), id, description);
            command.execute();
            System.out.println("Expense updated successfully (ID: " + id + ")");
        } catch (Exception e) {
            System.err.println("Error, cannot update expense tracker: " + e.getMessage());
        }
    }
}
