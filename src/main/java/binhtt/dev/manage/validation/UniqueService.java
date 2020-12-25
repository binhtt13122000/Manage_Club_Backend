package binhtt.dev.manage.validation;


public interface UniqueService {
    boolean fieldValueExists(String fieldName, String fieldValue, String className) throws UnsupportedOperationException;
}
