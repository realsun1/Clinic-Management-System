package User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import UserDatabase.DataManager;
import Validator.Validator;

public class Admin {
    private List<String> doctors;
    private List<String> receptionists;

    private Scanner in;
    private DataManager manager;

    // constructor
    public Admin(Scanner in, DataManager manager) {
        this.in = in;
        this.manager = manager;
    }

    // get all doctors' information from database
    public void getDoctors() {
        doctors = new ArrayList<>();
        String sql = "select * from doctor";
        try {
            doctors = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    // print all doctors' information
    public void printDoctors() {
        getDoctors();

        if (doctors.size() == 0) {
            System.out.println("There are no doctors to display.");
            return;
        }

        System.out.printf("%-15s", "ID");
        System.out.printf("%-20s", "Name");
        System.out.printf("%-10s", "Location");
        System.out.println();
        for (String doctor : doctors) {
            String[] info = doctor.split(", ");
            System.out.printf("%-15s", String.valueOf(info[0]));
            System.out.printf("%-20s", info[1]);
            System.out.printf("%-10s", info[2]);
            System.out.println();
        }
    }

    // get all receptionists' information from database
    public void getReceptionists() {
        receptionists = new ArrayList<>();
        String sql = "select username from user where type=\"receptionist\";";
        try {
            receptionists = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    // print all receptionists' information
    public void printReceptionists() {
        getReceptionists();
        System.out.println("UserName");
        for (String receptionist : receptionists)
            System.out.println(receptionist);
    }

    // delete a user from database
    public void deleteUser() {
        System.out.println("Please Enter the Username:");
        String name = in.nextLine();
        String search = "select username from user where username=\"" + name + "\";";
        String sql = "delete from user where username=\"" + name + "\";";
        try {
            List<String> ret = manager.query(search);
            if (ret.size() == 0) {
                System.out.println("User not found.");
            } else {
                manager.execute(sql);
                System.out.println("Delete Successful.");
            }
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    // delete doctor by doctor's id
    public void deleteDoctorByID() {
        System.out.println("Please Enter the Doctor ID You Want To Delete:");
        String dno = in.nextLine();
        Pattern pattern = Pattern.compile("[0-9]+");
        while (!pattern.matcher(dno).matches()) {
            System.out.println("Doctor ID Must Be Digits Only. Please Re-enter:");
            dno = in.nextLine();
        }
        String sql1 = "select * from user where doctor_number=" + Integer.parseInt(dno) + ";";
        List<String> ret = new ArrayList<>();
        try {
            ret = manager.query(sql1);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        if (ret.size() == 0) {
            System.out.println("Doctor Not Found.");
            return;
        } else {
            String sql2 = "delete from user where doctor_number=" + Integer.parseInt(dno) + ";";
            try {
                manager.execute(sql2);
            } catch (SQLException e) {
                System.err.println("Error: (" + e.getMessage() + ").");
            }
            System.out.println("Delete Successfully.");
        }
    }

    // public static void main(String[] args) throws ClassNotFoundException,
    // SQLException {
    // Admin admin = new Admin(new Scanner(System.in), new DataManager());
    // admin.deleteDoctorByID();
    // }
}
