
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * @author lakmal
 */
public class Pet {
    private Connection dbcon;
    
    Pet(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public void addPet(String name, String species, int years, int months, String breed, String sex, String custID, Component comp){
        try{
            String SQL = "insert into pets(petID,name,species,ageYears,ageMonths,breed,sex,custID) values(?,?,?,?,?,?,?,?)";
            String petID = generatePetID(comp);
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, petID);
            pst.setString(2, name);
            pst.setString(3, species);
            pst.setInt(4, years);
            pst.setInt(5, months);
            pst.setString(6, breed);
            pst.setString(7, sex);
            pst.setString(8, custID);
            pst.execute();
            increaseNoPetsByOne();
            JOptionPane.showMessageDialog(comp, "Pet added Successfully!","Pet Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(comp, "Pet adding failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String generatePetID(Component comp){
        String prefix="P";
        int noPets=0;
        String petID=null;
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select pet from count");
            while(rs.next()){
                noPets = rs.getInt("pet");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot retrive data from the database!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
        
        if(noPets<9){
            petID=prefix.concat("00000").concat(Integer.toString(noPets+1));
            return petID;
        }
        else if(noPets<99){
            petID=prefix.concat("0000").concat(Integer.toString(noPets+1));
            return petID;
        }
        else if(noPets<999){
            petID=prefix.concat("000").concat(Integer.toString(noPets+1));
            return petID;
        }
        else if(noPets<9999){
            petID=prefix.concat("00").concat(Integer.toString(noPets+1));
            return petID;
        }
        else if(noPets<99999){
            petID=prefix.concat("0").concat(Integer.toString(noPets+1));
            return petID;
        }
        else if(noPets<999999){
            petID=prefix.concat(Integer.toString(noPets+1));
            return petID;
        }
        else{
            JOptionPane.showMessageDialog(comp, "Cannot Generate PetID. Maximum no.of Pets Reached","Database Error",JOptionPane.ERROR_MESSAGE);
            return petID;
        }
    }
    
    public void increaseNoPetsByOne(){
        try{
            int noPets=0;
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select pet from count");
            while(rs.next()){
                noPets = rs.getInt("pet");
            }
            try{
                String SQL = "update count set pet="+(noPets+1)+" where pet="+noPets;
                Statement stmnt2 = dbcon.createStatement();
                stmnt2.executeUpdate(SQL);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ResultSet getPetDetails(){
        String sql = "select petID as 'Pet ID',name as 'Pet Name',species as 'Species', ageYears as 'Years',ageMonths as 'Months' from pets";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_pet_details = stmnt.executeQuery(sql);
            return rs_pet_details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet getPetDetails(String custID){
        String sql = "select petID as 'Pet ID',name as 'Pet Name',species as 'Species', ageYears as 'Years',ageMonths as 'Months' from pets where custID='"+custID+"'";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_pet_details = stmnt.executeQuery(sql);
            return rs_pet_details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String getCustomerID(String petID){
        String sql = "select custID from pets where petID='"+petID+"'";
        String customer="";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);
            while(rs.next()){
                customer = rs.getString("custID");
            }
            return customer;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String getCustomerName(String petID){
        String sql = "select c.name from customer c, pets p where p.custID=c.custID and p.petID='"+petID+"'";
        String customername="";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);
            while(rs.next()){
                customername = rs.getString("name");
            }
            return customername;
        }
        catch(Exception e){
            return null;
        }
    }
}
