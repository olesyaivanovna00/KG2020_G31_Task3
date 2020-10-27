package com.company.rectangle;

import com.company.ScreenConverter;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.point.ScreenPoint;

import java.util.ArrayList;

public interface RectangleDrawer {
    void drawRectangle(ScreenPoint p1, ScreenPoint p2, LineDrawer ld);
    ArrayList<Line> mergeRectangle(Rectangle r1, Rectangle r2);

}
