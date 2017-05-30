package OOP.Provided.Trait;

import java.lang.reflect.Method;

public class OOPTraitMissingImpl extends OOPTraitException {
    private Method missing;

    /***
     * Builds an exception with the missing method.
     *
     * @param missing the method object of the method that is missing.
     */
    public OOPTraitMissingImpl(Method missing) {
        this.missing = missing;
    }

    @Override
    public String getMessage() {
        Class<?> declaringClass = missing.getDeclaringClass();
        return super.getMessage() +
                declaringClass.getName() +
                " : " + missing.getName() + " is missing!";
    }
}
