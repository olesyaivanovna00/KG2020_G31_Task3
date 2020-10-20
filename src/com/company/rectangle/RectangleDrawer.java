package com.company.rectangle;

import com.company.ScreenConverter;
import com.company.line.LineDrawer;
import com.company.point.ScreenPoint;

public interface RectangleDrawer {
    void drawRectangle(ScreenPoint p1, ScreenPoint p2, LineDrawer ld);
}
