package Validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import UserDatabase.DataManager;

public class Validator {
    // check all information before create a new record.
    public static List<String> userValidator(List<String> info, Scanner in, DataManager manager) throws SQLException {
        List<String> username = new ArrayList<>();

        while (!validateUserType(info.get(0))) {
            System.out.println("User Type Doesn't Exist. Please Retry:");
            info.set(4, in.nextLine());
        }

        String sql1 = "select * from user where username=\"" + info.get(1) + "\";";
        username = manager.query(sql1);
        while (username.size() != 0) {
            System.out.println("Username Already Exists. Please Enter a New Username:");
            info.set(1, in.nextLine());
            sql1 = "select * from user where username=\"" + info.get(1) + "\";";
            username = manager.query(sql1);
        }

        if (info.get(0).equals("doctor")) {
            // Check ID
            List<String> id = new ArrayList<>();
            Pattern pattern = Pattern.compile("[0-9]+");
            while (!pattern.matcher(info.get(4)).matches()) {
                System.out.println("ID must be digits only. Please Re-enter:");
                info.set(4, in.nextLine());
            }
            String sql2 = "select * from user where doctor_number=" + info.get(4) + ";";
            id = manager.query(sql2);
            while (id.size() != 0) {
                System.out.print("ID already Exists. Please Enter a New ID:");
                info.set(4, in.nextLine());
                while (!pattern.matcher(info.get(4)).matches()) {
                    System.out.println("ID must be digits only. Please Re-enter:");
                    info.set(4, in.nextLine());
                }
                sql2 = "select * from user where doctor_number=" + info.get(4) + ";";
                id = manager.query(sql2);
            }
        }

        return info;
    }

    public static boolean validateUserType(String givenType) {
        return (givenType.equals("doctor") || givenType.equals("admin") || givenType.equals("receptionist"));
    }

    // check all information before create a new report
    public static List<String> reportValidator(List<String> info, Scanner in, DataManager manager) throws SQLException {
        // check patient id
        int id = Integer.parseInt(info.get(0));
        String sql = "select pnumber from patient where pnumber=" + id + ";";
        List<String> result = manager.query(sql);
        while (result.size() == 0) {
            System.out
                    .println("Patient Not Found. Please Re-Enter the Patient's Health Card Number:");
            id = Integer.parseInt(in.nextLine());
            sql = "select pnumber from patient where pnumber=" + id + ";";
            result = manager.query(sql);
        }
        info.set(0, String.valueOf(id));
        // check diagnosis
        while (info.get(1).equals("")) {
            System.out.println("Diagnosis Can't be Empty, Please Re-enter:");
            info.set(1, in.nextLine());
        }
        return info;
    }
}
