package org.tomcat.catalina;

import java.util.EventObject;

public final  class LifecycleEvent extends EventObject {
    private final Lifecycle lifecycle;
    private final String type;
    private final Object data;

    /**
     * Constructs a prototypical Event.
     * @throws IllegalArgumentException if source is null
     */

    public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
        super(lifecycle);
        this.lifecycle = lifecycle;
        this.type = type;
        this.data = data;
    }

    public Lifecycle getLifecycle() {
        return lifecycle;
    }

    public String getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
