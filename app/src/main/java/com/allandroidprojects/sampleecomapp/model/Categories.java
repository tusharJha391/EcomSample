package com.allandroidprojects.sampleecomapp.model;


import java.util.List;

public class Categories {
    private String categoryHeader;
    private List<CategoryData> categoryData = null;

    public String getCategoryHeader() {
        return categoryHeader;
    }

    public void setCategoryHeader(String categoryHeader) {
        this.categoryHeader = categoryHeader;
    }

    public List<CategoryData> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryData> categoryData) {
        this.categoryData = categoryData;
    }

}