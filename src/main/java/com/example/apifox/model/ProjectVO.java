package com.example.apifox.model;

public class ProjectVO {
    private String createdAt;
    private Long creatorId;
    private String description;
    private Long editorId;
    private long id;
    private String name;
    private Long roleType;
    private Long strictMode;
    private Long teamId;
    private String updatedAt;
    private String visibility;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String value) {
        this.createdAt = value;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long value) {
        this.creatorId = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long value) {
        this.editorId = value;
    }

    public long getid() {
        return id;
    }

    public void setid(long value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Long getRoleType() {
        return roleType;
    }

    public void setRoleType(Long value) {
        this.roleType = value;
    }

    public Long getStrictMode() {
        return strictMode;
    }

    public void setStrictMode(Long value) {
        this.strictMode = value;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long value) {
        this.teamId = value;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String value) {
        this.updatedAt = value;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String value) {
        this.visibility = value;
    }
}

