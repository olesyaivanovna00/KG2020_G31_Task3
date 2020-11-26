package com.company.rectangle;

import com.company.point.RealPoint;


import java.util.*;
import java.util.List;
import java.util.stream.Collectors;



public class RectangleMerging {
    public List<RealPoint> makePolygon(List<Rectangle> rectangles) {
        List<RealPoint> points = calcPoints(rectangles);
        return points;
    }

    private List<RealPoint> calcPoints(List<Rectangle> rectangles) {
        boolean checker = false;

        List<RealPoint> ret = new ArrayList<>();

        List<Double> yCords = new ArrayList<>(getAllYCords(rectangles));
        List<Double> xCords = new ArrayList<>(getAllXCords(rectangles));
        yCords.sort(Comparator.naturalOrder());

        double previousLeftCoord = 0.0;
        double previousRightCoord = 0.0;
        List<Double> previousXCords = new ArrayList<>();

        for (double xCord : xCords
        ) {
            System.out.println("Considering xCords " + xCord);
        }
        System.out.println("size = " + yCords.size());
        for (double yCord : yCords) {
            System.out.println("Considering yCords " + yCord);
            Double minimumXLeftCord = minXLeftCoord(yCord, rectangles);
            Double maximumXRightCord = maxXRightCoord(yCord, rectangles);
            System.out.println("min X: " + minimumXLeftCord);
            System.out.println("max X: " + maximumXRightCord);

            if (yCord == yCords.get(0)) {
                ret.add(new RealPoint(minimumXLeftCord, yCord));

            } else {

                if (!minimumXLeftCord.equals(previousLeftCoord)) {
                    ret.add(0, new RealPoint(previousLeftCoord, yCord));
                }

                if (rectsOverYBottomLine(yCord, rectangles).size() > 1){

                    checker = true;
                }

                ret.add(0, new RealPoint(minimumXLeftCord, yCord));

                if (!maximumXRightCord.equals(previousRightCoord)) {
                    ret.add(new RealPoint(previousRightCoord, yCord));
                }
                


            }


            ret.add(new RealPoint(maximumXRightCord, yCord));
            previousLeftCoord = minimumXLeftCord;
            previousRightCoord = maximumXRightCord;
        }

        if(checker){
            ret = addPoints(rectangles, yCords, ret);
        }

        return ret;

    }

    private List<RealPoint> addPoints(List<Rectangle> rets, List<Double> yCords, List<RealPoint> polygon){


        for (double yCord : yCords) {
            Double minimumXLeftCord = minXLeftCoord(yCord, rets);
            Double maximumXRightCord = maxXRightCoord(yCord, rets);

            if(rectsOverYBottomLine(yCord, rets).size() > 1){
                List<Rectangle> rectsOverYBottomLine = rectsOverYBottomLine(yCord, rets);
                List<Double> xCords = new ArrayList<>(getAllXCords(rectsOverYBottomLine));
                xCords.sort(Comparator.naturalOrder());

                //int index = indexOfCord(polygon, new RealPoint(minimumXLeftCord,yCord));
//                int index = polygon.indexOf(new RealPoint(xCords.get(0),yCord));
                //System.out.println(index);
                for (double xCord:xCords) {
                    int index = indexOfCord(polygon, new RealPoint(minimumXLeftCord,yCord));
                    System.out.println(index);

                    polygon.add(index, new RealPoint(xCord, yCord));
                }

            }


        }


        return polygon;
    }

    private int prevIndexOfCord(List<RealPoint> polygon, RealPoint realPoint){
        for (RealPoint rp:polygon) {

        }
        return -1;
    }


    private int indexOfCord(List<RealPoint> polygon, RealPoint realPoint){
        for (RealPoint rp:polygon) {
            if((rp.getX() == realPoint.getX())&& (rp.getY() == realPoint.getY()))
                return polygon.indexOf(rp);
        }
        return -1;
    }

