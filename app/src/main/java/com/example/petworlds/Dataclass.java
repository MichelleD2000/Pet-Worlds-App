package com.example.petworlds;

public class Dataclass {

    public Dataclass(){

    }
    private String datauploadTopic;
    private String dataPrice;
    private String dataAge;
    private String dataBreed;
    private String dataGender;
    private String dataColor;
    private String dataDescription;
    private String datauploadImage;

    public Dataclass(String datauploadTopic, String dataPrice, String dataAge, String dataBreed, String dataGender, String dataColor, String dataDescription, String datauploadImage) {
        this.datauploadTopic = datauploadTopic;
        this.dataPrice = dataPrice;
        this.dataAge = dataAge;
        this.dataBreed = dataBreed;
        this.dataGender = dataGender;
        this.dataColor = dataColor;
        this.dataDescription = dataDescription;
        this.datauploadImage = datauploadImage;
    }

    public String getDatauploadTopic() {
        return datauploadTopic;
    }

    public String getDataPrice() {
        return dataPrice;
    }

    public String getDataAge() {
        return dataAge;
    }

    public String getDataBreed() {
        return dataBreed;
    }

    public String getDataGender() {
        return dataGender;
    }

    public String getDataColor() {
        return dataColor;
    }

    public String getDataDescription() {
        return dataDescription;
    }

    public String getDatauploadImage() {
        return datauploadImage;
    }
}

