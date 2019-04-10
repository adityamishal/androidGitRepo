package com.example.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("round")
    @Expose
    private String round;

    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("image")
    @Expose
    private String image;


    @SerializedName("interviewer")
    @Expose
    private String interviewer;


    @SerializedName("date")
    @Expose
    private String date;


    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("candidate")
    @Expose
    private String candidate;

    public Feedback(String id, String round, String description, String image, String interviewer, String date, String status, String candidate) {

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