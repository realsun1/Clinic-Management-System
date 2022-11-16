package Login;

import java.util.Scanner;
import UserDatabase.DataManager;
import java.sql.*;

public class login {

    private static String input;
    private register register = new register();

    public void start() {

        System.out.println("+-----------Welcome to Clinic Management System-----------+");
        System.out.println("|                 1. Admin login                          |");
        System.out.println("|                 2. Doctor login                         |");
        System.out.println("|                 3. Receptionist login                   |");
        System.out.println("|                 4. Register                             |");
        System.out.println("|                 5. Exit                                 |");
        System.out.println("+---------------------------------------------------------+");

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                input = scanner.nextLine().toLowerCase();

                if (input.equals("1")) {
                    userLogin("admin");
                    break;
                }

                else if (input.equals("2")) {
                    userLogin("doctor");
                    break;
                }

                else if (input.equals("3")) {
                    userLogin("receptionist");
                    break;
                }

                else if (input.equals("register") || input.equals("4")) {
                    register.createUser();
                    start();
                }

                else if (input.equals("exit") || input.equals("5")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }

                else {
                    System.out.println("Invalid command");
                    continue;
                }
            } while (true);
        }
    }

    public void userLogin(String type) {
        String user,pass;
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter Username: ");
        String username = input.nextLine();
        System.out.println("\nEnter Password: ");
        String password = input.nextLine();

        try {
            Connection connection = DataManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Users WHERE type=" + '"' + type + '"');
            while (rs.next()) {
                user = rs.getString(3);
                pass = rs.getString(4);

                System.out.println(user);
                System.out.println(pass);

                if (user.equals(username) && pass.equals(password)) {
                    System.out.println("Login Success.\n");
                }

                else
                    continue;

                    if (type.equals("admin")) {
                        adminMenu();
                        break;
                    }

                    else if (type.equals("doctor")) {
                        doctorMenu();
                        break;
                    }

                    else {
                        receptionistMenu();
                        break;
                    }
                }
                System.out.println("Invalid login, returning..");
                start();
                
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void adminMenu() {
    System.out.println("+-----------------------Admin Menu------------------------+");
    System.out.println("|                 1. View Doctors                         |");
    System.out.println("|                 2. View Receptionists                   |");
    System.out.println("|                 3. View Patients                        |");
    System.out.println("|                 4. Return to Start                      |");
    System.out.println("|                 5. Exit                                 |");
    System.out.println("+---------------------------------------------------------+");
    try (Scanner scanner = new Scanner(System.in)) {
        do {
            input = scanner.nextLine().toLowerCase();

            if (input.equals("1")) {
                System.out.println("nothing here for now");
                continue;
            }

            else if (input.equals("2")) {
                System.out.println("nothing here for now");
                continue;
            }

            else if (input.equals("3")) {
                System.out.println("nothing here for now");
                continue;
            }

            else if (input.equals("4")) {
                start();
            }

            else if (input.equals("exit") || input.equals("5")) {
                System.out.println("Exiting...");
                System.exit(0);
            }

            else {
                System.out.println("Invalid command.");
                continue;
            }
        } while (true);
    }
    } 

    public void doctorMenu() {
    System.out.println("+-----------------------Doctor Menu-----------------------+");
    System.out.println("|                 1. View Patients                        |");
    System.out.println("|                 2. Search Patient                       |");
    System.out.println("|                 3. Prescribe Medicine                   |");
    System.out.println("|                 4. Return to Start                      |");
    System.out.println("|                 5. Exit                                 |");
    System.out.println("+---------------------------------------------------------+");
    try (Scanner scanner = new Scanner(System.in)) {
        do {
            input = scanner.nextLine().toLowerCase();

            if (input.equals("1")) {
                System.out.println("nothing here for now");
                continue;
            }

            else if (input.equals("2")) {
                System.out.println("nothing here for now");
                continue;
            }

            else if (input.equals("3")) {
                System.out.println("nothing here for now");
                continue;
            }

            else if (input.equals("4")) {
                start();
            }

            else if (input.equals("exit") || input.equals("5")) {
                System.out.println("Exiting...");
                System.exit(0);
            }

            else {
                System.out.println("Invalid command.");
                continue;
            }
        } while (true);
    }
    } 

    public void receptionistMenu() {
        System.out.println("+--------------------Receptionist Menu--------------------+");
        System.out.println("|                 1. View Patients                        |");
        System.out.println("|                 2. Search Patient                       |");
        System.out.println("|                 3. Appointments                         |");
        System.out.println("|                 4. Return to Start                      |");
        System.out.println("|                 5. Exit                                 |");
        System.out.println("+---------------------------------------------------------+");
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                input = scanner.nextLine().toLowerCase();

                if (input.equals("1")) {
                    System.out.println("nothing here for now");
                    continue;
                }

                else if (input.equals("2")) {
                    System.out.println("nothing here for now");
                    continue;
                }

                else if (input.equals("3")) {
                    System.out.println("nothing here for now");
                    continue;
                }

                else if (input.equals("4")) {
                    start();
                }

                else if (input.equals("exit") || input.equals("5")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }

                else {
                    System.out.println("Invalid command.");
                    continue;
                }
            } while (true);
        }
    } 

}
