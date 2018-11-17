package dataBaseManagement.service;

import dataBaseManagement.dao.ObjectDao;
import dataBaseManagement.model.ObjectFromDB;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class ObjectServiceImpl implements  ObjectService{

    public void setObjDao(ObjectDao objDao) {
        this.objDao = objDao;
    }

    private ObjectDao objDao;

    @Override
    @Transactional
    public BigInteger addObject(ObjectFromDB obj) {
       return this.objDao.addObject(obj);
    }

    @Override
    @Transactional
    public void updateObject(ObjectFromDB obj, String name) {
        this.objDao.updateObject(obj, name);
    }

    @Override
    @Transactional
    public ObjectFromDB getObject(String name, String type) {
        return objDao.getObject(name,type);
    }

    @Override
    @Transactional
    public void removeObject(BigInteger id) {
        objDao.removeObject(id);
    }

    @Override
    @Transactional
    public ObjectFromDB getObjectById(BigInteger id) {
        return objDao.getObjectById(id);
    }

    @Override
    @Transactional
    public List<ObjectFromDB> getAll() {
        return objDao.getAll();
    }

    @Override
    @Transactional
    public List<ObjectFromDB> getByType(String type){
        return objDao.getByType(type);
    }
}
