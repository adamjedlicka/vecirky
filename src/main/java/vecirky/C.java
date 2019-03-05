package vecirky;

import jeda00.container.Bindable;
import jeda00.container.DefaultContainer;
import jeda00.eventbus.EventBus;
import vecirky.support.Config;

public class C {

    private static DefaultContainer container;

    private C() {

    }

    public static <T> T get(Class<T> iface) {
        return container.get(iface);
    }

    public static EventBus eventBus() {
        return container.get(EventBus.class);
    }

    public static Config config() {
        return container.get(Config.class);
    }

    public static Bindable reset() {
        container = new DefaultContainer();

        return container;
    }

}
