import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.nio.file.*;

public class Seller extends Account {
    ArrayList<Store> stores;


    public Seller(){ 
        super();
        stores = new ArrayList<Store>();
    }
    public Seller(String email, String password){
        super(email, password);
        stores = new ArrayList<Store>();
    }
    //can return a boolean if we want to make sure no
    //duplicate store names
    //do stores have parameters???
    public boolean createStore(String storeName) {
        boolean nameUsed = false;
        try (BufferedReader br = new BufferedReader(new FileReader("stores.csv"))) {
            String line = br.readLine();
            while(line != null) {
                String[] splitted = line.split(",");
                if (splitted[1].equals(storeName)){
                    nameUsed = true;
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!nameUsed) {    
            stores.add(new Store(storeName));
            try {
                PrintWriter writer = new PrintWriter( new FileWriter("stores.csv", true));
                writer.println(this.getEmail() + "," + storeName+","+new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            System.out.println("Sorry, name is already taken.");
            return false;
        }
    }
  
    public void createCalendar(Scanner scan, String store) {
        boolean existing = false;
        String calName = null;

        try (BufferedReader bfr = new BufferedReader(new FileReader("calendars.csv"))) {
            do {
                existing = false;
                System.out.println("Enter your calendar's name: ");
                calName = scan.nextLine();
                String line = bfr.readLine();
                while (line != null) {
                    String[] lineList = line.split(",");
                    if (lineList[0].equals(store) && lineList[1].equals(calName)) {
                        System.out.println("This name is already in use! Please enter another name.");
                        existing = true;
                    }
                    line = bfr.readLine();
                }
            } while (existing);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("calendars.csv",true))) {
            bw.write(store + "," + calName + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Would you like to add appointment windows? [y][n]");
        String input = scan.nextLine();
        if (input.equals("y")) {
            Boolean repeat = true;
            do {
                makeAppointmentWindow(scan, store, calName);
                System.out.println("Would you like to add another appointment window? [y][n]");
                String another = scan.nextLine();
                if (another.equals("n")) {
                    repeat = false;
                }
            } while (repeat);
        }

    }

    public void importCalendar(String storename, String filename) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader( new FileReader(filename))) {
            for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getStoreName().equals(storename)) {
                    Store storeObject = stores.get(i);
                    int lineCount = 0;
                    String line = br.readLine();
                    String calTitle = "";
                    String calDesc = "";
                    String apptWTitle = "";
                    String startTime = "";
                    String endTime = "";
                    String maxAttended = "";
                    String currentBookings = "";
                    while (line != null) {
                        if (lineCount == 0) {
                            calTitle = line;
                            lineCount++;
                        } else if (lineCount == 1) {
                            calDesc = line;
                            Calendar newCalendar = new Calendar(calTitle, calDesc);
                            storeObject.addCalendar(newCalendar);
                            lineCount++;
                        } else {
                            //title,starttime,endtime,maxAttendeed,currentBookings
                            String[] attr = line.split(",");
                            apptWTitle = attr[0];
                            startTime = attr[1];
                            endTime = attr[2];
                            maxAttended = attr[3];
                            currentBookings = attr[4];
                            try (PrintWriter writer = new PrintWriter(new FileWriter("windows.csv", true))) {
                                //!!NOT SURE IF TIMESTAMP IS IMPLEMENTED CORRECTLY!!
                                writer.println(storename + "," + calTitle + "," + apptWTitle + "," + calDesc + "," +
                                        startTime + "," + endTime + "," + maxAttended + "," + currentBookings + "," +
                                        new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date().getTime()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        line = br.readLine();
                    }
                }
            }
            System.out.println("Calendar Imported Successfully!");
        } catch (FileNotFoundException f) {
            System.out.println("The file was not found, please try again!");
        } catch (IOException e) {
            System.out.println("An exception occurred, please try again!");
        }
    }

    public void approve(Scanner scan) {
        ArrayList<String> nonRequestLines = new ArrayList<>();
        ArrayList<String> requestLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("appointments.csv"))) {
            String line = br.readLine();
            while (line != null) {

                String[] requestSplit = line.split(",");
                if ((requestSplit[1].equals(getEmail())) && (requestSplit[7].equals("false")) && (requestSplit[8].equals("true"))) {
                    requestLines.add(line);

                } else {
                    nonRequestLines.add(line);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int totalRequests = 0;
        System.out.println("These are your requests:");
        for (int i = 0; i < requestLines.size(); i++) {
            totalRequests++;
            String[] requestLineArray = requestLines.get(i).split(",");
            System.out.println("[" + i + "]");
            System.out.println("Customer: " + requestLineArray[0]);
            System.out.println("Store: " + requestLineArray[2]);
            System.out.println("Calendar: " + requestLineArray[3]);
            System.out.println("From " + requestLineArray[4] + " To " + requestLineArray[5]);
            System.out.println(requestLineArray[6] + " people requested to attend \n");
        }


        boolean quit = false;
        System.out.println("Choose the request you wish to approve, or type -1 to quit.");
        int choice = 0;
        boolean integer;
        boolean exceed = true;
        do {
            integer = true;
            String input = scan.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice == -1) {
                    quit = true;
                    break;
                }
            } catch (Exception e) {
                integer = false;
                System.out.println("Invalid entry.");
            }
            if ((choice < 0) || (choice >= totalRequests)) {
                System.out.println("Invalid entry");
            } else {
                exceed = false;
            }
        } while(!integer || exceed);

        if (quit) {
            return;
        }

        String[] chosenRequest = requestLines.get(choice).split(",");

        String approved = null;
        String[] approvedList = new String[0];
        String store;
        String calendar;
        String appointTitle;
        String appointDescription;
        String start;
        String end;
        int max;
        int current;
        try {
            BufferedReader windowsReader = new BufferedReader(new FileReader("windows.csv"));
            BufferedWriter windowWriter = new BufferedWriter(new FileWriter("tempWindow.csv"));

            String line = windowsReader.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                if ((chosenRequest[2].equals(lineArray[0])) &&
                        (chosenRequest[3].equals(lineArray[1])) &&
                        (chosenRequest[4].equals(lineArray[4])) &&
                        (chosenRequest[5].equals(lineArray[5]))) {
                    store = lineArray[0];
                    calendar = lineArray[1];
                    appointTitle = lineArray[2];
                    appointDescription = lineArray[3];
                    start = lineArray[4];
                    end = lineArray[5];
                    max = Integer.parseInt(lineArray[6]);
                    current = Integer.parseInt(lineArray[7]);
                    approved = chosenRequest[0] + "," + chosenRequest[1] + "," + chosenRequest[2] + "," +
                            chosenRequest[3] + ","  + chosenRequest[4] + "," + chosenRequest[5] + ","  +
                            chosenRequest[6] + ",false,true," + new SimpleDateFormat("yyyy" +
                            ".MM.dd.HH").format(new java.util.Date())+"\n";
                    approvedList = approved.split(",");
                    windowWriter.write(store + "," + calendar + "," + appointTitle + "," + appointDescription + "," +
                            start + "," + end + "," + max + "," + (current + Integer.parseInt(approvedList[6])) + "," + new SimpleDateFormat("yyyy" +
                                    ".MM.dd.HH").format(new java.util.Date())+"\n");
                } else {
                    windowWriter.write(line + "\n");
                }
                line = windowsReader.readLine();

            }
            windowWriter.close();
            windowsReader.close();
            Files.move(Paths.get("tempWindow.csv"), Paths.get("windows.csv"),
                    StandardCopyOption.REPLACE_EXISTING);

            BufferedReader appointmentReader = new BufferedReader(new FileReader("appointments.csv"));
            BufferedWriter appointmentWriter = new BufferedWriter(new FileWriter("tempAppointments.csv"));


            System.out.println("approved: " + approved);

            String appointmentLine = appointmentReader.readLine();
            while (appointmentLine != null) {
                System.out.println("line: " + appointmentLine + ":" + appointmentLine.equals(approved));
                String[] appointmentList = appointmentLine.split(",");
                if ((appointmentList[0].equals(approvedList[0])) && (appointmentList[2].equals(approvedList[2])) &&
                        (appointmentList[3].equals(approvedList[3])) && (appointmentList[4].equals(approvedList[4])) &&
                        (appointmentList[5].equals(approvedList[5])) && (appointmentList[6].equals(approvedList[6]))) {
                    appointmentWriter.write(appointmentList[0] + "," + appointmentList[1] + "," +
                            appointmentList[2] + "," + appointmentList[3] + "," + appointmentList[4] + "," +
                            appointmentList[5] + "," + appointmentList[6] + ",true,false," + new SimpleDateFormat(
                                    "yyyy.MM.dd.HH").format(new java.util.Date())+"\n");
                } else {
                    appointmentWriter.write(appointmentLine + "\n");
                }
                appointmentLine = appointmentReader.readLine();
            }
            appointmentReader.close();
            appointmentWriter.close();
            Files.move(Paths.get("tempAppointments.csv"), Paths.get("appointments.csv"),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void editCalendar(String sName, String cName, Scanner sc) {
        System.out.println("What action would you like to perform?");
        System.out.println("[0]: Change calendar title");
        System.out.println("[1]: Change calendar description");
        //System.out.println("[3] Edit calendar window title");
        System.out.println("[2]: Edit calendar window timeframe (Warning: will remove all related appointments)");
        //System.out.println("[4] Delete calendar window (Warning: will remove all related appointments)");
        int select = sc.nextInt();
        sc.nextLine();
        switch (select) {
            case 0 -> {
                System.out.println("What will the new title be?");
                String title = sc.nextLine();
                //CALENDAR FILE
                //RENAMES THE RELEVANT CALENDAR
                File calendars = new File("calendars.csv");
                try (BufferedReader br = new BufferedReader(new FileReader(calendars))) {
                    File temp = new File("temp.csv");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp,true));
                    String line = br.readLine();
                    while (line != null) {
                        String[] cals = line.split(",");
                        if (!cals[0].equals(sName) || !cals[1].equals(cName)) {
                            bw.write(line + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()) + "\n");
                            bw.flush();
                        } else {
                            bw.write(cals[0] + "," + title +  "," + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()) + "\n");
                            bw.flush();
                        }
                        line = br.readLine();
                    }
                    br.close();
                    bw.close();
                    Files.move(Paths.get("temp.csv"),Paths.get("calendars.csv"),StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //APPOINTMENT FILE
                //RENAMES THE RELEVANT CALENDAR
                File appointments = new File("appointments.csv");
                try (BufferedReader br = new BufferedReader(new FileReader(appointments))) {
                    File temp = new File("temp.csv");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp,true));
                    String line = br.readLine();
                    while (line != null) {
                        String[] apps = line.split(",");
                        if (!apps[2].equals(sName) || !apps[3].equals(cName)) {
                            bw.write(line + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()) + "\n");
                            bw.flush();
                        } else {
                            bw.write(apps[0] + "," + apps[1] + "," + apps[2] + "," +
                                    title + "," + apps[4] + "," + apps[5] + "," + apps[6] +
                                    "," + apps[7] + "," + apps[8] + "," + apps[9] +","+new SimpleDateFormat("yyyy.MM" +
                                    ".dd.HH").format(new java.util.Date()) + "\n");
                            bw.flush();
                        }
                        line = br.readLine();
                    }
                    br.close();
                    bw.close();
                    Files.move(Paths.get("temp.csv"),Paths.get("appointments.csv"),StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //WINDOWS FILE
                //RENAMES THE RELEVANT CALENDAR
                File windows = new File("windows.csv");
                try (BufferedReader br = new BufferedReader(new FileReader(windows))) {
                    File temp = new File("temp.csv");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp,true));
                    String line = br.readLine();
                    while (line != null) {
                        String[] wins = line.split(",");
                        if (!wins[0].equals(sName) || !wins[1].equals(cName)) {
                            bw.write(line + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()) + "\n");
                            bw.flush();
                        } else {
                            bw.write(wins[0] + "," + title + "," + wins[2] + "," +
                                    wins[3] + "," + wins[4] + "," + wins[5] + "," + wins[6] +
                                    "," + wins[7]+","+new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date())+ "\n");
                            bw.flush();
                        }
                        line = br.readLine();
                    }
                    br.close();
                    bw.close();
                    Files.move(Paths.get("temp.csv"),Paths.get("windows.csv"),StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case 1 -> {
                System.out.println("What will the new description be?");
                String desc = sc.nextLine();
                //WINDOWS FILE
                //RENAMES THE RELEVANT DESCRIPTION
                File windows = new File("windows.csv");
                try (BufferedReader br = new BufferedReader(new FileReader(windows))) {
                    File temp = new File("temp.csv");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp,true));
                    String line = br.readLine();
                    while (line != null) {
                        String[] wins = line.split(",");
                        if (!wins[0].equals(sName) || !wins[1].equals(cName)) {
                            bw.write(line + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()) + "\n");
                            bw.flush();
                        } else {
                            bw.write(wins[0] + "," + wins[1] + "," + desc + "," +
                                    wins[3] + "," + wins[4] + "," + wins[5] + "," + wins[6] +
                                    "," + wins[7]+","+new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date())+ "\n");
                            bw.flush();
                        }
                        line = br.readLine();
                    }
                    br.close();
                    bw.close();
                    Files.move(Paths.get("temp.csv"),Paths.get("windows.csv"),StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                System.out.println("Which window would you like to adjust?");
                ArrayList<Integer> indexes = new ArrayList<Integer>();
                ArrayList<String[]> winArr = new ArrayList<String[]>();
                String oldStart = "";
                String oldEnd = "";
                //WINDOWS FILE
                //CHANGES A TIMESLOT
                File windows = new File("windows.csv");
                try (BufferedReader br = new BufferedReader(new FileReader(windows))) {
                    String win = br.readLine();
                    int index = 0;
                    int count = 0;
                    while (win != null) {
                        String[] temp = win.split(",");
                        winArr.add(temp);
                        if (temp[0].equals(sName) && temp[1].equals(cName)) {
                            indexes.add(count);
                            System.out.println("[" + index + "]" + temp[2]);
                            index++;
                        }
                        count++;
                        win = br.readLine();
                    }
                    int winSel = indexes.get(sc.nextInt());
                    sc.nextLine();
                    System.out.println("What will the new start time be?");
                    int sTime = sc.nextInt();
                    sc.nextLine();
                    System.out.println("What will the new end time be?");
                    int eTime = sc.nextInt();
                    sc.nextLine();
                    oldStart = winArr.get(winSel)[3];
                    oldEnd = winArr.get(winSel)[4];
                    winArr.get(winSel)[3] = "" + sTime;
                    winArr.get(winSel)[4] = "" + eTime;
                    //writes adjusted time to windows.csv
                    File temp = new File("temp.csv");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp,true));
                    for (String[] s : winArr) {
                        bw.write(s[0] + "," + s[1] + "," + s[2] + "," +
                                s[3] + "," + s[4] + "," + s[5] + "," + s[6] +
                                "," + s[7]+","+new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date())+ "\n");
                        bw.flush();
                    }
                    br.close();
                    bw.close();
                    Files.move(Paths.get("temp.csv"),Paths.get("windows.csv"),StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //APPOINTMENT FILE
                //REMOVES RELEVANT APPOINTMENTS
                File appointments = new File("appointments.csv");
                try (BufferedReader br = new BufferedReader(new FileReader(appointments))) {
                    File temp = new File("temp.csv");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp,true));
                    String line = br.readLine();
                    while (line != null) {
                        String[] apps = line.split(",");
                        if (!apps[2].equals(sName) || !apps[3].equals(cName) ||
                            !apps[4].equals(oldStart) || !apps[5].equals(oldEnd)) {
                            bw.write(line + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date()) + "\n");
                            bw.flush();
                        }
                        line = br.readLine();
                    }
                    br.close();
                    bw.close();
                    Files.move(Paths.get("temp.csv"),Paths.get("appointments.csv"),StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    //verfies the start and end time so that it doenst conflict with any other start and end times of the same calendar in th same store
    public boolean verify(int startTime, int endTime, String storeName, String calendarName) {
        boolean verified = false;
        try (BufferedReader br = new BufferedReader(new FileReader("windows.csv"))) {
            ArrayList<String> windows = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                windows.add(line);
                line = br.readLine();
            }
            boolean hasWin = false;
            for(int i = 0; i < windows.size(); i++) {
                String[] splitted = windows.get(i).split(",");
                if (splitted[0].equals(storeName) && splitted[1].equals(calendarName)) {
                    //start and end need to be before another event's start, or both after another event's end:
                    hasWin = true;
                    if ((startTime < Integer.parseInt(splitted[4]) && endTime < Integer.parseInt(splitted[4])) ||
                            (startTime > Integer.parseInt(splitted[5]) && endTime > Integer.parseInt(splitted[5]))) {
                        verified = true;
                    }
                }
            }
            if (!hasWin) {
                verified = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return verified;
    }

    //creates appointment windows based on the Seller's preference
    public void makeAppointmentWindow(Scanner scan, String storeName, String calendarName) {
        System.out.println("Enter the title of the appointment: ");
        String appointment = scan.nextLine();
        System.out.println("What's the description of the appointment: ");
        String description = scan.nextLine();
        System.out.println("What's the start time of this appointment: ");
        int startTime = scan.nextInt();
        scan.nextLine();
        System.out.println("What is the end time of this appointment");
        int endTime = scan.nextInt();
        scan.nextLine();
        boolean verified = verify(startTime, endTime, storeName, calendarName);
        //keeps looping until the Seller inputs a valid start and end time
        while (!verified) {
            System.out.println("Invalid start time!");
            System.out.println("Enter another start time:");
            startTime = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter another end time:");
            endTime = scan.nextInt();
            scan.nextLine();

            verified = verify(startTime, endTime, storeName, calendarName);
        }

        System.out.println("What is the max number of attendees: ");
        String maxAttendees = String.format("%d", scan.nextInt());
        scan.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("windows.csv", true))) {
            bw.write(storeName+","+calendarName+","+appointment+","+description+","+
                    String.format("%d", startTime)+","+String.format("%d", endTime)+","+
                    maxAttendees+",0,"+new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date())+"\n");
            System.out.println("Successfully made Appointment window!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //goes through each of the files that has a calendar matching the one passed into the method and deletes the row that it occurs.
    public void deleteCalendar(String storeName, String calendarName){
        File calendars = new File("calendars.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(calendars))) {
            File temp = new File("temp.csv");
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp, true));
            String line = br.readLine();
            while(line != null) {
                String[] splitted = line.split(",");
                if (!splitted[0].equals(storeName) || !splitted[1].equals(calendarName)) {
                    bw.write(line+ "," + new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date())+"\n");
                    bw.flush();
                }
                line = br.readLine();
            }
            br.close();
            bw.close();
            Files.move(Paths.get("temp.csv"),Paths.get("calendars.csv"),StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e) {
            e.printStackTrace();
        }

        File appointments = new File("appointments.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(appointments))) {
            File temp = new File("temp.csv");
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp,true));
            String line = br.readLine();
            while(line != null) {
                String[] splitted = line.split(",");
                if(!splitted[2].equals(storeName) || !splitted[3].equals(calendarName)) {
                    bw.write(line+","+new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date())+"\n");
                    bw.flush();
                }
                line = br.readLine();
            }
            br.close();
            bw.close();
            Files.move(Paths.get("temp.csv"),Paths.get("appointments.csv"),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File windows = new File("windows.csv");
        try(BufferedReader br = new BufferedReader(new FileReader(windows))) {
            File temp = new File("temp.csv");
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp, true));
            String line = br.readLine();
            while (line != null){
                String[] splitted = line.split(",");
                if(!splitted[0].equals(storeName) || !splitted[1].equals(calendarName)) {
                    bw.write(line+","+new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date())+"\n");
                    bw.flush();
                }
                line = br.readLine();
            }
            br.close();
            bw.close();
            Files.move(Paths.get("temp.csv"),Paths.get("windows.csv"),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void viewApprovedAppointments() {
        // by store
        System.out.println("\nAll Approved Appointments:\n");
        System.out.println("*******************************");
        ArrayList<String> allStores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("appointments.csv"))) {
            String line = br.readLine();
            while (line != null) {
                if (line.split(",")[1].equals(this.getEmail())) {
                    allStores.add(line.split(",")[2]);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("appointments.csv"))) {
            for (int i = 0; i < allStores.size(); i++) {
                String currentStore = allStores.get(i);
                System.out.println("Store " + (i+1) + ": " + currentStore);
                String line = br.readLine();
                while (line != null) {
                    String[] lineList = line.split(",");
                    if ((lineList[1].equals(this.getEmail())) && (lineList[2].equals(currentStore) && lineList[7].equals("true") && lineList[8].equals("false"))) {
                        System.out.println("Customer: " + lineList[0]);
                        System.out.println("From " + lineList[4] + " to " + lineList[5]);
                        System.out.println("Booked for: " + lineList[6] + "person / people");
                    }
                    line = br.readLine();
                }

            }
            if (allStores.isEmpty()) {
                System.out.println("No appointments!");
            }
            System.out.println("*******************************");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStores() {
        if (stores.size() == 0) {
            try (BufferedReader br = new BufferedReader(new FileReader("stores.csv"))) {
                String line = br.readLine();
                while (line != null) {
                    if (super.getEmail().equals(line.split(",")[0])) {
                        stores.add(new Store(line.split(",")[1]));
                    }
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ***STATISTICS***
    public void viewCustomerApprovedAppointments() {
        updateStores();
        System.out.println("Here are the Number of Approved Appointments for Each Customer: ");
        System.out.println("******************************************************");
        ArrayList<String> uniqueCustomers = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("appointments.csv"))) {
            String line = br.readLine();
            ArrayList<String> allCustNames = new ArrayList<String>();
            while (line != null) {
                if (line.split(",")[1].equals(super.getEmail())) {
                    allCustNames.add(line.split(",")[0]);
                }
                line = br.readLine();
            }
            for(int i = 0; i < allCustNames.size(); i++) {
                if (!uniqueCustomers.contains(allCustNames.get(i))) {
                    uniqueCustomers.add(allCustNames.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br2 = new BufferedReader(new FileReader("appointments.csv"))){
            for (int k = 0; k < uniqueCustomers.size(); k++) {
                String currentCustomer = uniqueCustomers.get(k);
                System.out.print(currentCustomer + ": ");
                int count = 0;
                String line = br2.readLine();
                while (line != null) {
                    if (line.split(",")[1].equals(super.getEmail())) {
                        if (line.split(",")[0].equals(currentCustomer) &&
                                Boolean.parseBoolean(line.split(",")[7]) &&
                                !Boolean.parseBoolean(line.split(",")[8])) {
                                count++;
                        }
                    }
                    line = br2.readLine();
                }
                System.out.print(count + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("******************************************************");
    }

    public void viewPopularAppointmentWindows() {
        updateStores();
        System.out.println("Here are the Most Popular Appointment Windows for Each Store: ");
        System.out.println("*********************************************************");
        //go through appointments.csv and get all sum all of the bookings for each appointment window per store
        ArrayList<ArrayList<String>> startTimes = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < stores.size(); i++) {
            try (BufferedReader br = new BufferedReader(new FileReader("windows.csv"))) {
                ArrayList<String> storeStartTimes = new ArrayList<String>();
                String line = br.readLine();
                while (line != null) {
                    if (line.split(",")[0].equals(stores.get(i).getStoreName())) {
                        storeStartTimes.add(line.split(",")[4] + " - " + line.split(",")[5]);
                        storeStartTimes.add(line.split(",")[4] + " - " + line.split(",")[5]); //startTime
                    }
                    line = br.readLine();
                }
                startTimes.add(storeStartTimes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < stores.size(); j++) {
            System.out.println("Store: " + stores.get(j).getStoreName());
            ArrayList<String> currentStoreTimes = startTimes.get(j);
            ArrayList<Integer> bookingsPerWindow = new ArrayList<Integer>(currentStoreTimes.size());
            for (int k = 0; k < currentStoreTimes.size(); k++) {
                String startTime = currentStoreTimes.get(k).split(" - ")[0];
                String endTime = currentStoreTimes.get(k).split(" - ")[1];
                try (BufferedReader br2 = new BufferedReader(new FileReader("appointments.csv"))) {
                    String line = br2.readLine();
                    int bookings = 0;
                    while (line != null) {
                        if (line.split(",")[4].equals(startTime) && line.split(",")[5].equals(endTime)) {
                            bookings += Integer.parseInt(line.split(",")[6]);
                        }
                        line = br2.readLine();
                    }
                    bookingsPerWindow.add(bookings);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            int popularBooking = 0;
            int index = 0;
            if (!bookingsPerWindow.isEmpty()) {
                for (int l = 0; l < bookingsPerWindow.size(); l++) {
                    if (bookingsPerWindow.get(l) > popularBooking) {
                        popularBooking = bookingsPerWindow.get(l);
                        index = l;
                    }
                }
            }
            if (!currentStoreTimes.isEmpty()) {
                System.out.println("Appointment Window: " + currentStoreTimes.get(index));
                System.out.println("Number of Bookings: " + popularBooking + "\n");
            }
        }
    }

    //you can only sort approved appointments by customer alphabetically
    //you can only sort most popular appointment windows in ascending or descending order
    public void sortDashboard(String type) {
        switch (type) {
            case "alphabetical":
                updateStores();
                System.out.println("Here are the Number of Approved Appointments for Each Customer: ");
                System.out.println("******************************************************");
                ArrayList<String> uniqueCustomers = new ArrayList<String>();
                try (BufferedReader br = new BufferedReader(new FileReader("appointments.csv"))) {
                    String line = br.readLine();
                    ArrayList<String> allCustNames = new ArrayList<String>();
                    while (line != null) {
                        if (line.split(",")[1].equals(super.getEmail())) {
                            allCustNames.add(line.split(",")[0]);
                        }
                        line = br.readLine();
                    }
                    for(int i = 0; i < allCustNames.size(); i++) {
                        if (!uniqueCustomers.contains(allCustNames.get(i))) {
                            uniqueCustomers.add(allCustNames.get(i));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (BufferedReader br2 = new BufferedReader(new FileReader("appointments.csv"))){
                    ArrayList<Integer> appointNumber = new ArrayList<Integer>();
                    for (int k = 0; k < uniqueCustomers.size(); k++) {
                        String currentCustomer = uniqueCustomers.get(k);
                        int count = 0;
                        String line = br2.readLine();
                        while (line != null) {
                            if (line.split(",")[1].equals(super.getEmail())) {
                                if (line.split(",")[0].equals(currentCustomer) &&
                                        Boolean.parseBoolean(line.split(",")[7]) &&
                                        !Boolean.parseBoolean(line.split(",")[8])) {
                                    count++;
                                }
                            }
                            line = br2.readLine();
                        }
                        appointNumber.add(count);
                    }
                    ArrayList<String> orgUniqueCustomers = uniqueCustomers;
                    Collections.sort(uniqueCustomers);
                    for (int j = 0; j < uniqueCustomers.size(); j++) {
                        System.out.print(uniqueCustomers.get(j) + ": ");
                        for (int l = 0; l < orgUniqueCustomers.size(); l++) {
                            if (uniqueCustomers.get(j).equals(orgUniqueCustomers.get(l))) {
                                System.out.print(appointNumber.get(l) + "\n");
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("******************************************************");
                break;
            case "ascending":
                ArrayList<String> appointmentWindows = new ArrayList<String>();
                ArrayList<Integer> numBookings = new ArrayList<Integer>();
                //go through appointments.csv and get all sum all of the bookings for each appointment window per store
                System.out.println("Here are the Most Popular Appointment Windows for Each Store: ");
                System.out.println("*********************************************************");
                //go through appointments.csv and get all sum all of the bookings for each appointment window per store
                ArrayList<ArrayList<String>> startTimes = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < stores.size(); i++) {
                    try (BufferedReader br = new BufferedReader(new FileReader("windows.csv"))) {
                        ArrayList<String> storeStartTimes = new ArrayList<String>();
                        String line = br.readLine();
                        while (line != null) {
                            if (line.split(",")[0].equals(stores.get(i).getStoreName())) {
                                storeStartTimes.add(line.split(",")[4] + " - " + line.split(",")[5]); //startTime
                            }
                            line = br.readLine();
                        }
                        startTimes.add(storeStartTimes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < stores.size(); j++) {
                    System.out.println("Store: " + stores.get(j).getStoreName() + "\n");
                    ArrayList<String> currentStoreTimes = startTimes.get(j);
                    ArrayList<Integer> bookingsPerWindow = new ArrayList<Integer>(currentStoreTimes.size());
                    for (int k = 0; k < currentStoreTimes.size(); k++) {
                        String startTime = currentStoreTimes.get(k).split(" - ")[0];
                        String endTime = currentStoreTimes.get(k).split(" - ")[1];
                        try (BufferedReader br2 = new BufferedReader(new FileReader("appointments.csv"))) {
                            String line = br2.readLine();
                            int bookings = 0;
                            while (line != null) {
                                if (line.split(",")[4].equals(startTime) && line.split(",")[5].equals(endTime)) {
                                    bookings += Integer.parseInt(line.split(",")[6]);
                                }
                                line = br2.readLine();
                            }
                            bookingsPerWindow.add(bookings);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    int popularBooking = 0;
                    int index = 0;
                    if (!bookingsPerWindow.isEmpty()) {
                        for (int l = 0; l < bookingsPerWindow.size(); l++) {
                            if (bookingsPerWindow.get(l) > popularBooking) {
                                popularBooking = bookingsPerWindow.get(l);
                                index = l;
                            }
                        }
                    }
                    if (!currentStoreTimes.isEmpty()) {
                        appointmentWindows.add(currentStoreTimes.get(index));
                        numBookings.add(popularBooking);
                    }
                }
                ArrayList<Integer> duplNumBookings = numBookings;
                Collections.sort(numBookings, Collections.reverseOrder());
                for (int m = 0; m < numBookings.size(); m++) {
                    for (int n = 0; n < duplNumBookings.size(); n++) {
                        if (duplNumBookings.get(n).equals(numBookings.get(m))) {
                            System.out.println("Store: " + stores.get(n));
                            System.out.println("Appointment Window: " + appointmentWindows.get(n));
                            System.out.println("Bookings: " + numBookings.get(m));
                        }
                    }
                }
                break;
            case "descending":
                ArrayList<String> appointmentWindows2 = new ArrayList<String>();
                ArrayList<Integer> numBookings2 = new ArrayList<Integer>();
                //go through appointments.csv and get all sum all of the bookings for each appointment window per store
                System.out.println("Here are the Most Popular Appointment Windows for Each Store: ");
                System.out.println("*********************************************************");
                //go through appointments.csv and get all sum all of the bookings for each appointment window per store
                ArrayList<ArrayList<String>> startTimes2 = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < stores.size(); i++) {
                    try (BufferedReader br = new BufferedReader(new FileReader("windows.csv"))) {
                        ArrayList<String> storeStartTimes = new ArrayList<String>();
                        String line = br.readLine();
                        while (line != null) {
                            if (line.split(",")[0].equals(stores.get(i).getStoreName())) {
                                storeStartTimes.add(line.split(",")[4] + " - " + line.split(",")[5]); //startTime
                            }
                            line = br.readLine();
                        }
                        startTimes2.add(storeStartTimes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < stores.size(); j++) {
                    System.out.println("Store: " + stores.get(j).getStoreName());
                    ArrayList<String> currentStoreTimes = startTimes2.get(j);
                    ArrayList<Integer> bookingsPerWindow = new ArrayList<Integer>(currentStoreTimes.size());
                    for (int k = 0; k < currentStoreTimes.size(); k++) {
                        String startTime = currentStoreTimes.get(k).split(" - ")[0];
                        String endTime = currentStoreTimes.get(k).split(" - ")[1];
                        try (BufferedReader br2 = new BufferedReader(new FileReader("appointments.csv"))) {
                            String line = br2.readLine();
                            int bookings = 0;
                            while (line != null) {
                                if (line.split(",")[4].equals(startTime) && line.split(",")[5].equals(endTime)) {
                                    bookings += Integer.parseInt(line.split(",")[6]);
                                }
                                line = br2.readLine();
                            }
                            bookingsPerWindow.add(bookings);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    int popularBooking = 0;
                    int index = 0;
                    if (!bookingsPerWindow.isEmpty()) {
                        for (int l = 0; l < bookingsPerWindow.size(); l++) {
                            if (bookingsPerWindow.get(l) > popularBooking) {
                                popularBooking = bookingsPerWindow.get(l);
                                index = l;
                            }
                        }
                    }
                    if (!currentStoreTimes.isEmpty()) {
                        appointmentWindows2.add(currentStoreTimes.get(index));
                        numBookings2.add(popularBooking);
                    }
                }
                ArrayList<Integer> duplNumBookings2 = numBookings2;
                Collections.sort(numBookings2);
                for (int m = 0; m < numBookings2.size(); m++) {
                    for (int n = 0; n < duplNumBookings2.size(); n++) {
                        if (duplNumBookings2.get(n).equals(numBookings2.get(m))) {
                            System.out.println("Appointment Window: " + appointmentWindows2.get(n));
                            System.out.println("Bookings: " + numBookings2.get(m) + "\n");
                        }
                    }
                }
                break;
        }
    }

}
