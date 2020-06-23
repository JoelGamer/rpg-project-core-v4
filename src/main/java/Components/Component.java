package Components;

import Exceptions.InvalidValue;

/**
 * The {@code Component} class is a somewhat abstract class, shared amongst all other classes in this package.
 * It's used to define the extended class that it contains a unique identifier defined in the database of this aplication
 *
 * @author Guilherme Theodoro
 * @since 0.0.1
 * @version 0.0.1
 */

public class Component {

    private long uid;

    public Component(long uid) throws InvalidValue {
        setUid(uid);
    }

    protected Component() {
        this.uid = 0;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid) throws InvalidValue {
        if(uid < 1) throw new InvalidValue(this.getClass().getName() + ".setUid()");
        this.uid = uid;
    }
}
