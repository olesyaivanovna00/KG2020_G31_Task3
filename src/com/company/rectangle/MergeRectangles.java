package com.company.rectangle;


import com.company.point.RealPoint;

import java.util.ArrayList;

public class MergeRectangles {
    public void mergeRectangles(ArrayList<Rectangle> allRectangles) {



    }

//    private ArrayList<RectanglePoint> sortedPoints(ArrayList<Rectangle> allRectangles){
//
//    }

    private ArrayList<RectanglePoint> allPoint(ArrayList<Rectangle> allRectangles){
        ArrayList<RectanglePoint> allPoint = new ArrayList<>();
        for (Rectangle r:allRectangles) {
            for (RealPoint rp: r.getMarkers()) {
                allPoint.add(new RectanglePoint(rp, r));
            }
        }
        return allPoint;
    }

//    private ArrayList<RectanglePoint> allYCords(ArrayList<Rectangle> allRectangles){
//        ArrayList<RectanglePoint> allYCords = new ArrayList<>();
//        for (Rectangle r:allRectangles) {
//            for (Double yCord: r.getAllYCords()) {
//                allYCords.add(new RectanglePoint())
//            }
//            allYCords.add(r.getAllYCords().)
//        }
//    }

//    private ArrayList<RectanglePoint> allXCords(ArrayList<Rectangle> allRectangles){
//
//    }

//    private boolean isCrossed(Rectangle r1, Rectangle r2){
//
//    }
}
