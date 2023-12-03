import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class ClientThread extends Thread {
    @Override
    public void run(){
        try {

            String[] options = {"1", "2", "3", "q"};
            Arrays.sort(options);

            Socket socket = new Socket("localhost", 6000);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            Scanner sc = new Scanner(System.in); 

            System.out.println();
            System.out.println("************ Mwasalat Journey Planner ************");
            System.out.println();
            System.out.print("Enter your civil number: ");
            String civilnumber = sc.nextLine();
            System.out.println();

            while (true) {
                System.out.println("==========================");
                System.out.println("1.        Monthly");
                System.out.println("2.        Half Yearly");
                System.out.println("3.        Yearly");
                System.out.println("q to terminate the program.");
                System.out.println("==========================");
                System.out.println();
                System.out.print("Select an option by entering the option number: ");
                String option = sc.nextLine();
                System.out.println();
                //Validate the input
                if (Arrays.binarySearch(options, option) < 0) {
                    System.out.println();
                    System.out.println(option + " is invalid input.");
                    System.out.println();
                    continue;
                }

                if (option.equals("q")) {
                    System.out.println("Terminating...");
                    outputStream.writeUTF(option);
                    socket.close();
                    break;
                }
                
                outputStream.writeUTF(option);
                System.out.println("Sent: " + option);
                
                String value = inputStream.readUTF();
                System.out.println("Recieved: " + value);
                System.out.println();
            }

            sc.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
