package com.company;

import com.company.line.DDALineDrawer;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.pixel.BufferedImagePixelDrawer;
import com.company.pixel.PixelDrawer;
import com.company.point.RealPoint;
import com.company.point.ScreenPoint;
import com.company.rectangle.DrawRectangle;
import com.company.rectangle.RectangleDrawer;
import com.company.rectangle.Rectangle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private Line xAxis = new Line(-1, 0, 1, 0);
    private Line yAxis = new Line(0, -1, 0, 1);

    ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4,
            800, 600);

    private ArrayList<Rectangle> allRect = new ArrayList<>();
    private ArrayList<Line> allLines = new ArrayList<>();
    private Line currentNewLine = null;
    private boolean toMerge = false;


    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenWidth(getWidth());
        sc.setScreenHeight(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics gr= bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();

        PixelDrawer pd = new BufferedImagePixelDrawer(bi);

        LineDrawer ld = new DDALineDrawer(pd);


        drawLine(ld, xAxis);
        drawLine(ld, yAxis);

        RectangleDrawer rd = new DrawRectangle();

        if (toMerge){
            for (int i = 0; i < allRect.size() / 2; i++) {
                ArrayList<Line> polygon = rd.mergeRectangle(allRect.get(2*i), allRect.get(2*i + 1));
                for (Line l:polygon
                ) {
                    ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
                }
            }

            //rd.drawRectangle(sc.r2s(allRect.get(0).getP1()), sc.r2s(allRect.get(0).getP2()),ld);
        }else {
            if (currentNewLine != null) {
                drawRect(ld, currentNewLine, rd);

            }
            for (Line l : allLines) {
                drawRect(ld, l, rd);
            }
        }



        g.drawImage(bi, 0, 0, null);

    }

    private void drawLine(LineDrawer ld, Line l){
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }
    private void drawRect(LineDrawer ld, Line l, RectangleDrawer rd) {
        rd.drawRectangle(sc.r2s(l.getP1()), sc.r2s(l.getP2()), ld);
    }


    private ScreenPoint lastPosition = null;

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
        if (lastPosition != null) {
            ScreenPoint deltaScreen = new ScreenPoint(currentPosition.getX() - lastPosition.getX(),
                    currentPosition.getY() - lastPosition.getY());

            RealPoint deltaReal = sc.s2r(deltaScreen);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));

            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(),
                    deltaReal.getY() - zeroReal.getY());
            sc.setCornerX(sc.getCornerX() - vector.getX());
            sc.setCornerY(sc.getCornerY() - vector.getY());
            lastPosition = currentPosition;

        }

        if (currentNewLine != null){
            currentNewLine.setP2(sc.s2r(currentPosition));
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            toMerge = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        toMerge = false;
        if (e.getButton() == MouseEvent.BUTTON3) {
            lastPosition = new ScreenPoint(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1){
            currentNewLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())),
                                        sc.s2r(new ScreenPoint(e.getX(), e.getY())));

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            lastPosition = null;
        } else if (e.getButton() == MouseEvent.BUTTON1){
            allLines.add(currentNewLine);
            allRect.add(new Rectangle(currentNewLine.getP1(), currentNewLine.getP2()));

            currentNewLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks < 0 ? 1.1 : 0.9;
        for(int i = 0; i < Math.abs(clicks); i++){
            scale *= coef;
        }

        sc.setRealWidth(sc.getRealWidth() * scale);
        sc.setRealHeight(sc.getRealHeight() * scale);
        repaint();
    }
}
