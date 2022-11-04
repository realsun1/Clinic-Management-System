package Login;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class login {

    private static String input;
    private register register = new register();

    public void start() {
        System.out.println("Welcome to Clinic Management System.");
        System.out.println("Options:\n- Register\n- Login\n- Exit");

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                input = scanner.nextLine().toLowerCase();

                if (input.equals("login")) {
                    loginAccount();
                    break;
                }

                else if (input.equals("register")) {
                    register.createAccount();
                    start();
                    break;
                }

                else if (input.equals("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                else  {
                    System.out.println("Invalid command");
                    continue;
                }
            }   while (true);
        }   
    }

    public void loginAccount() {
        try (Scanner input = new Scanner(System.in)) {

            /* do this later */
            // System.out.println("\nAre you an Admin, Doctor or Receptionist?: ");
            // String usertype = input.nextLine();

            System.out.println("\nEnter Username: ");
            String username = input.nextLine();

            System.out.println("\nEnter Password: ");
            String password = input.nextLine();

            user user = new user(username,password);

            verifyAccount(user);
        }
    }

    public void verifyAccount(user account) {
        String user,pass;
        boolean loggedin = false;

        try {
            Scanner input = new Scanner(new File("users.txt"));
            input.nextLine();   // skip first empty line of file
            while (input.hasNextLine() && !loggedin) {
                String s = input.nextLine();  
                user = s.split(":")[0];
                pass = s.split(":")[1];

                if (user.equals(account.getUser()) && (pass.equals(account.getPass()))) {
                    System.out.println("Login success");
                    loggedin = true;
                    break;
              }
            }
            if (!loggedin) {
                System.out.println("Invalid login, returning..\n");
                start();
            }
            input.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
    
}
