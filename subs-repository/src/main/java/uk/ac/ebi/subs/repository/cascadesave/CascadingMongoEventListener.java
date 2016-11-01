package uk.ac.ebi.subs.repository.cascadesave;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import uk.ac.ebi.subs.data.annotation.CascadeSave;
import uk.ac.ebi.subs.repository.submittable.AbstractSubsEntityRepository;

import java.lang.reflect.Field;
import java.util.List;

/**
 * What is happening?
 * When object MongoTemplate#save method is called, before the object is actually saved it's being converted into a DBObject from MongoDB API.
 * CascadingMongoEventListener, implemented below, provides a hook that catches the object before its converted and:
 *  - Goes through all its fields to check if there are fields annotated with @DBRef and @CascadeSave at once;
 *  - When the field is found checks if its a list and determines the object class to save accordingly;
 *  - Checks if the @Id annotation is present in the object.
 *  - Saves the child object.
 **/
@Component
public class CascadingMongoEventListener extends AbstractMongoEventListener {
    private static final Logger logger = LoggerFactory.getLogger(CascadingMongoEventListener.class);

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    AbstractSubsEntityRepository absRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        logger.debug("onBeforeConvert( " + event.getSource() + " )");

        ReflectionUtils.doWithFields(event.getSource().getClass(), field -> {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
                Class<?> clazz;
                List list = null;

                if (field.get(event.getSource()) instanceof List) {
                    list = (List) field.get(event.getSource());
                    if (list.isEmpty())
                        return;
                    clazz = list.get(0).getClass();
                } else {
                    clazz = field.get(event.getSource()).getClass();
                }

                DbRefFieldCallback callback = new DbRefFieldCallback();
                ReflectionUtils.doWithFields(clazz, callback);

                if (!callback.isIdFound()) {
                    throw new MappingException("Cannot perform cascade save on child object without id set");
                }

                if (list != null) {
                    absRepository.save(list);
                } else {
                    mongoOperations.save(field.get(event.getSource()));
                }
            }

        });
    }

    private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;

        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            ReflectionUtils.makeAccessible(field);

            // Child class needs to have property annotated with @Id
            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        public boolean isIdFound() {
            return idFound;
        }
    }
}
