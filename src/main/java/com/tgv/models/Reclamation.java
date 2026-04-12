package com.tgv.models;

public class Reclamation {

    private int id;
    private String subject;
    private String message;
    private String status;
    private String createdAt;
    private String updatedAt;
    private Integer userId;
    private String typeService;
    private String dejaRepondu;
    private String phone;

    public Reclamation() {
    }

    public Reclamation(String subject, String message, String status, String createdAt, String updatedAt, Integer userId, String typeService) {
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.typeService = typeService;
    }

    public Reclamation(int id, String subject, String message, String status, String createdAt, String updatedAt, Integer userId, String typeService) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.typeService = typeService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public String getDejaRepondu() {
        return dejaRepondu;
    }

    public void setDejaRepondu(String dejaRepondu) {
        this.dejaRepondu = dejaRepondu;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
