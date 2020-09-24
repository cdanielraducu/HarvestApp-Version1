package com.example.harvestmesaje;

public class Audio {
    private String audioName;
    private String audioUrl;

    public Audio() {
    }

    public Audio(String audioName, String audioUrl) {
        this.audioName = audioName;
        this.audioUrl = audioUrl;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
