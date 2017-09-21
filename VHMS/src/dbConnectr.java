/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hirantha
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import javax.swing.*;

public class dbConnectr {
    
    Connection conn = null;
    
    public Connection Connect(){
        String conURL = "";
        String conUN = "";
        String conPW = "";

        try{
            try (Scanner sc = new Scanner(new File("conData"))) {
                while(sc.hasNext()){
                    conURL = "jdbc:mysql://"+sc.next()+":"+sc.next()+"/vhms?autoReconnect=true&useSSL=false";
                    conUN = sc.next();
                    conPW = sc.next();
                }
            }
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(conURL,conUN,conPW);
            //JOptionPane.showMessageDialog(null, "Connection Successfull", "Notification",JOptionPane.INFORMATION_MESSAGE);
            return conn;
        }
        catch(SQLException e){  
            JOptionPane.showMessageDialog(null, "Connection error1", "Notification",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Connection eror2", "Notification",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Connection error3", "Notification",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
