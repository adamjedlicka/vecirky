package jeda00.container;

import java.util.function.Supplier;

public class FactoryResolver<T> implements Resolver<T> {

    private Supplier<T> factory;

    public FactoryResolver(Supplier<T> factory) {
        this.factory = factory;
    }

    @Override
    public T resolve() {
        return factory.get();
    }

}
