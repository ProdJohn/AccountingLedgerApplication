

---

# AccountingLedgerApplication

**AccountingLedgerApplication** is a console-based Java application to track financial transactions. This application allows users to add deposits, make payments, view the transaction ledger, and more. It's my first Java project with one month of learning. It includes file handling, user input, and data management.

## Features

- **Add Deposits**: Record deposits with a description, vendor, and amount.
- **Make Payments**: Record payments with details.
- **View Ledger**: Display all transactions, only deposits, or only payments.
- **Exit Option**: Gracefully exit the program and save all transactions.

## Screenshots

### 1. Example of a Deposit Entry
![deposit example](https://github.com/user-attachments/assets/65c43e5f-ac4d-4579-b401-1036daa736f9)

In this example, the user selects `D` for adding a deposit, enters the description, vendor, and amount, and the transaction is recorded successfully.

### 2. Example of a Payment Entry
![Payment example](https://github.com/user-attachments/assets/69371959-c97c-49ec-9098-be295f19ca7c)

This screenshot shows a user making a payment. After selecting `P`, they enter the payment details, and the transaction is recorded in the ledger.

### 3. Viewing the Ledger
![transactions example](https://github.com/user-attachments/assets/2b966b95-6bed-47ec-bd45-9de077220665)

Users can view the ledger by selecting `L` from the main menu, then choosing:
- `A` to view all transactions.
- `D` to view only deposits.
- `P` to view only payments.
- `H` to exit the ledger and return to the main menu.

### 4. Returning to Home Screen from the Ledger
![ledger return home](https://github.com/user-attachments/assets/3c9badfb-d29f-4291-91d0-3dc0db4ffa7c)

When the user selects `H` in the ledger view, they are taken back to the main menu, allowing them to perform other actions or exit the application.

### 5. Notable Code
![interesting code](https://github.com/user-attachments/assets/9d99b003-f922-4a1b-9d5d-d2a88904202f)

When the user selects to make a payment (type.equalsIgnoreCase("payment")), it ensures the amount is stored as a negative value using amount = -Math.abs(amount);. This reflects the deduction of money from the account. It keeps deposits positive and payments negative, which is a practical way to differentiate between the two in the ledger.


