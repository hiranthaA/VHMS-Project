
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
public class Income {
    private Connection dbcon;
    
    Income(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public void addNewIncome(String payID, String service, String date, double amount,Component comp){
        String sql = "insert into income(paymentID,service,date,amount) values(?,?,?,?)";
        try{
            PreparedStatement pst = dbcon.prepareStatement(sql);
            pst.setString(1,payID);
            pst.setString(2,service);
            pst.setString(3,date);
            pst.setDouble(4,amount);
            pst.execute();
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(comp, "Cannot Add Income Details!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ResultSet updateIncomeTable(String from, String to,boolean PH, boolean HO, boolean MV, boolean PS, boolean DC, boolean HC, Component comp){
        String sql = "select paymentID as '#', date as 'Date', service as 'Service', tot_amount as 'Bill Amount (Rs.)' from income where date between ? and ? and (service like ? or service like ? or service like ? or service like ? or service like ? or service like ?)";
        String pharmacy,hospital,mobvet,petshop,daycare,healthcare;
        //----------------------------------------
        if(PH){
            pharmacy="PH";
        }
        else{
            pharmacy=null;
        }
        if(HO){
            hospital="HO";
        }
        else{
            hospital=null;
        }
        if(MV){
            mobvet="MV";
        }
        else{
            mobvet=null;
        }
        if(PS){
            petshop="PS";
        }
        else{
            petshop=null;
        }
        if(DC){
            daycare="DC";
        }
        else{
            daycare=null;
        }
        if(HC){
            healthcare="HC";
        }
        else{
            healthcare=null;
        }
        //----------------------------------------
        try{
            PreparedStatement pst = dbcon.prepareStatement(sql);
            pst.setString(1, from);
            pst.setString(2, to);
            pst.setString(3, pharmacy);
            pst.setString(4, hospital);
            pst.setString(5, mobvet);
            pst.setString(6, petshop);
            pst.setString(7, daycare);
            pst.setString(8, healthcare);
            ResultSet rs_income_Details = pst.executeQuery();
            return rs_income_Details;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot Refresh Income Details!","Database Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public double calcNetInc(String from, String to,boolean PH, boolean HO, boolean MV, boolean PS, boolean DC, boolean HC, Component comp){
        String sql = "select sum(tot_amount) from income where date between ? and ? and (service like ? or service like ? or service like ? or service like ? or service like ? or service like ?)";
        String pharmacy,hospital,mobvet,petshop,daycare,healthcare;
        double net_income=0;
        //----------------------------------------
        if(PH){
            pharmacy="PH";
        }
        else{
            pharmacy=null;
        }
        if(HO){
            hospital="HO";
        }
        else{
            hospital=null;
        }
        if(MV){
            mobvet="MV";
        }
        else{
            mobvet=null;
        }
        if(PS){
            petshop="PS";
        }
        else{
            petshop=null;
        }
        if(DC){
            daycare="DC";
        }
        else{
            daycare=null;
        }
        if(HC){
            healthcare="HC";
        }
        else{
            healthcare=null;
        }
        //----------------------------------------
        try{
            PreparedStatement pst = dbcon.prepareStatement(sql);
            pst.setString(1, from);
            pst.setString(2, to);
            pst.setString(3, pharmacy);
            pst.setString(4, hospital);
            pst.setString(5, mobvet);
            pst.setString(6, petshop);
            pst.setString(7, daycare);
            pst.setString(8, healthcare);
            ResultSet rs_Net_Income = pst.executeQuery();
            while(rs_Net_Income.next()){
                net_income=rs_Net_Income.getDouble("sum(tot_amount)");
            }
            return net_income;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot Refresh Income Details!","Database Error",JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    public int countNoInc(String from, String to,boolean PH, boolean HO, boolean MV, boolean PS, boolean DC, boolean HC, Component comp){
        String sql = "select count(tot_amount) from income where date between ? and ? and (service like ? or service like ? or service like ? or service like ? or service like ? or service like ?)";
        String pharmacy,hospital,mobvet,petshop,daycare,healthcare;
        int no_inc=0;
        //----------------------------------------
        if(PH){
            pharmacy="PH";
        }
        else{
            pharmacy=null;
        }
        if(HO){
            hospital="HO";
        }
        else{
            hospital=null;
        }
        if(MV){
            mobvet="MV";
        }
        else{
            mobvet=null;
        }
        if(PS){
            petshop="PS";
        }
        else{
            petshop=null;
        }
        if(DC){
            daycare="DC";
        }
        else{
            daycare=null;
        }
        if(HC){
            healthcare="HC";
        }
        else{
            healthcare=null;
        }
        //----------------------------------------
        try{
            PreparedStatement pst = dbcon.prepareStatement(sql);
            pst.setString(1, from);
            pst.setString(2, to);
            pst.setString(3, pharmacy);
            pst.setString(4, hospital);
            pst.setString(5, mobvet);
            pst.setString(6, petshop);
            pst.setString(7, daycare);
            pst.setString(8, healthcare);
            ResultSet rs_Net_Income = pst.executeQuery();
            while(rs_Net_Income.next()){
                no_inc=rs_Net_Income.getInt("count(tot_amount)");
            }
            return no_inc;
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(comp, "Cannot Refresh Income Details!","Database Error",JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
}
