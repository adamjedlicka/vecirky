package jeda00.container;

import java.util.function.Supplier;

/**
 * Container interface indicating that new factories or singleton instances can
 * be binded into the container.
 */
public interface Bindable {

    /**
     * Binds factory method that returns implementation instance of the interface.
     *
     * @param iface   Interface for future resolution
     * @param factory Factory function returning concrete instance
     */
    public <T> void bindFactory(Class<T> iface, Supplier<T> factory);

    /**
     * Binds factory method which is used exactly once for creation of singleton
     * instance.
     *
     * @param iface     Interface for future resolution
     * @param singleton Factory function returning the instance of the singleton
     */
    public <T> void bindSingletonFactory(Class<T> iface, Supplier<T> singleton);

    /**
     * Binds already created instance as singleton into the container
     *
     * @param iface    Interface for future resolution
     * @param instance Instance of the interface which will be resolved
     */
    public <T> void bindSingletonInstance(Class<T> iface, T instance);

}
