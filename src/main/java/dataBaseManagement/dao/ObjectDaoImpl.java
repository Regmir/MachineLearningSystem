package dataBaseManagement.dao;

import dataBaseManagement.model.ObjectFromDB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class ObjectDaoImpl implements ObjectDao{
    private static final Logger log = LoggerFactory.getLogger(ObjectDaoImpl.class);

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private SessionFactory sessionFactory;

    @Override
    public BigInteger addObject(ObjectFromDB obj) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(obj);
        return obj.getId();
    }

    @Override
    public void updateObject(ObjectFromDB obj, String name) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public ObjectFromDB getObject(String name, String type) {
        Session session = this.sessionFactory.getCurrentSession();
        List<ObjectFromDB> objList = session.createQuery("from ObjectFromDB where name = '" + name + "'" +
                "and type = '" + type + "'").list();
        return objList.get(0);
    }

    @Override
    public void removeObject(BigInteger id) {
        Session session = this.sessionFactory.getCurrentSession();
        ObjectFromDB objToRemove = (ObjectFromDB) session.get(ObjectFromDB.class, id);
        session.delete(objToRemove);
    }

    @Override
    public List<ObjectFromDB> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ObjectFromDB> objList = session.createQuery("from ObjectFromDB").list();

        for(ObjectFromDB obj: objList){
            log.info("obj list: " + obj);
        }

        return objList;
    }

    @Override
    public List<ObjectFromDB> getByType(String type) {
        Session session = this.sessionFactory.getCurrentSession();
        List<ObjectFromDB> objList = session.createQuery("from ObjectFromDB where type = '" + type + "'").list();
        return objList;
    }

    @Override
    public ObjectFromDB getObjectById(BigInteger id){
        Session session = this.sessionFactory.getCurrentSession();
        ObjectFromDB obj = (ObjectFromDB) session.get(ObjectFromDB.class, id);
        return  obj;
    }
}
