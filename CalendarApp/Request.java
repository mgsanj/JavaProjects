public class Request extends Appointment {
    private Customer customer;

    public Request(String title, int maxAttendees, int currentBookings, int startTime, int endTime, Customer customer) {
        super(title, maxAttendees, currentBookings, startTime, endTime);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Max Attendees: " + String.format("%d", maxAttendees) + "\n" +
                "Current number of bookings: " + String.format("%d", currentBookings) + "\n" +
                "Start Time (in military time): " + String.format("%d", startTime) + " | End Time (in military time): "
                + String.format("%d", endTime) + "\n" +
                "Customer Email: " + customer.getEmail() + "\n";
    }

}
