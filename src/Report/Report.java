package Report;

import java.sql.SQLException;
import java.util.List;

import UserDatabase.DataManager;

public class Report {
    private int rid;
    private String doctorName;
    private String patientName;
    private String diagnosis;
    private List<String> medicine;

    // constructor
    public Report(int rid, String doctorName, String diagnosis, List<String> medicine, String patientName,
            DataManager manager) throws SQLException {
        this.rid = rid;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.medicine = medicine;
        this.patientName = patientName;
        addReport(manager);
    }

    public Report() {
    }

    // add the report to database
    private void addReport(DataManager manager) throws SQLException {
        String medicineList = "";
        for (String med : medicine)
            medicineList = medicineList + med + " ";
        String sql = "insert into report(rid, dname, pname, diagnosis, medicine)"
                + " values(" + rid + ",\"" + doctorName + "\",\"" + patientName + "\",\"" + diagnosis + "\",\""
                + medicineList + "\");";
        try {
            manager.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error: (" + e.getMessage() + ").");
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

    // public static void main(String[] args) throws ClassNotFoundException,
    // SQLException {
    // DataManager manager = new DataManager();
    // int rid = 16854381;
    // String doctorName = "Jack";
    // String patientName = "Andy";
    // String diagnosis = "sfvoniuvrsnfirtbvjknsolcoricfn";
    // List<String> medicine = new ArrayList<>();
    // medicine.add("aaa");
    // medicine.add("bbb");
    // medicine.add("ccc");
    // Report report = new Report(rid, doctorName, diagnosis, medicine, patientName,
    // manager);
    // report.printReport();
    // Report report = new Report();
    // report.updateDiagnosis(rid, "seabhlruaegorig", manager);
    // }
}
