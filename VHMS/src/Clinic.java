
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
 * @author lakmal
 */
public class Clinic {
    private Connection dbcon;
    
    Clinic(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public ResultSet getClinicList(String petid){
        String sql = "select clinicID from clinic where petID='"+petid+"'";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_clinic_list = stmnt.executeQuery(sql);
            return rs_clinic_list;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet getClinicDetails(String clinicid){
        String sql = "select date,complaints,observations,labfindings,diagnosis,remarks from clinic where clinicID='"+clinicid+"'";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_clinic_details = stmnt.executeQuery(sql);
            return rs_clinic_details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String generateClinicID(Component comp){
        String prefix="CL";
        int noClinics=0;
        String clinicID=null;
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select clinic from count");
            while(rs.next()){
                noClinics = rs.getInt("clinic");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot retrive data from the database!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
        
        if(noClinics<9){
            clinicID=prefix.concat("00000").concat(Integer.toString(noClinics+1));
            return clinicID;
        }
        else if(noClinics<99){
            clinicID=prefix.concat("0000").concat(Integer.toString(noClinics+1));
            return clinicID;
        }
        else if(noClinics<999){
            clinicID=prefix.concat("000").concat(Integer.toString(noClinics+1));
            return clinicID;
        }
        else if(noClinics<9999){
            clinicID=prefix.concat("00").concat(Integer.toString(noClinics+1));
            return clinicID;
        }
        else if(noClinics<99999){
            clinicID=prefix.concat("0").concat(Integer.toString(noClinics+1));
            return clinicID;
        }
        else if(noClinics<999999){
            clinicID=prefix.concat(Integer.toString(noClinics+1));
            return clinicID;
        }
        else{
            JOptionPane.showMessageDialog(comp, "Cannot Generate ClinicID. Maximum no.of Clinics Reached","Database Error",JOptionPane.ERROR_MESSAGE);
            return clinicID;
        }
    }
    
    public void increaseNoClinicsByOne(){
        try{
            int noClinics=0;
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select clinic from count");
            while(rs.next()){
                noClinics = rs.getInt("clinic");
            }
            try{
                String SQL = "update count set clinic="+(noClinics+1)+" where clinic="+noClinics;
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
}
