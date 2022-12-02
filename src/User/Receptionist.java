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

        System.out.printf("%-25s", "Healthy Card Number");
        System.out.printf("%-20s", "Name");
        System.out.printf("%-20s", "Contact");
        System.out.printf("%-20s", "Symptoms");
        System.out.println();
        for (String patient : patients) {
            String[] info = patient.split(", ");
            System.out.printf("%-25s", info[0]);
            System.out.printf("%-20s", info[0]);
            System.out.printf("%-20s", info[0]);
            System.out.printf("%-20s", info[0]);
            System.out.println();
        }
    }

    public void getAppointments() {
        appointments = new ArrayList<>();
        String sql = "select D.dname, P.pname, P.phone, A.date, D.location from doctor D, patient P, appointment A where P.pnumber = A.pnumber and D.dnumber = A.dnumber";
        try {
            appointments = manager.query(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    public void printAppointments() {
        getAppointments();
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
        String getdno = "select dnumber from doctor where dname=\"" + info[1] + "\";";
        String getpno = "select pnumber from patient where pname=\"" + info[0] + "\";";
        List<String> dno = new ArrayList<>();
        List<String> pno = new ArrayList<>();
        try {
            dno = manager.query(getdno);
            pno = manager.query(getpno);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        if (dno.size() == 0) {
            System.out.println("Doctor Not Found.");
            return;
        }
        if (pno.size() == 0) {
            System.out.println("Patient Not Found.");
            return;
        }
        String sql0 = "select dnumber from appointment where dnumber=" + Integer.valueOf(dno.get(0)) + " and pnumber="
                + Integer.valueOf(pno.get(0)) + ";";
        List<String> ret = new ArrayList<>();
        try {
            ret = manager.query(sql0);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        if (ret.size() != 0) {
            String sql = "delete from appointment"
                    + " where dnumber=" + dno.get(0) + " and pnumber=" + pno.get(0) + ";";
            try {
                manager.execute(sql);
            } catch (SQLException e) {
                System.err.println("Error: (" + e.getMessage() + ").");
            }
            System.out.println("Delete Successfully");
        } else {
            System.out.println("Appointment Not Found.");
        }

    }

    public static void main(String[] args) throws ClassNotFoundException,
            SQLException {
        Scanner in = new Scanner(System.in);
        DataManager manager = new DataManager();
        Receptionist receptionist = new Receptionist(in, manager);
        receptionist.deleteAppointment();
    }
}
