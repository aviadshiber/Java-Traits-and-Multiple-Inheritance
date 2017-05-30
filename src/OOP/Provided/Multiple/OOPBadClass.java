package OOP.Provided.Multiple;

import java.lang.reflect.Method;

public class OOPBadClass extends OOPMultipleException {

    private Method corruptedMethod;

    private Class<?> corruptedInterface;

    /***
     * Builds an exception with the bad method.
     *
     * @param corrupted the method object of the method that isn't written appropriately.
     */
    public OOPBadClass(Method corrupted) {
        this.corruptedMethod = corrupted;
        this.corruptedInterface = null;
    }

    /***
     * Builds an exception with the bad interface.
     *
     * @param corrupted the Class object of the interface that isn't written appropriately.
     */
    public OOPBadClass(Class<?> corrupted) {
        this.corruptedInterface = corrupted;
        this.corruptedMethod = null;
    }

    @Override
    public String getMessage() {
        if (corruptedInterface == null) {
            Class<?> declaringClass = corruptedMethod.getDeclaringClass();
            return super.getMessage() +
                    declaringClass.getName() +
                    " : " + corruptedMethod.getName() + " is Corrupted!";
        }
        return super.getMessage() +
                corruptedInterface.getName() + " is Corrupted!";
    }
}
