import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

public class Customer extends Account {
    private ArrayList<Appointment> appointments;

    public Customer(){
        super();
    }
    public Customer(String email, String password){
        super(email, password);

    }

    public ArrayList<Appointment> viewApproved(Scanner scan) {
        ArrayList<String[]> approved = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("appointments.csv"))){
            String line = bfr.readLine();
            while (line != null) {
                String[] list = line.split(",");
                if ((list[0].equals(this.getEmail())) && (list[7].equals("true")) && (list[8].equals("false"))) {
                    approved.add(list);
                }
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Here are your approved appointments:");
        for (int i = 0; i < approved.size(); i++) {
            String store = approved.get(i)[2];
            String calendar = approved.get(i)[3];
            String booking = approved.get(i)[6];
            String start = approved.get(i)[4];
            String end = approved.get(i)[5];
            System.out.printf("Store: %s\n Calendar: %s\nPeople attending:%s\nFrom %s to %s\n", store, calendar,
            booking, start, end);
            //System.out.printf("Store: %s\n Calendar: %s\nPeople attending:%s\nFrom %s:%s to %s:%s\n", store, calendar,
                    //booking, start.substring(0,2), start.substring(2,4), end.substring(0,2), end.substring(2,4));
        }

        System.out.println("do you want to export into a .csv file? [y][n]");
        if (scan.nextLine().equals("y")) {
            System.out.println("where to you want to write this file to?");
            String fileName = scan.nextLine();
            export(approved, fileName);
        }


        return appointments;
    }

    public void request(Scanner sc) {
        //holds selections for writing into appointments
        String stName = "";
        String cName = "";
        String email = "";

        String line;
        ArrayList<String[]> stores = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.csv"))) {
            line = bfr.readLine();
            while (line != null) {
                String[] lineList = line.split(",");
                stores.add(lineList);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stores.isEmpty()) {
            System.out.println("No stores currently available!");
            return;
        }
        System.out.println("Please select a store:");
        for (int i = 0; i < stores.size(); i++) {
            System.out.printf("[%d]: %s\n", i, stores.get(i)[1]);
        }

        boolean integer = true;
        do {
            String input = sc.nextLine();
            try {
                int choice = Integer.parseInt(input);
                email = stores.get(choice)[0];
                stName = stores.get(choice)[1];
            } catch (Exception e) {
                integer = false;
            }
        } while(!integer);

        ArrayList<String> calendars = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("calendars.csv"))) {
            line = bfr.readLine();
            while (line != null) {
                String[] lineList = line.split(",");
                if (lineList[0].equals(stName)) {
                    calendars.add(lineList[1]);
                }
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (calendars.isEmpty()) {
            System.out.println("No calendars currently available!");
            return;
        }
        System.out.println("Please select a calendar:");
        for (int i = 0; i < calendars.size(); i++) {
            System.out.printf("[%d]: %s\n", i, calendars.get(i));
        }

        do {
            String input = sc.nextLine();
            try {
                int choice = Integer.parseInt(input);
                cName = calendars.get(choice);
            } catch (Exception e) {
                integer = false;
            }
        } while(!integer);

        ArrayList<String[]> windows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("windows.csv"))) {
            line = br.readLine();
            while (line != null) {
                String[] window = line.split(",");
                if ((window[0].equals(stName)) && (window[1].equals(cName))) {
                    windows.add(window);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (windows.isEmpty()) {
            System.out.println("No windows currently available!");
            return;
        }
        System.out.println("Please select a window:");
        System.out.printf("Current Windows for %s; currently viewing %s Calendar\n\n", stName, cName);
        for (int j = 0; j < windows.size(); j++) {
            System.out.println("[" + j + "]");
            System.out.println("Title: " + windows.get(j)[2]);
            System.out.println("Start Time: " + windows.get(j)[4]);
            System.out.println("End Time: " + windows.get(j)[5]);
            if (Integer.parseInt(windows.get(j)[6]) <= Integer.parseInt(windows.get(j)[7])) {
                System.out.println("Capacity: Full");
            } else {
                System.out.println("Capacity: " + windows.get(j)[7] + " / " + windows.get(j)[6]);
            }
            System.out.println();
        }
        boolean vSelect = false;
        int select;
        while (!vSelect) {
            select = sc.nextInt();
            sc.nextLine();
            if (select >= 0 && select <= windows.size()) {
                String[] win = windows.get(select);

                int bookingsToAdd = 0;
                do {
                    System.out.println("How many people would be in the appointment?");
                    integer = true;
                    String input = sc.nextLine();
                    try {
                        bookingsToAdd = Integer.parseInt(input);
                    } catch (Exception e) {
                        integer = false;
                        System.out.println("Invalid entry. Please enter again.");
                    }
                } while (!integer);


                if (Integer.parseInt(windows.get(select)[6]) >= Integer.parseInt(windows.get(select)[7]) + bookingsToAdd) {
                    String date = new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date());
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("appointments.csv", true))) {
                        bw.write(this.getEmail() + "," + email + "," + stName + "," + cName + ","

                                + win[4] + "," + win[5] + "," + bookingsToAdd + "," + false + "," + true + "," + date +
                                "\n");
                        bw.flush();
                        vSelect = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Appointment Successfully Requested. You may cancel request at any time.");
                } else {
                    System.out.println("Requested booking number exceeds max capacity. Please choose another window " +
                            "from the choices above.");
                }
            } else {
                System.out.println("Invalid number. Please enter another.");
            }
        }
    }

    public void view(String store, String calendar) {
        ArrayList<String[]> windows = new ArrayList<>();
        String description = null;
        try (BufferedReader bfr = new BufferedReader(new FileReader("windows.csv"))) {
            String line = bfr.readLine();
            while (line != null) {
                String[] lineList = line.split(",");
                if ((lineList[0].equals(store)) && (lineList[1].equals(calendar))) {
                    windows.add(lineList);
                    description = lineList[3];
                }
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!windows.isEmpty()) {
            ArrayList<String[]> sorted = new ArrayList<>();
            while (!windows.isEmpty()) {
                int index = 0;
                String earliest = "9999";

                for (int j = 0; j < windows.size(); j++) {
                    String start = windows.get(j)[4];
                    String comparing = "";
                    if (start.length() < 4) {
                        for (int i = 0; i < 4 - start.length(); i++) {
                            comparing += "0";
                        }
                        comparing += start;
                    } else {
                        comparing = start;
                    }
                    if (comparing.compareTo(earliest) < 0) {
                        earliest = comparing;
                        index = j;
                    }
                }
                sorted.add(windows.get(index));
                windows.remove(index);

            }

            System.out.println(calendar);
            System.out.println(description);
            System.out.println("-------------------------------");
            for (int j = 0; j < sorted.size(); j++) {
                String[] window = sorted.get(j);
                System.out.println("Title: " + window[2]);
                System.out.println("Max Occupancy: " + window[6]);
                System.out.println("Current Bookings: " + window[7]);
                System.out.println("Time: " + window[4] + " - " + window[5]);
                System.out.println("Last modified on: " + window[8]);
                System.out.println("-------------------------------");
            }
        } else {
            System.out.println("No Calendars Available for this store.");
        }


    }

    /*
    public void request(String store, String calendar, Scanner scan) {
        int requestingBooking = 0;
        System.out.println("How many people would be in the appointment?");
        boolean integer = true;
        do {
            String input = scan.nextLine();
            try {
                requestingBooking = Integer.parseInt(input);
            } catch (Exception e) {
                integer = false;
            }
        } while(!integer);

        String line;
        ArrayList<String[]> windows = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("windows.csv"))) {
            while (bfr.readLine() == null) {
                line = bfr.readLine();
                String[] lineList = line.split(",");
                if ((lineList[0].equals(store)) && (lineList[1].equals(calendar))) {
                    windows.add(lineList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String[]> available = new ArrayList<>();
        for (int i = 0; i < windows.size(); i++) {
            if ((Integer.parseInt(windows.get(i)[6])) - (Integer.parseInt(windows.get(i)[7])) >= requestingBooking) {
                available.add(windows.get(i));
            }
        }

        int choice = 0;
        System.out.println("Choose the time that you wish to make the appointment: ");
        for (int j = 0; j < available.size(); j++) {
            System.out.printf("[%d]: %s:%s - %s:%s", j + 1, available.get(j)[4].substring(0, 2),
                    available.get(j)[4].substring(2, 4), available.get(j)[5].substring(0, 2),
                    available.get(j)[4].substring(2, 4));
        }
        integer = true;
        do {
            String input = scan.nextLine();
            try {
                choice = Integer.parseInt(input) - 1;
            } catch (Exception e) {
                integer = false;
            }
        } while(!integer);
        String[] chosenWindow = available.get(choice);

        System.out.println("How would you like to title the appointment?");
        String appointmentDescription = scan.nextLine();

        String sellerEmail = null;
        try (BufferedReader br = new BufferedReader(new FileReader("stores.csv"))) {
            while (br.readLine() != null) {
                String[] lineList = br.readLine().split(",");
                if (lineList[1].equals(store)) {
                    sellerEmail = lineList[0];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        customerEmail,sellerEmail,storeName,calendarName,startTime,endTime,booking,isApproved,isRequest,timeStamp
        String request = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %b, %b, %s", getEmail(), sellerEmail,
                store, calendar, chosenWindow[4], chosenWindow[5], requestingBooking, false, true,
                LocalDateTime.now());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("appointments.csv", true))) {
            bw.write(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/
    public void export(ArrayList<String[]> approved, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < approved.size(); i++) {
                String printing = "";
                for (int j = 0; j < approved.get(i).length; j++) {
                    printing += approved.get(i)[j];
                    printing += ",";
                }
                bw.write(printing.substring(0, printing.length() - 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void cancel(Scanner sc) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("appointments.csv"));
            String line = br.readLine();
            String[] temp;
            int count = 0;
            ArrayList<String[]> appointments = new ArrayList<String[]>();
            System.out.println("Please select an appointment to cancel:");
            while (line != null) {
                temp = line.split(",");
                if (temp[0].equals(getEmail()) && temp[7].equals("false") &&
                        temp[8].equals("true")) {
                    appointments.add(temp);
                    System.out.println("[" + count + "] " + temp[2] + ", " + temp[3]
                    + ", " + temp[4] + "-" + temp[5] + ", " + temp[6] + " attending");
                    count++;
                }
                line = br.readLine();
            }
            if (appointments.isEmpty()) {
                System.out.println("No appointments to cancel.");
                return;
            }
            br.close();
            br = new BufferedReader(new FileReader("appointments.csv"));
            String[] select = appointments.get(sc.nextInt());
            sc.nextLine();
            System.out.println(Arrays.toString(select));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.csv"));
            line = br.readLine();
            boolean removed = false;
            while ((line != null)) {
                temp = line.split(",");
                if (temp[0].equals(getEmail()) && temp[7].equals("false") &&
                        temp[8].equals("true") && temp[2].equals(select[2]) &&
                        temp[3].equals(select[3]) && temp[4].equals(select[4]) &&
                        temp[5].equals(select[5]) && temp[6].equals(select[6]) && (!removed)) {
                    removed = true;
                } else {
                    bw.write(line + "\n");
                }
                line = br.readLine();
            }
            br.close();
            bw.close();
            Files.move(Paths.get("temp.csv"),Paths.get("appointments.csv"), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String popularStores(Scanner scan) {
        String stats = "";
        try (BufferedReader br = new BufferedReader(new FileReader("appointments.csv"))) {
            ArrayList<String> allStoreBookings = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                String[] splitted = line.split(",");
                allStoreBookings.add(splitted[2]+","+splitted[6]);
            }
            Collections.sort(allStoreBookings);
            if (allStoreBookings.isEmpty()) {
                return "No stores made yet";
            }

            //gets the total number of bookings for each store and adds them to eachStoreBookings
            String currentStore = allStoreBookings.get(0).split(",")[0];
            int storeBookings = 0;
            ArrayList<String> eachStoreBooking = new ArrayList<>();
            for(int i = 1; i < allStoreBookings.size(); i++){
                String[] splitted = allStoreBookings.get(i).split(",");
                storeBookings += Integer.parseInt(splitted[1]);
                if (!splitted[0].equals(currentStore)) {
                    eachStoreBooking.add(currentStore+":"+String.format("%d", storeBookings));
                    currentStore = splitted[0];
                    storeBookings = 0;
                }
            }

            for (int i = 0; i < eachStoreBooking.size(); i++) {
                stats += eachStoreBooking.get(i) + "\n";
            }

            String isSorted;
            do {
                System.out.println("Do you want the dashboard to be sorted or not? \n[Y] \n[N]");
                isSorted = scan.nextLine();
                switch (isSorted) {
                    case "y":
                        stats = "";
                        ArrayList<String> sortedBookings = new ArrayList<>();
                        while(!eachStoreBooking.isEmpty()) {
                            int max = 0;
                            int index = 0;
                            for (int i = 0; i < eachStoreBooking.size(); i++) {
                                if (Integer.parseInt(eachStoreBooking.get(i).split(":")[1]) > max) {
                                    max = Integer.parseInt(eachStoreBooking.get(i).split(",")[1]);
                                    index = i;
                                }
                            }
                            sortedBookings.add(eachStoreBooking.get(index));
                            eachStoreBooking.remove(index);
                        }

                        for(int i = 0; i < sortedBookings.size(); i++) {
                            stats += sortedBookings.get(i);
                        }
                        break;
                    case "n":
                        return stats;
                    default:
                        System.out.println("Error! Invalid input, try again");
                        break;
                }
            } while (!isSorted.equals("y"));
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return stats;
    }


    public String popularAppointment(Scanner scan) {
        String popular = "";
        try(BufferedReader br = new BufferedReader(new FileReader("appointments.csv"))) {
            ArrayList<String> allStoreBookings = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                String[] splitted = line.split(",");
                allStoreBookings.add(splitted[0]+":"+splitted[5]+":"+splitted[7]);
            }

            //gets the appointment windows with the largest bookings.
            String currentStore = allStoreBookings.get(0).split(",")[0];
            int maxBooking = 0;
            ArrayList<String> popularAppointment = new ArrayList<>();
            for (int i = 0; i < allStoreBookings.size(); i++) {
                String[] splitted = allStoreBookings.get(i).split(",");
                if(Integer.parseInt(splitted[2]) > maxBooking) {
                    maxBooking = Integer.parseInt(splitted[2]);
                }
                if(!splitted[0].equals(currentStore)) {
                    popularAppointment.add(currentStore+","+splitted[1]+","+String.format("%d", maxBooking));
                    maxBooking = 0;
                    currentStore = splitted[0];
                }
            }

            for(int i = 0; i < popularAppointment.size(); i++) {
                popular += popularAppointment.get(i) + "\n";
            }

            String isSorted;
            do {
                System.out.println("Do you want to sort the popular ");
                isSorted = scan.nextLine().toLowerCase();
                switch (isSorted) {
                    case "y":
                        popular = "";
                        ArrayList<String> sortedPopular = new ArrayList<>();
                        while(!popularAppointment.isEmpty()) {
                            int max = 0;
                            int index = 0;
                            for(int i = 0; i < popularAppointment.size(); i++){
                                if (max < Integer.parseInt(popularAppointment.get(i).split(":")[2])){
                                    max = Integer.parseInt(popularAppointment.get(i).split(":")[2]);
                                    index = i;
                                }
                            }
                            sortedPopular.add(popularAppointment.get(index));
                            popularAppointment.remove(index);
                        }

                        for (int i = 0; i < sortedPopular.size(); i++) {
                            popular += sortedPopular.get(i);
                        }
                        break;
                    case "n":
                        return popular;
                    default:
                        System.out.println("Error! Invalid input, try again");
                }
            } while (!isSorted.equals("n") && !isSorted.equals("y"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return popular;
    }

}
