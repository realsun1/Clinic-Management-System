package Menu;

import Login.Login;
import User.Admin;
import User.Doctor;
import User.Receptionist;
import UserDatabase.DataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserMenu {
    private static DataManager dataManager;
    private static Scanner scanner;

    private static boolean validateInput(String input, int max) {
        List<String> validInputs = new ArrayList<String>();

        for (int i = 1; i <= max; i++) {
            validInputs.add(Integer.toString(i));
        }

        return validInputs.contains(input);
    }

    private static String getValidInput(int max) {
        String input = scanner.nextLine();
        while (!validateInput(input, max)) {
            System.out.println("Invalid input.");
            input = scanner.nextLine();
        }

        return input;
    }

    private static String getValidInput() {
        return getValidInput(5);
    }

    public static void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }

    private static void receptionistMenu() {
        Receptionist receptionist = new Receptionist(scanner, dataManager);
        String input = "";

        do {
            System.out.println("+--------------------Receptionist Menu--------------------+");
            System.out.println("|                 1. Manage Patients                      |");
            System.out.println("|                 2. Manage Appointments                  |");
            System.out.println("|                 3. Logout                               |");
            System.out.println("|                 4. Exit                                 |");
            System.out.println("+---------------------------------------------------------+");

            input = getValidInput(4);

            if (input.equals("1")) {
                do {
                    System.out.println("+--------------------Receptionist Menu--------------------+");
                    System.out.println("|                 1. View Patients                        |");
                    System.out.println("|                 2. Add Patient                          |");
                    System.out.println("|                 3. Remove Patient                       |");
                    System.out.println("|                 4. Back                                 |");
                    System.out.println("|                 5. Exit                                 |");
                    System.out.println("+---------------------------------------------------------+");

                    input = getValidInput();

                    switch (input) {
                        case "1":
                            receptionist.printPatients();
                            break;
                        case "2":
                            receptionist.addPatient();
                            break;
                        case "3":
                            receptionist.deletePatient();
                            break;
                        case "5":
                            exit();
                            break;
                    }
                    System.out.println();
                } while (!input.equals("4"));

                input = "";
            } else if (input.equals("2")) {
                do {
                    System.out.println("+--------------------Receptionist Menu--------------------+");
                    System.out.println("|                 1. View Appointments                    |");
                    System.out.println("|                 2. Create Appointment                   |");
                    System.out.println("|                 3. Delete Appointment                   |");
                    System.out.println("|                 4. Back                                 |");
                    System.out.println("|                 5. Exit                                 |");
                    System.out.println("+---------------------------------------------------------+");

                    input = getValidInput();

                    switch (input) {
                        case "1":
                            receptionist.printAppointments();
                            break;
                        case "2":
                            System.out.println("MAKE APPOINTMENT");
                            break;
                        case "3":
                            receptionist.deleteAppointment();
                            break;
                        case "5":
                            exit();
                            break;
                    }
                } while (!input.equals("4"));

                input = "";
            } else if (input.equals("4")) {
                exit();
            }
        } while (!input.equals("3"));
    }

    private static void doctorMenu(String asUser) {
        Doctor doctor = new Doctor(asUser, scanner, dataManager);
        Receptionist receptionist = new Receptionist(scanner, dataManager);
        String input = "";

        do {
            System.out.println("+-----------------------Doctor Menu-----------------------+");
            System.out.println("|                 1. View Patients                        |");
            System.out.println("|                 2. Find Patient Record                  |");
            System.out.println("|                 3. Create Patient Record                |");
            System.out.println("|                 4. Return to Start                      |");
            System.out.println("|                 5. Exit                                 |");
            System.out.println("+---------------------------------------------------------+");

            input = getValidInput();

            switch (input) {
                case "1":
                    receptionist.printPatients();
                    break;
                case "2":
                    doctor.printReports();
                    break;
                case "3":
                    doctor.createReport();
                    break;
                case "5":
                    exit();
                    break;
            }
            System.out.println();
        } while (!input.equals("4"));
    }

    private static void adminMenu() {
        Admin admin = new Admin(scanner, dataManager);
        Login login = new Login(scanner, dataManager);
        Receptionist receptionist = new Receptionist(scanner, dataManager);

        String input = "";

        do {
            System.out.println("+-----------------------Admin Menu------------------------+");
            System.out.println("|                 1. Manage Doctors                       |");
            System.out.println("|                 2. Manage Receptionists                 |");
            System.out.println("|                 3. Manage Patients                      |");
            System.out.println("|                 4. Logout                               |");
            System.out.println("|                 5. Exit                                 |");
            System.out.println("+---------------------------------------------------------+");

            input = getValidInput();

            if (Objects.equals(input, "1")) {
                do {
                    System.out.println("+-----------------------Admin Menu------------------------+");
                    System.out.println("|                 1. View Doctors                         |");
                    System.out.println("|                 2. Add Doctor                           |");
                    System.out.println("|                 3. Remove Doctor                        |");
                    System.out.println("|                 4. Back                                 |");
                    System.out.println("|                 5. Exit                                 |");
                    System.out.println("+---------------------------------------------------------+");

                    input = getValidInput();

                    switch (input) {
                        case "1":
                            admin.printDoctors();
                            break;
                        case "2":
                            login.addUser("doctor");
                            break;
                        case "3":
                            admin.deleteUser();
                            break;
                        case "5":
                            exit();
                            break;
                    }
                    System.out.println();
                } while (!input.equals("4"));

                input = ""; // reset input
            } else if (Objects.equals(input, "2")) {
                do {
                    System.out.println("+-----------------------Admin Menu------------------------+");
                    System.out.println("|                 1. View Receptionists                   |");
                    System.out.println("|                 2. Add Receptionist                     |");
                    System.out.println("|                 3. Remove Receptionist                  |");
                    System.out.println("|                 4. Return to Start                      |");
                    System.out.println("|                 5. Exit                                 |");
                    System.out.println("+---------------------------------------------------------+");

                    input = getValidInput();

                    switch(input) {
                        case "1":
                            admin.printReceptionists();
                            break;
                        case "2":
                            login.addUser("receptionist");
                            break;
                        case "3":
                            admin.deleteUser();
                            break;
                        case "5":
                            exit();
                            break;
                    }
                } while (!input.equals("4"));

                input = ""; // reset input
            } else if (Objects.equals(input, "3")) {
                do {
                    System.out.println("+-----------------------Admin Menu------------------------+");
                    System.out.println("|                 1. View Patients                        |");
                    System.out.println("|                 2. Add Patient                          |");
                    System.out.println("|                 3. Remove Patient                       |");
                    System.out.println("|                 4. Back                                 |");
                    System.out.println("|                 5. Exit                                 |");
                    System.out.println("+---------------------------------------------------------+");

                    input = getValidInput();

                    switch (input) {
                        case "1":
                            receptionist.printPatients();
                            break;
                        case "2":
                            receptionist.addPatient();
                            break;
                        case "3":
                            receptionist.deletePatient();
                            break;
                        case "5":
                            exit();
                            break;
                    }
                    System.out.println();
                } while (!input.equals("4"));

                input = "";
            } else if (Objects.equals(input, "5")) {
                exit();
            } // close if
        } while (!Objects.equals(input, "4"));
    } // close adminMenu()

    public static void main(String[] args) {
        try {
            dataManager = new DataManager();
        } catch (Exception e) {
            System.out.println("There is a problem with your database setup or configuration.");
            System.out.println("Please fix the issues and run the program again.");
            e.printStackTrace();

            System.exit(1);
        }

        scanner = new Scanner(System.in);

        Login login = new Login(scanner, dataManager);

        String input;

        do {

            System.out.println("+-----------Welcome to Clinic Management System-----------+");
            System.out.println("|                 1. Receptionist login                   |");
            System.out.println("|                 2. Doctor login                         |");
            System.out.println("|                 3. Admin login                          |");
            System.out.println("|                 4. Register                             |");
            System.out.println("|                 5. Exit                                 |");
            System.out.println("+---------------------------------------------------------+");

            input = getValidInput();

            switch (input) {
                case "1":
                    if (login.userLogin("receptionist") != null) {
                        receptionistMenu();
                    }
                    break;
                case "2":
                    String asUser = login.userLogin("doctor");
                    if (asUser != null) {
                        doctorMenu(asUser);
                    }
                    break;
                case "3":
                    if (login.userLogin("admin") != null) {
                        adminMenu();
                    }
                    break;
                case "4":
                    login.addUser();
                    break;
                case "5":
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
            }
            System.out.println();
        } while (!input.equals("5"));
    }
}
