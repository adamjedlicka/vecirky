package jeda00.container;

import java.util.function.Supplier;

public class SingletonResolver<T> implements Resolver<T> {

    private T singleton;

    private Supplier<T> factory;

    public SingletonResolver(T singleton) {
        this.singleton = singleton;
    }

    public SingletonResolver(Supplier<T> factory) {
        this.factory = factory;
    }

    @Override
    public T resolve() {
        if (singleton == null) {
            singleton = factory.get();
        }

        return singleton;
    }

}
