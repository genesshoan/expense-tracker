package dev.shoangenes.expensetracker;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ExpenseTracker {
    private final Path FILE_PATH = Path.of("expense_tracker.json");
    private Map<Integer, Expense> expenses = new HashMap<>();

    /**
     * Creates a new ExpenseTracker instance.
     * <p>
     * If the 'expenses.json' file is empty or missing, an empty expense collection is initialized
     * Otherwise, all existing expenses are loaded from the file into memory
     */

    public ExpenseTracker() {
        expenses = deserializeExpenses();
    }

    /**
     * Reads all expenses from a JSON file
     * <p>
     * If the file does not exist, returns an empty map
     * If the file exists but is empty, returns an empty map
     * Otherwise, returns a map containing all stored expenses
     *
     * @return a map with all expenses, or an empty map if none are found
     * @throws ExpenseStorageException if reading the file fails
     */
    private Map<Integer, Expense> deserializeExpenses() {
        Map<Integer, Expense> storedExpenses = new HashMap<>();

        if (!Files.exists(FILE_PATH)) {
            return storedExpenses;
        }

        try {
            // Read Json content
            String jsonContent = Files.readString(FILE_PATH);
            Gson gson = new Gson();
            Type type = new TypeToken<Map<Integer, Expense>>(){}.getType();

            // Parse jsonContent to Map
            storedExpenses = gson.fromJson(jsonContent, type);

            // Update lastSavedId
            int max = storedExpenses.values().stream()
                    .mapToInt(Expense::getId)
                    .max()
                    .orElse(0);
            Expense.setLastIdSaved(max);
        } catch (Exception e) {
            throw new ExpenseStorageException("Error reading expenses from file: " + FILE_PATH, e);
        }

        return storedExpenses;
    }

    /**
     * Serializes all expenses and saves them to a JSON file
     *
     * @throws ExpenseStorageException if writing to the file fails
     */
    public void serializeExpenses() {
        try {
            Gson gson = new Gson();
            Files.writeString(FILE_PATH, gson.toJson(expenses));
        } catch (IOException e) {
            throw new ExpenseStorageException("Error writing expenses to file: " + FILE_PATH, e);
        }
    }

    /**
     * Adds a new expense to the system
     *
     * @param description the expense's description
     * @param amountStr the expense's amount
     * @param categoryStr the expense's category
     * @throws IllegalArgumentException if amount format is invalid, category is invalid,
     *                                  or business rules are violated (negative amount, empty description)
     */
    public void addExpense(String description, String amountStr, String categoryStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            ExpenseCategory category = ExpenseCategory.parseCategory(categoryStr);
            Expense expense = new Expense(description, amount, category);
            expenses.put(expense.getId(), expense);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format: " + amountStr, e);
        }
    }

    /**
     * Parses the provided identifier string to an integer.
     *
     * @param id the identifier as a string
     * @return the identifier as an integer
     * @throws IllegalArgumentException if the id format is invalid
     */
    private int parseId(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id format: " + id, e);
        }
    }

    /**
     * Updates the description of the given expense
     *
     * @param idStr the ID of the expense to update
     * @param description the new expense's description
     * @throws NoSuchElementException if the expense doesn't exist
     * @throws IllegalArgumentException if the ID has an invalid format
     */
    public void updateExpense(String idStr, String description) {
        int id = parseId(idStr);
        Expense expense = Optional.ofNullable(expenses.get(id)).orElseThrow(() -> new NoSuchElementException("No such expense with id: " + id));
        expense.updateDescription(description);
    }

    /**
     * Deletes the given expense
     *
     * @param idStr the ID of the expense to delete
     * @throws NoSuchElementException if the expense doesn't exist
     * @throws IllegalArgumentException if the ID has an invalid format
     */
    public void deleteExpense(String idStr) {
        int id = parseId(idStr);
        Optional.ofNullable(expenses.remove(id)).orElseThrow(() -> new NoSuchElementException("No such expense with id: " + id));
    }

    /**
     * Returns a list of expenses filtered and sorted according to the provided predicate and comparator.
     *
     * @param query a predicate to filter expenses
     * @return a list of filtered and sorted expenses
     */
    public List<Expense> listExpenses(ExpenseQuery query) {
        /*System.out.printf("%-3s %-10s %-12s %s", "ID", "Date", "Description", "Amount");
        System.out.println("--------------------------------");*/
        return expenses.values().stream()
                .filter(query.getFilter())
                .sorted(query.getSorter())
                .toList();
    }

    /**
     * Calculates the total sum of the amounts of all expenses that match the given filter.
     *
     * @param query a predicate to filter which expenses to include in the sum
     * @return the sum of the amounts of the filtered expenses
     */
    public double summaryExpenses(ExpenseQuery query) {
        return expenses.values().stream()
                .filter(query.getFilter())
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}
