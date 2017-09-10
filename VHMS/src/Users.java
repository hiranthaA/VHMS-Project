
import java.sql.Connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hirantha
 */
public class Users {
    private Connection dbcon;
    
    Users(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
    
    public void createNewUser(String userName, String password, String cPassword){
        
    }
    
    public void validateLoginDetails(){
        
    }
}
