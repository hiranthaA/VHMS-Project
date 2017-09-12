
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hirantha
 */
public class User {
    private Connection dbcon;
    
    User(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public void createNewUser(String userName, String password, String cPassword){
        
    }
    
    public boolean validateLoginDetails(String usrn, String pw, Component comp){
        boolean unOK = false;
        boolean pwOK = false;
        String user="";
        
        if(usrn.equals("") | pw.equals("")){
            JOptionPane.showMessageDialog(comp,"Incorrect Username or Password !","Sign In Failed",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            try{
                Statement stmnt = dbcon.createStatement();
                ResultSet rsLoginfo = stmnt.executeQuery("select * from access_info");

                while(rsLoginfo.next()){
                    if(usrn.equals(rsLoginfo.getString("username"))){
                        unOK = true;
                        if(pw.equals(rsLoginfo.getString("password"))){
                            pwOK = true;
                            MainWindow.accessedUser = rsLoginfo.getString("userID");
                        }
                    }  
                }

                if(unOK && pwOK){
                    return true;
                }
                else{
                    return false;
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(comp,"Database connection failed!","Database Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }
}
