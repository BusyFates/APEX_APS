import java.util.*;
import java.time.LocalTime;
import java.time.LocalDate;

public class APEX_APS{
    private static double hours;
    private static int dayswork;

        public APEX_APS(int d) {
            dayswork = d;
        }
    
        public APEX_APS(double h) {
            hours = h;
        }
    
    
        public static void flush() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        public static void CheckInOut(){
            LocalTime t = LocalTime.now();
            LocalDate d = LocalDate.now();
    
            Scanner sc = new Scanner(System.in);
            System.out.print("Yo, what is ya name: ");
            String n = sc.next();
            System.out.println(n + " checked in at " + d+ " " + t);
    
            System.out.print("What hour do you start work, 9:30 am = 9.30: ");
            double start = sc.nextDouble();
            System.out.print("When does your shift end: ");
            double end = sc.nextDouble();
            AccHours(start, end, n);
    
        }
        public static void AccHours(double s, double e, String n){
            
            Scanner sc = new Scanner(System.in);
            System.out.print("Number of days in office: ");
            int days = sc.nextInt();
            flush();
            double hrs = (s - e) * days;
            hours = s-e;
            System.out.printf("%s, you have worked a total of %d days and have worked a total of %f hours\n", n, days, hrs);
            dayswork = days;
            homepage();
        }
        public static void PayCheck(double h){
            Scanner sc = new Scanner(System.in);
            System.out.print("What is your hourly wage %s: ");
            int wage = sc.nextInt();
    
            System.out.print("How mnay days per week do you work: ");
            int dw = sc.nextInt();
    
            double pay = h * wage * dw;
            System.out.println(" Your weekly paycheck is " + pay);
    
    
        }
        public static void Attend(int d){
            for (int i = 1; i < d; i++) {
                System.out.printf("You checked in at %d");
            }
        }
        public static void metric(){}
        
        public static void homepage() {
            Scanner scan = new Scanner(System.in);
            System.out.print("Its reccomended to Check in first");
            System.out.printf("\nEnter a number to choose where to nagivate\n 1 = CheckIn/Out \n 2 = PayCheck \n 3 = Attendence\n 4 = metric \n");
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
                Attend(dayswork);
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

        System.out.printf("     ====================\n     %s\n\n     ready to get started? Y or N: ", "Welcome to Apex APS");
        String decide = scan.next();
        System.out.println("     ====================");

        if (decide.equalsIgnoreCase("Y") || decide.contains("Y") || decide.contains("y")) {
        flush();
        homepage();}
    }


    public static void main(String[] args) {
     welcomepage();
    }
}