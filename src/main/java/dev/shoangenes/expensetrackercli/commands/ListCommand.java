package dev.shoangenes.expensetrackercli.commands;

import dev.shoangenes.expensetracker.command.ListExpensesCommand;
import dev.shoangenes.expensetracker.model.Expense;
import dev.shoangenes.expensetracker.model.ExpenseCategory;
import dev.shoangenes.expensetracker.model.ExpenseQuery;
import dev.shoangenes.expensetrackercli.ExpenseTrackerCli;
import picocli.CommandLine;
import dev.shoangenes.expensetracker.command.Command;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@CommandLine.Command(name = "list", description = "List expenses with optional filters")
public class ListCommand implements Runnable {
    @CommandLine.ParentCommand
    private ExpenseTrackerCli parent;

    @CommandLine.Option(names = {"-c", "--category"}, description = "Filter by category")
    private List<ExpenseCategory> categoryList;

    @CommandLine.Option(names = {"-m", "--min"}, description = "Minimum amount")
    private Double minAmount;

    @CommandLine.Option(names = {"-ym", "yearMonth"}, description = "Filter by year month")
    YearMonth yearMonth;

    @CommandLine.Option(names = {"-y", "--year"}, description = "Filter by year")
    Year year;

    @CommandLine.Option(names = {"-sa", "-sortByAmount"}, description = "Sort by amount, ascending or descending")
    Boolean ascending;

    /**
     * Executes the command to list expenses based on the provided filters.
     * If no expenses are found, it prints a message indicating that.
     * Otherwise, it prints the expenses in a formatted table.
     */
    @Override
    public void run() {
        ExpenseQuery query = ExpenseQuery.makeQuery(categoryList, minAmount, yearMonth, year, ascending);
        Command<List<Expense>> command = new ListExpensesCommand(parent.getExpenseTracker(), query);
        List<Expense> result = command.execute();;
        if (result.isEmpty()) {
            System.out.println("No expenses found");
        } else {
            System.out.printf("%-3s %-10s %-12s %s%n", "ID", "Date", "Description", "Amount");
            System.out.println("--------------------------------");
            result.forEach(System.out::println);
        }
    }
}
