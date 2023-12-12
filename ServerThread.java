import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

// Creating ServerThread class that extends Thread class
public class ServerThread extends Thread {
    // Overriding run() method
    @Override
    public void run(){
        try {
            //Port number
            int portNumber = 6000;

            //Database credentials
            String host = "jdbc:mysql://localhost:3306/ap";
            String username = "nmz";
            String password = "nasmz20";

            
            // Create server socket with port number
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Listening on port " + portNumber + "...");
            // Start listening for client requests
            Socket socket = ss.accept();
            // Create input and output streams associated with the socket
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println();

            // Connect to database by passing host, username and password to connectToDB method
            Connection con = connectToDB(host, username, password);

            //This while 
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
                // Prepare query string by passing the recieved option to prepareQuery method
                String sql = prepareQuery(value);
                //Execute query and save the result in the ResultSet rs
                ResultSet rs = stmt.executeQuery(sql);

                // Declare variables to store the result
                int option = 0;
                String booking_duration = "";
                double discount = 0;

                // Process result set by iterating through the result set
                // and storing the result in the declared variables.
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
                //Calculate the cost price
                double cp = ticketPrice * option * numberOfDays;
                //Calculate the discount amount
                double discountAmount = cp * discount;
                //Calculate the final price by subtracting the discount amount from the cost price
                double finalPrice = cp - discountAmount;
                //Send the result to the client
                outputStream.writeUTF("CP: " + Double.toString(cp) + " DiscountAmount: " + Double.toString(discountAmount) + " FinalPrice: " + Double.toString(finalPrice));
                System.out.println();
            }
            
            // Flush the output stream
            outputStream.flush();
            // Close input stream
            inputStream.close();
            // Close db connection
            con.close();
            // Close socket connection
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to connect to database
    public static Connection connectToDB(String host, String username, String password) {
        try {
            Connection con = DriverManager.getConnection(host, username, password);
            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // Method to prepare query, it uses the recieved option to prepare the query
    // that retrieves the discount whose id is equal to the recieved option
    public static String prepareQuery(String id) {
        String sql = "SELECT * FROM discounts WHERE `id` = " + id + ";";
        return sql;
    }
}

