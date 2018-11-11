/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainIntefaces;

import solvers.PerceptronSolverImpl;

/**
 *
 * @author Acer
 */
public interface BasicLearningAlgorythm {
    
    Object[] getParamters();
    
    void setParameters(Object[] parameters);

    BasicSolver learn(BasicSolver solver, BasicTask task);
}
