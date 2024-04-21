import java.util.ArrayList;
/**
 * A interface that outlines the methods of a Park
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 24, 2023
 */
public interface Park {

    public double getAdmissionCost();
    public void setAdmissionCost(double admissionCost);
    public double getLand();
    public String getName();
    public void setName(String name);
    public ArrayList<Ride> getRides();
    public boolean isIndoor();
    public boolean isOutdoor();
    public boolean[] getSeasons();
    public void setSeasons(boolean[] seasons);
    public void addRide(Ride ride) throws WrongRideException;
    public void enlarge(double addedLand, double maxLand, boolean addedIndoor, boolean addedOutdoor) throws SpaceFullException;
    public void removeRide(Ride ride);
    public void close();


}