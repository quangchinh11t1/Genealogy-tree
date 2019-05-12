package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MySQL {
    private String driver;
    private String url ;
    private  String user;
    private String password;



    public MySQL(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public MySQL() {
        this.driver = "com.mysql.jdbc.Driver";
        this.url ="jdbc:mysql://localhost:3306/";
        this.user = "root";
        this.password="";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Connection getConnection() throws Exception{
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
            return conn ;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public ResultSet getMemberData() throws Exception {
        Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("select * from member");

        ResultSet result = statement.executeQuery();

        return result;
    }

    public ArrayList<String> get() throws Exception {
        Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("select maGD, hotenTV, maTVcha from thanhvien");

        ResultSet result = statement.executeQuery();
        ArrayList<String> array = new ArrayList<String>();
        while (result.next()){
         //   System.out.println(result.toString());
            System.out.println(result.getString("hotenTV"));
            array.add(result.getString("hotenTV"));
        }
        return array;
    }
}
