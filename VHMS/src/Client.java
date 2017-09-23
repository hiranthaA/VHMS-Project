
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
 * @author hirantha
 */
public class Client {
    
    private Connection dbcon;
    
    Client(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public ResultSet searchByCompany(String searchText){
        String company=null;
        company=searchText;
        String sql = "select clientID as 'ClientID',company_name as 'Company', address as 'Address', tele1 as 'Telephone #1', tele2 as 'Telephone #2', email as 'Email' from finance_clients where company_name like '%"+company+"%'";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_Client_Details = stmnt.executeQuery(sql);
            return rs_Client_Details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet searchByProduct(String searchText){
        String product=null;
        product=searchText;
        String sql = "select distinct fc.clientID,fc.company_name,fc.address,fc.tele1,fc.tele2,fc.email from client_products cp,finance_clients fc where cp.clientID=fc.clientID and cp.product like '%"+product+"%'";
        if(product.equals("")){
            return this.getClientDetails();
        }
        else{
            try{
                Statement stmnt = dbcon.createStatement();
                ResultSet rs_Product_Details = stmnt.executeQuery(sql);
                return rs_Product_Details;
            }
            catch(Exception e){
                return null;
            }
        }
    }
    
    public ResultSet getClientDetails(){
        String sql = "select clientID as 'ClientID',company_name as 'Company', address as 'Address', tele1 as 'Telephone #1', tele2 as 'Telephone #2', email as 'Email' from finance_clients";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_Client_Details = stmnt.executeQuery(sql);
            return rs_Client_Details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet getProductDetails(String client){
        String ClientID = client;
            String sql = "select product as 'Product' from client_products where clientID='"+ClientID+"'";
            try{
                Statement stmnt = dbcon.createStatement();
                ResultSet rs_Product_Details = stmnt.executeQuery(sql);
                return rs_Product_Details;
            }
            catch(Exception e){
                return null;
            }
    }
    
    public void setNewClient(String company,String address,String tele1,String tele2,String email,Component comp){
        try{
            String SQL = "insert into finance_clients (clientID,company_name,address,tele1,tele2,email) values(?,?,?,?,?,?)";
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, generateClientID(comp));
            pst.setString(2, company);
            pst.setString(3, address);
            pst.setString(4, tele1);
            pst.setString(5, tele2);
            pst.setString(6, email);
            pst.execute();
            increaseNoClientsByOne();
            JOptionPane.showMessageDialog(comp, "Client added Successfully!","Client Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Client adding failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void updateClient(String company,String address,String tele1,String tele2,String email,String clientID,Component comp){
        try{
            String updateClientDetailsSQL = "update finance_clients set company_name=?,address=?,tele1=?,tele2=?,email=? where clientID=?";
            PreparedStatement pst = dbcon.prepareStatement(updateClientDetailsSQL);
            pst.setString(1, company);
            pst.setString(2, address);
            pst.setString(3, tele1);
            pst.setString(4, tele2);
            pst.setString(5, email);
            pst.setString(6, clientID);
            pst.execute();
            JOptionPane.showMessageDialog(comp, "Client Updated Successfully!","Client Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Client Updating failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ResultSet getClientDetails(String ClientID){
        String sql = "select clientID,company_name,address,tele1,tele2,email from finance_clients where clientID='"+ClientID+"'";
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_Client_Details = stmnt.executeQuery(sql);
            return rs_Client_Details;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public void deleteClient(String ClientID, Component comp){
        String deleteSelectedClientSQL = "delete from finance_clients where clientID='"+ClientID+"'";
        try{
            Statement stmnt = dbcon.createStatement();
            stmnt.executeUpdate(deleteSelectedClientSQL);
            JOptionPane.showMessageDialog(comp, "Client details deleted successfully", "Delete Client Details", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Deleting client details failed", "Delete Client Details", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String generateClientID(Component comp){
        String prefix="CL";
        int noClients=0;
        String clientID=null;
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select fclients from count");
            while(rs.next()){
                noClients = rs.getInt("fclients");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot retrive data from the database!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
        
        if(noClients<9){
            clientID=prefix.concat("000").concat(Integer.toString(noClients+1));
            return clientID;
        }
        else if(noClients<99){
            clientID=prefix.concat("00").concat(Integer.toString(noClients+1));
            return clientID;
        }
        else if(noClients<999){
            clientID=prefix.concat("0").concat(Integer.toString(noClients+1));
            return clientID;
        }
        else if(noClients<9999){
            clientID=prefix.concat(Integer.toString(noClients+1));
            return clientID;
        }
        else{
            JOptionPane.showMessageDialog(comp, "Cannot Generate ClientID. Maximum no.of Clients Reached","Database Error",JOptionPane.ERROR_MESSAGE);
            return clientID;
        }
    }
    
    public void increaseNoClientsByOne(){
        try{
            int noClients=0;
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select fclients from count");
            while(rs.next()){
                noClients = rs.getInt("fclients");
            }
            try{
                String SQL = "update count set fclients="+(noClients+1)+" where fclients="+noClients;
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
    
    public boolean validateCompanyName(String textCompany){
        if(textCompany.equals("")){
            return false;
        }
        else if(textCompany.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateCompanyAddress(String textAddress){
        if(textAddress.equals("")){
            return true;
        }
        else if(textAddress.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateTelephone(String textTP){
        if(textTP.equals("")){
            return true;
        }
        else if(textTP.length()==10){
            if(textTP.charAt(0)=='0'){
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
