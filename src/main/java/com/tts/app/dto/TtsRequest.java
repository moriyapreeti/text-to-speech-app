package com.tts.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TtsRequest {

    @NotBlank(message = "Text cannot be empty.")
    @Size(
            max = 5000,
            message = "Text cannot exceed 5000 characters."
    )
    private String text;

    @NotBlank(message = "Language is required.")
    private String language;

    @NotBlank(message = "Voice is required.")
    private String voice;


    public TtsRequest() {
    }


    public TtsRequest(
            String text,
            String language,
            String voice
    ) {
        this.text = text;
        this.language = language;
        this.voice = voice;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }


    public String getVoice() {
        return voice;
    }


    public void setVoice(String voice) {
        this.voice = voice;
    }
}