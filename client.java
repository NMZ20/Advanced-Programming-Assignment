import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }
}