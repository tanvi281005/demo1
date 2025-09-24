package com.thecodealchemist.spring_boot_project.model;

import java.time.LocalTime;

public class TransportRouteTiming {
    private int routeId;
    private LocalTime timing;

    public TransportRouteTiming() {}

    public TransportRouteTiming(int routeId, LocalTime timing) {
        this.routeId = routeId;
        this.timing = timing;
    }

    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    public LocalTime getTiming() { return timing; }
    public void setTiming(LocalTime timing) { this.timing = timing; }

}
