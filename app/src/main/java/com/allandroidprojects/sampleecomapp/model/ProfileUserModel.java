package com.allandroidprojects.sampleecomapp.model;

public class ProfileUserModel {

    private int imageProfileDetailId;
    private String profileDetailCategories;

    public ProfileUserModel(int imageProfileDetailId, String profileDetailCategories) {
        this.imageProfileDetailId = imageProfileDetailId;
        this.profileDetailCategories = profileDetailCategories;
    }

    public int getImageProfileDetailId() {
        return imageProfileDetailId;
    }

    public void setImageProfileDetailId(int imageProfileDetailId) {
        this.imageProfileDetailId = imageProfileDetailId;
    }

    public String getProfileDetailCategories() {
        return profileDetailCategories;
    }

    public void setProfileDetailCategories(String profileDetailCategories) {
        this.profileDetailCategories = profileDetailCategories;
    }
}
