package dev.shoangenes.expensetrackercli.commands;

import dev.shoangenes.expensetracker.command.Command;
import dev.shoangenes.expensetracker.command.SummaryExpensesCommand;
import dev.shoangenes.expensetracker.model.ExpenseCategory;
import dev.shoangenes.expensetracker.model.ExpenseQuery;
import dev.shoangenes.expensetrackercli.ExpenseTrackerCli;
import picocli.CommandLine;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public class SummaryCommand implements Runnable {
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


    @Override
    public void run() {
        ExpenseQuery query = ExpenseQuery.makeQuery(categoryList, minAmount, yearMonth, year, null);
        Command<Double> command = new SummaryExpensesCommand(parent.getExpenseTracker(), query);
        Double result = command.execute();
        System.out.println("Successfully executed summary expense command: " + result);
    }

}
