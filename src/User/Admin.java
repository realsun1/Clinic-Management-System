package User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import UserDatabase.DataManager;
import Validator.Validator;

public class Admin {

    private int id;
    private String name;
    private List<String> patients;
    private List<String> doctors;
    private List<String> receptionists;

    // constructor
    public Admin(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // get all patients' information from database
    public void getPatients(DataManager manager) throws SQLException {
        patients = new ArrayList<>();
        String sql = "select * from patient";
        try {
            patients = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    // print all patients' information
    public void printPatients() {
        for (String patient : patients)
            System.out.println(patient);
    }

    // get all doctors' information from database
    public void getDoctors(DataManager manager) throws SQLException {
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
        for (String doctor : doctors)
            System.out.println(doctor);
    }

    // get all receptionists' information from database
    public void getReceptionists(DataManager manager) throws SQLException {
        receptionists = new ArrayList<>();
        String sql = "select name,id from login_info where type=\"receptionist\";";
        try {
            receptionists = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    // print all receptionists' information
    public void printReceptionists() {
        for (String receptionist : receptionists)
            System.out.println(receptionist);
    }

    // add a new user to database
    public void addUser(DataManager manager, Scanner in) throws SQLException {
        List<String> info = new ArrayList<>();
        System.out.println("Please Enter Username:");
        info.add(in.nextLine());
        System.out.println("Please Enter Password:");
        info.add(in.nextLine());
        System.out.println("Please Enter Name:");
        info.add(in.nextLine());
        System.out.println("Please Enter ID:");
        info.add(in.nextLine());
        System.out.println("Please Enter Type:");
        info.add(in.nextLine());
        Validator validator = new Validator();
        info = validator.userValidator(info, in, manager);
        String sql = "insert into login_info(username,password,name,id,type)"
                + " values(\"" + info.get(0) + "\",\"" + info.get(1) + "\",\"" + info.get(2)
                + "\"," + Integer.parseInt(info.get(3)) + ",\"" + info.get(4) + "\");";
        manager.execute(sql);
        System.out.println("Add Successfully.");
    }

    // delete a user from database
    public void deleteUser(DataManager manager, Scanner in) throws SQLException {
        System.out.println("Please the Username:");
        String name = in.nextLine();
        String sql = "delete from login_info where username=\"" + name + "\";";
        try {
            manager.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        System.out.println("Delete Successfully.");
    }

    // public static void main(String[] args) throws ClassNotFoundException,
    // SQLException {
    // Admin admin = new Admin(000000, "sfdegvsd");
    // DataManager manager = new DataManager();
    // Scanner in = new Scanner(System.in);
    // admin.getDoctors(manager);
    // admin.printDoctors();
    // admin.getPatients(manager);
    // admin.printPatients();
    // admin.getReceptionists(manager);
    // admin.printReceptionists();
    // admin.addUser(manager, in);
    // }
}
