package Login;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import UserDatabase.DataManager;
import Validator.Validator;

import java.sql.*;

public class Login {

    private static String input;

    private Scanner in;
    private DataManager manager;

    public Login(Scanner in, DataManager manager) {
        this.in = in;
        this.manager = manager;
    }

//    public void start() {
//
//        System.out.println("+-----------Welcome to Clinic Management System-----------+");
//        System.out.println("|                 1. Admin login                          |");
//        System.out.println("|                 2. Doctor login                         |");
//        System.out.println("|                 3. Receptionist login                   |");
//        System.out.println("|                 4. Register                             |");
//        System.out.println("|                 5. Exit                                 |");
//        System.out.println("+---------------------------------------------------------+");
//
//
//        do {
//            input = in.nextLine().toLowerCase();
//
//            if (input.equals("1")) {
//                userLogin("admin");
//                break;
//            }
//
//            else if (input.equals("2")) {
//                userLogin("doctor");
//                break;
//            }
//
//            else if (input.equals("3")) {
//                userLogin("receptionist");
//                break;
//            }
//
//            else if (input.equals("register") || input.equals("4")) {
//                register.createUser();
//                start();
//            }
//
//            else if (input.equals("exit") || input.equals("5")) {
//                System.out.println("Exiting...");
//                System.exit(0);
//            }
//
//            else {
//                System.out.println("Invalid command");
//                continue;
//            }
//        } while (true);
//    }

