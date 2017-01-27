/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaclient;

import java.io.*;
import java.net.*;
import java.util.List;

public class RCAClient {
    
    public static int e,n = 0;
    private static BufferedReader in;
    private static PrintWriter out; 
    private static Socket fromserver;
    
    public static void main(String[] args) throws IOException, Exception {
   
    System.out.println("Welcome to Client side");
    
    connectToServer();
    connectionSession();
   // endingConnectionSession();
  }
    private static void connectToServer() throws IOException{
        fromserver = new Socket("localhost",9999);
        in  = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
        out = new PrintWriter(fromserver.getOutputStream(),true);
    }

    private static void connectionSession() throws IOException, Exception{
       analiticMessage(getMessageFromServer());
       clientmessagesesson();
       
    }
    
    private static void clientmessagesesson() throws IOException, Exception{
        BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
        String message;

        while ((message = inu.readLine())!=null) {
            sendClientMessageToServer(message);
        }
    }
    
    private static void sendClientMessageToServer(String message){
        String hashMessage = "";
        for(int i = 0; i<message.length();i++){
        int code = Character.codePointAt(message, i);
        int hashCode = Brain.hashCode(code, i);
        if(hashMessage.equals("")){
            hashMessage = "\""+hashCode+"\"";
        }else{
            hashMessage = hashMessage+" ; \""+hashCode+"\"";
        }
        //System.out.println("char = "+message.charAt(i)+"\n"+ "int = "+code);
        }
        System.out.println(hashMessage);
        out.println("clientMessage{"+hashMessage+"}\n"
                + "end of message");
    }
    
    private static String getMessageFromServer() throws IOException{
        String input = "";
        String output;
        String buffer = "";
        long wdt = 0;
        while (true) {
            boolean lw = false;
            boolean eom = false;
            System.out.println("wdt = "+ wdt);
            if(in.ready()){
                input = in.readLine();
                lw = true;
                wdt = 0;
            }
            if(!input.equalsIgnoreCase("end of message")&&lw) {
                System.out.println("next line");
                buffer = buffer + input;
                System.out.println(buffer);
                lw = false;
            }
            if(input.equalsIgnoreCase("end of message")){
                eom = true;
            }
            if(eom){
                System.out.println("end of message");
                break;
            }
            if(wdt == 1000000){
                break;
            }
            wdt++;
        }
        return buffer;
    }
    
    private static boolean analiticMessage(String message) throws IOException, Exception{
        if(message.equals("")){
            return false;
        }
        Lexer lexer = new Lexer();
        lexer.processInput(message);
        List<Token> tokens = lexer.getTokens();
        Parser parser = new Parser();
        parser.setTokens(tokens);
        parser.lang();
        System.out.println("end of Analitic Message");
        return true;
    }
    
    private static void sendMessageToServer(String message) throws IOException{
        String messageToServer;
        messageToServer = message;
        System.out.println(messageToServer);
        out.println(messageToServer);
    }
    
    private static void endingConnectionSession() throws IOException{
        out.close();
        in.close();
        fromserver.close();
    }
    
}