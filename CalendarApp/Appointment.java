public class Appointment {
    public String title;
    public int maxAttendees;
    public int currentBookings;
    public int startTime; //military timing
    public int endTime; //military timing
    public boolean approved;

    public Appointment(String title, int maxAttendees, int currentBookings, int startTime, int endTime) {
        this.title = title;
        this.maxAttendees = maxAttendees;
        this.currentBookings = currentBookings;
        this.startTime = startTime;
        this.endTime = endTime;
        this.approved = false;
    }

    public String getTitle() {
        return this.title;
    }

    public int getMaxAttendees() {
        return this.maxAttendees;
    }

    public int getCurrentBooking() {
        return this.currentBookings;
    }

    public int getStartTime(){ 
        return this.startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Appointment) {
            Appointment appointment = (Appointment) obj;
            if ( appointment.getTitle().equals(this.title) &&
                    appointment.getMaxAttendees() == this.maxAttendees &&
                    appointment.getCurrentBooking() == this.currentBookings &&
                    appointment.getStartTime() == this.startTime &&
                    appointment.getEndTime() == this.endTime ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String toString() {
        return "Title: " + title + "\n" +
               "Max Attendees: " + String.format("%d", maxAttendees) + "\n" +
               "Current number of bookings: " + String.format("%d", currentBookings) + "\n" +
               "Start Time (in military time): " + String.format("%d", startTime) + " | End Time (in military time): " + String.format("%d", endTime) + "\n";
    }

}
