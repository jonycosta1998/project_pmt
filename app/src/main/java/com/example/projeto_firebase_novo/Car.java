package com.example.projeto_firebase_novo;

public class Car {

    String carId;
    String carMaker;
    String carModel;
    String carBuildYear;
    String carEngine;

    public Car(){}

    public Car(String carId, String carMaker, String carModel, String carBuildYear, String carEngine) {
        this.carId = carId;
        this.carMaker = carMaker;
        this.carModel = carModel;
        this.carBuildYear = carBuildYear;
        this.carEngine = carEngine;
    }

    public String getCarId() {
        return carId;
    }

    public String getCarMaker() {
        return carMaker;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarBuildYear() {
        return carBuildYear;
    }

    public String getCarEngine() {
        return carEngine;
    }
}