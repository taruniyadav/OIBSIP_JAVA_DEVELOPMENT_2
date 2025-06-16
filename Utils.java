import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {

    /* read an int safely */
    public static int safeInt(Scanner sc) {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("Enter a valid number: "); }
        }
    }

    /* read a double safely */
    public static double safeDouble(Scanner sc) {
        while (true) {
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("Enter a valid amount: "); }
        }
    }
}
