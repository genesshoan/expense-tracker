# Expense Tracker CLI

A simple and efficient command-line expense tracker application built with Java. Track your daily expenses, categorize them, and get insightful summaries of your spending habits.

> **Project Source**: This project is based on the [Expense Tracker](https://roadmap.sh/projects/expense-tracker) challenge from [roadmap.sh](https://roadmap.sh).

## Features

- **Add Expenses**: Record expenses with description, amount, and category
- **List Expenses**: View all expenses with flexible filtering options
- **Update Expenses**: Modify expense descriptions
- **Delete Expenses**: Remove unwanted expense records
- **Summary Reports**: Get total spending summaries with filters
- **Persistent Storage**: All data is saved to JSON file automatically
- **Flexible Filtering**: Filter by category, amount, date, and more
- **Sorting Options**: Sort expenses by amount or date

## Installation

### Prerequisites
- Java 11 or higher
- Gradle (for building from source)

### Build from Source
```bash
git clone <repository-url>
cd expense-tracker
gradle clean build
gradle installDist
```

The executable will be available at:
- **Windows**: `build/install/expense-tracker/bin/expense-tracker.bat`
- **Linux/Mac**: `build/install/expense-tracker/bin/expense-tracker`

## Usage

### Adding Expenses
```bash
# Add a food expense
./expense-tracker add -d "Lunch at restaurant" -a 15.50 -c FOOD

# Add transportation expense
./expense-tracker add -d "Bus ticket" -a 2.75 -c TRANSPORT

# Add entertainment expense
./expense-tracker add -d "Movie ticket" -a 12.99 -c ENTERTAINMENT
```

### Listing Expenses
```bash
# List all expenses
./expense-tracker list

# Filter by category
./expense-tracker list -c FOOD

# Filter by multiple categories
./expense-tracker list -c FOOD -c TRANSPORT

# Filter by minimum amount
./expense-tracker list -m 10.0

# Filter by year
./expense-tracker list -y 2024

# Filter by specific month
./expense-tracker list -ym 2024-01

# Sort by amount (ascending)
./expense-tracker list -sa

# Combine filters
./expense-tracker list -c FOOD -y 2024 -m 5.0
```

### Updating Expenses
```bash
# Update expense description by ID
./expense-tracker update -i 1 -d "Expensive lunch"
```

### Deleting Expenses
```bash
# Delete expense by ID
./expense-tracker delete -i 1
```

### Summary Reports
```bash
# Total of all expenses
./expense-tracker summary

# Summary by category
./expense-tracker summary -c FOOD

# Summary by year
./expense-tracker summary -y 2024

# Summary with multiple filters
./expense-tracker summary -c FOOD -c TRANSPORT -y 2024
```

### Help
```bash
# General help
./expense-tracker --help

# Command-specific help
./expense-tracker add --help
./expense-tracker list --help
```

## Expense Categories

The following categories are supported:

- **FOOD** - Food & Dining
- **TRANSPORT** - Transportation
- **ENTERTAINMENT** - Entertainment
- **HEALTH** - Health & Medical
- **EDUCATION** - Education
- **HOME** - Home & Utilities
- **MISC** - Miscellaneous

## Examples

### Daily Workflow
```bash
# Morning coffee
./expense-tracker add -d "Coffee" -a 4.50 -c FOOD

# Lunch
./expense-tracker add -d "Lunch" -a 12.00 -c FOOD

# Evening bus ride
./expense-tracker add -d "Bus home" -a 2.25 -c TRANSPORT

# Check today's spending
./expense-tracker list -ym $(date +%Y-%m)

# Get monthly food expenses summary
./expense-tracker summary -c FOOD -ym 2024-01
```

### Monthly Review
```bash
# See all expenses for January 2024
./expense-tracker list -ym 2024-01

# Get spending by category
./expense-tracker summary -c FOOD -ym 2024-01
./expense-tracker summary -c TRANSPORT -ym 2024-01

# Find expensive items (over $20)
./expense-tracker list -m 20.0 -ym 2024-01
```

## Data Storage

- Expenses are automatically saved to `expense_tracker.json` in the current directory
- Data persists between application runs
- The JSON file is human-readable and can be backed up easily

## Architecture

The application follows clean architecture principles:

- **Model Layer**: `Expense`, `ExpenseCategory`, `ExpenseQuery`
- **Service Layer**: `ExpenseTracker` (business logic)
- **Command Layer**: Command pattern implementation
- **CLI Layer**: PicoCLI-based user interface

Key design patterns used:
- **Command Pattern**: For encapsulating operations
- **Builder Pattern**: For flexible query building
- **Repository Pattern**: For data persistence abstraction

## Development

### Running Tests
```bash
gradle test
```

### Running in Development
```bash
gradle run --args="add -d 'Test expense' -a 10.0 -c FOOD"
gradle run --args="list"
```

### Project Structure
```
src/
├── main/java/
│   ├── dev/shoangenes/expensetracker/
│   │   ├── command/          # Command pattern implementations
│   │   ├── exception/        # Custom exceptions
│   │   ├── model/           # Data models and queries
│   │   └── service/         # Business logic
│   └── dev/shoangenes/expensetrackercli/
│       ├── commands/        # CLI command handlers
│       └── ExpenseTrackerCli.java
└── test/java/               # Unit tests
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Future Enhancements

- Export to CSV/PDF
- Budget tracking and alerts
- Multiple currency support
- Recurring expense templates
- Web interface
- Database integration