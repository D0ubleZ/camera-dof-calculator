package com.example.myapplication.Model;

public class Lens {
    private String make;
    private double max_aperture;
    private int focal_length;
    private int iconID;

    public Lens(String make, double max_aperture, int focal_length,int iconID) {
        this.make = make;
        this.max_aperture = max_aperture;
        this.focal_length = focal_length;
        this.iconID = iconID;
    }

    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public double getMax_aperture() {
        return max_aperture;
    }
    public void setMax_aperture(double max_aperture) {
        this.max_aperture = max_aperture;
    }
    public int getFocal_length() {
        return focal_length;
    }
    public void setFocal_length(int focal_length) {
        this.focal_length = focal_length;
    }
    public int geticonID() {
        return iconID;
    }
}
