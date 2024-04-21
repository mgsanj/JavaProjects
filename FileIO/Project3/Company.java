/**
 * Project 3: Company
 * Records data associated with an individual company.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 27, 2023
 */
public class Company {
    private String name;
    private int[] prices;

    public Company(String name, int[] prices) {
        this.name = name;
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }
}
