import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> marks = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.print("Enter marks of student " + i + ": ");
            marks.add(sc.nextInt());
        }

        int total = 0;
        int highest = marks.get(0);
        int lowest = marks.get(0);

        for (int m : marks) {
            total += m;

            if (m > highest)
                highest = m;

            if (m < lowest)
                lowest = m;
        }

        double average = (double) total / n;

        System.out.println("\n--- RESULT ---");
        System.out.println("Average Marks : " + average);
        System.out.println("Highest Marks : " + highest);
        System.out.println("Lowest Marks  : " + lowest);

        sc.close();
    }
}
