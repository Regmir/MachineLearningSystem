/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainIntefaces;

import java.math.BigInteger;

/**
 *
 * @author Acer
 */
public interface DataBaseManagement {
    
    BigInteger writeToDataBase();
    
    Object readFromDatabase(BigInteger objectID);
    
}
