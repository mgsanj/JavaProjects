import java.util.ArrayList;
/**
 * Midterm 2: VideoGame.java
 * A public class that implements AnimatedProject.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 30, 2023
 */
public class VideoGame implements  AnimatedProject {
    private String projectName;
    private int employeeCount;
    private boolean priority;

    public VideoGame(String projectName, int employeeCount, boolean priority) throws NullPointerException,
            IllegalArgumentException{
        if (projectName != null) {
            this.projectName = projectName;
        } else {
            throw new NullPointerException();
        }
        if (employeeCount >= 0) {
            this.employeeCount = employeeCount;
        } else {
            throw new IllegalArgumentException();
        }
        this.priority = priority;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public boolean hasPriority() {
        return priority;
    }

    public double calculateCostEstimate(double averageSalary, int months) {
        double result = (employeeCount * averageSalary) * months;
        if (priority) {
            result *= 1.1;
        }
        return result;
    }

    public String[] findSimilarProjects(AnimatedProject[] animatedProjects, int employeeThreshold) {
        try {
            ArrayList<String> similarProjects = new ArrayList<String>();
            for (int i = 0; i < animatedProjects.length; i++) {
                VideoGame game = (VideoGame) animatedProjects[i];
                if (Math.abs(game.getEmployeeCount() - employeeCount) <= employeeThreshold) {
                    similarProjects.add(game.getProjectName());
                }
            }
            String[] similar = new String[similarProjects.size()];
            for (int i = 0; i < similarProjects.size(); i++) {
                similar[i] = similarProjects.get(i);
            }
            return similar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
