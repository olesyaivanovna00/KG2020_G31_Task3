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
        List<RealPoint> missingCords = new ArrayList<>();
        List<Double> yCords2 = new ArrayList<>();

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

            } else if (yCord != yCords.get(yCords.size() - 1)) {

                if (!minimumXLeftCord.equals(previousLeftCoord)) {
                    ret.add(0, new RealPoint(previousLeftCoord, yCord));
                }

                ret.add(0, new RealPoint(minimumXLeftCord, yCord));

                if (!maximumXRightCord.equals(previousRightCoord)) {
                    ret.add(new RealPoint(previousRightCoord, yCord));
                }


            } else {
                if (yCord == yCords.get(yCords.size() - 1) && !checker){
                    System.out.println("iiii");
                    if (!minimumXLeftCord.equals(previousLeftCoord)) {
                        ret.add(0, new RealPoint(previousLeftCoord, yCord));
                    }

                    ret.add(0, new RealPoint(minimumXLeftCord, yCord));

                    if (!maximumXRightCord.equals(previousRightCoord)) {
                        ret.add(new RealPoint(previousRightCoord, yCord));
                    }
                }
            }
            if (rectsOverYLine(yCord, rectangles).size() > 1) {
                if (!ifCordsBelongOneRectangle(previousLeftCoord, previousRightCoord, rectanglesAtY(yCord, rectangles))) {
                    checker = true;
                    yCords2.add(yCord);
                }


            }
            if (yCord != yCords.get(yCords.size() - 1)) {
                ret.add(new RealPoint(maximumXRightCord, yCord));
            } else {
                if (!checker){
                    System.out.println("uuuu");
                    ret.add(new RealPoint(maximumXRightCord, yCord));
                }
            }
            previousLeftCoord = minimumXLeftCord;
            previousRightCoord = maximumXRightCord;
        }
        System.out.println("____________________________");
        /**/
        if (checker) {
            yCords.sort(Comparator.reverseOrder());

            for (double yCord : yCords) {

                System.out.println("Considering yCords " + yCord);
                xCords = new ArrayList<>(getAllXCords(rectsAtYBottomLines(yCord, rectangles)));
                xCords.sort(Comparator.naturalOrder());
                Double minimumXLeftCord = xCords.get(1);
                Double maximumXRightCord = xCords.get(xCords.size() - 2);
                if (yCord >= yCords2.get(0)) {
//            if (!ifCordsBelongOneRectangle(minXLeftCoord(yCord, rectangles), maxXRightCoord(yCord, rectangles), rectanglesAtY(yCord, rectangles)) && xCords.size()!=2) {

                    System.out.println("min X: " + minimumXLeftCord);
                    System.out.println("max X: " + maximumXRightCord);


                    if (!minimumXLeftCord.equals(previousLeftCoord)) {
                        if (!containsPoint(new RealPoint(previousLeftCoord, yCord), ret))
                            //if (!ret.contains(new RealPoint(previousLeftCoord, yCord)))
                            ret.add(0, new RealPoint(previousLeftCoord, yCord));
                    }


                    if (!containsPoint(new RealPoint(minimumXLeftCord, yCord), ret)) {

                        if (yCord == yCords.get(0)){
                            ret.add(new RealPoint(minimumXLeftCord, yCord));

                        } else {

                            if (findRectangleByCord(rectangles, new RealPoint(minimumXLeftCord, yCord)) != null){
                                ret.add(new RealPoint(minimumXLeftCord, yCord));
                            } else {
                                if (liesAtTheIntersection(new RealPoint(minimumXLeftCord, yCord), rectangles)){
                                    ret.add(new RealPoint(minimumXLeftCord, yCord));
                                }
                            }
//                            int index = yCords.indexOf(yCord);
//                            if (containsPoint(new RealPoint(minimumXLeftCord, yCords.get(index - 1)), ret)) {
//                                if (liesAtTheIntersection(new RealPoint(minimumXLeftCord, yCord), rectangles)) {
//                                    ret.add(new RealPoint(minimumXLeftCord, yCord));
//                                }
//                            } else {
//                                ret.add(new RealPoint(minimumXLeftCord, yCord));
//                            }
                        }

//                        if (liesAtTheIntersection(new RealPoint(minimumXLeftCord, yCord), rectangles)){
//                            ret.add(0, new RealPoint(minimumXLeftCord, yCord));
//                        }
//                        int index = yCords.indexOf(yCord);
//                        if (!containsPoint(new RealPoint(minimumXLeftCord, yCords.get(index - 1)), ret) && (liesAtTheIntersection(new RealPoint(minimumXLeftCord, yCord), rectangles)))
//                            ret.add(0, new RealPoint(minimumXLeftCord, yCord));

                    }

                    if (!maximumXRightCord.equals(previousRightCoord)) {
                        if (!containsPoint(new RealPoint(previousRightCoord, yCord), ret))
                            ret.add(new RealPoint(previousRightCoord, yCord));
                    }


                    if (!containsPoint(new RealPoint(maximumXRightCord, yCord), ret)) {
                        if (yCord == yCords.get(0)){
                            ret.add(new RealPoint(maximumXRightCord, yCord));

                        } else {

                            if (findRectangleByCord(rectangles, new RealPoint(maximumXRightCord, yCord)) != null){
                                ret.add(new RealPoint(maximumXRightCord, yCord));
                            } else {
                                if (liesAtTheIntersection(new RealPoint(maximumXRightCord, yCord), rectangles)){
                                    ret.add(new RealPoint(maximumXRightCord, yCord));
                                }
                            }
//                            int index = yCords.indexOf(yCord);
//                            if (containsPoint(new RealPoint(maximumXRightCord, yCords.get(index - 1)), ret)) {
//                                if (liesAtTheIntersection(new RealPoint(maximumXRightCord, yCord), rectangles)) {
//                                    ret.add(new RealPoint(maximumXRightCord, yCord));
//                                }
//                            } else {
//                                ret.add(new RealPoint(maximumXRightCord, yCord));
//
//                            }
                        }
//                        int index = yCords.indexOf(yCord);
//                        if (!containsPoint(new RealPoint(maximumXRightCord, yCords.get(index - 1)), ret) && (liesAtTheIntersection(new RealPoint(maximumXRightCord, yCord), rectangles)))
                    }

                }
                previousLeftCoord = minimumXLeftCord;
                previousRightCoord = maximumXRightCord;

            }
        }
        /**/


        if (checker) {
            //ret = addPoints(rectangles, yCords, ret, yCords2);
        }

        //xCords.sort(Comparator.reverseOrder());
        //return sort(rectangles, ret, yCords, xCords);
        return ret;

    }

    private List<RealPoint> sort(List<Rectangle> rectangles, List<RealPoint> points, List<Double> yCords, List<Double> xCords){
        List<RealPoint> sorted = new ArrayList<>();
        List<RealPoint> unSorted = new ArrayList<>(points);
        //Double midX = xCords.get(xCords.size()/2);
        for (Double yCord:yCords) {

            xCords = new ArrayList<>(getAllXCords(rectanglesAtY(yCord, rectangles)));

            //xCords = new ArrayList<>(getAllXCordsByPoint(pointsInY(points, yCord)));
            xCords.sort(Comparator.naturalOrder());
            for (Double xCord:xCords
                 ) {
                System.out.println(" --- " + xCord);
            }
            System.out.println("0000");
            //Double midX = xCords.get(xCords.size()/2);
            for (int i = 0; i < xCords.size() - 1; i++) {
                Double xCord = xCords.get(i);
                if (containsPoint(new RealPoint(xCord, yCord), unSorted)){
                    int index = xCords.size()/2;
                    if (i<=index){
                        sorted.add(0, new RealPoint(xCord, yCord));
                    } {
                        sorted.add(new RealPoint(xCord, yCord));
                    }
                }
            }
//            for (Double xCord:xCords) {
//                if (containsPoint(new RealPoint(xCord, yCord), unSorted)){
//                    int index =
//                    if (xCord<=midX){
//                        sorted.add(0, new RealPoint(xCord, yCord));
//                    } else if (xCord>=midX){
//                        sorted.add(new RealPoint(xCord, yCord));
//                    }
//                }
//            }
        }
//        for (Double xCord:xCords) {
//            for (Double yCord:yCords) {
//                if (containsPoint(new RealPoint(xCord, yCord), unSorted)){
//                    sorted.add(new RealPoint(xCord, yCord));
//                }
//            }
//        }
        System.out.println("unSorted size " + unSorted.size());

        System.out.println("sorted size " + sorted.size());

        return sorted;
    }

    private List<RealPoint> pointsInY(List<RealPoint> points, double y){
        List<RealPoint> pointsInY = new ArrayList<>();
        for (RealPoint point:points) {
            if (point.getY() == y){
                pointsInY.add(point);
            }
        }
        return pointsInY;
    }

    private Set<Double> getAllXCordsByPoint(List<RealPoint> points) {

        Set<Double> allCords = new HashSet<>();
        for (RealPoint point:points) {
            allCords.add(point.getX());
        }
        return allCords;
    }


    private boolean liesAtTheIntersection(RealPoint rp, List<Rectangle> rectangles) {
        int k = 0;
        for (Rectangle r : rectangles) {
            if (r.getTop().getY() >= rp.getY() && r.getBottom().getY() <= rp.getY()
                    && r.getRight().getX() >= rp.getX() && r.getLeft().getX() <= rp.getX()) {
                k++;
            }
        }
        return k > 1;

    }

    private boolean containsPoint(RealPoint point, List<RealPoint> points) {
        for (RealPoint p : points) {
            if (p.getX() == point.getX() && p.getY() == point.getY()) return true;
        }
        return false;
    }


    private List<RealPoint> addPoints(List<Rectangle> rets, List<Double> yCords, List<RealPoint> polygon, List<Double> yCords2) {
        List<RealPoint> missedPoint = new ArrayList<>();

        yCords2.sort(Comparator.reverseOrder());


        for (double yCord : yCords) {
            Double minimumXLeftCord = minXLeftCoord(yCord, rets);
            Double maximumXRightCord = maxXRightCoord(yCord, rets);
            List<Rectangle> rectsOverYLine = rectanglesAtY(yCord, rets);
            List<Double> xCords = new ArrayList<>(getAllXCords(rectsOverYLine));

            xCords.sort(Comparator.naturalOrder());
            for (int i = 0; i < xCords.size() / 2; i++) {
                if (ifCordsBelongOneRectangle(xCords.get(2 * i), xCords.get(2 * i + 1), rectsOverYLine)) {
                    missedPoint.add(new RealPoint(xCords.get(2 * i), yCord));
                    missedPoint.add(new RealPoint(xCords.get(2 * i + 1), yCord));
                }
            }

        }


        return missedPoint;
    }


    private boolean ifCordsBelongOneRectangle(double xLeft, double xRight, List<Rectangle> rectangles) {
        for (Rectangle r : rectangles) {
            if (r.getLeft().getX() == xLeft && r.getRight().getX() == xRight) {
                return true;
            }
        }
        return false;
    }

    private Rectangle findRectangleByCord(List<Rectangle> rectangles, RealPoint rp) {
        for (Rectangle r : rectangles) {
            if (rp.getX() == r.getRight().getX() && rp.getY() == r.getRight().getY()) {
                return r;
            }

            if (rp.getX() == r.getTop().getX() && rp.getY() == r.getTop().getY()) {
                return r;
            }

            if (rp.getX() == r.getLeft().getX() && rp.getY() == r.getLeft().getY()) {
                return r;
            }

            if (rp.getX() == r.getBottom().getX() && rp.getY() == r.getBottom().getY()) {
                return r;
            }
        }

        return null;
    }


    private int prevIndexOfCord(List<RealPoint> polygon, RealPoint realPoint) {
        for (RealPoint rp : polygon) {

        }
        return -1;
    }


    private int indexOfCord(List<RealPoint> polygon, RealPoint realPoint) {
        for (RealPoint rp : polygon) {
            if ((rp.getX() == realPoint.getX()) && (rp.getY() == realPoint.getY()))
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

    private List<Rectangle> rectsOverYLine(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectsOverYLine = new ArrayList<>();
        for (Rectangle r : rectangles) {
            if (r.getTop().getY() > y && r.getBottom().getY() < y) {
                rectsOverYLine.add(r);
            }
        }
        return rectsOverYLine;
    }

    private List<Rectangle> rectsOverYBottomLine(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectsAtYBottomLines = new ArrayList<>();
        for (Rectangle r : rectangles) {
            if (r.getTop().getY() > y && r.getBottom().getY() <= y) {
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

    private List<Rectangle> rectsInXLine(double x, List<Rectangle> rectangles) {
        List<Rectangle> rectsInXLine = new ArrayList<>();
        for (Rectangle r : rectangles) {
            if (r.getTop().getX() == x || r.getRight().getX() == x) {
                rectsInXLine.add(r);
            }

            if (r.getTop().getX() < x && r.getRight().getX() > x) {
                rectsInXLine.add(r);
            }
        }

        return rectsInXLine;
    }

    private List<Rectangle> rectsCrossedInYLine(List<Rectangle> rectanglesInY) {
        List<Rectangle> rectsCrossedInYLine = new ArrayList<>();
        List<Double> xCords = new ArrayList<>(getAllXCords(rectanglesInY));
        xCords.sort(Comparator.naturalOrder());

        for (Double x : xCords) {
            if (rectsInXLine(x, rectanglesInY).size() <= 2) {
                rectsCrossedInYLine.addAll(rectsInXLine(x, rectanglesInY));
            }
        }


        return rectsCrossedInYLine;
    }


}
