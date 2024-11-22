package org.example.entity;

public class Client {
    private int id;
    private String fullname;
    private String email;
    private long phoneNumber;
    private String address;

    public Client (){}

    public Client (int id,String fullname,String email,long phoneNumber,String address){
        this.id= id;
        this.fullname= fullname;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getFullname() {return fullname;}

    public void setFullname(String fullname) {this.fullname = fullname;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public long getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(int phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}
}
