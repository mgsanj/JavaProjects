import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.file.*;
public class TestSeller {
    public static void main(String[] args) {
        Path calBackup = Paths.get("calendarsCopy.csv");
        Path winBackup = Paths.get("windowsCopy.csv");
        Path appBackup = Paths.get("appointmentsCopy.csv");
        File calendars = new File("calendars.csv");
        File windows = new File("windows.csv");
        File appointments = new File("appointments.csv");
        Path calPath = calendars.toPath();
        Path winPath = windows.toPath();
        Path appPath = appointments.toPath();
        try {
            Files.copy(calPath, calBackup, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(winPath, winBackup, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(appPath, appBackup, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        Seller testSeller = new Seller("testseller@gmail.com","1234567890");

        try {

            System.out.println("Test #1: Add an appointment calendar to a store.");
            System.out.println("Note - This can only by done by a seller, not a customer.");
            System.out.println("Please enter 'testStoreCreation':");
            String sName = sc.nextLine();
            test1(testSeller, sName);

            System.out.println("Test #2: Create a calendar to our test store. Name the calendar 'testCalendar'. Then try " +
                    "making a second calendar with the same name. Once rejected, name the second calendar 'testCalendar2'.");
            System.out.println("For both calendars, make a window with title 'title', description 'description', start " +
                    "time '1', end time '2', and max attendees '3'.");
            test2(testSeller, sc);

            System.out.println("Test #3: Select testStoreCreation, then delete testCalendar2 from it.");
            test3(testSeller, sc);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Files.move(calBackup,calPath,StandardCopyOption.REPLACE_EXISTING);
            Files.move(winBackup,winPath,StandardCopyOption.REPLACE_EXISTING);
            Files.move(appBackup,appPath,StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void test1 (Seller testSeller, String sName) {
        testSeller.createStore("testStoreCreation");
        try (BufferedReader br = new BufferedReader(new FileReader("stores.csv"))) {
            ArrayList<String[]> lines = new ArrayList<String[]>();
            String line = br.readLine();
            while (line != null) {
                lines.add(line.split(","));
                line = br.readLine();
            }
            String correct = "testseller@gmail.com," + sName + ",tests";
            String date = new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date());
            correct += date;
            String[] newLine = lines.get(lines.size() - 1);
            String test = newLine[0] + "," + newLine[1] + "," + date;
            if (test.equals(correct)) {
                System.out.println("Test #1 successful!");
            } else {
                System.out.println("Incorrect output. Test #1 failed.");
            }
        } catch (Exception e) {
            System.out.println("Test #1 failed.");
            e.printStackTrace();
        }
    }
    public static void test2 (Seller testSeller, Scanner sc) {
        String store = Main.chooseStore(sc, "testseller@gmail.com");
        testSeller.createCalendar(sc, store);
        testSeller.createCalendar(sc, store);

        try (BufferedReader br = new BufferedReader(new FileReader("windows.csv"))) {
            ArrayList<String[]> lines = new ArrayList<String[]>();
            String line = br.readLine();
            while (line != null) {
                lines.add(line.split(","));
                line = br.readLine();
            }
            String correct1 = "testStoreCreation,testCalendar,";
            String correct2 = "testStoreCreation,testCalendar2,";
            String date = new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date());
            correct1 += date;
            correct2 += date;

            String[] newLine1 = lines.get(lines.size() - 2);
            String[] newLine2 = lines.get(lines.size() - 1);
            String testing1 = newLine1[0] + "," + newLine1[1] + "," + date;
            String testing2 = newLine2[0] + "," + newLine2[1] + "," + date;
            if (testing1.equals(correct1) && testing2.equals(correct2)) {
                System.out.println("Test #2 successful!");
            } else if (testing2.equals(testing1)) {
                System.out.println("Duplicate calendar created. Test #2 failed.");
            } else {
                System.out.println("Incorrect output. Test #2 failed.");

            }
        } catch (Exception e) {
            System.out.println("Test #2 failed.");
            e.printStackTrace();
        }
    }

    public static void test3(Seller testSeller, Scanner sc) {
        String store = Main.chooseStore(sc, testSeller.getEmail());
        if (!store.equals("empty")) {
            String calendar = Main.chooseCalendar(sc, store);
            if (!calendar.equals("empty")) {
                testSeller.deleteCalendar(store, calendar);
                System.out.println("Deletion successful!");
            } else {
                System.out.println("No calendars for this store!");
            }
        } else {
            System.out.println("No stores!");
        }
        String date = new SimpleDateFormat("yyyy.MM.dd.HH").format(new java.util.Date());
        String[] deletedCal = new String[] {"testStoreCreation","testCalendar2",date};
        ArrayList<String[]> lines = new ArrayList<String[]>();
        try (BufferedReader br = new BufferedReader(new FileReader("calendars.csv"))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line.split(","));
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean success = true;
        for (String[] s : lines) {
            if (Arrays.equals(s,deletedCal)) {
                success = false;
            }
        }
        if (success) {
            System.out.println("Deletion verified. Test #3 successful!");
        } else {
            System.out.println("Deletion failed. Test #3 failed.");
        }

    }
}

