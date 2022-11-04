package Login;

public class user {

    String username;
    String password;

    // constructor
    public user(String user, String pass) {
        username = user;
        password = pass;
    }

    // getter for username
    public String getUser() {
        return username;
    }

    // getter for password
    public String getPass() {
        return password;
    }

}

