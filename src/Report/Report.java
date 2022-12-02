package Report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import UserDatabase.DataManager;
import Validator.Validator;

public class Report {
    private int rid;
    private int doctorId;
    private int patientId;
    private String diagnosis;
    private List<String> medicine;
    private String doctorName;
    private String patientName;

    // constructor
    public Report(int rid, int doctorId, String diagnosis, List<String> medicine, int patientId,
            DataManager manager) throws SQLException {
        this.rid = rid;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.medicine = medicine;
        this.patientId = patientId;
        addReport(manager);

        String query1 = "select dname from doctor where dnumber=" + Integer.toString(doctorId);
        String query2 = "select pname from patient where pnumber=" + Integer.toString(patientId);

        try {
            List<String> match1 = manager.query(query1);
            List<String> match2 = manager.query(query2);

            this.doctorName = match1.get(0);
            this.patientName = match2.get(0);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
        }
    }

    // add the report to database
    private void addReport(DataManager manager) throws SQLException {
        String medicineList = "";
        for (String med : medicine)
            medicineList = medicineList + med + " ";
        Scanner in = new Scanner(System.in);
        List<String> info = new ArrayList<>();
        info.add(String.valueOf(patientId));
        info.add(diagnosis);
        info = Validator.reportValidator(info, in, manager);
        patientId = Integer.parseInt(info.get(0));
        diagnosis = info.get(1);
        String sql = "insert into report(rid, dnumber, pnumber, diagnosis, medicine)"
                + " values(" + rid + ",\"" + doctorId + "\",\"" + patientId + "\",\"" + diagnosis + "\",\""
                + medicineList + "\");";
        try {
            manager.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Add Successfully");
    }

    // update the diagnosis for the current record
    public void updateDiagnosis(int id, String newDiagnosis, DataManager manager) throws SQLException {
        this.diagnosis = newDiagnosis;
        String sql = "update report"
                + " set diagnosis=\"" + newDiagnosis + "\""
                + " where rid=" + id + ";";
        try {
            manager.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
            return;
        }
        System.out.println("Update Successfully");
    }

    // print the report
    public void printReport() {
        System.out.println("Report ID:" + rid + "\n"
                + "Patient's Name:" + patientName + "\n"
                + "Doctor's Name:" + doctorName + "\n"
                + "Diagnosis:" + diagnosis);
        System.out.print("Medicine:");
        for (String med : medicine) {
            System.out.print(med + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws SQLException,
            ClassNotFoundException {
        List<String> medicine = new ArrayList<>();
        medicine.add("asdf");
        DataManager manager = new DataManager();
        Report report = new Report(123456789, 456789, "", medicine, 59845, manager);
    }
}
