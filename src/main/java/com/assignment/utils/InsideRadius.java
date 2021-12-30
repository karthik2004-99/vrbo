package com.assignment.utils;

public class InsideRadius {
    public static double getDistance(double pLat, double pLong, double sLat, double sLong){
        if ((pLat == sLat) && (pLong == sLong)) {
            return 0;
        }
        else {
            double theta = pLong - sLong;
            double dist = Math.sin(Math.toRadians(pLat)) * Math.sin(Math.toRadians(sLat)) + Math.cos(Math.toRadians(pLat)) * Math.cos(Math.toRadians(sLat)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344; //By default we are assuming radius is in Kms
            //dist = dist * 0.8684; comment above line and uncomment to get in Nautical Miles
            return (dist);
        }

    }
}
