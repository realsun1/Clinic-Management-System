package Login;

import UserDatabase.DataManager;
import java.util.Scanner;
import java.sql.*;

public class register {

    public void createUser()  {
        int id = 1;
        String type;
        String username;
        String password;

        Scanner input = new Scanner(System.in);
        System.out.println("\nRegister as an Admin, Doctor or Receptionist: ");
        type = input.nextLine().toLowerCase();
        System.out.println("\nRegister Username: ");
        username = input.nextLine();
        System.out.println("\nRegister Password: ");
        password = input.nextLine();

        try {
            Connection connection = DataManager.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Users VALUES ('"+ getUserID(id) +"','"+ type +"','"+ username +"','"+ password +"')");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Registered successfully.\n");
    }

    // auto incrementing id for user database
    public int getUserID(int id) {
        Connection connection = DataManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) FROM Users");
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return id + 1;
    }

}


