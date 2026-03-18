package play.utils;

import play.content.Gender;

import java.util.Locale;
import java.util.Scanner;

public class ScannerUtils {
    public static Scanner SCANNER = new Scanner(System.in).useLocale(new Locale("es", "MX"));

    public static String captureText(String message){
        System.out.println(message + ":");
        return SCANNER.nextLine();
    }

    public static int captureInt(String message){
        System.out.println(message + ":");
        while(!SCANNER.hasNextInt()){
            System.out.println("Select a valid option");
            SCANNER.next();
        }
        int data = SCANNER.nextInt();
        SCANNER.nextLine();
        return data;
    }

    public static double captureDouble(String message){
        System.out.println(message + ":");
        while(!SCANNER.hasNextDouble()){
            System.out.println("Select a valid option");
            SCANNER.next();
        }
        double dato = SCANNER.nextDouble();
        SCANNER.nextLine();
        return dato;
    }
    public static Gender captureGender(String message){
        while(true){
            for(Gender gender : Gender.values()){
                System.out.println("-" + gender.name());
            }

            String gender = captureText(message);
            try{
                return Gender.valueOf(gender.toUpperCase());
            }catch (Exception e){
                System.out.println("Gender not found");
            }
        }
    }
}
