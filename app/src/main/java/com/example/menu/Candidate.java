package com.example.menu;

public class Candidate {

    private String id;
    private String name;
    private String phone_no;
    private String email;
    private String resume;
    private String date;
    private String status;
    private String position;

    public Candidate(String id, String name, String phone_no, String email, String resume, String date, String status, String position) {
        this.id = id;
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;
        this.resume = resume;
        this.date = date;
        this.status = status;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getEmail() {
        return email;
    }

    public String getResume() {
        return resume;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getPosition() {
        return position;
    }


}