    private Set<Double> getAllXCords(List<Rectangle> rectangles) {
        List<Double> allLeftXCords = rectangles.stream().map(rectangle -> rectangle.getLeft().getX()).collect(Collectors.toList());
        List<Double> allRightXCords = rectangles.stream().map(rectangle -> rectangle.getRight().getX()).collect(Collectors.toList());

        Set<Double> allCords = new HashSet<>();
        allCords.addAll(allLeftXCords);
        allCords.addAll(allRightXCords);
        return allCords;
    }

    private Set<Double> getAllYCords(List<Rectangle> rectangles) {

        List<Double> allBottomYCords = rectangles.stream().map(rectangle -> rectangle.getBottom().getY()).collect(Collectors.toList());
        List<Double> allTopYCords = rectangles.stream().map(rectangle -> rectangle.getTop().getY()).collect(Collectors.toList());

        Set<Double> allCords = new HashSet<>();
        allCords.addAll(allTopYCords);
        allCords.addAll(allBottomYCords);
        return allCords;
    }

    private Double minXLeftCoord(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectAtY = rectanglesAtY(y, rectangles);
        double minXLeftCord = 99.0;
        for (Rectangle r : rectAtY) {
            if (r.getLeft().getX() < minXLeftCord) {
                minXLeftCord = r.getLeft().getX();
            }
        }

        return minXLeftCord;

    }

    private Double maxXRightCoord(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectAtY = rectanglesAtY(y, rectangles);

        double maxXRightCord = -99.0;
        for (Rectangle r : rectAtY) {
            if (r.getRight().getX() > maxXRightCord) {
                maxXRightCord = r.getRight().getX();
            }
        }

        return maxXRightCord;

    }


    private List<Rectangle> rectanglesAtY(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectsAtYBottomLines = rectsAtYBottomLines(y, rectangles);
        if (rectsAtYBottomLines.size() == 1) {
            return rectsAtYBottomLines;
        } else {

            return rectsOverYBottomLine(y, rectangles);
        }

        //надо понять пересекается ли он с чем-то
        //если размер листа = 1, то просто точки соединяем, а если нет то что-то надо делать

    }

    private List<Rectangle> rectsOverYBottomLine(double y, List<Rectangle> rectangles){
        List<Rectangle> rectsAtYBottomLines = new ArrayList<>();
        for (Rectangle r : rectangles) {
            if (r.getTop().getY() > y && r.getBottom().getY() < y) {
                rectsAtYBottomLines.add(r);
            }
        }
        return rectsAtYBottomLines;
    }

    private List<Rectangle> rectsAtYBottomLines(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectsAtYBottomLines = new ArrayList<>();
        for (Rectangle r : rectangles) {

            if (r.getTop().getY() == y || r.getBottom().getY() == y) {
                rectsAtYBottomLines.add(r);
            }
            if (r.getTop().getY() > y && r.getBottom().getY() < y) {
                rectsAtYBottomLines.add(r);
            }
        }
        return rectsAtYBottomLines;

    }

    private List<Rectangle> rectsInXLine(double x, List<Rectangle> rectangles){
        List<Rectangle> rectsInXLine = new ArrayList<>();
        for (Rectangle r:rectangles) {
            if (r.getTop().getX() == x || r.getRight().getX() == x){
                rectsInXLine.add(r);
            }

            if (r.getTop().getX() < x && r.getRight().getX() > x){
                rectsInXLine.add(r);
            }
        }

        return rectsInXLine;
    }

    private List<Rectangle> rectsCrossedInYLine(List<Rectangle> rectanglesInY){
        List<Rectangle> rectsCrossedInYLine = new ArrayList<>();
        List<Double> xCords = new ArrayList<>(getAllXCords(rectanglesInY));
        xCords.sort(Comparator.naturalOrder());

        for (Double x:xCords) {
            if (rectsInXLine(x, rectanglesInY).size() <= 2){
                rectsCrossedInYLine.addAll(rectsInXLine(x, rectanglesInY));
            }
        }


        return rectsCrossedInYLine;
    }


}
