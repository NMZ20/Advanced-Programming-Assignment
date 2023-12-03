import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

public class ServerThread extends Thread {

    @Override
    public void run(){
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

            // ServerThread serverThread = new ServerThread(socket, con);
            // serverThread.start();

            while(true){
                //Read optionID from client
                String value = inputStream.readUTF();

                //If client sends "q", server will terminate
                if (value.equals("q")) {
                    System.out.println("Server is terminating...");
                    break;
                }

                //Print the recieved option
                System.out.println("Recieved option number: " + value);

                // Create statement
                Statement stmt = con.createStatement();
                // Prepare query string
                String sql = prepareQuery(value);
                //Execute query
                ResultSet rs = stmt.executeQuery(sql);

                // Declare variables
                int option = 0;
                String booking_duration = "";
                double discount = 0;

                // Process result set
                while (rs.next()) {
                    option = rs.getInt("option");
                    booking_duration = rs.getString("booking_duration");
                    discount = Double.parseDouble(rs.getString("discount"));
                    System.out.println("option: " + option);
                    System.out.println("booking_duration: " + booking_duration);
                    System.out.println("discount: " + discount);
                    System.out.println();
                }

                //Number of days in a month
                double numberOfDays = 30;
                //Ticket price in OMR
                double ticketPrice = 1.5;
                
                double cp = ticketPrice * option * numberOfDays;
                double discountAmount = cp * discount;
                double finalPrice = cp - discountAmount;
                outputStream.writeUTF("CP: " + Double.toString(cp) + " DiscountAmount: " + Double.toString(discountAmount) + " FinalPrice: " + Double.toString(finalPrice));
                System.out.println();
            }
            
            
            
            // Close db connection
            con.close();
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

    public static String prepareQuery(String id) {
        String sql = "SELECT * FROM discounts WHERE `id` = " + id + ";";
        return sql;
    }
}

