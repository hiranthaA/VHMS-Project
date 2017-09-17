
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
public class Customer {
    private Connection dbcon;
    
    Customer(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public void addCustomer(String name, String address, String tele, String email,Component comp){
        try{
            String SQL = "insert into customer(custID,name,address,telephone,email) values(?,?,?,?,?)";
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, generateCustomerID(comp));
            pst.setString(2, name);
            pst.setString(3, address);
            pst.setString(4, tele);
            pst.setString(5, email);
            pst.execute();
            increaseNoCustomersByOne();
            JOptionPane.showMessageDialog(comp, "Customer added Successfully!","Customer Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Customer adding failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String generateCustomerID(Component comp){
        String prefix="CS";
        int noCusts=0;
        String custID=null;
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select cust from count");
            while(rs.next()){
                noCusts = rs.getInt("cust");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot retrive data from the database!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
        
        if(noCusts<9){
            custID=prefix.concat("00000").concat(Integer.toString(noCusts+1));
            return custID;
        }
        else if(noCusts<99){
            custID=prefix.concat("0000").concat(Integer.toString(noCusts+1));
            return custID;
        }
        else if(noCusts<999){
            custID=prefix.concat("000").concat(Integer.toString(noCusts+1));
            return custID;
        }
        else if(noCusts<9999){
            custID=prefix.concat("00").concat(Integer.toString(noCusts+1));
            return custID;
        }
        else if(noCusts<99999){
            custID=prefix.concat("0").concat(Integer.toString(noCusts+1));
            return custID;
        }
        else if(noCusts<999999){
            custID=prefix.concat(Integer.toString(noCusts+1));
            return custID;
        }
        else{
            JOptionPane.showMessageDialog(comp, "Cannot Generate CustomerID. Maximum no.of Customers Reached","Database Error",JOptionPane.ERROR_MESSAGE);
            return custID;
        }
    }
    
    public void increaseNoCustomersByOne(){
        try{
            int noCusts=0;
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select cust from count");
            while(rs.next()){
                noCusts = rs.getInt("cust");
            }
            try{
                String SQL = "update count set cust="+(noCusts+1)+" where cust="+noCusts;
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
    
    public ResultSet getCustomerDetails(){
        String sql = "select custID as 'CustomerID',name as 'Name',address as 'Address',telephone as 'Telephone',email as 'Email' from customer";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_Customer_Details = stmnt.executeQuery(sql);
            return rs_Customer_Details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet getCustomerDetails(String ID){
        String sql = "select custID as 'CustomerID',name as 'Name',address as 'Address',telephone as 'Telephone',email as 'Email' from customer where custID='"+ID+"'";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_Customer_Details = stmnt.executeQuery(sql);
            return rs_Customer_Details;
        }
        catch(Exception e){
            return null;
        }
    }
}
