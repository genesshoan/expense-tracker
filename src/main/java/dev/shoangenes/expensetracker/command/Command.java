package dev.shoangenes.expensetracker.command;

/**
 * Represents a command that can be executed.
 */
public interface Command<R> {
    /**
     * Executes the command.
     */
    R execute();
}
