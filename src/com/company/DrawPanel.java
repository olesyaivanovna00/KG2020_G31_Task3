package com.company;

import com.company.line.DDALineDrawer;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.pixel.BufferedImagePixelDrawer;
import com.company.pixel.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private Line xAxis = new Line(-1, 0, 1, 0);
    private Line yAxis = new Line(0, -1, 0, 1);

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics gr= bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();

        PixelDrawer pd = new BufferedImagePixelDrawer(bi);

        LineDrawer ld = new DDALineDrawer(pd);

        ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);

        ld.drawLine(sc.r2s(xAxis.getP1()), sc.r2s(xAxis.getP2()));
        ld.drawLine(sc.r2s(yAxis.getP1()), sc.r2s(yAxis.getP2()));


        g.drawImage(bi, 0, 0, null);


    }
}
