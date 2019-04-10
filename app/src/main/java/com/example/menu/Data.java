package com.example.menu;

public class Data {

    private String id;
    private String round;
    private String image;
    private String interviewer;
    private String status;
    private String date;
    private String candidate;
    private String description;

    public Data(String id, String round, String description, String image, String interviewer, String date, String status, String candidate) {

        this.id = id;
        this.round = round;
        this.description = description;
        this.image = image;
        this.interviewer = interviewer;
        this.date = date;
        this.status = status;
        this.candidate = candidate;
    }

    public String getId() {
        return id;
    }

    public String getRound() {
        return round;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getInterviewer() {
        return interviewer;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getCandidate() {
        return candidate;
    }


}
