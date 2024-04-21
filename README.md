# MarketPlace-Calendar

## How to Compile and Run our Project
1. Download zip file of all of our code
2. Open in preferred IDE
3. Navigate to Main.java file
4. Type the following command into your terminal or run via IDE tools
```
java Main.java
```
5. Enter prompted user input and enjoy our MarketPlace project!

## Submissions
Brightspace (Report) - Submitted by Ashish Chenna

Vocareum Code - Submitted by Ian Lam

## Class Descriptions and Functionality
### Account
This class is our overarching Account class that's parent to Seller and Customer. Account has two fields, email and password, and two constructors, one with no parameters and one with email and password as parameters. This class has one getter, which is getEmail, and no getters and setters for password since this should remain private to the object. 

Methods in this class are **emailInvalid(), accountExists(), and verifyAccount()**

**emailInvalid(String email)** - checks to see whether the email passed in the parameter is a valid address, meaning whether or not it has an "@" and "." in the string. It returns a boolean value once it determines whether the address is valid or not.

**accountExists(String email)** - determines whether or not the account has already been created by going through accounts.csv and checking whether the email is listed in the file. This method return a boolean value that signifies whether the account exists or not. 

**verifyAccount(String email, String password)** - this method verifies that the email and password associated with it is correct. This is done so by parsing through the accounts.csv file and checking the respective line. This method returns a boolean value for whether the email and password are correct. 

### Seller
Seller is a version of Account, or more technically, is a child class of Account (extends Account) and inherits the email and password fields from Account. Seller also has a field called stores which is an ArrayList of Store objects. Seller has two constructors, one with no parameters that instantiated empty assignments for email, password and stores, and one with parameters that assigns them to their respective fields. This class contains no getters and setters. 

Methods in this class are **createStore(), createCalendar(), importCalendar(), approve(), editCalendar(), verify(), makeAppointmentWindow(), deleteCalendar(), viewApprovedAppointments(), viewCustomerApprovedAppointments(), viewPopularAppointmentWindows(), and sortDashboard()**

**createStore(String storeName)** - this method created a Store object given the parameter of storeName. It adds the new object to the Seller field of stores and writes the new store to stores.csv. This method also returns a boolean value that signifies whether or not the store was successfully created. If it's not successfully created, that means that the store name was already taken, which would return false. 

**createCalendar(Scanner scan, String store)** - this method creates a new calendar given the store the calendar should be under. We prompt the user to enter the calendar name and check to see whether that calendar name has already been used by parsing through calendars.csv. Then, we prompt the use to add any appointment windows they would like and call makeAppointmentWindow() to create them. 

**importCalendar(String storename, String filename)** - this method takes a file the seller wants to import as a calendar and parse through it to create a new Calendar object under the specified store. We assume that the imported file has the following format:
```
calendarTitle
calendarDescription
appointmentWindow1: title,starttime,endtime,maxAttendeed,currentBookings
appoinmentWindow2: ...
...
```
This method also throws an exception if the file name passed in the parameter is not valid or if an unknown exception occurs. 

**approve(Scanner scan)** - this method goes through the requests that the Seller has not approved yet and prompts the user to choose for a specific request to approve. Once the seller chooses a request to approve, this would update the currentbookings value of an appointment in the windows.csv and change the appointment in appointments.csv to an approved state by making isApproved true, and isRequest false.

**editCalendar(String sName, String cName, Scanner sc)** - allows the seller to change the calendar title, description, or window timeframe. The user is prompted to choose what they want to do and the changes are updated to calendars.csv and appointments.csv depending on the action. *Editing the calendar window timeframes removes all of the related appointments given the nature of our start and end times. 

**verify(int startTime, int endTime, String storeName, String calendarName)** - this method verifies the time frame of the window the seller wants to create and determines whether or not it conflicts with any other start or end times of the calendar in its respective store. This is done by parsing through windows.csv and checking the time frames of the given store and calendar. 

**makeAppointmentWindow(Scanner scan, String storeName, String calendarName)** - this method creates a new appointment window by prompting the user to enter the title, description, start time, and end time of the window. It verifies the time frame by calling the verify() method and reprompts the user if its invalid, or prompts the user for more information regarding the appointment window if its valid. It then writes the new appointment window to windows.csv.

