import java.util.ArrayList;
/**
 * Project 4 -- Calendar
 *
 * This class holds appointments, a title, and a description.
 * It also holds requests for each appointment.
 *
 * @author Ashish Chenna, 
 *         Sanjana Gadaginmath,
 *         Ian Lam,
 *         Gunyoung Park, lab sec 12
 *
 * @version November 13, 2023
 *
 */

public class Calendar {
    public ArrayList<Appointment> appointments;
    public ArrayList<Request> requests;
    public String title;
    public String description;

    public Calendar(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String displaySortedAppointments() {
        String appoints = "";
        ArrayList<Appointment> unsorted = appointments;
        ArrayList<Appointment> sorted = new ArrayList<>();
        while (unsorted.size() > 0) {
            int min = 2400;
            int index = 0;
            for (int i = 0; i < unsorted.size(); i++) {
                if (unsorted.get(i).getStartTime() < min) {
                    min = unsorted.get(i).getStartTime();
                    index = i;
                }
            }
            sorted.add(unsorted.get(index));
            unsorted.remove(index);
        }
        for (int i = 0; i < sorted.size(); i++) {
            appoints += sorted.get(i).toString();
        }
        return appoints;
    }

    public String toString() {
        return this.title + "|" + this.description + "\n" +
                displaySortedAppointments() + "\n";
    }
    
}
