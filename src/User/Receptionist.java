package User;

import UserDatabase.DataManager;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Receptionist {
    private int id;
    private int name;

    public Receptionist(int id, int name) {
        this.id = id;
        this.name = name;
    }

    // add a new patient to patient table
    public void addPatient(Scanner in, DataManager manager) throws SQLException {
        System.out.println("Please Enter the Patient's Name:");
        String name = in.nextLine();
        System.out.println("Please Enter the Patient's Contact:");
        String contact = in.nextLine();
        System.out.println("Please Enter the Patient's Symptom:");
        String symptom = in.nextLine();
        String sql = "insert into patient(pname, pcontact, symptom)"
                + " values(\"" + name + "\",\"" + contact + "\",\"" + symptom + "\");";
        try {
            manager.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
            return;
        }
        System.out.println("Add Successfully");
    }

    // search a patient by name and return patient's information.
    public String getPatient(Scanner in, DataManager manager) throws SQLException {
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

    // delete a patient's information by name.
    public void deletePatient(Scanner in, DataManager manager) throws SQLException {
        System.out.println("Please Enter the Name You Want to Delete");
        String name = in.nextLine();
        String sql = "delete from patient"
                + " where pname=\"" + name + "\";";
        try {
            manager.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
            return;
        }
        System.out.println("Delete Successfully");
    }

    // create a new appointment
    // assume that doctors work from 10 a.m. to 5 p.m. and each appointment will
    // last 30 min.
    public void makeAppointment(Scanner in, DataManager manager) throws SQLException, ParseException {
        System.out.println("Please Enter the Patient's Name: ");
        String pname = in.nextLine();
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
        String sql3 = "select pcontact"
                + " from patient"
                + " where pname=\"" + pname + "\";";
        String pcontact = "";
        try {
            pcontact = manager.query(sql3).get(0);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        String sql4 = "insert into appointment(dname, dno, pname, pcontact, date, cost, location)"
                + "values(\"" + d_info.get(selected)[1] + "\"," + d_info.get(selected)[0] + ",\"" + pname + "\",\""
                + pcontact
                + "\",\"" + date + "\"," + "5" + ",\"" + d_info.get(selected)[2] + "\");";
        try {
            manager.execute(sql4);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
        System.out.println("Add Successfully");
    }

    public void deleteAppointment(Scanner in, DataManager manager) throws SQLException {
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

    /*
     * public static void main(String[] args) throws ClassNotFoundException,
     * SQLException, ParseException {
     * Receptionist receptionist = new Receptionist(0, 0);
     * DataManager manager = new DataManager();
     * Scanner in = new Scanner(System.in);
     * // receptionist.makeAppointment(in, manager);
     * // receptionist.deleteAppointment(in, manager);
     * // receptionist.addPatient(in, manager);
     * receptionist.deletePatient(in, manager);
     * }
     */
}
