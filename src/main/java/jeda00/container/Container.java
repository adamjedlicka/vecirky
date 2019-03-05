package jeda00.container;

/**
 * Dependency injection container
 */
public interface Container {

    /**
     * Resolves the entry of the container and returns it
     *
     * @param iface Interface whose instance is resolved
     * @return Instance of the interface supplied by the iface argument
     */
    public <T> T get(Class<T> iface);

    /**
     * Indicates whether the container has entry for the given interface
     *
     * @param iface Interface which is checked if ti can be resolved
     * @return True/false if the given interface can be resolved
     */
    public <T> boolean has(Class<T> iface);

}
