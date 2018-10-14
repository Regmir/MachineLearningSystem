/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostgreSQL;

import java.math.BigInteger;

/**
 *
 * @author Acer
 */
public interface DataBaseConnection {
    
    BigInteger writeToDataBase();
    
    BigInteger generateID(); 
    
    Object readFromDatabase(BigInteger objectID);
    
}
