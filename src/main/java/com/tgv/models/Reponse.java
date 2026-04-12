package com.tgv.models;

public class Reponse {

    private int id;
    private String content;
    private String author;
    private String createdAt;
    private String updatedAt;
    private int reclamationId;

    public Reponse() {}

    public Reponse(String content, String author, String createdAt, String updatedAt, int reclamationId) {
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reclamationId = reclamationId;
    }

    public Reponse(int id, String content, String author, String createdAt, String updatedAt, int reclamationId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reclamationId = reclamationId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public int getReclamationId() { return reclamationId; }
    public void setReclamationId(int reclamationId) { this.reclamationId = reclamationId; }
}
