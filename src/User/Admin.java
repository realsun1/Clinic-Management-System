package User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        for (String doctor : doctors)
            System.out.println(doctor);
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
}
