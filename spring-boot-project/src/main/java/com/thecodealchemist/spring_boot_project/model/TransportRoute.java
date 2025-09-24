package com.thecodealchemist.spring_boot_project.model;

public class TransportRoute {
    private int routeId;
    private String origin;
    private String name;
    private String destination;

    public TransportRoute() {}

    public TransportRoute(int routeId, String origin, String name, String destination) {
        this.routeId = routeId;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
    }

    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}
