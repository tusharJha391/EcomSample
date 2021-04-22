package com.allandroidprojects.sampleecomapp.model;

public class ModelReviewer {
    int circleImageView;
    String mTextName;
    String mTextReview;
    double ratingNumber;

    public ModelReviewer(int circleImageView, String mTextName, String mTextReview, double ratingNumber) {
        this.circleImageView = circleImageView;
        this.mTextName = mTextName;
        this.mTextReview = mTextReview;
        this.ratingNumber = ratingNumber;
    }

    public ModelReviewer(int circleImageView, String mTextName, String mTextReview) {
        this.circleImageView = circleImageView;
        this.mTextName = mTextName;
        this.mTextReview = mTextReview;

    }

    public ModelReviewer(String mTextName, String mTextReview) {
        this.mTextName = mTextName;
        this.mTextReview = mTextReview;
    }

    public int getCircleImageView() {
        return circleImageView;
    }

    public void setCircleImageView(int circleImageView) {
        this.circleImageView = circleImageView;
    }

    public String getmTextName() {
        return mTextName;
    }

    public void setmTextName(String mTextName) {
        this.mTextName = mTextName;
    }

    public String getmTextReview() {
        return mTextReview;
    }

    public void setmTextReview(String mTextReview) {
        this.mTextReview = mTextReview;
    }

    public double getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(double ratingNumber) {
        this.ratingNumber = ratingNumber;
    }
}
