Design Document:

## Design Patterns:

Singleton Pattern: The singleton pattern is used in our project in many classes. All the UI classes are singleton classes to avoid displaying stale data. The Portfolio class is also a singleton class. This ensures that there will not be corrupted data in memory.

Factory Pattern: Used to create stocks with the createStock() method. StockFactory Class handles generating the new stocks and returns for the PortfolioManageSystem Class to set its Stocks in the StockMarket object.

Observer Pattern: We use a pull based observer pattern that allows the system to be aware that a Customer has made total profit beyond the threshold. Customer class has reference to the Portfolio system, and on customer trading, it will call the corresponding method through the reference. The system will then check if the customer’s cumulative realized profit is greater than the threshold. 

### Design Decisions

We made a PortfolioManageSystem Class that is responsible for managing the whole system. We divided the functionalities into modular classes that maximizes the parallelism of development of the application. This included division in the GUI frames and the backend logic where each modular piece was connected after being built independently and does not have dependencies built into them.

We decided to use SimpleDB and the JDBC connectors for them to store and maintain the state of the system and data about the users, stocks, transactions, etc. This ensures scalability and helps serve the system.


### Features:

Scalability: Our portfolio management system is able to support many customers as well as take on new customers. The database we created is more scalable than using text files as it allows us to support more data as well as process and manage the data efficiently.

Extendability: We added a feature so that the manager, on top of being able to see all of the customers’ realized and unrealized profits, can also see all of the customers’ transactions. The manager can see what time a customer bought or sold a certain stock, at what price, and how many shares.

Maintainability: The tester class acts like unit tests that allow us to make sure the functionality still persists after changes in code. The frontend and backend are separated such that logic change in backend does not impact the apis that UI classes invoke, and the display change does not affect the backend functionalities.

Reusability: The PortfolioManageSystem class is designed to exist on its own and can be used in any system requiring a stock and portfolio management system by utilizing the functionality implemented in it.

User Experience: A dedicated GUI layer and design that focuses on having a consistent and robust user experience. The operations on different pages use similar logics to reduce the effort and the learning curve.

Robustness: The system class, customer class and other classes does various levels of checking on the data entered by users and ensure the integrity of the data in the database.
