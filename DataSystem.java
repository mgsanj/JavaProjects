import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;
/**
 * Project 3: DataSystem
 * Includes all logic used to actually process the logs.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 27, 2023
 */
public class DataSystem {
    public static void main(String[] args) {
        String file = args[0];
        try {
            Validator.checkFile(file);
            ArrayList<String> listCompanies = new ArrayList<>();
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            int counter = 0, maxVal = 0, minVal = 0, compNum = 0;
            while (line != null) {
                if (counter == 0) {
                    maxVal = Validator.checkValueFormat(line, "MaxValue");
                    maxVal = Validator.checkPrice(maxVal);
                    counter++;
                } else if (counter == 1) {
                    minVal = Validator.checkValueFormat(line, "MinValue");
                    minVal = Validator.checkPrice(minVal);
                    counter++;
                } else if (counter == 2) {
                    compNum = Validator.checkValueFormat(line, "CompanyNumberValue");
                    compNum = Validator.checkPrice(compNum);
                    counter++;
                } else {
                    listCompanies.add(line);
                }
                line = bfr.readLine();
            }
            bfr.close();

            ArrayList<Company> companies = new ArrayList<Company>(compNum);
            String compName;
            int[] compPrice;
            for (int i = 0; i < compNum; i++) {
                compName = listCompanies.get(i).split(":")[0];
                String[] compPrices = listCompanies.get(i).split(":")[1].split(",");
                compPrice = new int[compPrices.length];
                for (int j = 0; j < compPrices.length; j++) {
                    try {
                        Validator.checkPrice(Integer.parseInt(compPrices[j]));
                        compPrice[j] = Integer.parseInt(compPrices[j]);
                    } catch (InvalidPriceException e) {
                        e.printStackTrace();
                    }
                }
                companies.add(new Company(compName, compPrice));
            }
            Report report1 = new Report(minVal, maxVal, companies);

            report1.generateReport();
            report1.generateReportMax();
        } catch (FileNotFoundException e) {
            System.out.println(file + " does not exist. Please provide a valid file name.");
        } catch (WrongFormatException e) {
            e.printStackTrace();
        } catch (InvalidPriceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
