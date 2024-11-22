package org.example.entity;

import org.example.helper.StatusMachine;

public class Machine {
    private int id;
    private String name;
    private String model;
    private String serialNumber;
    private StatusMachine status;

    public Machine(){}

    public Machine(int id,String name,String model,String serialNumber){
        this.id=id;
        this.name=name;
        this.model=model;
        this.serialNumber=serialNumber;

    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getModel() {return model;}

    public void setModel(String model) {this.model = model;}

    public String getSerialNumber() {return serialNumber;}

    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}

    public StatusMachine getStatus() {return status;}

    public void setStatus(StatusMachine status) {this.status = status;}
    @Override
    public String toString() {
        return "Machine [id=" + id + ", name=" + name + ", model=" + model + ", serialNumber=" + serialNumber + ", status=" + status + "]";
    }
}
