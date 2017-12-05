package com.example.kevin.mygooglemapsapp1204174;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by kevin on 12/3/2017.
 */

public class ListItem implements Comparable{

    private String dest;
    private Double dist;
    private String duration;
    //private String imageUrl;

    public ListItem(String dest, Double dist, String duration){
        this.dest = dest;
        this.dist = dist;
        this.duration = duration;
       // this.imageUrl = imageUrl;
    }

    public String getDest() { return dest; }

    public String getDist(){
        return dist.toString() + " " + "mi";
    }

    public String getDuration(){
        return duration;
    }

    public int getDist_Int(){
        return dist.intValue();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        int compareDist=((ListItem)o).getDist_Int();
        return this.getDist_Int()-compareDist;
    }
}
