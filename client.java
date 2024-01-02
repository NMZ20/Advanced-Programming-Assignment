import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

// Creating ClientThread class that extends Thread class
class ClientThread extends Thread {
    // Overriding run() method
    @Override
    public void run(){
        try {
            // Create array of acceptable options
            String[] options = {"1", "2", "3", "q"};
            // Sort the array using Arrays.sort() method
            Arrays.sort(options);
            //Create socket that connects to localhost on port 6000 which is the port number of the server
            Socket socket = new Socket("localhost", 6000);
            // Create input and output streams associated with the socket
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            // Create scanner object to read input from the user
            Scanner sc = new Scanner(System.in); 

            System.out.println();
            System.out.println("************ Mwasalat Journey Planner ************");
            System.out.println();
            System.out.print("Enter your civil number: ");
            // Read civil number from the user
            String civilnumber = sc.nextLine();
            System.out.println();

            // The while loop will keep running until the user enters "q"
            while (true) {
                // Display the options to the user
                System.out.println("==========================");
                System.out.println("1.        Monthly");
                System.out.println("2.        Half Yearly");
                System.out.println("3.        Yearly");
                System.out.println("q to terminate the program.");
                System.out.println("==========================");
                System.out.println();
                System.out.print("Select an option by entering the option number: ");
                // Read the option from the user
                String option = sc.nextLine();
                System.out.println();

                //Validate the input, if the input is not in the array of acceptable options,
                //display error message and continue to the next iteration.
                if (Arrays.binarySearch(options, option) < 0) {
                    System.out.println();
                    System.out.println(option + " is invalid input.");
                    System.out.println();
                    continue;
                }

                //If the user enters "q", send "q" to the server and terminate the program
                if (option.equals("q")) {
                    System.out.println("Terminating...");
                    outputStream.writeUTF(option);
                    socket.close();
                    break;
                }
                
                //Send the valid option to the server
                outputStream.writeUTF(option);
                //Print the sent option to the console
                System.out.println("Sent: " + option);
                //Read the recieved value from the server
                String value = inputStream.readUTF();
                //Print the recieved value to the console
                System.out.println("Recieved: " + value);
                System.out.println();
            }
            //Close the scanner and the socket
            sc.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class client {
    public static void main(String[] args) {
        // Creating ClientThread object
        ClientThread clientThread = new ClientThread();
        // Start the thread, the overridden run() method will execute
        clientThread.start();
    }
}