package User;

import UserDatabase.DataManager;
import Validator.Validator;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Receptionist {
    private List<String> patients;
    private List<String> appointments;

    private Scanner in;
    private DataManager manager;

    public Receptionist(Scanner in, DataManager manager) {
        this.in = in;
        this.manager = manager;
    }

    // add a new patient to patient table
    public void addPatient() throws SQLException {
        System.out.println("Please Enter the Patient's Health Card Number:");
        String id = in.nextLine();
        System.out.println("Please Enter the Patient's Name:");
        String name = in.nextLine();
        System.out.println("Please Enter the Patient's Phone Number:");
        String contact = in.nextLine();
        System.out.println("Describe the Patient's Symptoms:");
        String symptoms = in.nextLine();
        List<String> info = new ArrayList<>();
        info.add(id);
        info.add(contact);
        info.add(symptoms);
        info = Validator.patientValidator(info, in, manager);
        String sql = "insert into patient(pnumber, pname, phone, symptoms)"
                + " values(" + Integer.parseInt(info.get(0)) + ",\"" + name + "\",\"" + info.get(1) + "\",\""
                + info.get(2)
                + "\");";
        System.out.println("SQL QUERY:");
        System.out.println(sql);
        try {
            manager.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
            return;
        }
        System.out.println("Add Successful.");
    }

    // search a patient by name and return patient's information.
    public String getPatient() {
        System.out.println("Please Enter the Patient You Want to Search:");
        String name = in.nextLine();
        String sql = "select *"
                + " from patient"
                + " where pname=\"" + name + "\";";
        List<String> list = new ArrayList<>();
        try {
            list = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
            return "";
        }
        return list.get(0);
    }

    // get all patients' information from database
    public void getPatients() {
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
        getPatients();

        for (String patient : patients)
            System.out.println(patient);
    }

    public void getAppointments() {
        appointments = new ArrayList<>();
        String sql = "select * from appointment";
        try {
            appointments = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    public void printAppointments() {
        getAppointments();

        for (String appointment : appointments)
            System.out.println(appointment);
    }

    // delete a patient's information by name.
    public void deletePatient() {
        System.out.println("Please enter the patient's health card number:");
        String number = in.nextLine();
        String search = "select pnumber from patient where pnumber = " + number + ";";
        String sql = "delete from patient"
                + " where pnumber=\"" + Integer.parseInt(number) + "\";";
        try {
            List<String> ret = manager.query(search);
            if (ret.size() == 0) {
                System.out.println("Patient not found.");
            } else {
                manager.execute(sql);
                System.out.println("Patient deleted.");
            }

        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    // create a new appointment
    // assume that doctors work from 10 a.m. to 5 p.m. and each appointment will
    // last 30 min.
    public void makeAppointment() throws SQLException, ParseException {
        System.out.println("Please Enter the Patient's Health Card Number:");
        String pnumber = in.nextLine();
        String sql3 = "select phone"
                + " from patient"
                + " where pname=\"" + pnumber + "\";";
        String phone = "";
        try {
            phone = manager.query(sql3).get(0);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        if (phone == "") {
            System.out.println("Patient's Information doesn't exit. Please Create Patient Record First.");
            return;
        }
        String sql1 = "select *"
                + " from doctor;";
        List<String> list1 = new ArrayList<>();
        try {
            list1 = manager.query(sql1);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        List<String[]> d_info = new ArrayList<>();
        for (String info : list1) {
            d_info.add(info.split(", "));
        }
        for (int i = 0; i < d_info.size(); ++i) {
            System.out.println((i + 1) + "." + d_info.get(i)[1]);
        }
        System.out.println("Please Choose the Doctor by No: ");
        int selected = Integer.parseInt(in.nextLine()) - 1;
        String sql2 = "select date"
                + " from appointment"
                + " where dname=\"" + d_info.get(selected)[1] + "\";";
        List<String> list2 = new ArrayList<>();
        list2 = manager.query(sql2);
        System.out.println("The Following Times Have Been Booked: ");
        for (String date : list2) {
            System.out.println(date);
        }
        System.out.println("Please Choose the Date(YYYY/MM/DD HH:mm):");
        String date = in.nextLine();
        while (!timeCheck(list2, date)) {
            System.out.println("Conflict Exist! Please Enter another time:");
            date = in.nextLine();
        }
        String sql4 = "insert into appointment(dname, dno, pname, phone, date, cost, location)"
                + "values(\"" + d_info.get(selected)[1] + "\"," + d_info.get(selected)[0] + ",\"" + pnumber + "\",\""
                + phone
                + "\",\"" + date + "\"," + "5" + ",\"" + d_info.get(selected)[2] + "\");";
        try {
            manager.execute(sql4);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        System.out.println("Add Successfully");
    }

    // Check if the patient's perferring time is available
    private boolean timeCheck(List<String> timeList, String selectedTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD HH:mm");
        Long select = sdf.parse(selectedTime).getTime();
        for (String time : timeList) {
            Long busy = sdf.parse(time).getTime();
            Long gap = Math.abs(select - busy) / 60000;
            if (gap < 30)
                return false;
        }
        return true;
    }

    public void deleteAppointment() {
        System.out.println("Please Enter the Patient's Name and Doctor's Name(Split by ,):");
        String[] info = in.nextLine().split(",");
        String sql = "delete from appointment"
                + " where dname=\"" + info[1] + "\" and pname=\"" + info[0] + "\";";
        try {
            manager.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        System.out.println("Delete Successfully");
    }

    // public static void main(String[] args) throws ClassNotFoundException,
    // SQLException {
    // Scanner in = new Scanner(System.in);
    // DataManager manager = new DataManager();
    // Receptionist receptionist = new Receptionist(in, manager);
    // receptionist.addPatient();
    // }
}
