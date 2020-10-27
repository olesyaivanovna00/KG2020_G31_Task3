package com.company.rectangle;

import com.company.point.RealPoint;

public class Rectangle {
    private RealPoint p1, p2; //координата начала, координата конца по диагонали
    private RealPoint p0, p3;

        /*
        p1-----p0
        |       |
        |       |
        p3-----p2
         */

    public Rectangle(RealPoint p1, RealPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.p0 = new RealPoint(p2.getX(), p1.getY());
        this.p3 = new RealPoint(p1.getX(), p2.getY());
    }

    public Rectangle(double x1, double y1, double x2, double y2){
        p1 = new RealPoint(x1, y1);
        p2 = new RealPoint(x2, y2);
        p0 = new RealPoint(x2, y1);
        p3 = new RealPoint(x1, y2);
    }


    public RealPoint getP1() {
        return p1;
    }

    public void setP1(RealPoint p1) {
        this.p1 = p1;
    }

    public RealPoint getP2() {
        return p2;
    }

    public void setP2(RealPoint p2) {
        this.p2 = p2;

    }

    public RealPoint getP0() {
        return p0;
    }

    public RealPoint getP3() {
        return p3;
    }


}
