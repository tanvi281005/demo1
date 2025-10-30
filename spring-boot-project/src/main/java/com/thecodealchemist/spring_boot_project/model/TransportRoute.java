package com.thecodealchemist.spring_boot_project.model;

public class TransportRoute {
    private int routeId;
    private String origin;
    private String name;
    private String destination;
    private Integer price;

    public TransportRoute() {}

    public TransportRoute(int routeId, String origin, String name, String destination, Integer price) {
        this.routeId = routeId;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
        this.price= price;
    }

    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}
