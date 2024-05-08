package com.pluralsight;


import java.io.*;
import java.nio.Buffer;
import java.security.KeyStore;
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
        try {
            BufferedWriter theFile = new BufferedWriter(new FileWriter(FILE_NAME, true));
            theFile.write(deposit.getDate() + "|" + deposit.getTime() + "|" + deposit.getDescription() + "|" + deposit.getAmount() + "\n");
            theFile.close();
        } catch (Exception e) {
            System.out.println("The deposit is added successfully");


        }
    }

    private static void addPayment(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Payment` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
        System.out.println("Enter the date " + DATE_FORMAT);
        String dateEntered = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateEntered, DATE_FORMATTER);
        System.out.println("Enter the time " + TIME_FORMAT);
        String timeEntered = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeEntered, TIME_FORMATTER);
        System.out.println("Enter Vendor ");
        String vendorEntered = scanner.nextLine();
        System.out.println("Enter type ");
        String typeEntered = scanner.nextLine();
        System.out.println("Enter Desired amount ");
        double amountEntered = Double.parseDouble(scanner.nextLine());
        if (amountEntered > 0) {
            amountEntered = amountEntered * -1;

            Transaction newPayment = new Transaction(date, time, typeEntered, vendorEntered, amountEntered);
            transactions.add(newPayment);
            try {
                BufferedWriter toFile = new BufferedWriter(new FileWriter(FILE_NAME, true));
                toFile.write(newPayment.getDate() + "|" + newPayment.getTime() + "|" + newPayment.getDescription() + "|" + newPayment.getVendor() + "|" + newPayment.getAmount() + "\n");
                toFile.close();


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


            String input = scanner.nextLine().trim();


        }
    }
}