    public String userLogin(String type) {
        String user,pass;
        System.out.println("\nEnter Username: ");
        String username = in.nextLine();
        System.out.println("\nEnter Password: ");
        String password = in.nextLine();

        try {
            Connection connection = DataManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE type=" + '"' + type + '"');

            while (rs.next()) {
                user = rs.getString(1);
                pass = rs.getString(2);

                if (user.equals(username) && pass.equals(password)) {
                    System.out.println("Login Success.\n");
                    return username;
                }
            }

            System.out.println("\nInvalid login, returning..");
            return null;
        } catch (SQLException e) {
            System.out.println("\nAn unknown error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    public void addUser() {
        String input = "";
        System.out.println("Please Enter Type (doctor, receptionist, admin):");
        input = (in.nextLine().toLowerCase());
        while (!Validator.validateUserType(input)) {
            System.out.println("Invalid user type. Enter 'doctor', 'receptionist', or 'admin':");
            input = (in.nextLine().toLowerCase());
        }

        addUser(input);
    }

    public void addUser(String type) {
        List<String> info = new ArrayList<>();
        info.add(type); // 0: type

        System.out.println("Please Enter Username:");
        info.add(in.nextLine()); // 1: username
        System.out.println("Please Enter Password:");
        info.add(in.nextLine()); // 2: password

        if (type.equals("doctor")) {
            System.out.println("Please Enter Doctor Name:");
            info.add(in.nextLine()); // 3: name
            System.out.println("Please Enter Doctor ID:");
            info.add(in.nextLine()); // 4: id
            System.out.println("Please Enter Doctor Location:");
            info.add(in.nextLine()); // 5: location
        }

        try {
            info = Validator.userValidator(info, in, manager);
            String sql = "";
            String sql2 = "";

            if (!info.get(0).equals("doctor")) {
                sql = "insert into user (type, username,password)"
                        + " values(\"" + info.get(0) + "\",\"" + info.get(1) + "\",\"" + info.get(2)
                        + "\");";
            } else {
                sql = "insert into user (type, username,password,doctor_number)"
                        + " values(\"" + info.get(0) + "\",\"" + info.get(1) + "\",\"" + info.get(2)
                        + "\"," + Integer.parseInt(info.get(4)) + ");";
                sql2 = "insert into doctor (dnumber, dname, location)"
                        + " values(\"" + info.get(4) + "\",\"" + info.get(3) + "\",\"" + info.get(5)
                        + "\");";
            }
            manager.execute(sql);
            if (!sql2.equals("")) {
                manager.execute(sql2);
            }

            System.out.println("Add Successful.");
        } catch (SQLException e) {
            System.out.println("User Add Failed.");
            e.printStackTrace();
        }
    }

//    public void userLogin(String type) {
//        String user,pass;
//        System.out.println("\nEnter Username: ");
//        String username = in.nextLine();
//        System.out.println("\nEnter Password: ");
//        String password = in.nextLine();
//
//        try {
//            Connection connection = DataManager.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM login_info WHERE type=" + '"' + type + '"');
//            while (rs.next()) {
//                user = rs.getString(3);
//                pass = rs.getString(4);
//
//                if (user.equals(username) && pass.equals(password)) {
//                    System.out.println("Login Success.\n");
//                }
//
//                else
//                    continue;
//
//                    if (type.equals("admin")) {
//                        adminMenu();
//                        break;
//                    }
//
//                    else if (type.equals("doctor")) {
//                        doctorMenu();
//                        break;
//                    }
//
//                    else {
//                        receptionistMenu();
//                        break;
//                    }
//                }
//                System.out.println("Invalid login, returning..");
//                start();
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

//    public void adminMenu() {
//    System.out.println("+-----------------------Admin Menu------------------------+");
//    System.out.println("|                 1. View Doctors                         |");
//    System.out.println("|                 2. View Receptionists                   |");
//    System.out.println("|                 3. View Patients                        |");
//    System.out.println("|                 4. Return to Start                      |");
//    System.out.println("|                 5. Exit                                 |");
//    System.out.println("+---------------------------------------------------------+");
//    try (Scanner scanner = new Scanner(System.in)) {
//        do {
//            input = scanner.nextLine().toLowerCase();
//
//            if (input.equals("1")) {
//                System.out.println("nothing here for now");
//                continue;
//            }
//
//            else if (input.equals("2")) {
//                System.out.println("nothing here for now");
//                continue;
//            }
//
//            else if (input.equals("3")) {
//                System.out.println("nothing here for now");
//                continue;
//            }
//
//            else if (input.equals("4")) {
//                start();
//            }
//
//            else if (input.equals("exit") || input.equals("5")) {
//                System.out.println("Exiting...");
//                System.exit(0);
//            }
//
//            else {
//                System.out.println("Invalid command.");
//                continue;
//            }
//        } while (true);
//    }
//    }

//    public void doctorMenu() {
//    System.out.println("+-----------------------Doctor Menu-----------------------+");
//    System.out.println("|                 1. View Patients                        |");
//    System.out.println("|                 2. Search Patient                       |");
//    System.out.println("|                 3. Prescribe Medicine                   |");
//    System.out.println("|                 4. Return to Start                      |");
//    System.out.println("|                 5. Exit                                 |");
//    System.out.println("+---------------------------------------------------------+");
//    try (Scanner scanner = new Scanner(System.in)) {
//        do {
//            input = scanner.nextLine().toLowerCase();
//
//            if (input.equals("1")) {
//                System.out.println("nothing here for now");
//                continue;
//            }
//
//            else if (input.equals("2")) {
//                System.out.println("nothing here for now");
//                continue;
//            }
//
//            else if (input.equals("3")) {
//                System.out.println("nothing here for now");
//                continue;
//            }
//
//            else if (input.equals("4")) {
//                start();
//            }
//
//            else if (input.equals("exit") || input.equals("5")) {
//                System.out.println("Exiting...");
//                System.exit(0);
//            }
//
//            else {
//                System.out.println("Invalid command.");
//                continue;
//            }
//        } while (true);
//    }
//    }

//    public void receptionistMenu() {
//        System.out.println("+--------------------Receptionist Menu--------------------+");
//        System.out.println("|                 1. View Patients                        |");
//        System.out.println("|                 2. Search Patient                       |");
//        System.out.println("|                 3. Appointments                         |");
//        System.out.println("|                 4. Return to Start                      |");
//        System.out.println("|                 5. Exit                                 |");
//        System.out.println("+---------------------------------------------------------+");
//        try (Scanner scanner = new Scanner(System.in)) {
//            do {
//                input = scanner.nextLine().toLowerCase();
//
//                if (input.equals("1")) {
//                    System.out.println("nothing here for now");
//                    continue;
//                }
//
//                else if (input.equals("2")) {
//                    System.out.println("nothing here for now");
//                    continue;
//                }
//
//                else if (input.equals("3")) {
//                    System.out.println("nothing here for now");
//                    continue;
//                }
//
//                else if (input.equals("4")) {
//                    start();
//                }
//
//                else if (input.equals("exit") || input.equals("5")) {
//                    System.out.println("Exiting...");
//                    System.exit(0);
//                }
//
//                else {
//                    System.out.println("Invalid command.");
//                    continue;
//                }
//            } while (true);
//        }
//    }
}