**deleteCalendar(String storeName, String calendarName)** - this method deletes an existing calendar by going through calendars.csv, windows.csv, and appointments.csv and deletes rows in which the matching calendar occurs.

**viewApprovedAppointments()** - lists all of the approved appointments the seller has by store. This is done by parsing through appointments.csv and creating an Appointment object for each relevant approved appointment and printing out it's toString(). 

**viewCustomerApprovedAppointments()** - lists out the number of approved appointments for each customer by parsing through appointments.csv to find all unique customers that have made appointments with the seller's stores and counting the approved appointments by customer. 

**viewPopularAppointmentWindows()** - lists out the most popular appointment windows for each store by parsing through appointments.csv and getting all of the appointment windows which have appointments either requested or approved for that timeframe. Then, parsing through appointment.csv again, all of the bookings for those windows are added up and compared to determine the highest, or most popular booking and appointment window, for the store. 

**sortDashboard()** - sorts the above two lists alphabetically or in ascending/descending order. *You can only sort approved appointments by customer alphabetically. *You can only sort most popular appointment windows in ascending or descending order

### Customer 
Customer is a version of Account, or more technically, is a child class of Account (extends Account) and inherits the email and password fields from Account. Customer also has a field called appointments which is an ArrayList of Appointment objects respective that the customer has made. Customer has two constructors, one with no parameters that instantiated empty assignments for email and password, and one with parameters that assigns them to email and password to their respective values. This class contains no getters and setters. 

Methods in this class are **viewApproved(), request(), view(), export(), cancel(), popularStores(), and popularAppointment()**

**viewApproved(Scanner scan)** - prints out all of the approved appointments the customer made and prompts the user whether they want to export the appointments into a csv file or not. This is done by parsing through appointments.csv and getting all of the appointments via the customer's email. 

**request(Scanner sc)** - allows the customer to make a request by prompting them to choose a store, calendar, and appointment window they want to make an appointment under and asks them to provide the number of people that would be in the appointment, which then adds to the bookings attribute of the appointment request. 

**view(String store, String calendar)** - parses through windows.csv to print out the calendar appointment windows for the given store. 

**export(ArrayList<String[]> approved, String fileName)** - exports all approved appointments passed as a parameter to the filename passed as the second parameter using BufferedWriter. 

**cancel(Scanner sc)** - cancels an appoinment by parsing through appointments.csv and removing the appointment from the file using a temp.csv.
 
**popularStores(Scanner scan)** - lists out all popular Stores by comparing the booking numbers of all of the appointments of the calendars under the stores. It also prompts to user to choose whether they want the dashboard to be sorted or not.

**popularAppointment(Scanner scan)** - lists out the popular appointments based on the windows with the largest bookings by parsing through appointments.csv and adding up the booking integer values. It also prompts to user to choose whether they want the dashboard to be sorted or not.

### Store
This object represents a Seller's store, which has a name and it's repective calendars. This store does not inherit anything. It's fields are storeName and an ArrayList of Calendar objects. Store has two constructors, one with just the storeName passed as a parameter, and another with storeName and the ArrayList of Calendar passed as paramters. This object has two getters for the two fields it contains. It has no setters.

Methods in this class are **addCalendar(), removeCalendar(), and toString()**

**addCalendar(Calendar calendar)** - adds the calendar passed in the parameter to the field calendars, which is an ArrayList containing all of the stores calendars.

**removeCalendar(Calendar calendar)** - removes the calendar passed in the parameter to the field calendars. It returns a boolean value that signifies whether the removal was successful or not.

**toString()** - creates a String of all calendar descriptions by iterating through the ArrayList of calendars and returning all of their toString() strings combined.

### Calendar
Calendar represents a calendar of appointments approved by sellers and requests made by customers. This class does not inherit anything. It has four fields; appointments, requests, title, and description. appointments is an ArrayList of Appointment objects and requests is an ArrayList of Request objects. This class contains getters for all fields, but no setters. 

