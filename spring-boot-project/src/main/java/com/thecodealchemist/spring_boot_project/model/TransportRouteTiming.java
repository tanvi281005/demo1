package com.thecodealchemist.spring_boot_project.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransportRouteTiming {
    private int routeId;
    private LocalTime timing;
    private LocalDate date;

    public TransportRouteTiming() {}

    public TransportRouteTiming(int routeId, LocalTime timing, LocalDate date) {
        this.routeId = routeId;
        this.timing = timing;
        this.date = date;
    }

    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    public LocalTime getTiming() { return timing; }
    public void setTiming(LocalTime timing) { this.timing = timing; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
