import javax.xml.crypto.Data;
import java.nio.file.Files;

//    you can choose to create a main method to accomplish a similar goal. Simply call each method being tested and
//    save the result, then compare it to an expected value. You should pair each class created in your solution with
//    a corresponding testing class.

//          Calendar:
//      Any number of appointment calendars may be added to a store.
//      A calendar title and description must be listed at the top of the page.
//      Appointment windows will be listed below the title ordered by time.
//      Details on the appointments will appear beneath each entry.
//          Title of the appointment
//          Maximum number of attendees.
//          Current number of approved bookings.
//          Start and end time
//      All created text content must display a timestamp tracking the most recent modification.

//          Sellers:
//      Sellers can create, edit, and delete calendars with individual appointment windows.
//      Sellers can approve or decline customer appointment requests.
//      Sellers can view a list of currently approved appointments by store.

//          Customers:
//      Customers can view any of the created calendars for a store.
//      Customers can make or cancel appointment requests.
//      Customers can view a list of their currently approved appointments.

//          Files
//      All file imports must occur as a prompt to enter the file path.
//      Sellers can import a csv file with a calendar to automatically create a calendar and appointment window list.
//      Customers can export a file with all of their approved appointments.

//          Statistics
//      Sellers can view a dashboard that lists statistics for each of their stores.
//          Data will include a list of customers with the number of approved appointments they made and the most
//          popular appointment windows by store.
//          Sellers can choose to sort the dashboard.
//      Customers can view a dashboard with store and seller information.
//          Data will include a list of stores by number of customers and the most popular appointment windows by store.
//          Customers can choose to sort the dashboard.

public class Test {
    public static void main(String[] args) {
        Seller testSeller = new Seller();
        System.out.println("Test #1: Add an appointment calendar to a store.");
        System.out.println("Note - This can only by done by a seller, not a customer.");

        System.out.println("Test #2: View the Calendar - for both customer and seller");

        System.out.println("Test #3: Edit calendar");

        System.out.println("Test #4: Delete Calendar");

        System.out.println("Test #5: Approve / Decline requests");

        System.out.println("Test #6: View Approved Appointment by Store");

        System.out.println("Test #7: Make request");

        System.out.println("Test #8: Cancel request");

        System.out.println("Test #9: Import Calendar");

        System.out.println("Test #10: Export approved appointments");

        System.out.println("Test #11: Statistics");


    }
}
