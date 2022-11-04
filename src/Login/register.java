package Login;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class register {

    public void createAccount()  {
        Scanner input = new Scanner(System.in);

        System.out.println("\nRegister Username: ");
        String newUsername = input.nextLine();

        System.out.println("\nRegister Password: ");
        String newPassword = input.nextLine();

        user user = new user(newUsername,newPassword);
        addAccount(user);
        System.out.println("Registered successfully.\n");
    }

    public void addAccount(user account) {
        String s = "\n" + account.getUser() + ":" + account.getPass();
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

}


