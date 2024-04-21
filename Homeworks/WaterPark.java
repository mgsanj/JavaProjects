import java.util.ArrayList;
/**
 * A program that represents a Water Park and implements the interface Park
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 24, 2023
 */
public class WaterPark extends Object implements Park {
    private double admissionCost;
    private boolean indoor;
    private double land;
    private boolean lazyRiver;
    private String name;
    private boolean outdoor;
    private ArrayList<Ride> rides;
    private boolean[] seasons;
    private boolean wavePool;

    public WaterPark(String name, double admissionCost, double land, ArrayList<Ride> rides, boolean indoor, boolean outdoor, boolean lazyRiver, boolean wavePool, boolean[] seasons) {
        this.name = name;
        this.admissionCost = admissionCost;
        this.land = land;
        this.rides = rides;
        this.indoor = indoor;
        this.outdoor = outdoor;
        this.lazyRiver = lazyRiver;
        this.wavePool = wavePool;
        this.seasons = seasons;
    }

    public double getAdmissionCost() {
        //Returns the admission cost of the water park
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ride> getRides() {
        return rides;
    }

    public boolean[] getSeasons() {
        return seasons;
    }

    public void setSeasons(boolean[] seasons) {
        this.seasons = seasons;
    }
    public boolean isIndoor() {
        return indoor;
    }

    public boolean isLazyRiver() {
        return lazyRiver;
    }

    public void setLazyRiver(boolean lazyRiver) {
        this.lazyRiver = lazyRiver;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public boolean isWavePool() {
        return wavePool;
    }

    public void setWavePool(boolean wavePool) {
        this.wavePool = wavePool;
    }

    public void addRide(Ride ride) throws WrongRideException {
        //Adds a new ride to the end of the list of rides in the water park, a water park can only have waterslides so
        // throw a WrongRideException if the ride to be added is not a waterslide
        try {
            Waterslide ride1 = (Waterslide) ride;
            rides.add(ride1);
        } catch (ClassCastException e) {
            throw new WrongRideException("An water park can only have waterslide rides!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyRide(Ride ride, String newName, String newColor, int newMinHeight, int newMaxRiders, double newSplashDepth) {
        //Modifies the ride given as input to have the name, color, minHeight, maxRiders, and simulated boolean provided
        // as parameters. The ride should remain at the same position in the list of rides where it was previously.
        //Note: It can be assumed the ride exists in the list
        Waterslide ride1 = (Waterslide) ride;
        for (int i = 0; i < rides.size(); i++) {
            if (rides.get(i).equals(ride1)) {
                rides.remove(i);
                rides.add(i, new Waterslide(newName, newColor, newMinHeight, newMaxRiders, newSplashDepth));
            }
        }
    }

    public void removeRide(Ride ride) {
        //Removes the ride given as an input parameter from the list of rides
        //Note: It can be assumed the ride exists in the list
        Waterslide ride1 = (Waterslide) ride;
        for (int i = 0; i < rides.size(); i++) {
            if (rides.get(i).equals(ride1)) {
                rides.remove(i);
            }
        }
    }

    public void enlarge(double addedLand, double maxLand, boolean addedIndoor, boolean addedOutdoor) throws SpaceFullException{
        //Enlarges the water park by adding a certain amount of land to the park.
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
        //Closes the park by setting name to an empty string, admissionCost and land to 0, rides and seasons to null,
        // and indoor, outdoor, lazyRiver, and wavePool to false
        name = "";
        admissionCost = 0;
        land = 0;
        rides = null;
        seasons = null;
        indoor = false;
        outdoor = false;
        lazyRiver = false;
        wavePool = false;
    }

}