Methods in this class are **displaySortedAppointments(), and toString()**

**displaySortedAppointments()** - returns a String of all of the toString() for all of the appointments in the appointments ArrayList sorted by start time and end time.

**toString()** - creates a String describing the calendar with it's title description and all appointments by calling displaySortedAppointments() method.

### Appointment
Represents an appointment, whether approved or not, that a customer creates. This Appoinment contains 6 fields; title, maximumAtendees, currentBookings, startTime, endTime, and approved. maximumAtendees is an integer signifying the maximum number of people allowed to book the appointment and currentBookings is an integer of the current number of people attending the appointment. startTime and endTime are integers that are in military timing. approved is a boolean value that signifies whether or not the appointment has been approved by the seller. Appointment has one constructor with all field parameters. It also has getters for all fields and a setter for approved. 

Methods in this class are **equals() and toString()**

**equals(Object obj)** - overrides the equals() method and determines whether the object passed as a parameter is an instance of Appointment and compares the fields to the current instance. 

**toString()** - returns a String describing the appointment with title, maximum attendees, current number of bookings, and start time and end time. 

### Request
Object represents a type of Appointment, which is a request. This object is a child of Appointment (extends Appointment) and inherits the fields from Appointment. It also has it's own field, customer, which stores the Customer object that created the request. This object has a getter and setter for customer. 

Method in this class is **toString()**

**toString()** - returns a String describing the request. same format as Appointment toStrings except customer email is also added to the string.

### Appointment
Represents an appointment, whether approved or not, that a customer creates. This Appoinment contains 6 fields; title, maximumAtendees, currentBookings, startTime, endTime, and approved. maximumAtendees is an integer signifying the maximum number of people allowed to book the appointment and currentBookings is an integer of the current number of people attending the appointment. startTime and endTime are integers that are in military timing. approved is a boolean value that signifies whether or not the appointment has been approved by the seller. Appointment has one constructor with all field parameters. It also has getters for all fields and a setter for approved. 

### SellerTesting
Presents simple tests for the seller class' main functions. All testing done will not affect appointments.csv, windows.csv, and stores.csv - backups of these files are made beforehand then reloaded at the end.

**test1(Seller testSeller, String sName)** - tests store creation; makes a store in stores.csv for the given seller with the given name.

**test2(Seller testSeller, Scanner sc)** - tests calendar creation; makes two calendars for the store in test1. Tests for name overlap.

**test3(Seller testSeller, Scanner sc)** - tests calendar deletion; deletes the second calendar made in test1.

### Customer testing
Creates tests for the Customer classes that tests all the functionality.

**test1(Custoemr testCustomer, Scanner scan)** - Tests the view calendar by printing out a specific calendar and comparing that to a previously set calendar.

**test2(Customer testCustomer, Scanner scan)** - Tests requesting an appointment by creating a request and then seeing if it is written into the file.

**test3(Customer testCustomer, Scanner scan, String expected)** - Tests if the appointment gets canceled by reading the file and seeing if the appointment doesn't exist anymore.

**test4(Customer testCustomer, Scanner scan)** - Looks at the approved appointments and check if the export functions.

**test5(Customer testCustomer, Scanner scan)** - Saves the statistic console output to a file and compares that to a test statistics.

### Main
Contains the entire workflow for our program in the main method. 

**chooseStore(Scanner scan)** - takes in a scanner and runs through all stores, printing them all out into a list where the user can choose which store
they wish to interact with, then returns the selected store name as a String.

**chooseCalendar(Scanner scan, String store)** - takes in a scanner and String store; runs through all calendars, presents the ones matching store to the user in a list, and returns the selected calendar name as a string.

## Files and Attributes
Accounts.csv
```
type, email, password
```

Stores.csv  
```
sellerEmail, storeName
```

Calendars.csv  
```
storeName, calendarName
```

Appointments.csv  
```
customerEmail, sellerEmail, storeName, calendarName, startTime, endTime, booking, isApproved, isRequest, timeStamp
```

Windows.csv  
```
storeName, calendarName, calendarDescription, startTime, endTime, title, maxAttendees, currentBooking
```
