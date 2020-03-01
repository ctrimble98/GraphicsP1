import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Curves extends PApplet {

    private ArrayList<Vector3> points;
    private BezierCurve b;
    private BezierSpline s;

    public static void main(String[] args) {
        PApplet.main("Curves");
    }

    @Override
    public void settings() {
        size(600, 600);
    }

    @Override
    public void setup() {
        fill(120,50,240);
        points = new ArrayList<Vector3>();
//        points.add(new Vector3(0, 0, 0));
//        points.add(new Vector3(2, 0, 0));
//        points.add(new Vector3(0, 2, 0));
//        points.add(new Vector3(2, 2, 0));
//        points.add(new Vector3(-2, -2, 0));
//        points.add(new Vector3(-2, 2, 0));
    }

    @Override
    public void draw(){

        background(255);
        line(0, height/2, width, height/2);
        line(width/2, 0, width/2, height);


        pushMatrix();
        translate(width/2, height/2);
        ellipseMode(CENTER);
        for (Vector3 point: points) {
            ellipse((float)(point.getX()), (float)(point.getY()), 5, 5);
        }

        //TODO PRoper solution
        if (points.size() > 1) {
            for (Vector3 v : b.getCurve()) {
                point((float) (v.getX()), (float) (v.getY()));
            }

            for (Vector3 v : s.getCurve()) {
                point((float) (v.getX()), (float) (v.getY()));
            }
        }
        popMatrix();
    }

    public void mousePressed() {
        addPoint(new Vector3(mouseX - width/2, mouseY - height/2, 0));
    }

    private void addPoint(Vector3 p) {
        points.add(p);

        if (points.size() > 1) {
            b = new BezierCurve(points);
            s = new BezierSpline(points);
        }
    }
}