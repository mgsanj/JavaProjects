/**
 * A program that represents a Rollercoaster and extends the class Ride
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 24, 2023
 */
public class Rollercoaster extends Ride {
    private boolean simulated;

    public Rollercoaster(String name, String color, int minHeight, int maxRiders, boolean simulated) {
        super(name, color, minHeight, maxRiders);
        this.simulated = simulated;
    }

    public boolean isSimulated() {
        //Returns whether the rollercoaster is simulated or not
        return simulated;
    }

    public void setSimulated(boolean simulated) {
        //Sets the simulated instance variable to the boolean given as a parameter
        this.simulated = simulated;
    }

    @Override
    public boolean equals(Object o) {
        //Determines whether or not the object given as a parameter is equal to this rollercoaster. true is returned
        // if the specified object is an instance of Rollercoaster and the name, color, minHeight, maxRiders, and
        // simulated value of the given rollercoaster are equal to the name, color, minHeight, maxRiders, and
        // simulated value of this rollercoaster.
        try {
            Rollercoaster ride = (Rollercoaster) o;
            return ride.getName().equals(super.getName()) && ride.getColor().equals(super.getColor()) &&
                    ride.getMinHeight() == super.getMinHeight() && ride.getMaxRiders() == super.getMaxRiders() &&
                    ride.isSimulated() == simulated;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        //Returns the String representation of this rollercoaster.
        return "Name: " + super.getName() + "\n" +
                "Color" + super.getColor() + "\n" +
                "MinHeight: " + super.getMinHeight() + " inches\n" +
                "MaxRiders: " + super.getMaxRiders() + "\n" +
                "Simulated: " + simulated + "\n";
    }

}
