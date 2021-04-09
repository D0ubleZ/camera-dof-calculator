package com.example.myapplication.Model;

public class DepthofFieldCalculator {
    private Lens L;
    public DepthofFieldCalculator(Lens L) {
        this.L = L;
    }
    public static final double confusion = 0.029;

//    public int lens_focal_length = L.getFocal_length();
//    public double max_aperture = L.getMax_aperture();

    public double Hyperfocal_distance(double lens_focal_length,double aperture){
        return (lens_focal_length*lens_focal_length)/(aperture*confusion);
    }

    public double Near_focal_point(double lens_focal_length,double hyper_focal_point, double distance){
        return (hyper_focal_point*distance)/(hyper_focal_point+(distance-lens_focal_length));
    }
    public double Far_focal_point(double lens_focal_length,double hyper_focal_point,double distance){
        if (distance > hyper_focal_point){
            return Double.POSITIVE_INFINITY;
        }
        double Far = (hyper_focal_point*distance)/(hyper_focal_point-(distance-lens_focal_length));
        return Far;
    }
    public double Depth_of_field(double near, double far){
        return far-near;
    }
}
