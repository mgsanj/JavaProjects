/**
 * Midterm 2: FirstPersonPerspective.java
 * A public class that extends VideoGame.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 30, 2023
 */
public class FirstPersonPerspective extends VideoGame {
    private int gameModeCount;
    private double sales;

    public FirstPersonPerspective(String projectName, int employeeCount, boolean priority, int gameModeCount,
                                  double sales) {
        super(projectName, employeeCount, priority);
        if (gameModeCount > 0) {
            this.gameModeCount = gameModeCount;
        } else {
            throw new IllegalArgumentException();
        }
        this.sales = sales;
    }

    public int getGameModeCount() {
        return gameModeCount;
    }

    public double getSales() {
        return sales;
    }

    public double calculateAverageGameModeSales() {
        return (sales / gameModeCount);
    }

    public double calculateReturnOnInvestment(double averageSalary, int months) {
        return (sales - calculateCostEstimate(averageSalary, months));
    }

}
