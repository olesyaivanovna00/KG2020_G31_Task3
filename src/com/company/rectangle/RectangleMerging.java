package com.company.rectangle;

import com.company.point.RealPoint;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RectangleMerging {
    public List<RealPoint> makePolygon(List<Rectangle> rectangles) {
        List<RealPoint> points = calcPoints(rectangles);
        return points;
    }

    private List<RealPoint> calcPoints(List<Rectangle> rectangles) {
        List<RealPoint> ret = new ArrayList<>();

        List<Double> yCords = new ArrayList<>(getAllYCords(rectangles));
        List<Double> xCords = new ArrayList<>(getAllXCords(rectangles));
        yCords.sort(Comparator.naturalOrder());

        double previousLeftCoord = 0.0;
        double previousRightCoord = 0.0;

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
                ret.add(0, new RealPoint(minimumXLeftCord, yCord));

                if (!maximumXRightCord.equals(previousRightCoord)) {
                    ret.add(new RealPoint(previousRightCoord, yCord));
                }

            }
            ret.add(new RealPoint(maximumXRightCord, yCord));

            previousLeftCoord = minimumXLeftCord;
            previousRightCoord = maximumXRightCord;
        }

        return ret;

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
        if (rectsAtYBottomLines.size() == 1){
            return rectsAtYBottomLines;
        } else {

//            for (Rectangle r: rectsAtYBottomLines) {
//                r.
//            }
            return rectsAtYBottomLines;
        }

        //надо понять пересекается ли он с чем-то
        //если размер листа = 1, то просто точки соединяем, а если нет то что-то надо делать

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



}
