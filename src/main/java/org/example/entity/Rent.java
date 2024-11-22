package org.example.entity;

import java.time.LocalDate;

public class Rent {
    private int id;
    private int clientId;
    private int machineId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;


    public Rent (){}

    public Rent(int id, int clientId, int machineId, LocalDate startDate, LocalDate endDate, boolean active){
        this.id=id;
        this.clientId=clientId;
        this.machineId=machineId;
        this.startDate=startDate;
        this.endDate= endDate;
        this.active= active;



    }



    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getClientId() {return clientId;}

    public void setClientId(int clientId) {this.clientId = clientId;}

    public int getMachineId() {return machineId;}

    public void setMachineId(int machineId) {this.machineId = machineId;}

    public LocalDate getStartDate() {return startDate;}

    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

    public LocalDate getEndDate() {return endDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    public boolean isActive() {return active;}

    public void setActive(boolean active) {this.active = active;}


}
