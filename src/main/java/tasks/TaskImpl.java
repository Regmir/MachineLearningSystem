/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;


import antlr.StringUtils;
import dataBaseManagement.model.ObjectFromDB;
import mainIntefaces.BasicTask;
import mainIntefaces.DataBaseManagement;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class TaskImpl implements BasicTask, Serializable {

    int recordCount;

    int id;

    int parameterCount;

    String name;

    ArrayList<double[]> records;

    public ArrayList<double[]> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<double[]> records) {
        this.records = records;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParapeterCount() {
        return parameterCount;
    }

    public void setParapeterCount(int parapeterCount) {
        this.parameterCount = parapeterCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void divideSelection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] getTestSelection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] getLearningSelection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] getSelection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public TaskImpl parseFile(File file, String name) throws Exception{
        TaskImpl task = new TaskImpl();
        ArrayList<String> records = new ArrayList<String>();
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException exc){ throw exc; }
        while ((line = br.readLine()) != null) {
            line = br.readLine();
            records.add(line);
        }
        br.close();
        ArrayList<double[]> taskRecords = new ArrayList<double[]>();
        ArrayList<String[]> taskRecordsString = new ArrayList<String[]>();
        for (String string:records) {
            taskRecordsString.add(string.split(","));
        }
        int[] flags = new int[taskRecordsString.get(0).length];
        HashMap<String,Double> map = new HashMap<String, Double>();
        for (String[] string:taskRecordsString) {
            double[] values = new double[string.length];
            for (int i = 0; i<string.length; i++){
                try { values[i]=Double.parseDouble(string[i]); }
                catch (NumberFormatException e){ flags[i]=1; }
            }
            taskRecords.add(values);
        }
        for (int i = 0; i<flags.length; i++){
            if (flags[i] == 1){
                for (String[] string:taskRecordsString) {
                    if (!map.containsKey(string[i])) map.put(string[i],0.0);
                }
                double value = -1.0;
                for (String string:map.keySet()) {
                    map.put(string,value);
                    value = value + 2.0 / (map.size()-1);
                }
                int j = 0;
                for (String[] string:taskRecordsString) {
                    taskRecords.get(j)[i] = map.get(string[i]);
                    j = j + 1;
                }
                map.clear();
            }
        }
        this.records = taskRecords;
        this.name = name;
        this.recordCount = records.size();
        this.parameterCount = taskRecords.get(0).length;
        return  task;
    }

    public ObjectFromDB prepareObjectFromDB(){
        ObjectFromDB objToPersist = new ObjectFromDB();
        objToPersist.setName(this.name);
        objToPersist.setType("task");
        byte[] parameters = SerializationUtils.serialize(this);
        objToPersist.setParameters(parameters);
        return  objToPersist;
    }

    public static TaskImpl parseTask(ObjectFromDB objectFromDB){
        TaskImpl task = null;
        if (objectFromDB.getType().equals("task"))
            task = (TaskImpl) SerializationUtils.deserialize(objectFromDB.getParameters());
        return task;
    }
}
