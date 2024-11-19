import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.LocalDate;

public class APEX_APS{
    private static double hours;
    private static int dayswork;
   
        // This creates a variable that can exist in any method, but we first declare them above.
        public APEX_APS(int d) {
            dayswork = d;      }
        public APEX_APS(double h) {
            hours = h;            }
    
        public static void flush() {
            System.out.print("\033[H\033[2J");
            System.out.flush();    }

        public static void CheckInOut(){
            LocalTime t = LocalTime.now();
            LocalDate d = LocalDate.now();
    
            Scanner sc = new Scanner(System.in);
            System.out.print("Hello, what is your name and last name: ");
            String n = sc.next();
	        String ln = sc.next();
            System.out.println(n +" "+ ln + " checked in at " + d+ " " + t);
    
            System.out.print("\ne.g.(9:30 am = 9.3)\nWhen does your shift start: ");
            double start = sc.nextDouble();
            System.out.print("When does your shift end: ");
            double end = sc.nextDouble();
            start = Math.abs(start);
            end = Math.abs(end);
            AccHours(start, end, n);
                                       }

        public static void AccHours(double s, double e, String n){
            Scanner sc = new Scanner(System.in);
            System.out.print("Number of days in office: ");
            int days = sc.nextInt();
            flush();
            double hrs = (s - e) * days;
            hours = s-e;
            System.out.printf("%s, you have worked a total of %d days which totals %f hours\n", n, days, hrs);
            dayswork = days;
            homepage();
                                                                 }

        public static void PayCheck(double h){
            Scanner sc = new Scanner(System.in);
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
    
            System.out.print("How many days per week do you work: ");
            int dw = sc.nextInt();
            
            System.out.print("How many weeks of work until you are payed: ");
            int weeks = sc.nextInt();

            double pay = ((h+wl) * wage) * (dw * weeks);
            System.out.println(" Your total paycheck is " + pay);
                                             }

        public static void Attend(){
            LocalTime t = LocalTime.now();
            LocalDate d = LocalDate.now();
            // Adds data to file
            try (FileOutputStream red = new  FileOutputStream("data.txt", true)) {
                String data = String.format("Date: %s, Time: %s\n", d, t);
                red.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // prints out data
            try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
                String line;
                System.out.println("Attendence log: ");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            homepage();
        }

        public static void metric(){}
        
        public static void homepage() {
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

    public static void welcomepage(){
        Scanner scan = new Scanner(System.in);

        System.out.printf("     ====================\n     %s\n     ====================\n     ready to get started? Y or N: ", "Welcome to Apex APS");
        String decide = scan.next();
    
        if (decide.equalsIgnoreCase("Y")) {
        flush();
        homepage();}
    }

    public static void main(String[] args) {
     welcomepage();
    }
}
