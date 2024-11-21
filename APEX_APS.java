import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.LocalDate;

public class APEX_APS{
    private static double hours;
    private static int dayswork;
    private static String name;
    private static String lastname;
    public static Scanner sc;
    

   
    // This creates a variable that can exist/be used in any method, but we first declare them above.
    public APEX_APS(int d) {
        dayswork = d;      }
    public APEX_APS(double h) {
        hours = h;            }
    public APEX_APS(String n){
        name = n;            }

    // This method flushes the screen(aka clears everythings) so that past screens aren't overlapping.
    public static void flush() {
        System.out.print("\033[H\033[2J");
        System.out.flush();    }

    /* The CheckInOut method is the first opton most users will navigate to from the homepage.
     * This simply logs the users name, time and date they checked into the system. */
    public static void CheckInOut(){
        LocalTime t = LocalTime.now();
        LocalDate d = LocalDate.now();
        sc = new Scanner(System.in);
        
        System.out.print("Hello, what is your first name: ");
        String n = sc.next();
        System.out.print("What is your last name: ");
	    String ln = sc.next();
        name = n + " " + ln;
        System.out.println(n +" "+ ln + " checked in at " + d+ " " + t);

        System.out.print("\ne.g.(9:30 am = 9.3)\nWhen does your shift start: ");
        double start = sc.nextDouble();
        System.out.print("When does your shift end: ");
        double end = sc.nextDouble();
        start = Math.abs(start);
        end = Math.abs(end);
        AccHours(start, end, n);
    }
        /* The AccHours method gives the total amount of hours the user has worked during their time in office. */
    public static void AccHours(double s, double e, String n){
        System.out.print("Number of days in office: ");
        int days = sc.nextInt();
        flush();
        double hrs = (s - e) * days;
        hours = s-e;
        System.out.printf("%s, you have worked a total of %d days which totals %f hours\n", n, days, hrs);
        dayswork = days;
        homepage();
    }
    /* The PayCheck Method is used to calculate the earnings of the user by using the data they provided. */
    public static void PayCheck(double h){
        Scanner sc = new Scanner(System.in);
        int dw= 0;
        int wl= 0;
        System.out.print("full day = f, half-day = h\nDo you work full day or half-day: ");
        String hpd = sc.next();
        if (hpd.equals("h")) {
            wl = 12;
        } else {
            wl = 24;
        }
        System.out.print("What is your hourly wage: ");
        int wage = sc.nextInt();

        //goof juice
    
        do {
            System.out.print("How many days per week do you work: ");
            dw = sc.nextInt();
        }while(dw > 7|| dw <0);
       
        
        System.out.print("How many weeks of work until you are payed: ");
        int weeks = sc.nextInt();
        double pay = ((h+wl) * wage) * (dw * weeks);
        System.out.println(" Your total paycheck is " + pay);
        System.out.println();
        homepage();
    }
    /* The Attend method is used to print out the entire attendance log from a database file that stores each time
     * someone checked into the system. */
    public static void Attend(){
        LocalTime t = LocalTime.now();
        LocalDate d = LocalDate.now();
        // Adds data to file
        try (FileOutputStream red = new  FileOutputStream("data.txt", true)) {
            String data = String.format("%s logged at, Date: %s, Time: %s%n", name, d, t);
            red.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // prints out data
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            // A declared string will have the value of null
            String line;
            System.out.println("Attendence log: ");
            // null means we have reached the end of the file meaning there is no line.
            /* In this while loop, we are assigning the data on the current line(reader.readLine) to the variable "line"
             * The condtion becomes "while the value of line is not null(aka empty), print the current line."
             * At the end of the file "reader.readLine" will assign null to "line". line(null) is equal to null so loop stops */
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        homepage();
    }

    public static void metric(){

    }

    /* The homepage method starts a loop of infinite method calling so that once the user chooses an option,
     they will end up back to the Homepage to choose another option. */
    public static void homepage(){
        Scanner scan = new Scanner(System.in);
        System.out.printf("\nEnter a number to choose where to nagivate\n 1 = CheckIn/Out \n 2 = PayCheck \n 3 = Attendence\n 4 = metric \n It is reccomended to check in first\n");
        System.out.print("Nagivate: ");
        int choice = scan.nextInt();
        switch(choice) {
            case 1:
                flush(); 
                CheckInOut();
                break;
            case 2:
                flush();
                PayCheck(hours);
                break;
            case 3:
                flush();
                Attend();
                break;
            case 4:
                flush();
                metric();
                break;
            default:
                flush();
                welcomepage();
                break;
        }
    }
    /* The Welcomepage method is quite literally self-explainitory. It also calls the Homepage method.*/
    public static void welcomepage(){
        Scanner scan = new Scanner(System.in);
        System.out.printf("     ====================\n     %s\n     ====================\n     ready to get started? Y or N: ", "Welcome to Apex APS");
        String decide = scan.next();
        if (decide.equalsIgnoreCase("Y")) {
        flush();
        homepage();}
    }
    // The Main method calls the WelcomePage Method
    public static void main(String[] args) {
     welcomepage();
    }
}
