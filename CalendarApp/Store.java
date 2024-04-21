import java.util.ArrayList;

public class Store {
    private ArrayList<Calendar> calendars;

    private String storeName;

    public Store(String storeName) {
        this.storeName = storeName;
        this.calendars = new ArrayList<Calendar>();
    }

    public Store(String storeName, ArrayList<Calendar> calendars) {
        this.storeName = storeName;
        this.calendars = calendars;
    }

    public String getStoreName() {
        return storeName;
    }

    public ArrayList<Calendar> getCalendars() {
        return calendars;
    }

    public void addCalendar(Calendar calendar) {
        calendars.add(calendar);
    }

    public boolean removeCalendar(Calendar calendar) {
        return calendars.remove(calendar);
    }

    public String toString() {
        String cals = "";
        for (int i = 0; i < calendars.size(); i++) {
            cals += calendars.toString();
        }
        return cals;
    }

}
