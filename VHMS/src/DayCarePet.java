
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringTokenizer;
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
    
    public ResultSet getDCPets(){
        String sql = "select petID from pet_daycare";
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery(sql);
            return r;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getPetName(String petID){
        String sql = "select name from pets where petID='"+petID+"'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery(sql);
            return r;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getPetOwnerName(String petID){
        String sql = "select c.name from customer c, pets p where c.custID=p.custID and p.petID='"+petID+"'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery(sql);
            return r;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getPetAssignedDate(String petID){
        String sql = "select date from pet_daycare where status=1 and petID='"+petID+"'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery(sql);
            return r;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getPetMeal(String petID){
        String sql = "select meal from pet_daycare where status=1 and petID='"+petID+"'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery(sql);
            return r;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getPetComfort(String petID){
        String sql = "select comfort from pet_daycare where status=1 and petID='"+petID+"'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery(sql);
            return r;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getPetCage(String petID){
        String sql = "select cage from pet_daycare where status=1 and petID='"+petID+"'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet r = s.executeQuery(sql);
            return r;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public int getDateDiff(String sDate, String eDate){
        int year=0;
        int month=0;
        int day=0;
        StringTokenizer startDate = new StringTokenizer(sDate,"-");
        while(startDate.hasMoreElements()){
            year=Integer.parseInt(startDate.nextToken());
            month=Integer.parseInt(startDate.nextToken());
            day=Integer.parseInt(startDate.nextToken());
        }
        LocalDate d1 = LocalDate.of(year, month, day);
        StringTokenizer endDate = new StringTokenizer(eDate,"-");
        while(endDate.hasMoreElements()){
            year=Integer.parseInt(endDate.nextToken());
            month=Integer.parseInt(endDate.nextToken());
            day=Integer.parseInt(endDate.nextToken());
        }
        LocalDate d2 = LocalDate.of(year, month, day);

        int days = (int)ChronoUnit.DAYS.between(d1, d2);
        return days;
    }
    
    public void addDayCareFacilities(String petID, String meal, String comfort, String cage){
        String sql = "update pet_daycare set meal=?, comfort=?, cage=? where petID=?";
        try {
            PreparedStatement p = dbcon.prepareStatement(sql);
            p.setString(1, meal);
            p.setString(2, comfort);
            p.setString(3, cage);
            p.setString(4, petID);
            p.execute();
            JOptionPane.showMessageDialog(null, "Facilities Saved Successfully!","Day Care",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Saving facilities failed!","Day Care",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
