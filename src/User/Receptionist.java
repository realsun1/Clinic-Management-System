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
    public void addPatient() {
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

        try {
            info = Validator.patientValidator(info, in, manager);
            String sql = "insert into patient(pnumber, pname, phone, symptoms)"
                    + " values(" + Integer.parseInt(info.get(0)) + ",\"" + name + "\",\"" + info.get(1) + "\",\""
                    + info.get(2)
                    + "\");";
            manager.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
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

        if (patients.size() == 0) {
            System.out.println("There are no patients in the system.");
        }

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
    public void makeAppointment() {
        System.out.println("Please Enter the Patient's Health Card Number:");
        String pnumber = in.nextLine();
        String search = "select pnumber from patient where pnumber = " + pnumber + ";";
        boolean match = false;
        try {
            match = (manager.query(search).size() > 0);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        if (!match) {
            System.out.println("Patient's Information doesn't exit. Please Create Patient Record First.");
            return;
        }
        String sql1 = "select *"
                + " from doctor;";
        List<String> list1 = new ArrayList<>();
        try {
            list1 = manager.query(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (list1.size() == 0) {
            System.out.println("Your clinic must have a doctor to create appointments.");
            return;
        }
        List<String[]> d_info = new ArrayList<>();
        for (String info : list1) {
            d_info.add(info.split(", "));
        }
        for (int i = 0; i < d_info.size(); ++i) {
            System.out.println((i + 1) + ". " + d_info.get(i)[1]);
        }
        System.out.println("Please Choose the Doctor by No: ");
        int selected = Integer.parseInt(in.nextLine()) - 1;
        String sql2 = "select date"
                + " from appointment"
                + " where dnumber=" + selected + ";";
        List<String> list2 = new ArrayList<>();
        try {
            list2 = manager.query(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (list2.size() != 0) {
            System.out.println("The Following Times Have Been Booked: ");
            for (String date : list2) {
                System.out.println(date);
            }
        }

        System.out.println("Please Choose the Date(YYYY/MM/DD HH:mm):");
        String date = in.nextLine();
        while (timeCheck(list2, date) != 0) {
            if (timeCheck(list2, date) == 1) {
                System.out.println("The date was entered incorrectly. Please follow the format (YYYY/MM/DD HH:mm):");
            } else {
                System.out.println("The doctor has another appointment at that time.");
            }
            date = in.nextLine();
        }
        String sql4 = "insert into appointment(dnumber, pnumber, date)"
                + "values(" + d_info.get(selected)[0] + "," + pnumber + ",\"" + date + "\");";
        try {
            manager.execute(sql4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Add Successful");
    }

    // Check if the patient's perferring time is available
    private int timeCheck(List<String> timeList, String selectedTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD HH:mm");
        long select, busy;
        try {
            select = sdf.parse(selectedTime).getTime();
        } catch (ParseException e) {
            return 1;
        }
        for (String time : timeList) {
            try {
                busy = sdf.parse(time).getTime();
            } catch (ParseException e) {
                return 1;
            }
            long gap = Math.abs(select - busy) / 60000;
            if (gap < 30)
                return 2;
        }
        return 0;
    }

    public void deleteAppointment() {
        System.out.println("Please Enter the Patient's Name and Doctor's Name(Split by ,):");
        String[] info = in.nextLine().split(",");
        String sql = "delete from appointment"
                + " where dnumber=" + info[1] + " and pnumber=" + info[0] + ";";
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
