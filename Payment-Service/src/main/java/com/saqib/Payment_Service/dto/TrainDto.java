package com.saqib.Payment_Service.dto;

public class TrainDto {

    private Long trainId;
    private String trainName;
    private String sourceStation;
    private String destinationStation;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTrainName()           { return trainName; }
    public void setTrainName(String v)     { this.trainName = v; }

    public String getSourceStation()       { return sourceStation; }
    public void setSourceStation(String v) { this.sourceStation = v; }

    public String getDestinationStation()  { return destinationStation; }
    public void setDestinationStation(String v){ this.destinationStation = v; }
}
