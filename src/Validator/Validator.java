package Validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import UserDatabase.DataManager;

public class Validator {

    public Validator() {
    }

    // check all information before create a new record.
    public List<String> userValidator(List<String> info, Scanner in, DataManager manager) throws SQLException {
        List<String> username = new ArrayList<>();
        String sql1 = "select * from login_info where username=\"" + info.get(0) + "\";";
        username = manager.query(sql1);
        while (username.size() != 0) {
            System.out.println("Username Already Exists. Please Enter a New Username:");
            info.set(0, in.nextLine());
            sql1 = "select * from login_info where username=\"" + info.get(0) + "\";";
            username = manager.query(sql1);
        }

        List<String> id = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]*");
        while (!pattern.matcher(info.get(3)).matches()) {
            System.out.println("Illegal characters in the ID, Please Re-enter:");
            info.set(3, in.nextLine());
        }
        String sql2 = "select id from login_info where id=" + info.get(3) + ";";
        id = manager.query(sql2);
        while (id.size() != 0) {
            System.out.print("ID already Exists. Please Enter a New ID:");
            info.set(3, in.nextLine());
            while (!pattern.matcher(info.get(3)).matches()) {
                System.out.println("Illegal characters in the ID, Please Re-enter:");
                info.set(3, in.nextLine());
            }
            sql2 = "select id from login_info where id=" + info.get(3) + ";";
            id = manager.query(sql2);
        }

        while (!info.get(4).equals("doctor") && !info.get(4).equals("admin") && !info.get(4).equals("receptionist")) {
            System.out.println("User Type Doesn't Exist. Please Retry:");
            info.set(4, in.nextLine());
        }
        return info;
    }

    // public static void main(String[] args) {
    // Pattern pattern = Pattern.compile("[0-9]*");
    // System.out.println(pattern.matcher("asd123").matches());
    // }
}
