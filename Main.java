import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {

    //still working on checking the login part
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        //determines what input value will logout
        int logout = -1;
        while (true) {
            String action;
            String currentType = null;
            Customer currentCustomer;
            Seller currentSeller;
            do {
                currentCustomer = new Customer();
                currentSeller = new Seller();
                System.out.println("Would you like to login in or create an account? \n[L]ogin \n[C]reate \n[Q]uit");
                action = scan.nextLine().toLowerCase();

                String type;
                switch (action) {
                    case "q":
                        System.out.println("Thank you for using Calendar!");
                        return;

                    case "c":
                        do {
                            System.out.println("Are you a Customer or a Seller? \n[C]ustomer \n[S]eller");
                            type = scan.nextLine().toLowerCase();
                            switch (type) {
                                case "c":
                                    String customerEmail;
                                    //check if the email already exists and asks for a new email if it does exist
                                    boolean customerCheck = true;
                                    boolean customerEmailInvalid;
                                    do {
                                        System.out.println("Enter an email:");
                                        customerEmail = scan.nextLine();
                                        customerEmailInvalid = currentCustomer.emailInvalid(customerEmail);
                                        if (customerEmailInvalid) {
                                            System.out.println("Invalid email format!");
                                        } else {
                                            customerCheck = currentCustomer.accountExists(customerEmail);
                                            if (customerCheck) {
                                                System.out.println("Error! Account already exists!");
                                            }
                                        }
                                    } while (customerEmailInvalid || customerCheck);

                                    System.out.println("Enter a password");
                                    String password = scan.nextLine();
                                    while (password.length() < 8) {
                                        System.out.println("Must be longer than 8 characters!");
                                        System.out.println("Enter a password:");
                                        password = scan.nextLine();
                                    }

                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv", true))) {
                                        writer.write(type + "," + customerEmail + "," + password + "," + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()) + "\n");
                                        writer.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "s":
                                    String sellerEmail;
                                    //check if the email already exists and asks for a new email if it does exist
                                    boolean sellerCheck = true;
                                    boolean sellerEmailInvalid;
                                    do {
                                        System.out.println("Enter an email:");
                                        sellerEmail = scan.nextLine();
                                        sellerEmailInvalid = currentSeller.emailInvalid(sellerEmail);
                                        if (sellerEmailInvalid) {
                                            System.out.println("Invalid email format!");
                                        } else {
                                            sellerCheck = currentSeller.accountExists(sellerEmail);
                                            if (sellerCheck) {
                                                System.out.println("Error! Account already exists!");
                                            }
                                        }
                                    } while (sellerEmailInvalid || sellerCheck);

                                    //checks if the password is greater than 8 digits
                                    System.out.println("Enter a password:");
                                    String sellerPass = scan.nextLine();
                                    while (sellerPass.length() < 8) {
                                        System.out.println("Must be longer than 8 characters!");
                                        System.out.println("Enter a password:");
                                        sellerPass = scan.nextLine();
                                    }
                                    //testing
                                    //System.out.println("password is valid; fell through");
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv", true))) {
                                        writer.write(type + "," + sellerEmail + "," + sellerPass + "," + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()) + "\n");
                                        writer.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid input");
                                    break;
                            }
                        } while (!type.equals("c") && !type.equals("s"));
                        System.out.println("Account created!");
                        break;
                    case "l":
                        do {
                            System.out.println("Are you a Customer or Seller? \n[C] \n[S]");
                            type = scan.nextLine().toLowerCase();

                            String email;
                            String pass;
                            boolean check;
                            switch (type) {
                                case "c":
                                    do {
                                        System.out.println("Enter your email:");
                                        email = scan.nextLine();

                                        System.out.println("Enter your password:");
                                        pass = scan.nextLine();

                                        check = currentCustomer.verifyAccount(email, pass);
                                        if (!check) {
                                            System.out.println("Credentials are incorrect.");
                                        }
                                    } while (!check);
                                    currentCustomer = new Customer(email, pass);
                                    currentType = "c";
                                    break;
                                case "s":
                                    do {
                                        System.out.println("Enter your email");
                                        email = scan.nextLine();

                                        System.out.println("Enter your password:");
                                        pass = scan.nextLine();
                                        check = currentSeller.verifyAccount(email, pass);
                                        if (!check) {
                                            System.out.println("Credentials are incorrect.");
                                        }
                                    } while (!check);
                                    currentSeller = new Seller(email, pass);
                                    currentType = "s";
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                                    break;
                            }
                        } while (!type.equals("c") && !type.equals("s"));
                        System.out.println("Login successful!");
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            } while (!action.equals("l"));

            //main program after logging in
            //determines which option allows quitting the program
            if (currentType.equals("s")) {
                logout = 8;
            } else {
                logout = 5;
            }
            int option = -1;
            while (option != logout) {
                //0 (maybe),1(maybe),2,3,4 (mostly),5,6,7
                if (currentType.equals("s")) {
                    System.out.println("[0]: View Approved Appointments");
                    System.out.println("[1]: Appointment Requests");
                    System.out.println("[2]: Create Store");
                    System.out.println("[3]: Create Calendar");
                    System.out.println("[4]: Edit Calendar");
                    System.out.println("[5]: Delete Calendar");
                    System.out.println("[6]: Show Statistics");
                    System.out.println("[7]: Import Calendar");
                    System.out.println("[8]: Logout");
                    System.out.println("[9]: Quit");

                    option = scan.nextInt();
                    scan.nextLine();
                    switch (option) {
                        case 0 -> {
                            currentSeller.viewApprovedAppointments();
                        }
                        case 1 -> {
                            currentSeller.approve(scan);
                        }
                        case 2 -> {
                            System.out.println("Please enter your store's name:");
                            String name = scan.nextLine();
                            currentSeller.createStore(name);
                        }
                        case 3 -> {
                            boolean gate;
                            do {
                                System.out.println("Would you like to import the calendar or create it by hand? (Please enter \"import\" or \"hand\")");
                                String type = scan.nextLine();
                                switch (type) {
                                    case "import":
                                        boolean gate2;
                                        do {
                                            String store = chooseStore(scan, currentSeller.getEmail());
                                            System.out.println("Please enter the name of the file you would like to import from: ");
                                            String fileName = scan.nextLine();
                                            try {
                                                currentSeller.importCalendar(store, fileName);
                                                gate2 = false;
                                            } catch (IOException e) {
                                                System.out.println("Something went wrong, make sure to check your file name/path. Please try again!");
                                                gate2 = true;
                                            }
                                        } while (gate2);
                                        gate = false;
                                        break;
                                    case "hand":
                                        String store = chooseStore(scan, currentSeller.getEmail());
                                        currentSeller.createCalendar(scan, store);
                                        gate = false;
                                        break;
                                    default:
                                        System.out.println("Please enter either \"import\" or \"hand.\"");
                                        gate = true;
                                        break;
                                }
                            } while (gate);
                        }
                        case 4 -> {
                            String store = chooseStore(scan, currentSeller.getEmail());
                            if (!store.equals("empty")) {
                                System.out.println("Please select a calendar:");
                                String calendar = chooseCalendar(scan, store);
                                if (!calendar.equals("empty")) {
                                    currentSeller.editCalendar(store, calendar, scan);
                                    System.out.println("Modification successful!");
                                } else {
                                    System.out.println("No calendars for this store!");
                                }
                            } else {
                                System.out.println("No stores!");
                            }
                        }
                        case 5 -> {
                            String store = chooseStore(scan, currentSeller.getEmail());
                            if (!store.equals("empty")) {
                                System.out.println("Please select a calendar:");
                                String calendar = chooseCalendar(scan, store);
                                if (!calendar.equals("empty")) {
                                    currentSeller.deleteCalendar(store, calendar);
                                    System.out.println("Deletion successful!");
                                } else {
                                    System.out.println("No calendars for this store!");
                                }
                            } else {
                                System.out.println("No stores!");
                            }
                        }
                        case 6 -> {
                            try {
                                currentSeller.viewCustomerApprovedAppointments();
                                currentSeller.viewPopularAppointmentWindows();
                                System.out.println("Would you like to sort these dashboards? (y/n)");
                                String type = scan.nextLine();
                                boolean gate;
                                do {
                                    switch (type) {
                                        case "y":
                                            System.out.println("Customer appointment numbers will be sorted alphabetically.");
                                            System.out.println("Would you like to sort popular appointments in ascending or descending order? (a/d)");
                                            String sort = scan.nextLine();
                                            boolean gate2;
                                            do {
                                                switch (sort) {
                                                    case "a":
                                                        currentSeller.sortDashboard("alphabetical");
                                                        currentSeller.sortDashboard("ascending");
                                                        gate2 = false;
                                                        break;
                                                    case "d":
                                                        currentSeller.sortDashboard("alphabetical");
                                                        currentSeller.sortDashboard("descending");
                                                        gate2 = false;
                                                        break;
                                                    default:
                                                        System.out.println("Please enter either a or d.");
                                                        gate2 = true;
                                                        break;
                                                }
                                            } while (gate2);
                                            gate = false;
                                            break;
                                        case "n":
                                            gate = false;
                                            break;
                                        default:
                                            System.out.println("Please enter either y or n.");
                                            gate = true;
                                            break;
                                    }
                                } while (gate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        case 7 -> {
                            System.out.println("Enter the name of the store: ");
                            String store = scan.nextLine();
                            System.out.println("Enter the name of the file you want to import from: ");
                            String fileName = scan.nextLine();
                            try {
                                currentSeller.importCalendar(store, fileName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        case 8 -> {
                            System.out.println("Logged out!");
                            currentSeller = null;
                        }
                        case 9 -> {
                            System.out.println("Thank you for using Calendar!");
                            return;
                        }
                        default -> {
                            System.out.println("Invalid option - please enter another number");
                        }
                    }
                } else {
                    //0,1,2,idk,5,6
                    //WE DONT KEEP TRACK OF CURRENT ATTENDEES GO FIX THIS LATER
                    System.out.println("[0]: View Calendars");
                    System.out.println("[1]: Request an Appointment");
                    System.out.println("[2]: Cancel an Appointment");
                    System.out.println("[3]: View Approved Appointments");
                    System.out.println("[4]: Show statistics");
                    System.out.println("[5]: Logout");
                    System.out.println("[6]: Quit");
                    option = scan.nextInt();
                    scan.nextLine();
                    switch (option) {
                        case 0 -> {
                            String store = chooseStore(scan);
                            String calendar = chooseCalendar(scan, store);
                            currentCustomer.view(store, calendar);
                        }
                        case 1 -> {
                            currentCustomer.request(scan);
                        }
                        case 2 -> {
                            currentCustomer.cancel(scan);
                        }
                        case 3 -> {
                            currentCustomer.viewApproved(scan);
                        }
                        case 4 -> {
                            currentCustomer.popularStores(scan);
                            currentCustomer.popularAppointment(scan);
                        }
                        case 5 -> {
                            System.out.println("Logged out!");
                            currentCustomer = null;
                        }
                        case 6 -> {
                            System.out.println("Thank you for using Calendar!");
                            return;
                        }
                        default -> {
                            System.out.println("Invalid option - please enter another number");
                        }
                    }
                }
            }
        }
    }

    public static String chooseStore(Scanner scan, String sellerEmail) {
        String store = null;
        String line;
        ArrayList<String> stores = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.csv"))) {
            line = bfr.readLine();
            while (line != null) {
                String[] lineList = line.split(",");
                if (lineList[0].equals(sellerEmail)) {
                    stores.add(lineList[1]);
                }
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stores.isEmpty()) {
            return "empty";
        } else {
            System.out.println("Please select a store:");
        }
        for (int i = 0; i < stores.size(); i++) {
            System.out.printf("[%d]: %s\n", i, stores.get(i));
        }

        boolean integer = true;
        do {
            integer = true;
            String input = scan.nextLine();
            try {
                int choice = Integer.parseInt(input);
                store = stores.get(choice);
            } catch (Exception e) {
                integer = false;
                System.out.println("Invalid entry.");
            }
        } while(!integer);
        return store;
    }

    public static String chooseStore(Scanner scan) {
        String store = null;
        String line;
        ArrayList<String> stores = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.csv"))) {
            line = bfr.readLine();
            while (line != null) {
                String[] lineList = line.split(",");
                stores.add(lineList[1]);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stores.isEmpty()) {
            return "empty";
        } else {
            System.out.println("Please select a store:");
        }
        for (int i = 0; i < stores.size(); i++) {
            System.out.printf("[%d]: %s\n", i, stores.get(i));
        }

        boolean integer = true;
        do {
            integer = true;
            String input = scan.nextLine();
            try {
                int choice = Integer.parseInt(input);
                store = stores.get(choice);
            } catch (Exception e) {
                integer = false;
                System.out.println("Invalid entry.");
            }
        } while(!integer);
        return store;
    }

    public static String chooseCalendar(Scanner scan, String store) {
        String calendar = null;
        String line;

        ArrayList<String> calendars = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("calendars.csv"))) {
            line = bfr.readLine();
            while (line != null) {
                String[] lineList = line.split(",");
                if (lineList[0].equals(store)) {
                    calendars.add(lineList[1]);
                }
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (calendars.isEmpty()) {
            return "empty";
        } else {
            System.out.println("Please select a calendar:");
        }
        for (int i = 0; i < calendars.size(); i++) {
            System.out.printf("[%d]: %s\n", i, calendars.get(i));
        }

        boolean integer;
        do {
            integer = true;
            String input = scan.nextLine();
            try {
                int choice = Integer.parseInt(input);
                calendar = calendars.get(choice);
            } catch (Exception e) {
                integer = false;
                System.out.println("Invalid entry.");
            }
        } while(!integer);
        return calendar;
    }

}

