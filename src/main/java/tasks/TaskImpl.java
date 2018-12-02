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
import org.springframework.web.multipart.MultipartFile;

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

    ArrayList<double[]> test;

    ArrayList<double[]> learning;

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

    public int getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(int parapeterCount) {
        this.parameterCount = parapeterCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void divideSelection(int percent) {
        test = (ArrayList<double[]>) records.subList(0,recordCount / 100 * percent);
        learning = (ArrayList<double[]>) records.subList(recordCount / 100 * percent, recordCount-1);
    }

    @Override
    public Object[] getTestSelection() {
        return test.toArray();
    }

    @Override
    public Object[] getLearningSelection() {
        return learning.toArray();
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
        if (records.get(0).contains(","))
        for (String string:records) {
            taskRecordsString.add(string.split(","));
        } else
        if (records.get(0).contains(";"))
            for (String string:records) {
                taskRecordsString.add(string.split(";"));
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
        flags[flags.length-1]=1;
        for (int i = 0; i<flags.length; i++){
            if (flags[i] == 1){
                for (String[] string:taskRecordsString) {
                    if (!map.containsKey(string[i])) map.put(string[i],0.0);
                }
                double value = 0.0;
                for (String string:map.keySet()) {
                    map.put(string,value);
                    value = value + 1.0 / (map.size()-1);
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

    public ArrayList<double[]> getX(){
        ArrayList<double[]> result = new ArrayList<double[]>();
        double[] record = new double[parameterCount-1];
        for(int i = 0; i < recordCount; i++ ){
            for(int j = 0; j < parameterCount-1; j++ )
                record[j] = records.get(i)[j];
            result.add(record);
        }
        return result;
    }

    public ArrayList<double[]> getXtest(int percent){
        ArrayList<double[]> result = new ArrayList<double[]>();
        for(int i = 0; i < recordCount * percent / 100; i++ ){
            result.add(records.get(i));
        }
        return result;
    }

    public ArrayList<double[]> getXlearn(int percent){
        ArrayList<double[]> result = new ArrayList<double[]>();
        for(int i = recordCount * percent / 100; i < recordCount; i++ ){
                result.add(records.get(i));
        }
        return result;
    }

    public double[] getY(){
        double[] result = new double[recordCount];
        for(int i = 0; i < recordCount; i++ ){
            result[i] = records.get(i)[parameterCount-1];
        }
        return result;
    }

    public double[] getYtest(int percent){
        double[] result = new double[recordCount * percent / 100];
        for(int i = 0; i < recordCount * percent / 100; i++ ){
            result[i] = records.get(i)[parameterCount-1];
        }
        return result;
    }

    public double[] getYlearn(int percent){
        double[] result = new double[recordCount - recordCount * percent / 100];
        int j = 0;
        for(int i =  recordCount * percent / 100; i < recordCount; i++ ){
            result[j] = records.get(i)[parameterCount-1];
            j++;
        }
        return result;
    }
}
