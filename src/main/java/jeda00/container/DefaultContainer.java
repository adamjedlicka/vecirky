package jeda00.container;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Default implementation of the Container interface.
 */
public class DefaultContainer implements Container, Bindable {

    private final Map<Class<?>, Resolver<?>> resolvers = new HashMap<>();

    @Override
    public <T> void bindFactory(Class<T> iface, Supplier<T> factory) {
        resolvers.put(iface, new FactoryResolver<T>(factory));
    }

    @Override
    public <T> void bindSingletonFactory(Class<T> iface, Supplier<T> singleton) {
        resolvers.put(iface, new SingletonResolver<T>(singleton));
    }

    @Override
    public <T> void bindSingletonInstance(Class<T> iface, T instance) {
        resolvers.put(iface, new SingletonResolver<T>(instance));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> iface) {
        return (T) resolvers.get(iface).resolve();
    }

    @Override
    public <T> boolean has(Class<T> iface) {
        return resolvers.containsKey(iface);
    }

}
