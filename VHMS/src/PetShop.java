
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
 * @author sashini
 */
public class PetShop {
    private Connection dbcon;
    
    PetShop(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public ResultSet get_toSell_PetIDs(){
        String querry = "Select petID from pets_tosell";
        try{
            Statement s = dbcon.createStatement();
            ResultSet rs = s.executeQuery(querry);
            return rs;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet get_toSell_PetIDByID(String ID){
        String querry = "select petID from pets_tosell where petID like '%"+ID+"%'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet rs = s.executeQuery(querry);
            return rs;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet get_toSell_PetIDBySpecies(String sp){
        String querry = "select petID from pets_tosell where species like '%"+sp+"%'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet rs = s.executeQuery(querry);
            return rs;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getPetDetails(String petID){
        String querry = "Select species, ageYears, ageMonths, breed, color, sex, photo, price, sellerID from pets_tosell where petID='"+petID+"'";
        try{
            Statement s = dbcon.createStatement();
            ResultSet rs = s.executeQuery(querry);
            return rs;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean updatePetDetails(String petID,String species, String breed, String color, String sex, double price, int years, int months){
        
        String querry = "update pets_tosell set species=?, ageYears=?, ageMonths=?, breed=?, color=?, sex=?,  price=? where petID='"+petID+"'";
        try{
            PreparedStatement ps = dbcon.prepareStatement(querry);
            ps.setString(1, species);
            ps.setInt(2, years);
            ps.setInt(3, months);
            ps.setString(4, breed);
            ps.setString(5, color);
            ps.setString(6, sex);
            ps.setDouble(7, price);
            ps.execute();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    
}
