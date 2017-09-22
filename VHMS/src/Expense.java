
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
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
public class Expense {
    
    private Connection dbcon;
    
    Expense(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public void addNewExpense(String service, String date, String desc, double billAmount, Component comp){
        try{
            String SQL = "insert into finance_expense (expenseID,date,description,amount) values(?,?,?,?)";
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            String prefix = "EXP/"+service+"/";
            pst.setString(1, generateExpenseID(prefix,comp));
            pst.setString(2, date);
            pst.setString(3, desc);
            pst.setDouble(4, billAmount);
            pst.execute();
            increaseNoExpenseByOne(comp);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Updating Expenses failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addTelephoneBill(String date, String desc, String billAmount, Component comp){
        try{
            String SQL = "insert into finance_expense (expenseID,date,description,amount) values(?,?,?,?)";
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, generateExpenseID("EXP/TP/",comp));
            pst.setString(2, date);
            pst.setString(3, desc);
            pst.setString(4, billAmount);
            pst.execute();
            increaseNoExpenseByOne(comp);
            JOptionPane.showMessageDialog(comp, "Telephone bill added Successfully!","Expense Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Telephone Bill adding failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addWaterBill(String date, String desc, String billAmount, Component comp){
        try{
            String SQL = "insert into finance_expense (expenseID,date,description,amount) values(?,?,?,?)";
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, generateExpenseID("EXP/WB/",comp));
            pst.setString(2, date);
            pst.setString(3, desc);
            pst.setString(4, billAmount);
            pst.execute();
            increaseNoExpenseByOne(comp);
            JOptionPane.showMessageDialog(comp, "Water bill added Successfully!","Expense Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Water Bill adding failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addElectricityBill(String date, String desc, String billAmount, Component comp){
        try{
            String SQL = "insert into finance_expense (expenseID,date,description,amount) values(?,?,?,?)";
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, generateExpenseID("EXP/EB/",comp));
            pst.setString(2, date);
            pst.setString(3, desc);
            pst.setString(4, billAmount);
            pst.execute();
            increaseNoExpenseByOne(comp);
            JOptionPane.showMessageDialog(comp, "Electricity bill added Successfully!","Expense Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Electricity Bill adding failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addSalaryPayment(String date, String desc, String TAmount, Component comp){
        try{
            String SQL = "insert into finance_expense (expenseID,date,description,amount) values(?,?,?,?)";
            PreparedStatement pst = dbcon.prepareStatement(SQL);
            pst.setString(1, generateExpenseID("EXP/SP/",comp));
            pst.setString(2, date);
            pst.setString(3, desc);
            pst.setString(4, TAmount);
            pst.execute();
            increaseNoExpenseByOne(comp);
            JOptionPane.showMessageDialog(comp, "Salary Payment added Successfully!","Expense Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Salary Payment adding failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public double calcNetExp(String from,String to,boolean CI, boolean SP, boolean EB, boolean WB, boolean TP, boolean OB, Component comp){
        String sql = "select sum(amount) from finance_expense where date between ? and ? and (expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ?)";
        String CInvoice,ElecBill,SalPay,WaterBill,TPBill,OtherBill;
        double net_expense=0;
        //----------------------------------------
        if(CI){
            CInvoice="EXP/CI/%";
        }
        else{
            CInvoice=null;
        }
        if(SP){
            SalPay="EXP/SP/%";
        }
        else{
            SalPay=null;
        }
        if(EB){
            ElecBill="EXP/EB/%";
        }
        else{
            ElecBill=null;
        }
        if(WB){
            WaterBill="EXP/WB/%";
        }
        else{
            WaterBill=null;
        }
        if(TP){
            TPBill="EXP/TP/%";
        }
        else{
            TPBill=null;
        }
        if(OB){
            OtherBill="EXP/OB/%";
        }
        else{
            OtherBill=null;
        }
        //----------------------------------------
        try{
            PreparedStatement pst = dbcon.prepareStatement(sql);
            pst.setString(1, from);
            pst.setString(2, to);
            pst.setString(3, CInvoice);
            pst.setString(4, SalPay);
            pst.setString(5, WaterBill);
            pst.setString(6, ElecBill);
            pst.setString(7, TPBill);
            pst.setString(8, OtherBill);
            ResultSet rs_Net_Expense = pst.executeQuery();
            while(rs_Net_Expense.next()){
                net_expense=rs_Net_Expense.getDouble("sum(amount)");
            }
            return net_expense;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot Refresh Expense Details!","Database Error",JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    public int countNoExp(String from, String to,boolean CI, boolean SP, boolean EB, boolean WB, boolean TP, boolean OB, Component comp){
        String sql = "select count(amount) from finance_expense where date between ? and ? and (expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ?)";
        String CInvoice,ElecBill,SalPay,WaterBill,TPBill,OtherBill;
        int no_exp=0;
        //----------------------------------------
        if(CI){
            CInvoice="EXP/CI/%";
        }
        else{
            CInvoice=null;
        }
        if(SP){
            SalPay="EXP/SP/%";
        }
        else{
            SalPay=null;
        }
        if(EB){
            ElecBill="EXP/EB/%";
        }
        else{
            ElecBill=null;
        }
        if(WB){
            WaterBill="EXP/WB/%";
        }
        else{
            WaterBill=null;
        }
        if(TP){
            TPBill="EXP/TP/%";
        }
        else{
            TPBill=null;
        }
        if(OB){
            OtherBill="EXP/OB/%";
        }
        else{
            OtherBill=null;
        }
        //----------------------------------------
        try{
            PreparedStatement pst = dbcon.prepareStatement(sql);
            pst.setString(1, from);
            pst.setString(2, to);
            pst.setString(3, CInvoice);
            pst.setString(4, SalPay);
            pst.setString(5, WaterBill);
            pst.setString(6, ElecBill);
            pst.setString(7, TPBill);
            pst.setString(8, OtherBill);
            ResultSet rs_Net_Expense = pst.executeQuery();
            while(rs_Net_Expense.next()){
                no_exp=rs_Net_Expense.getInt("count(amount)");
            }
            return no_exp;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot Refresh Expense Details!","Database Error",JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    public ResultSet updateExpenseTable(String from, String to,boolean CI, boolean SP, boolean EB, boolean WB, boolean TP, boolean OB, Component comp){
            String sql = "select expenseID as '#', date as 'Date', description as 'Description', amount as 'Amount (Rs.)' from finance_expense where date between ? and ? and (expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ? or expenseID like ?)";
            String CInvoice,ElecBill,SalPay,WaterBill,TPBill,OtherBill;
            //----------------------------------------
            if(CI){
                CInvoice="EXP/CI/%";
            }
            else{
                CInvoice=null;
            }
            if(SP){
                SalPay="EXP/SP/%";
            }
            else{
                SalPay=null;
            }
            if(EB){
                ElecBill="EXP/EB/%";
            }
            else{
                ElecBill=null;
            }
            if(WB){
                WaterBill="EXP/WB/%";
            }
            else{
                WaterBill=null;
            }
            if(TP){
                TPBill="EXP/TP/%";
            }
            else{
                TPBill=null;
            }
            if(OB){
                OtherBill="EXP/OB/%";
            }
            else{
                OtherBill=null;
            }
            //----------------------------------------
            try{
                PreparedStatement pst = dbcon.prepareStatement(sql);
                pst.setString(1, from);
                pst.setString(2, to);
                pst.setString(3, CInvoice);
                pst.setString(4, SalPay);
                pst.setString(5, WaterBill);
                pst.setString(6, ElecBill);
                pst.setString(7, TPBill);
                pst.setString(8, OtherBill);
                ResultSet rs_Expense_Details = pst.executeQuery();
                return rs_Expense_Details;
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(comp, "Cannot Refresh Expense Details!","Database Error",JOptionPane.ERROR_MESSAGE);
                return null;
            }
    }
    
    public void increaseNoExpenseByOne(Component comp){
        try{
            int noExpense=0;
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select fexpense from count");
            while(rs.next()){
                noExpense = rs.getInt("fexpense");
            }
            try{
                String SQL = "update count set fexpense="+(noExpense+1)+" where fexpense="+noExpense;
                Statement stmnt2 = dbcon.createStatement();
                stmnt2.executeUpdate(SQL);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(comp, "Communication with the database interrupted!","Database Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Communication with the database interrupted!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String generateExpenseID(String prefix,Component comp){
        int noExpense=0;
        String expenseID=null;
        try{
            Statement stmnt = dbcon.createStatement();
            ResultSet rs = stmnt.executeQuery("select fexpense from count");
            while(rs.next()){
                noExpense = rs.getInt("fexpense");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot retrive data from the database!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
        
        if(noExpense<9){
            expenseID=prefix.concat("000000").concat(Integer.toString(noExpense+1));
            return expenseID;
        }
        else if(noExpense<99){
            expenseID=prefix.concat("00000").concat(Integer.toString(noExpense+1));
            return expenseID;
        }
        else if(noExpense<999){
            expenseID=prefix.concat("0000").concat(Integer.toString(noExpense+1));
            return expenseID;
        }
        else if(noExpense<9999){
            expenseID=prefix.concat("000").concat(Integer.toString(noExpense+1));
            return expenseID;
        }
        else if(noExpense<99999){
            expenseID=prefix.concat("00").concat(Integer.toString(noExpense+1));
            return expenseID;
        }
        else if(noExpense<999999){
            expenseID=prefix.concat("0").concat(Integer.toString(noExpense+1));
            return expenseID;
        }
        else if(noExpense<9999999){
            expenseID=prefix.concat(Integer.toString(noExpense+1));
            return expenseID;
        }
        else{
            JOptionPane.showMessageDialog(comp, "Cannot Generate ExpenseID. Maximum no.of Expenses Reached","Database Error",JOptionPane.ERROR_MESSAGE);
            return expenseID;
        }
    }
    
}
