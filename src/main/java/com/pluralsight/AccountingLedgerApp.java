package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class AccountingLedgerApp {
    private static final String fileLocation = "C:\\Users\\Fright\\Desktop\\Pluralsight\\java-developement\\LearnToCode_Capstones\\AccountingLedgerApplication\\src\\main\\resources\\transactions.csv";

    /* below is the main method
    this method displays options for the user (D for deposit,
    P for payment, L for viewing the ledger, and X for exit).
     */


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

            /*the switch statement below was used to handle the user input
            and to call to the appropriate method
             */

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
  /* below is going to read the transaction.csv file and loads each line
  into a transactions object
  utilized try and catch if errors were encountered it would catch them
  data parsing on each line | to separate transaction details for the array list
  added error handling skipping the header line to help handle parsing errors such as invalid number format etc
  */
    private static ArrayList<Transactions> loadTransactions() {
        ArrayList<Transactions> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
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


    /* below is how we are able to save our user input
    aka the transactions that the user types
    it basically writes the current list of transactions inputted back into the csv file
     */

    private static void saveTransactions(ArrayList<Transactions> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation))) {
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
            System.out.println("Error in updating the transactions." + e.getMessage());
            e.printStackTrace();
        }
    }
    /* below is how we prompt the user to enter the details for a new transaction
    such as description, vendor, etc.
    */
    private static void addTransaction(ArrayList<Transactions> transactions, Scanner Keyboard, String type) {
        System.out.println("Please provide the item description: ");
        String description = Keyboard.nextLine().trim();
        System.out.print("Enter the vendor: ");
        String vendor = Keyboard.nextLine().trim();
        System.out.print("Enter the amount: ");
        double amount = Keyboard.nextDouble();
        Keyboard.nextLine();

        /* if the user chooses payment option the -math.abs will kick in and turn the number inputted into
        a negative showing that it is a payment being taken from the ledger compared to a deposit which would be a positive
        */


        if (type.equalsIgnoreCase("payment")) {
            amount = -Math.abs(amount);
        }

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss"));
        String[] dateTimeParts = dateTime.split("\\|");

        Transactions transaction = new Transactions(dateTimeParts[0], dateTimeParts[1], description, vendor, amount);
        transactions.add(transaction);
        saveTransactions(transactions);
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
    /* below is going to display transactions so deposits or payments
       it also asks the user for what transaction they want to view
       and based on their input the switch will display A,D, or P
    */
    private static void displayLedger(ArrayList<Transactions> transactions, Scanner Keyboard) {
        System.out.println("Select an option: \n" +
                "A - All transactions\n" +
                "D - Deposits only \n" +
                "P - Payments only \n" +
                "H - Exit Ledger and return back to the home screen");
        String transactionType = Keyboard.nextLine().trim().toUpperCase();
        switch (transactionType) {
            case "A": System.out.println("Displaying all transactions:");
                for (Transactions transaction : transactions) {
                    System.out.println(transaction);
                } break;
            case "D":
                System.out.println("Displaying only the deposits made: ");
                for (Transactions transaction : transactions) {
                    if (transaction.getAmount() > 0) {
                        System.out.println(transaction); }
                } break;
            case "P":
                System.out.println("Displaying only the payments made: ");
                for (Transactions transaction : transactions) {
                    if (transaction.getAmount() < 0) {
                        System.out.println(transaction);
                    }
                } break;

            case "H":
                System.out.println("Returning to the home screen.");
                return;
                default:
                System.out.println("Error, invalid option entered. Please try again and choose from the options provided.");
                displayLedger(transactions, Keyboard);
                break;
                    }
        /* below it will prompt the user if they need to do more with their ledger
        based on their response yes or no they have to type one or the other
        the ! was used so the condition checks if their response is anything other than yes or no
        added && to ensure both conditions must be true
        loop will keep asking for valid input if yes or no aren't entered
        */
        System.out.println("Is there anything else Prodigy Financials can help you with today? (yes/no)");
        String response = Keyboard.nextLine().trim().toLowerCase();

        while (!response.equals("yes") && !response.equals("no")) {
            System.out.println("Error: Please type 'yes' or 'no'.");
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
