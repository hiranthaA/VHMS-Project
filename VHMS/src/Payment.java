
import java.sql.Connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shashini
 */
public class Payment {
    private Connection dbcon;
    
    Payment(){
        dbConnectr conn = new dbConnectr();
        dbcon = conn.Connect();
    }
}
