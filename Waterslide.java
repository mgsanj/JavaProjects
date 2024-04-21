/**
 * A program that represents a Waterslide and extends the class Ride
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 24, 2023
 */
public class Waterslide extends Ride {
    private double splashDepth;

    public Waterslide(String name, String color, int minHeight, int maxRiders, double splashDepth) {
        super(name, color, minHeight, maxRiders);
        this.splashDepth = splashDepth;
    }

    public double getSplashDepth() {
        //Returns the depth at the bottom of the waterslide in feet
        return splashDepth;
    }

    public void setSplashDepth(double splashDepth) {
        //Sets the splashDepth instance variable to the value given as a parameter
        this.splashDepth = splashDepth;
    }

    @Override
    public boolean equals(Object o) {
        //Determines whether or not the object given as a parameter is equal to this waterslide.
        try {
            Waterslide slide = (Waterslide) o;
            return slide.getName().equals(super.getName()) && slide.getColor().equals(super.getColor()) &&
                    slide.getMinHeight() == super.getMinHeight() && slide.getMaxRiders() == super.getMaxRiders() &&
                    slide.getSplashDepth() == splashDepth;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        //Returns the String representation of this waterslide.
        return "Name: " + super.getName() + "\n" +
                "Color" + super.getColor() + "\n" +
                "MinHeight: " + super.getMinHeight() + " inches\n" +
                "MaxRiders: " + super.getMaxRiders() + "\n" +
                "SplashDepth: " + splashDepth + " feet\n";
    }
}