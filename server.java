import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

public class server {
    public static void main(String[] args) {
        try {
            //Port number
            int portNumber = 6000;
            //Database credentials
            String host = "jdbc:mysql://localhost:3306/ap";
            String username = "nmz";
            String password = "nasmz20";

            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Listening on port " + portNumber + "...");
            Socket socket = ss.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println();
            // Connect to database
            Connection con = connectToDB(host, username, password);

            while(true){
                //Read option from client
                String value = inputStream.readUTF();
                //If client sends "q", server will terminate
                if (value == "q") {
                    System.out.println("Server is terminating...");
                    break;
                }

                //Print the recieved option
                System.out.println("Recieved option: " + value);

                // Create statement
                Statement stmt = con.createStatement();
                // Prepare query string
                String sql = prepareQuery(value);
                //Execute query
                ResultSet rs = stmt.executeQuery(sql);
                // Process result set
                while (rs.next()) {
                    int option = rs.getInt("option");
                    String booking_duration = rs.getString("booking_duration");
                    String discount = rs.getString("discount");
                    System.out.println("option: " + option);
                    System.out.println("booking_duration: " + booking_duration);
                    System.out.println("discount: " + discount);
                    System.out.println();
                }
                
                int doubledValue = Integer.parseInt(value) * 2;
                outputStream.writeUTF(Integer.toString(doubledValue));
                System.out.println("Sent: " + doubledValue);
                System.out.println();
                // Close db connection
                con.close();
            }

            
                
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection connectToDB(String host, String username, String password) {
        try {
            Connection con = DriverManager.getConnection(host, username, password);
            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String prepareQuery(String option) {
        String sql = "SELECT * FROM discounts WHERE `option` = " + option + ";";
        return sql;
    }
}