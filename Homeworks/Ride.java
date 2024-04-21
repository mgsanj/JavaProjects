/**
 * A program that represents a Ride
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 24, 2023
 */
public class Ride extends Object {
    private String color;
    private int maxRiders;
    private int minHeight;
    private String name;

    public Ride() {
        this.color = "";
        this.name = "";
        this.maxRiders = 0;
        this.minHeight = 0;
    }

    public Ride(String name, String color, int minHeight, int maxRiders) {
        this.name = name;
        this.color = color;
        this.minHeight = minHeight;
        this.maxRiders = maxRiders;
    }

    public String getColor() {
        //Returns the color of the ride
        return color;
    }

    public void setColor(String color) {
        //Sets the color instance variable to the String given as a parameter
        this.color = color;
    }

    public int getMaxRiders() {
        //Returns the maximum number of people that can go on the ride at once
        return maxRiders;
    }

    public void setMaxRiders(int maxRiders) {
        //Sets the maxRiders instance variable to the value given as a parameter
        this.maxRiders = maxRiders;
    }

    public int getMinHeight() {
        //Returns the minimum height in inches required to go on the ride
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        //Sets the minHeight instance variable to the value given as a parameter
        this.minHeight = minHeight;
    }

    public String getName() {
        //Returns the name of the ride
        return name;
    }

    public void setName(String name) {
        //Sets the name instance variable to the String given as a parameter
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        //Determines whether the object given as a parameter is equal to this ride or not. true is returned if the
        // specified object is an instance of Ride and the name, color, minHeight, and maxRiders of the given ride are
        // equal to the name, color, minHeight and maxRiders of this ride.
        try {
            Ride ride = (Ride) o;
            return ride.getName().equals(name) && ride.getColor().equals(color) && ride.getMinHeight() == minHeight &&
                    ride.getMaxRiders() == maxRiders;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        //Returns the String representation of this ride.
        return "Name: " + name + "\n" +
                "Color" + color + "\n" +
                "MinHeight: " + minHeight + " inches\n" +
                "MaxRiders: " + maxRiders  + "\n";

    }

}
