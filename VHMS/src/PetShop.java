
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ButtonModel;
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
    
    public void addPet(String species, String breed, String color, int years, int months, String sex, double price, String sellerID, Component comp){
        try{
            String SQL = "insert into pets_tosell(petID,species,ageYears,ageMonths,breed,color,sex,price,sellerID) values(?,?,?,?,?,?,?,?,?)";
            String petID = generatePetID(comp);
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, petID);
            pst.setString(2, species);
            pst.setInt(3, years);
            pst.setInt(4, months);
            pst.setString(5, breed);
            pst.setString(6, color);
            pst.setString(7, sex);
            pst.setDouble(8, price);
            pst.setString(9, sellerID);
            pst.execute();
            increaseNoPetsByOne();
            JOptionPane.showMessageDialog(comp, "Pet Bought Successfully!","Pet Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(comp, "Pet Buying failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String generatePetID(Component comp){
        String prefix="PS";
        int noPets=0;
        String petID=null;
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select pspet from count");
            while(rs.next()){
                noPets = rs.getInt("pspet");
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
            ResultSet rs = stmnt.executeQuery("select pspet from count");
            while(rs.next()){
                noPets = rs.getInt("pspet");
            }
            try{
                String SQL = "update count set pspet="+(noPets+1)+" where pspet="+noPets;
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
    
    public boolean validatePetName(String petname){
        if(petname.equals("")){
            return false;
        }
        else if(petname.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateSpecies(String species){
        if(species.equals("Select...")){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateAge(int y, int m){
        if(m==0 && y==0){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateBreed(String breed){
        if(breed.equals("")){
            return false;
        }
        else if(breed.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateSex(ButtonModel sex){
        if(sex==null){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validatePrice(String price){
        if(price.equals("")){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateColor(String color){
        if(color.equals("")){
            return false;
        }
        else{
            return true;
        }
    }
}
