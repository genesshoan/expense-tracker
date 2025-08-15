package dev.shoangenes.expensetracker;

/**
 * Enum representing different categories of expenses.
 * Each category has a display name for user-friendly representation.
 */
public enum ExpenseCategory {
    /** Food & Dining expenses */
    FOOD("Food & Dining"),
    /** Transportation expenses */
    TRANSPORT("Transportation"),
    /** Entertainment expenses */
    ENTERTAINMENT("Entertainment"),
    /** Health & Medical expenses */
    HEALTH("Health & Medical"),
    /** Education expenses */
    EDUCATION("Education"),
    /** Home & Utilities expenses */
    HOME("Home & Utilities"),
    /** Miscellaneous expenses */
    MISC("Miscellaneous");

    /** The display name of the expense category */
    private final String displayName;

    /**
     * Constructs an ExpenseCategory with the specified display name.
     *
     * @param displayName the user-friendly name of the category
     */
    ExpenseCategory(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Parses a string to find the corresponding ExpenseCategory.
     * The method first tries to match the enum name, then the display name (case-insensitive).
     *
     * @param categoryStr the string to parse
     * @return the matching ExpenseCategory
     * @throws IllegalArgumentException if no matching category is found
     */
    public ExpenseCategory parseCategory(String categoryStr) {
        String normalized = categoryStr.toLowerCase().trim();

        try {
            return ExpenseCategory.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            for (ExpenseCategory category : values()) {
                if (category.displayName.equalsIgnoreCase(normalized)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("Invalid category: " + categoryStr);
        }
    }

    /**
     * Returns the display name of the expense category.
     *
     * @return the display name
     */
    @Override
    public String toString() {
        return displayName;
    }
}