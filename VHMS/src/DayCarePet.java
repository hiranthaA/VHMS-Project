
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pavithra
 */
public class DayCarePet {
    
    private Connection dbcon;
    
    DayCarePet(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public boolean isPetIn(String petID){
        String sql = "select status from pet_daycare where petID='"+petID+"'";
        boolean status=false;
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery(sql);
            while(r.next()){
                status = r.getBoolean("status");
            }
            return status;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return status;
        }
    }
    
    public void takeCare(String petID){
        String sql = "insert into pet_daycare(daycareID,date,petID,status) values(?,?,?,?)";
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String day = df.format(date);
        String dcID=generateDCID();
        try {
            PreparedStatement p = dbcon.prepareStatement(sql);
            p.setString(1, dcID);
            p.setString(2, day);
            p.setString(3, petID);
            p.setBoolean(4, true);
            p.execute();
            JOptionPane.showMessageDialog(null, "Took Care of the Pet Successfully!","Day Care",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String generateDCID(){
        int count = 0;
        String ID;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String day = df.format(date);
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery("select count(daycareID) as 'count' from pet_daycare");
            while(r.next()){
                count = r.getInt("count");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        ID = day.concat(Integer.toString(count+1));
        return ID;
    }
}
