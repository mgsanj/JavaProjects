/**
 * Midterm 2: AnimatedProject.java
 * A public interface
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 30, 2023
 */
public interface AnimatedProject {
    public String getProjectName();
    public int getEmployeeCount();
    public boolean hasPriority();
    public double calculateCostEstimate(double averageSalary, int months);
    public String[] findSimilarProjects(AnimatedProject[] animatedProjects, int employeeThreshold);
}
