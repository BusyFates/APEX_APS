import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.LocalDate;

public class APEX_APS{
    private static int tcincome;
    private static int tchours;
    private static int totaldatalogs;
    private static int blackCount;
    private static int asianCount;
    private static int whiteCount;
    private static int conficount;
   
    private static BufferedReader reader;
    private static FileInputStream fis;
    private static FileOutputStream fos;

    private static double hours;
    private static int dayswork;
    private static String name;
    public static Scanner sc = new Scanner(System.in);
    
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
        System.out.flush();
        }
// tried something here, wasnt really working out.
 /*   public static void race() throws NumberFormatException, IOException {
        int value = 0;
        System.out.printf("What is your race, enter the respective number: \n %s\n %s\n %s\n", "1 = black", "2 = asian", "3 = white");
        int ur = sc.nextInt();
        switch(ur) {
            case 1:
                fis = new FileInputStream("black.txt");
                fos = new FileOutputStream("black.txt", true);
                reader = new BufferedReader(new InputStreamReader(fis));
                value = Integer.parseInt(reader.readLine().trim());
                value += 1;
                blackCount += value;
                fos.write((value));
                break;
            case 2:
                fis = new FileInputStream("asian.txt");
                fos = new FileOutputStream("asian.txt", true);
                reader = new BufferedReader(new InputStreamReader(fis));
                value = Integer.parseInt(reader.readLine().trim());
                value += 1;
                asianCount += value;
                fos.write((value));
                break;
            case 3:
                fis = new FileInputStream("white.txt");
                fos = new FileOutputStream("white.txt", true);
                reader = new BufferedReader(new InputStreamReader(fis));
                value = Integer.parseInt(reader.readLine().trim());
                value += 1;
                whiteCount += value;
                fos.write((value));
                break;
            default:
                fis = new FileInputStream("other.txt");
                fos = new FileOutputStream("other.txt", true);
                reader = new BufferedReader(new InputStreamReader(fis));
                value = Integer.parseInt(reader.readLine().trim());
                value += 1;
                conficount += value;
                fos.write((value + "").getBytes());
                break;
        }
    } 
    *\
    /* The CheckInOut method is the first opton most users will navigate to from the homepage.
     * This simply logs the users name, time and date they checked into the system. */
    public static void CheckInOut(){
       /*  try {
            race();
        } catch (NumberFormatException e) {
    
        } catch (IOException e) {
          
        }
        */
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
        try {
            AccHours(start, end, n);
        } catch (NumberFormatException e) {
           
            e.printStackTrace();
        } catch (IOException e) {
          
            e.printStackTrace();
        }
    }
    /* The AccHours method gives the total amount of hours the user has worked during their time in office. */
    public static void AccHours(double s, double e, String n) throws NumberFormatException, IOException {
        
        System.out.print("Number of days in office: ");
        int days = sc.nextInt();
        flush();
        double hrs = Math.abs(s - e) * days;
        hours = s-e;
        fis = new FileInputStream("hours.txt");
        fos = new FileOutputStream("hours.txt");
        int value = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("hours.txt"))) {
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                value = Integer.parseInt(line.trim()); // Parse the existing value
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found, initializing hours to 0.");
        } catch (NumberFormatException ex) {
            System.out.println("Invalid data in file, resetting hours to 0.");
            value = 0;
        }
    
        value += hrs;
    
        try (FileOutputStream fos = new FileOutputStream("hours.txt")) {
            fos.write(String.valueOf(value).getBytes());
        } catch (IOException ex) {
            System.out.println("Error writing to file: ");
        }
        tchours += value;
        System.out.printf("%s, you have worked a total of %d days which totals %f hours\n", n, days, hrs);
        dayswork = days;
        homepage();
    }
    /* The PayCheck Method is used to calculate the earnings of the user by using the data they provided. */
    public static void PayCheck(double h){
        Scanner sc = new Scanner(System.in);
        int dw = 0;
        int wl = 0;
        System.out.print("full day = f, half-day = h\nDo you work full day or half-day: ");
        String hpd = sc.next();
        if (hpd.equals("h")) {
            wl = 12;
        } else {
            wl = 24;
        }
        System.out.print("What is your hourly wage: ");
        int wage = sc.nextInt();
        do {
            System.out.print("How many days per week do you work: ");
            dw = sc.nextInt();
        }while(dw > 7|| dw <0);
       
        
        System.out.print("How many weeks of work until you are payed: ");
        int weeks = sc.nextInt();
        double pay = ((h+wl) * wage) * (dw * weeks);
        System.out.println(" Your total paycheck is " + pay);
        System.out.println();

        try {
            //Read the file and parse the integer
            FileInputStream fis = new FileInputStream("income.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            int value = Integer.parseInt(reader.readLine().trim());
            reader.close();  // Close the input stream
    
            value += pay;
            tcincome = value;
            // Write the modified integer back to the file using OutputStream
            FileOutputStream fos = new FileOutputStream("income.txt");
            fos.write(String.valueOf(value).getBytes());
            fos.close();  // Close the output stream
            
        } catch (IOException e) {
            System.out.println("Error reading or writing file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing integer: " + e.getMessage());
        }
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
                totaldatalogs ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        homepage();
    }

    public static void metric(){
        System.out.printf(" ====================\n  APEX APS CORP DATA \n ====================\n\n");
        System.out.printf("TOTAL CUMULATIVE DATA LOGS: %d\n ============================\n", totaldatalogs);
        System.out.printf("TOTAL CUMMULATIVE EMPLOYEE HOURS: %d\n ==================================\n", tchours);
        System.out.printf("TOTAL GENERATED EMPLOYEE INCOME: %d\n ==================================\n", tcincome);
        System.out.printf("TOTAL DIVERSITY PERCENT INDEX \n ============================ \n Work in progress\n Black = %d \n Asian = %d \n White = %d \n =============================\n", blackCount, asianCount, whiteCount);
        System.out.print("Back to home? Y or N: ");
        String decide = sc.next();
        if (decide.equalsIgnoreCase("Y")) {
            flush();
            homepage();
        } else flush();
        
    }
    /* The homepage method starts a loop of infinite method calling so that once the user chooses an option,
     they will end up back to the Homepage to choose another option. */
    public static void homepage(){
        System.out.printf("\nEnter a number to choose where to nagivate\n 1 = CheckIn/Out \n 2 = PayCheck \n 3 = Attendence\n 4 = metric \n It is reccomended to check in first\n");
        System.out.print("Nagivate: ");
        int choice = sc.nextInt();
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
        System.out.printf("     ====================\n     %s\n     ====================\n     ready to get started? Y or N: ", "Welcome to Apex APS");
        String decide = sc.next();
        if (decide.equalsIgnoreCase("Y")) {
        flush();
        homepage();}
    }
    // The Main method calls the WelcomePage Method
    public static void main(String[] args) {
     welcomepage();
    }
}
