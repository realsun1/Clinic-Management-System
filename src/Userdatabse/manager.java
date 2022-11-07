package Userdatabse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class manager {
    static private Connection con;
    static private Statement stat;
    static private String driver = "com.mysql.cj.jdbc.Driver";
    static private String url = "jdbc:mysql://localhost:3306/clinicsystem?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static private String user = "root";
    static private String pwd = "123456";

    // creator for manager, connect to the database
    public manager() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, pwd);
        stat = con.createStatement();
    }

    // after execute all of the sqls, disconnect to the database
    public void disconnect() throws SQLException {
        con.close();
        stat.close();
    }

    // execute query
    public List<String> query(String sql) throws SQLException {
        ResultSet rs = stat.executeQuery(sql);
        List<String> ret = new ArrayList<>();
        int col = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            StringBuilder entry = new StringBuilder();
            for (int i = 1; i <= col; i++) {
                entry.append(rs.getObject(i).toString().trim());
                entry.append(", ");
            }
            entry.deleteCharAt(entry.length() - 1);
            entry.deleteCharAt(entry.length() - 1);
            ret.add(entry.toString());
        }
        return ret;
    }

    // execute other operations
    public boolean execute(String sql) throws SQLException {
        return stat.execute(sql);
    }

    // public static void main(String[] args) throws ClassNotFoundException,
    // SQLException {
    // manager mg = new manager();
    // /*String add = "insert into doctor(dname, dno, location)"
    // + "values(\"erc\",\"strb1\",\"dstn\");";
    // mg.execute(add);*/
    // String query = "select * from doctor";
    // List<String> display = mg.query(query);
    // System.out.println(display.get(0));
    // System.out.println(display.get(1));
    // }
}