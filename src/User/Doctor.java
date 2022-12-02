package User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Report.Report;
import UserDatabase.DataManager;

public class Doctor {
    private int id;
    private String name;
    private String location;
    private List<String> appointments;

    private Scanner in;
    private DataManager manager;

    // constructor
    public Doctor(String username, Scanner in, DataManager manager) {
        this.in = in;
        this.manager = manager;

        List<String> attributes = GetAttributesFromUsername(username);
        this.id = Integer.parseInt(attributes.get(0));
        this.name = attributes.get(1);
        this.location = attributes.get(2);
    }

    // get all the appointments belong to the doctor
    public void getAppointments() {
        String sql = "select D.dname, P.pname, P.phone, A.date, D.location from doctor D, patient P, appointment A where P.pnumber = A.pnumber and D.dnumber = "
                + id + ";";
        try {
            appointments = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    // print the appointments
    public void showAppointments() {
        getAppointments();
        if (appointments.size() == 0) {
            System.out.println("No Appointments Found.");
            return;
        }
        System.out.printf("%-20s", "Doctor Name");
        System.out.printf("%-20s", "Patient Name");
        System.out.printf("%-20s", "Patient Contact");
        System.out.printf("%-25s", "Date");
        System.out.printf("%-20s", "Location");
        System.out.println();
        for (String appointment : appointments) {
            String[] info = appointment.split(", ");
            System.out.printf("%-20s", info[0]);
            System.out.printf("%-20s", info[1]);
            System.out.printf("%-20s", info[2]);
            System.out.printf("%-25s", info[3]);
            System.out.printf("%-20s", info[4]);
            System.out.println();
        }
    }

    // create a new report for patient
    public void createReport() {
        System.out.println("Please Enter the Patient's Health Card Number:");
        String pnumber = in.nextLine();
        System.out.println("Please Enter the Diagnosis:");
        String diagnosis = in.nextLine();
        System.out.println("Please Enter the Medicine(Split by ,):");
        String med = in.nextLine();
        List<String> medicine = new ArrayList<>(Arrays.asList(med.split(",")));
        int rid = getReportId();
        try {
            Report report = new Report(rid, id, diagnosis, medicine, Integer.parseInt(pnumber), manager);
            report.printReport();
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    private int getReportId() {
        int returnId;
        try {
            ResultSet rs = manager.executeQuery("SELECT IFNULL(MAX(rid), 1000) FROM report;");
            rs.next();
            returnId = rs.getInt(1);

            return returnId + 1;
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
            e.printStackTrace();
        }

        // Backup in case query fails
        return (int) (Instant.now().getEpochSecond() % 86400);
    }

    // update patient's dignosis
    // public void updateDiagnosis() throws SQLException {
    // System.out.println("Please Enter the Patient's Name:");
    // String pname = in.nextLine();
    // int rid = id + pname.length() + pname.charAt(0) - 'A';
    // System.out.println("Please Enter the Diagnosis:");
    // String newDiagnosis = in.nextLine();
    // Report report = new Report();
    // report.updateDiagnosis(rid, newDiagnosis, manager);
    // }

    public void printReports() {
        System.out.println("Please Enter the Patient's Health Card Number:");
        String idString = in.nextLine();
        String sql = "select * from report where pnumber=" + idString + ";";
        List<String> reports = new ArrayList<>();
        try {
            reports = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        if (reports.size() == 0) {
            System.out.println("No reports were found for the patient.");
        } else {
            for (String report : reports)
                System.out.println(report);
        }
    }

    private List<String> GetAttributesFromUsername(String username) {
        String sql = "select dnumber, dname, location from user inner join doctor on user.doctor_number=doctor.dnumber";
        List<String> returnList = new ArrayList<String>();
        try {
            ResultSet rs = manager.executeQuery(sql);
            rs.next();
            returnList.add(Integer.toString(rs.getInt(1)));
            returnList.add(rs.getString(2));
            returnList.add(rs.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Doctor doctor = new Doctor("asd", new Scanner(System.in), new DataManager());
        doctor.showAppointments();
    }
}
