
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
    
    public String addCustomer(String name, String address, String tele, String email,Component comp){
        try{
            String SQL = "insert into customer(custID,name,address,telephone,email) values(?,?,?,?,?)";
            String custID = generateCustomerID(comp);
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, custID);
            pst.setString(2, name);
            pst.setString(3, address);
            pst.setString(4, tele);
            pst.setString(5, email);
            pst.execute();
            increaseNoCustomersByOne();
            JOptionPane.showMessageDialog(comp, "Customer added Successfully!","Customer Details",JOptionPane.INFORMATION_MESSAGE);
            return custID;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Customer adding failed!","Database Error",JOptionPane.ERROR_MESSAGE);
            return null;
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
    
    public void addReg_Service(String custID, String service){
        String sql=null;
        if(service.equals("ho")){
            sql = "insert into reg_services(custID,ho) values(?,?)";
        }
        else if(service.equals("mv")){
            sql = "insert into reg_services(custID,mv) values(?,?)";
        }
        else if(service.equals("ps")){
            sql = "insert into reg_services(custID,ps) values(?,?)";
        }
        else if(service.equals("dc")){
            sql = "insert into reg_services(custID,dc) values(?,?)";
        }
        else if(service.equals("hc")){
            sql = "insert into reg_services(custID,hc) values(?,?)";
        }
        
        try{
            PreparedStatement pst = dbcon.prepareStatement(sql);
            pst.setString(1, custID);
            pst.setBoolean(2, true);
            pst.execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateReg_Service(String custID, String service){
        String sql = "update reg_services set "+service+"=? where custID=?";
        
        try{
            PreparedStatement pst = dbcon.prepareStatement(sql);
            pst.setBoolean(1, true);
            pst.setString(2, custID);
            pst.execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateCustomer(String name,String address,String tele,String email,String custID,Component comp){
        try{
            String updateCustDetailsSQL = "update customer set name=?,address=?,telephone=?,email=? where custID=?";
            PreparedStatement pst = dbcon.prepareStatement(updateCustDetailsSQL);
            pst.setString(1, name);
            pst.setString(2, address);
            pst.setString(3, tele);
            pst.setString(4, email);
            pst.setString(5, custID);
            pst.execute();
            JOptionPane.showMessageDialog(comp, "Customer Updated Successfully!","Customer Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Customer Updating failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ResultSet searchByCustomer(String searchText){
        String cust=null;
        cust=searchText;
        String sql = "select custID as 'CustomerID',name as 'Name',address as 'Address',telephone as 'Telephone',email as 'Email' from customer where name like '%"+cust+"%'";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_Cust_Details = stmnt.executeQuery(sql);
            return rs_Cust_Details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet searchByCustID(String searchText){
        String custID=null;
        custID=searchText;
        String sql = "select custID as 'CustomerID',name as 'Name',address as 'Address',telephone as 'Telephone',email as 'Email' from customer where custID like '%"+custID+"%'";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_Cust_Details = stmnt.executeQuery(sql);
            return rs_Cust_Details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public boolean validateCustName(String name){
        if(name.equals("")){
            return false;
        }
        else if(name.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateAddress(String address){
        if(address.equals("")){
            return true;
        }
        else if(address.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateTelephone(String tp){
        if(tp.equals("")){
            return true;
        }
        else if(tp.length()==10){
            if(tp.charAt(0)=='0'){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean validateEmail(String email){
        if(email.equals("")){
            return true;
        }
        else{
            //---------------------------------------
            int atIndex=email.indexOf('@');
            int dotIndex=email.indexOf('.');
            int lastdotIndex=0;
            //----------------------------------------
            if(atIndex==-1){
                return false;
            }
            else{
                int atCount = 0;
                for(int x=0; x<email.length(); x++){
                    if(email.charAt(x)=='@'){
                        atCount++;
                    }
                }
                if(atCount>1){
                    return false;
                }
                else{
                    if(dotIndex==-1){
                        return false;
                    }
                    else{
                        for(int x=0; x<email.length();x++){
                            if(email.charAt(x)=='.'){
                                lastdotIndex=x;
                            }
                        }
                        if(lastdotIndex<atIndex){
                            //check "@" is before the last "."
                            return false;
                        }
                        else if(email.charAt(email.length()-1)=='.'){
                            //check last character is not a "."
                            return false;
                        }
                        else if(email.charAt(0)=='.' || email.charAt(0)=='@'){
                            //check starting characters are not "@" or "."
                            return false;
                        }
                        else if(email.charAt(atIndex-1)=='.' || email.charAt(atIndex+1)=='.'){
                            //check characters before and after "@" is not a "."
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                }
            } 
        }
    }
}
