package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class AccountingLedgerApp {
    private static final String fileLocation = "src/main/resources/transactions.csv";

    public static void main(String[] args) {
        ArrayList<Transactions> transactions = loadTransactions();
        Scanner Keyboard = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Prodigy Financials, please select an option: \n" +
                    "D- Add Deposit \n" +
                    "P- Make Payment \n" +
                    "L- Ledger \n" +
                    "X- Exit");
            String Selection = Keyboard.nextLine().trim().toUpperCase();
            switch (Selection) {
                case "D":
                    addTransaction(transactions, Keyboard, "deposit");
                    saveTransactions(transactions);
                    break;
                case "P":
                    addTransaction(transactions, Keyboard, "payment");
                    saveTransactions(transactions);
                    break;
                case "L":
                    displayLedger(transactions, Keyboard);
                    break;
                case "X":
                    saveTransactions(transactions);
                    System.out.println("Thank you for using Prodigy Financials. We hope to see you again soon!");
                    System.exit(0);
                default:
                    System.out.println("Error, invalid option entered. Please try again and choose from the options provided.");
            }
        }
    }

    private static ArrayList<Transactions> loadTransactions() {
        ArrayList<Transactions> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split("\\|");
                try {
                    Transactions transaction = new Transactions(
                            parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
                    transactions.add(transaction);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing amount: " + parts[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading the transactions file.");
            e.printStackTrace();
        }
        return transactions;
    }

    private static void saveTransactions(ArrayList<Transactions> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv"))) {
            writer.write("date|time|description|vendor|amount");
            writer.newLine();
            for (Transactions transaction : transactions) {
                String transactionLine = String.join("|",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        String.valueOf(transaction.getAmount())
                );
                writer.write(transactionLine);
                writer.newLine();
            }
            System.out.println("System was updated successfully.");
        } catch (IOException e) {
            System.out.println("Error in updating the transactions.");
            e.printStackTrace();
        }
    }

    private static void addTransaction(ArrayList<Transactions> transactions, Scanner Keyboard, String type) {
        System.out.println("Please provide the item description: ");
        String description = Keyboard.nextLine().trim();
        System.out.print("Enter the vendor: ");
        String vendor = Keyboard.nextLine().trim();
        System.out.print("Enter the amount: ");
        double amount = Keyboard.nextDouble();
        Keyboard.nextLine();

        if (type.equalsIgnoreCase("payment")) {
            amount = -Math.abs(amount);
        }

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss"));
        String[] dateTimeParts = dateTime.split("\\|");

        Transactions transaction = new Transactions(dateTimeParts[0], dateTimeParts[1], description, vendor, amount);
        transactions.add(transaction);
        System.out.println("Transaction recorded successfully.");
        System.out.println("Is there anything else you'd like to complete today? (yes/no)");
        String anotherTask = Keyboard.nextLine().trim().toLowerCase();

        while (!anotherTask.equals("yes") && !anotherTask.equals("no")) {
            System.out.println("Error: Please choose 'yes' or 'no'.");
            anotherTask = Keyboard.nextLine().trim().toLowerCase();
        }

        if (anotherTask.equals("yes")) {
            return;
        } else {
            System.out.println("Would you like to close the program? (yes/no)");
            String closeProgram = Keyboard.nextLine().trim().toLowerCase();

            while (!closeProgram.equals("yes") && !closeProgram.equals("no")) {
                System.out.println("Error: Please choose 'yes' or 'no'.");
                closeProgram = Keyboard.nextLine().trim().toLowerCase();
            }

            if (closeProgram.equals("yes")) {
                System.out.println("Thank you for using Prodigy Financials. We hope to see you again soon. Goodbye!");
                System.exit(0);
            } else {
                System.out.println("Returning to the main menu.");
            }
        }
    }

    private static void displayLedger(ArrayList<Transactions> transactions, Scanner Keyboard) {
        System.out.println("Select an option: \n" +
                "A - All transactions\n" +
                "D - Deposits only \n" +
                "P - Payments only");
        String transactionType = Keyboard.nextLine().trim().toUpperCase();
        switch (transactionType) {
            case "A":
                System.out.println("Displaying all transactions:");
                for (Transactions transaction : transactions) {
                    System.out.println(transaction);
                }
                break;
        }

        System.out.println("Is there anything else Prodigy Financials can help you with today?");
        String response = Keyboard.nextLine().trim().toLowerCase();

        while (!response.equals("yes") && !response.equals("no")) {
            System.out.println("Error: Please choose 'yes' or 'no'.");
            response = Keyboard.nextLine().trim().toLowerCase();
        }

        if (response.equals("yes")) {
            System.out.println("What else can we assist you with?: \n" +
                    "D- Add Deposit \n" +
                    "P- Make Payment \n" +
                    "L- Ledger \n" +
                    "X- Exit");
            String selection = Keyboard.nextLine().trim().toUpperCase();
            switch (selection) {
                case "D":
                    addTransaction(transactions, Keyboard, "deposit");
                    break;
                case "P":
                    addTransaction(transactions, Keyboard, "payment");
                    break;
                case "L":
                    displayLedger(transactions, Keyboard);
                    break;
                case "X":
                    saveTransactions(transactions);
                    System.out.println("Thank you for using Prodigy Financials. We hope to see you again soon!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error, invalid option entered. Please try again and choose from the options provided.");
                    break;
            }
        } else if (response.equals("no")) {
            System.out.println("Would you like to close the program? (yes/no)");
            String exitSelection = Keyboard.nextLine().trim().toLowerCase();
            while (!exitSelection.equals("yes") && !exitSelection.equals("no")) {
                System.out.println("Error: Please choose 'yes' or 'no'.");
                exitSelection = Keyboard.nextLine().trim().toLowerCase();
            }
            if (exitSelection.equals("yes")) {
                System.out.println("Thank you for using Prodigy Financials. We hope to see you again soon. Goodbye!");
                System.exit(0);
            } else {
                System.out.println("Returning to the main menu.");

            }
        }
    }
}
