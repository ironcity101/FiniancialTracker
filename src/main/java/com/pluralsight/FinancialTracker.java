package com.pluralsight;


import java.io.*;
import java.nio.Buffer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;


public class FinancialTracker {
    Scanner scanner = new Scanner(System.in);

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>,<time>,<vendor>,<type>,<amount>
        // For example: 2023-04-29,13:45:00,Amazon,PAYMENT,29.99
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                String datadate = data[0].trim();
                LocalDate date = LocalDate.parse(datadate, DATE_FORMATTER);
                String datatime = data[1].trim();
                LocalTime time = LocalTime.parse(datatime, TIME_FORMATTER);
                String description = data[2].trim();
                String VendorList = data[3].trim();
                double amount = Double.parseDouble(data[4].trim());

                Transaction transaction = new Transaction(date, time, description, VendorList, amount);
                transactions.add(transaction);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    private static void addDeposit(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.

        System.out.println("Enter the date : " + DATE_FORMAT);
        String userInputDate = scanner.nextLine();
        LocalDate dateGiven = LocalDate.parse(userInputDate, DATE_FORMATTER);
        System.out.println("Enter the time:" + TIME_FORMAT);
        String userInputTime = scanner.nextLine();
        LocalTime timeGiven = LocalTime.parse(userInputTime, TIME_FORMATTER);
        System.out.println("Enter the vendor");
        String vendorGiven = scanner.nextLine();
        System.out.println("Enter Type?");
        String type = scanner.nextLine();
        System.out.println("Enter amount of deposit");
        double amountGiven = Double.parseDouble(scanner.nextLine());
        if (amountGiven <= 0) {
            System.out.println("Invalid amount please try again");
            scanner.nextLine();
        }
        Transaction deposit = new Transaction(dateGiven, timeGiven, vendorGiven, type, amountGiven);
        transactions.add(deposit);
        System.out.println("The deposit is added successfully");


    }


    private static void addPayment(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Payment` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
        System.out.println("Enter the date" + DATE_FORMAT);
        String dateEntered = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateEntered, DATE_FORMATTER);
        System.out.println("Enter the time" + TIME_FORMAT);
        String timeEntered = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeEntered, TIME_FORMATTER);
        System.out.println("Enter Vendor");
        String vendorEntered = scanner.nextLine();
        System.out.println("Enter type");
        String typeEntered = scanner.nextLine();
        System.out.println("Enter Desired amount");
        double amountEntered = Double.parseDouble(scanner.nextLine());
        if (amountEntered > 0) {
            amountEntered = amountEntered * -1;

            Transaction newPayment = new Transaction(date, time, typeEntered, vendorEntered, amountEntered);
            transactions.add(newPayment);
            try {
                BufferedWriter toFile = new BufferedWriter(new FileWriter(FILE_NAME));


            } catch (Exception e) {
                System.out.println("Can not write to that file " + "get message" + e.getMessage());

            }

        }
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
        for (Transaction deposit : transactions) {
            double amount = deposit.getAmount();
            if (amount > 0)
                System.out.println(deposit);
        }
    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
        for (Transaction payments : transactions) {
            double amount = payments.getAmount();
            if (amount < 0)
                System.out.println(payments);
        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    // Generate a report for all transactions within the current month,
                    // including the date, vendor, and amount for each transaction.
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, vendor, and amount for each transaction.
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, vendor, and amount for each transaction.

                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, vendor, and amount for each transaction.
                case "5":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.
    }

    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    }
}