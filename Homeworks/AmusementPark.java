import java.util.ArrayList;
/**
 * A program that represents an Amusement Park and implements the interface Park
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 24, 2023
 */
public class AmusementPark extends Object implements Park {
    private String name;
    private double admissionCost;
    private double land;
    private boolean outdoor;
    private boolean arcade;
    private boolean bowling;
    private boolean indoor;
    private ArrayList<Ride> rides;
    private boolean[] seasons;

    public AmusementPark (String name, double admissionCost, double land, ArrayList<Ride> rides, boolean indoor, boolean outdoor, boolean arcade, boolean bowling, boolean[] seasons) {
        this.name = name;
        this.admissionCost = admissionCost;
        this.land = land;
        this.rides = rides;
        this.indoor = indoor;
        this.outdoor = outdoor;
        this.arcade = arcade;
        this.bowling = bowling;
        this.seasons = seasons;
    }


    public double getAdmissionCost() {
        //Returns the admission cost of the amusement park
        return admissionCost;
    }

    public void setAdmissionCost(double admissionCost) {
        this.admissionCost = admissionCost;
    }

    public double getLand() {
        //Returns the amount of land the amusement park covers in acres
        return land;
    }

    public String getName() {
        //Returns the name of the amusement park
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ride> getRides() {
        //Returns the list of rides in the amusement park
        return rides;
    }

    public boolean[] getSeasons() {
        //Returns the array representing the seasons when the amusement park is open and closed
        return seasons;
    }

    public void setSeasons(boolean[] seasons) {
        this.seasons = seasons;
    }

    public boolean isArcade() {
        //Returns whether or not the amusement park has an arcade
        return arcade;
    }

    public void setArcade(boolean arcade) {
        this.arcade = arcade;
    }

    public boolean isBowling() {
        //Returns whether or not the amusement park has bowling
        return bowling;
    }

    public void setBowling(boolean bowling) {
        this.bowling = bowling;
    }

    public boolean isIndoor() {
        //Returns whether or not the amusement park has an indoor component
        return indoor;
    }

    public boolean isOutdoor() {
        //Returns whether or not the amusement park has an outdoor component
        return outdoor;
    }

    public void addRide(Ride ride) throws WrongRideException {
        //Adds a new ride to the end of the list of rides in the amusement park, an amusement park can only have
        // rollercoasters so throw a WrongRideException if the ride to be added is not a rollercoaster
        try {
            Rollercoaster ride1 = (Rollercoaster) ride;
            rides.add(ride1);
        } catch (ClassCastException e) {
            throw new WrongRideException("An amusement park can only have rollercoaster rides!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyRide(Ride ride, String newName, String newColor, int newMinHeight, int newMaxRiders, boolean newSimulated) {
        //Modifies the ride given as input to have the name, color, minHeight, maxRiders, and simulated boolean provided
        // as parameters. The ride should remain at the same position in the list of rides where it was previously.
        // Note: It can be assumed the ride exists in the list
        Rollercoaster ride1 = (Rollercoaster) ride;
        for (int i = 0; i < rides.size(); i++) {
            if (rides.get(i).equals(ride1)) {
                rides.remove(i);
                rides.add(i, new Rollercoaster(newName, newColor, newMinHeight, newMaxRiders, newSimulated));
            }
        }

    }

    public void removeRide(Ride ride) {
        //Removes the ride given as an input parameter from the list of rides
        Rollercoaster ride1 = (Rollercoaster) ride;
        for (int i = 0; i < rides.size(); i++) {
            if (rides.get(i).equals(ride1)) {
                rides.remove(i);
            }
        }
    }

    public void enlarge(double addedLand, double maxLand, boolean addedIndoor, boolean addedOutdoor) throws SpaceFullException {
        //Enlarges the amusement park by adding a certain amount of land to the park. If the amount of land already
        // used combined with the amount of land to be added exceeds the maximum amount of land the park can use,
        // throw a SpaceFullException. If the existing land does not have either an indoor or outdoor component but
        // the added land does, adjust the indoor and outdoor instance variables accordingly.
        if (land + addedLand > maxLand) {
            throw new SpaceFullException("There is no more land to use for this park!");
        } else {
            System.out.println("Prior to enlarging park: Indoor = " + indoor + ", Outdoor = " + outdoor);
            if (!addedIndoor && addedOutdoor) {
                System.out.println("Newly added area is completely outdoor: Indoor = false, Outdoor = true");
            } else if (addedIndoor && !addedOutdoor) {
                System.out.println("Newly added area is completely indoor: Indoor = true, Outdoor = false");
            } else if (addedIndoor && addedOutdoor) {
                System.out.println("Newly added area is both indoor and outdoor: Indoor = true, Outdoor = true");
            }
            if (addedIndoor) {
                indoor = addedIndoor;
            }
            if (addedOutdoor) {
                outdoor = addedOutdoor;
            }
            if (indoor && outdoor) {
                System.out.println("Overall park is now both indoor and outdoor: Indoor = true, Outdoor = true");
            } else if (!indoor && outdoor) {
                System.out.println("Overall park is now completely outdoor: Indoor = false, Outdoor = true");
            } else if (indoor && !outdoor) {
                System.out.println("Overall park is now completely indoor: Indoor = true, Outdoor = false");
            }

        }
    }

    public void close() {
        //Closes the park by setting name to an empty string, admissionCost and land to 0, rides and seasons to null, and indoor, outdoor, arcade, and bowling to false
        name = "";
        admissionCost = 0;
        land = 0;
        rides = null;
        seasons = null;
        indoor = false;
        outdoor = false;
        arcade = false;
        bowling = false;
    }

}
