package dev.shoangenes.expensetracker;

import java.time.LocalDate;
import java.util.function.BiFunction;

public class Expense {
    private static int lastIdSaved = 0;
    private int id;
    private String description;
    private double amount;
    private LocalDate creationDate;
    private ExpenseCategory category;

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
    public Expense(String description, double amount, ExpenseCategory category) {
        validateInputs(description, amount);

        this.id = ++lastIdSaved;
        this.description = description.trim();
        this.amount = amount;
        creationDate = LocalDate.now();
        this.category = category;
    }

    private void validateInputs(String description, double amount) {
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
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
     * Gets the creation date
     *
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the amount of the expense
     *
     * @return the amount of the expense
     */
    public double getAmount() {
        return amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    /**
     * Truncates the string representation of an object to a specified length.
     * If the string exceeds the given length, it appends "..." at the end.
     * <p>
     * Example for description = "I need food" the result is something like "I need fo..."
     *
     * @param o the object whose string representation will be truncated
     * @param l the maximum length of the resulting string
     * @return the truncated string with "..." if it exceeds the specified length
     */
    private BiFunction<Object, Integer, String> truncate = (o, l) -> {
        String content = o.toString();
        return content.length() > l ? content.substring(0, l - 3).trim() + "..." : content;
    };

    /**
     * Returns a formatted string representation of the expense object.
     * This method provides a human-readable format of the task details, including ID, description, amount, and creation timestamp.
     *
     * @return a string representation of the expense
     */
    @Override
    public String toString() {
        return String.format("%-3d %-10s %-10s %-12s $%.2f",
                    id,
                    creationDate,
                    truncate.apply(category, 10),
                    truncate.apply(description, 12),
                    amount
        );
    }
}
