package dataBaseManagement.dao;

import dataBaseManagement.model.ObjectFromDB;

import java.math.BigInteger;
import java.util.List;

public interface ObjectDao {

    public BigInteger addObject(ObjectFromDB obj);

    public void updateObject(ObjectFromDB obj, String name);

    public ObjectFromDB getObject(String name, String type);

    public void removeObject(BigInteger id);

    public List<ObjectFromDB> getAll();

    public ObjectFromDB getObjectById(BigInteger id);

    public List<ObjectFromDB> getByType(String type);

}
