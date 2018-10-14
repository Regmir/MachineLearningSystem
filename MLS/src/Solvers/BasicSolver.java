/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Solvers;

import LearningAlgorythms.BasicLearningAlgorythm;
import Tasks.BasicTask;
import java.math.BigInteger;

/**
 *
 * @author Acer
 */
public interface BasicSolver {
    
    BigInteger solve(Object[] input);
    
    void learn(BasicLearningAlgorythm algorythm, BasicTask task);
    
    Object[] getParamters();
    
    void setParameters(Object[] parameters);
    
}
