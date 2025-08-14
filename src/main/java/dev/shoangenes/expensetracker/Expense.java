package dev.shoangenes.expensetracker;

import java.time.LocalDate;
import java.util.function.BiFunction;

public class Expense {
    private static int lastIdSaved = 0;
    private int id;
    private String description;
    private double amount;
    private LocalDate creationDate;

    /**
     * Empty constructor for Gson
     */
    public Expense() {}

    /**
     * Constructor for Expense
     * This constructor initializes a new expense with a
     * unique ID, description, amount, and timestamps for creation.
     * This ID is automatically incremented from the last saved ID to ensure uniqueness
     *
     * @param description the description of the expense
     * @param amount the amount of the expense
     */
    public Expense(String description, double amount) {
        this.id = ++lastIdSaved;
        this.description = description;
        this.amount = amount;
        creationDate = LocalDate.now();
    }

    /**
     * Gets the last id saved for all expenses
     *
     * @return the last id saved for all expenses
     */
    public static int getLastIdSaved() {
        return lastIdSaved;
    }

    /**
     * Updates the last id saved for all expenses
     *
     * @param updatedLastId the up-to-date last saved id
     */
    public static void setLastIdSaved(int updatedLastId) {
        lastIdSaved = updatedLastId;
    }

    /**
     * Gets the unique identifier of the expense
     *
     * @return the unique identifier of the expense
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the description of the expense
     *
     * @param description the new description for the expense
     */
    public void updateDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a formatted string representation of the expense object.
     * This method provides a human-readable format of the task details, including ID, description, amount, and creation timestamp.
     *
     * @return a string representation of the expense
     */
    @Override
    public String toString() {
        // Example for description = "I need food" the result is something like "I need fo..."
        BiFunction<Object, Integer, String> truncate = (o, l) -> {
            String content = o.toString();
            return content.length() > l ? content.substring(0, l - 3) + "..." : content;
        };

        return String.format("# %-3d %-10s %-12s $%.2f",
                    id,
                    creationDate,
                    truncate.apply(description, 12),
                    amount
        );
    }
}
