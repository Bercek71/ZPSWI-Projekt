package com.endpoints;

import com.persistence.Hotel;
import com.persistence.Room;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.lang.reflect.Method;
import java.util.List;

//Service layer beru zpět. Nemáme tolik entit a zárověň používáme PanacheEntityBase,
//který funguje jako active record, takže nějakou bussiness logiku můžeme mít přímo v entitách.
//Toto je zbytečně komplikované, bude se to špatně udržovat.
@ApplicationScoped
public class EndpointServiceLayer {

    public <T extends PanacheEntityBase> List<T> listAll(Class<T> entityClass) {
        try {
            //Use of reflection to invoke method
            Method findAllMethod = entityClass.getMethod("findAll");
            Object result = findAllMethod.invoke(null);
            return ((PanacheQuery<T>) result).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to list entities", e);
        }
    }

    public <T extends PanacheEntityBase> T findById(Class<T> entityClass, Object id) {
        try {
            //Use of reflection to invoke method
            Method findByIdMethod = entityClass.getMethod("findById", Object.class);
            return (T) findByIdMethod.invoke(null, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find entitiy", e);
        }
    }

    //TODO Update service
    public <T extends PanacheEntityBase> void update(T entity) {
        entity.persist();
    }

    public <T extends PanacheEntityBase> boolean delete(Class<T> entityClass, Long id) {
        try{
            //TODO Fix foreign key violations
            if (entityClass == Hotel.class) {
                Room.delete("hotel.id", id);
            }

            T entity = findById(entityClass, id);
            if (entity != null) {
                entity.delete();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
