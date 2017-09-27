
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Pharmacy {
    private Connection dbcon;
    
    Pharmacy(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    public boolean add_Item(String itemcode,String itemname,String quantity,String expiryDate,String stockPrice,String sellingPrice,String company){
    
    try{
            String addItemSQL = "insert into pharmacy_stock (Item_Code,Item_Name,Quantity,Expiry_Date,Stock_Price,Selling_Price,Company) values(?,?,?,?,?,?,?)";
            PreparedStatement pst = (PreparedStatement) dbcon.prepareStatement(addItemSQL);
            pst.setString(1, itemcode);
            pst.setString(2, itemname);
            pst.setString(3, quantity);
            pst.setString(4, expiryDate);
            pst.setString(5, stockPrice);
            pst.setString(6, sellingPrice);
            pst.setString(7, company);
           
           pst.execute();
           
           return true;
            
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
            return false;
            
        }
    
    
    }
    public ResultSet update_stock_table(){
        String sql="select Item_Code as 'Item Code' ,Item_Name as 'Item Name' ,Quantity as 'Quantity' ,Expiry_Date as 'Expiry Date' ,Stock_Price as 'Stock Price',Selling_Price as 'Selling Price' ,Company as 'Company' from pharmacy_stock";
         try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs_addItem = stmnt.executeQuery(sql);
            return rs_addItem;
            
        }
        catch(Exception e){
            return null;
        }
        
    }
    
    public boolean edit_Item(String itemcode,String itemname,String quantity,String expiryDate,String stockPrice,String sellingPrice,String company){
         try{
            String updateItemSQL = "update pharmacy_stock set Item_Name = ?, Quantity = ?, Expiry_Date = ?, Stock_Price = ?, Selling_Price = ? , Company = ? where Item_Code = ?";
            PreparedStatement pst = dbcon.prepareStatement(updateItemSQL);
           
            pst.setString(1, itemname);
            pst.setString(2, quantity);
            pst.setString(3, expiryDate);
            pst.setString(4, stockPrice);
            pst.setString(5, sellingPrice);
            pst.setString(6, company);
            pst.setString(7, itemcode);
           pst.execute();
           
           return true;
            
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
            return false;
            
        }
    }
   
    public boolean checkItemCode(String itemCode){
        String sql = "select Item_Code from pharmacy_stock where Item_Code='"+itemCode+"'";
        boolean itemOK = false;
        try {
            Statement st = dbcon.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("Item_Code").equals(itemCode)){
                    itemOK = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return itemOK;
    }
    
    public ResultSet getItemDetails(String itemCode){
        String sql = "select Item_Name, Quantity, Company, Selling_Price from pharmacy_stock where Item_Code='"+itemCode+"'";
        try {
            Statement st = dbcon.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } 
    }
    
    public ResultSet getAllItemDetails(String itemCode){
        String sql = "select * from pharmacy_stock where Item_Code='"+itemCode+"'";
        try {
            Statement st = dbcon.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } 
    }
}
