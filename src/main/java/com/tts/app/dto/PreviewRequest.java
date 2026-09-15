package com.tts.app.dto;

import jakarta.validation.constraints.NotBlank;

public class PreviewRequest {

    @NotBlank(message = "Language is required.")
    private String language;

    @NotBlank(message = "Voice is required.")
    private String voice;


    public PreviewRequest() {
    }


    public PreviewRequest(
            String language,
            String voice
    ) {
        this.language = language;
        this.voice = voice;
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