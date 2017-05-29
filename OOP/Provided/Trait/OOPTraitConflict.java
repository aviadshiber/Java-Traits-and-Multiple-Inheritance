package OOP.Provided.Trait;

import java.lang.reflect.Method;

public class OOPTraitConflict extends OOPTraitException {
    private Method conflicted;

    /***
     * Builds an exception with the missing method.
     *
     * @param conflicted the method object of the method that is missing.
     */
    public OOPTraitConflict(Method conflicted) {
        this.conflicted = conflicted;
    }

    @Override
    public String getMessage() {
        Class<?> declaringClass = conflicted.getDeclaringClass();
        return super.getMessage() +
                declaringClass.getName() +
                " : " + conflicted.getName() + " is conflicting!";
    }
}
