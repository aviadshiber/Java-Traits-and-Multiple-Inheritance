package OOP.Provided.Trait;

public class OOPTraitException extends Exception {

    @Override
    public String getMessage(){
        return OOPTraitException.class.getName() + " : \n";
    }
}
