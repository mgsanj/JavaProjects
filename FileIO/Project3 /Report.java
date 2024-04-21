import java.io.PrintWriter;
import java.util.ArrayList;
/**
 * Project 3: Report
 * This class records data associated with the generated reports.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 27, 2023
 */
public class Report {
    private int minPrice;
    private int maxPrice;
    private ArrayList<Company> companyList;

    public Report(int minPrice, int maxPrice, ArrayList<Company> companyList) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.companyList = companyList;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public ArrayList<Company> getCompanyList() {
        return companyList;
    }

    public void generateReport() {
        //Generates an output file named "Report.txt" using the format described in the next section.
        try {
            PrintWriter writer = new PrintWriter("Report.txt", "UTF-8");
            for (int i = 0; i < companyList.size(); i++) {
                Company comp = companyList.get(i);
                writer.println(comp.getName() + " Report");
                int[] prices = comp.getPrices();
                int counter = 0;
                for (int j = 0; j < prices.length; j++) {
                    if (prices[j] > maxPrice) {
                        writer.println("Above Maximum Price at " + j + " with " + prices[j] + ".");
                        counter++;
                    } else if (prices[j] < minPrice) {
                        writer.println("Below Minimum Price at " + j + " with " + prices[j] + ".");
                        counter++;
                    }
                }
                if (counter == 0) {
                    writer.println("All prices are within the range.");
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateReportMax() {
        //Generates an output file named "ReportMax.txt" using the format described in the next section.
        try {
            PrintWriter writer = new PrintWriter("ReportMax.txt", "UTF-8");
            for (int i = 0; i < companyList.size(); i++) {
                Company comp = companyList.get(i);
                int max = 0;
                int[] prices = comp.getPrices();
                for (int j = 0; j < prices.length; j++) {
                    if (prices[j] > max) {
                        max = prices[j];
                    }
                }
                writer.println(comp.getName() + "-" + max);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
