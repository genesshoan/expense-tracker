package dev.shoangenes.expensetracker;
/**
 * Custom exception for errors related to expense storage.
 */
public class ExpenseStorageException extends RuntimeException {
    public ExpenseStorageException(String message) {
        super(message);
    }
    public ExpenseStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
