package jeda00.container;

public interface Resolver<T> {

    /**
     * Resolves the resolver.
     *
     * @return The instance
     */
    public T resolve();

}
