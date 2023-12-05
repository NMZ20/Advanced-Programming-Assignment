import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        // Creating ClientThread object
        ClientThread clientThread = new ClientThread();
        // Start the thread, the overridden run() method will execute
        clientThread.start();
    }
}