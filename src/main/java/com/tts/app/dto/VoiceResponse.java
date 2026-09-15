package com.tts.app.dto;

public class VoiceResponse {

    private String id;

    private String language;

    private String name;

    private String quality;

    private int numSpeakers;

    private String gender;


    public VoiceResponse() {
    }


    public VoiceResponse(
            String id,
            String language,
            String name,
            String quality,
            int numSpeakers,
            String gender
    ) {

        this.id = id;
        this.language = language;
        this.name = name;
        this.quality = quality;
        this.numSpeakers = numSpeakers;
        this.gender = gender;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getQuality() {
        return quality;
    }


    public void setQuality(String quality) {
        this.quality = quality;
    }


    public int getNumSpeakers() {
        return numSpeakers;
    }


    public void setNumSpeakers(int numSpeakers) {
        this.numSpeakers = numSpeakers;
    }


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }
}