import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

public class server {
    public static void main(String[] args) {
            // Creating ServerThread object
            ServerThread serverThread = new ServerThread();
            // Start the thread, the overridden run() method will execute
            serverThread.start();
    }
}