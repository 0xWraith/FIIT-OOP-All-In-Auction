# Dmytro Dzhuha

## All In Auction

All in Auction is a place where you can safely and quickly buy or sell any item.
In my project, I've implemented English type of auction. First, the user must register/login. A user can be a seller or a buyer. There will also be administrators who control the lots, the auction process and users. When creating a lot, the seller can completely customize everything. To avoid auction disruption and fraud, users will need to top up their balance using a form of payment and if they have enough money, they will be admitted to the auction. There is also a notification system to inform users about anything: selling a slot, buying a slot, etc.

## Table of Contents

* [Project documentation](Documentation/000_project_documentation.md)
  * [JavaDoc documentation](Documentation/JavaDoc)
* [UML diagrams](Documentation/001_uml_diagrams.md)
* [Versions](Documentation/002_versions.md)
* [Technical details](Documentation/003_tech_details.md)
* [Simulation and demonstration](Documentation/004_simulation_and_demonstration.md)
  * [Video demonstration](Documentation/004_simulation_and_demonstration.md)

## Fulfillment of criteria
- Encapsulation
- Patterns
 - Adapter
 - Singletone 
- Own exceptions and fix of it
 - DataBaseException, UserException
- MultiThreading for creating bots
- RTTI
- Lambda
- Nested class and interface
- Interface default method
- Seriallization

## Certain implementations

* Polymorphism
* Inheritance
* Interface
 * DataBase.java

### Main criteria

* Polymorphism
  * Seller.java #11
  * Bidder.java #11
  * Admin.java #11
* Inheritance
  * Seller/Bidder/Admin.java #6
* Interface
  * DataBase.java

### Secondary criteria

* Encapsulation
  * Every class 
* Patterns
  * Adapter | ItemAdapter.java | NotificationsAdapater.java
  * Singletone | Database.java | SceneController.java
* Own exceptions and fix of it
  * DataBaseException, UserException 
  * RegistrationController.java #170
* MultiThreading
  * Main.java #79-81 
* RTTI
  * LoginController.java #111 
* Lambda
  * Main.java #79
  * User.java #143
* Nested class and interface
  * User.java # 26 
* Interface default method
  * UserInterface.java #12
* Seriallization
  * UtilController.java #15 #27
