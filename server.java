import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

public class server {
    public static void main(String[] args) {
            ServerThread serverThread = new ServerThread();
            serverThread.start();
    }
}