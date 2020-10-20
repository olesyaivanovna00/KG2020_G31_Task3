package com.company.rectangle;

import com.company.line.LineDrawer;
import com.company.pixel.PixelDrawer;
import com.company.point.ScreenPoint;

public class DrawRectangle implements RectangleDrawer {



    @Override
    public void drawRectangle(ScreenPoint p1, ScreenPoint p2, LineDrawer ld) {
        double width = p2.getX() - p1.getX();
        double height = p2.getY() - p1.getY();
        ScreenPoint p11 = new ScreenPoint(p2.getX(), p1.getY());
        ScreenPoint p3 = new ScreenPoint(p1.getX(), p2.getY());

        ld.drawLine(p1, p11);
        ld.drawLine(p11, p2);
        ld.drawLine(p2, p3);
        ld.drawLine(p3, p1);


        /* p1
         p11 = (p1.getX() + width), p1.getY()
         p2
         p3 = p1.getX(), p2.getY()

        p1-----p11
        |       |
        |       |
        p3-----p2
         */
    }
}
