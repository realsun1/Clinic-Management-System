package User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Report.Report;
import UserDatabase.DataManager;

public class Doctor {
    private int id;
    private String name;
    private List<String> appointments;

    // constructor
    public Doctor(int id, String name) {
        this.id = id;
        this.name = name;
        this.appointments = new ArrayList<>();
    }

    // get all of the appointments belong to the doctor
    public void getAppointments(DataManager manager) throws SQLException {
        String sql = "select * from appointment where dname =\"" + name + "\";";
        appointments = manager.query(sql);
    }

    // print the appointments
    public void showAppointments() {
        for (String appointment : appointments)
            System.out.println(appointment);
    }

    // create a new report for patient
    public void createReport(Scanner in, DataManager manager) throws SQLException {
        System.out.println("Please Enter the Patient's Name:");
        String pname = in.nextLine();
        System.out.println("Please Enter the Diagnosis:");
        String diagnosis = in.nextLine();
        System.out.println("Please Enter the Medicine(Split by ,):");
        String med = in.nextLine();
        List<String> medicine = new ArrayList<>(Arrays.asList(med.split(",")));
        int rid = id + pname.length() + pname.charAt(0) - 'A';
        Report report = new Report(rid, name, diagnosis, medicine, pname, manager);
        report.printReport();
    }

    // update patient's dignosis
    public void updateDiagnosis(Scanner in, DataManager manager) throws SQLException {
        System.out.println("Please Enter the Patient's Name");
        String pname = in.nextLine();
        int rid = id + pname.length() + pname.charAt(0) - 'A';
        System.out.println("Please Enter the Diagnosis:");
        String newDiagnosis = in.nextLine();
        Report report = new Report();
        report.updateDiagnosis(rid, newDiagnosis, manager);
    }

    public void getRecords(Scanner in, DataManager manager) throws SQLException {
        System.out.println("Please Enter the Patient's name");
        String name = in.nextLine();
        String sql = "select * from report where pname=\"" + name + "\";";
        List<String> reports = new ArrayList<>();
        try {
            reports = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        if (reports.size() == 0) {
            System.out.println("No Patient Found.");
        } else {
            for (String report : reports)
                System.out.println(report);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DataManager manager = new DataManager();
        Doctor doctor = new Doctor(156438485, "Jack");
        Scanner in = new Scanner(System.in);
        // doctor.getAppointments(manager);
        // doctor.showAppointments();
        doctor.getRecords(in, manager);
    }

}