package com.thecodealchemist.spring_boot_project.model;
import java.util.List;
public class TransportRouteWithTimings {
    private int routeId;
    private String origin;
    private String name;
    private String destination;
    private Integer price;
    private List<String> timings;

    public TransportRouteWithTimings() {}

    public TransportRouteWithTimings(int routeId, String origin, String name, String destination, Integer price, List<String> timings) {
        this.routeId = routeId;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
        this.timings = timings;
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

    public List<String> getTimings() { return timings; }
    public void setTimings(List<String> timings) { this.timings = timings; }

    public int getprice() { return price; }
    public void setprice(int price) { this.price = price; }
}
