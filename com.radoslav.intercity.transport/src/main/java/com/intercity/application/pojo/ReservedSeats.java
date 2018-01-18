package com.intercity.application.pojo;

import java.util.List;

public class ReservedSeats {
  
  private List<SeatData> seats;
  
  public List<SeatData> getSeats() {
    return seats;
  }

  public void setSeats(List<SeatData> reservedSeats) {
    this.seats = reservedSeats;
  }
  
  public void addSeatData(SeatData seatData) {
    seats.add(seatData);
  }
  
  public SeatData createSeatData(int seatNumber, String clientName, int clientId) {
    return new SeatData(seatNumber, clientName, clientId);
  }

  public class SeatData {
    private int seatNumber;
    private String clientName;
    private int clientId;
    
    public SeatData() {}
    
    public SeatData(int seatNumber, String clientName, int clientId) {
      this.seatNumber = seatNumber;
      this.clientName = clientName;
      this.clientId = clientId;
    }

    public int getSeatNumber() {
      return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
      this.seatNumber = seatNumber;
    }

    public String getClientName() {
      return clientName;
    }

    public void setClientName(String clientName) {
      this.clientName = clientName;
    }

    public int getClientId() {
      return clientId;
    }

    public void setClientId(int clientId) {
      this.clientId = clientId;
    }
    
  }
}
