package data.dao;
import java.util.List;

public interface DAO<Type> {

    List<Type> getAll();

    void add(Type t);
    void update(Type t,String[] l);
    void delete(Type t);
}



