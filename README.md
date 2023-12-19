# Stock-Portfolio-Management-System

CS611 Final project

### Student Details

Name/Student ID/BU Email:

#### Mary Choe, U97609234, marychoe@bu.edu

#### Xiaoyang Guo, U57266095, xzg5101@bu.edu

#### Sonja Poe, , svpoe@bu.edu

#### Hrishav Varma, U57996211, hri@bu.edu

To run:

    javac -cp .:lib/sqlite-jdbc-3.44.1.0.jar:lib/slf4j-api-2.0.9.jar:lib/slf4j-simple-2.0.9.jar *.java
    java -cp .:lib/sqlite-jdbc-3.44.1.0.jar:lib/slf4j-api-2.0.9.jar:lib/slf4j-simple-2.0.9.jar Main

VS code configurations

In the settings.json file add:

    "java.project.referencedLibraries": [
        "lib/sqlite-jdbc-3.44.1.0.jar",
        "lib/slf4j-api-2.0.9.jar",
        "lib/slf4j-simple-2.0.9.jar"
    ]

### Files

### General

    - Account.java: Contains general user information including username, password, and appropriate methods to adjust and retrieve these values

    - PortfolioManageSystem: This class maintains the states of the system, including the current customer (manager) and the reference to the stock market.

#### Graphic User Interfaces

    - LoginGUI.java: This class creates a general login GUI. Users can log in or create a new user account. System identifies manager/customer based on username and opens the appropriate GUI to follow

    - CustomerMainGUI.java: This class provides a main page for the customer, and opens up upon the system identifying an existing customer login.

    - CustomerStockGUI.java: This class allows users to proceed with stock purchase and sale. After the action, users will be notified with remaining balance in their account.

    - CustomerBalanceGUI.java: This class allows the customer to deposit and withdraw money from their account.

    - CustomerNotificationGUI.java: This class allows customers to check if they have received any messages from the managers.

    - CustomerDerivativeAccountGUI.java: This class allows customers to create a derivative trading account. This is only accessible to those who have more than $10000 in realized trading gains.

#### Customer

    - Customer.java: This class manages customer actions, such as the backend functions for deposit, withdraw, purchase stock, and sell stock.  It also maintains the state of the customer in memory, including the user's username, stocks, realized/unrealized profit, etc.

#### Manager

    - ManagerMainGUI: Class acts as the main screen for manager GUI housing all the buttons which allows the manager to perform all the functions.

    - ManagerEditStocksGUI: This class defines the GUI and the functional logic for the manager to be able to manipulate the stocks in the stock market.

    - ManagerViewCustomerGUI: This class defines the GUI and functional logic for the manager to be able to view all the customers and be able to approve newly registered users. It also allows the manager to get reports on the customers about their unrealized and realized profits.

#### Main

    - Main.java: entry point for users.

### Database

    - Database.java: This class acts as an api of functions we can call to access the database to read, write, update, and delete data.

    - DatabaseConnection.java: This class is for connecting the java code to the sqlite database.

### Notes

## Design Patterns:

Singleton Pattern: The singleton pattern is used in our project in many classes. All the UI classes are singleton classes to avoid displaying stale data. The Portfolio class is also a singleton class. This ensures that there will not be corrupted data in memory.

Factory Pattern: Used to create stocks with the createStock() method.

Observer Pattern: We use a pull based observer pattern that allows the system to be aware that a Customer has made total profit beyond the threshold. Customer class has reference to the Portfolio system, and on customer trading, it will call the corresponding method through the reference. The system will then check if the customer’s cumulative realized profit is greater than the threshold.
