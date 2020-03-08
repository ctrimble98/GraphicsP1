import processing.core.PApplet;

import java.util.ArrayList;

public class Curves extends PApplet {

    private ArrayList<Vector3> points;
    private BezierCurve b;
    private BezierSpline s;
    private InterpolatedSpline iS;
    private HermiteSpline h;

    public static void main(String[] args) {
        PApplet.main("Curves");
    }

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        points = new ArrayList<Vector3>();
    }

    @Override
    public void draw(){

        background(220);
        stroke(0);
        strokeWeight(5);
        line(0, height/2f, width, height/2f);
        line(width/2f, 0, width/2f, height);

        strokeWeight(1);
        line(0, height/4f, width, height/4f);
        line(width/4f, 0, width/4f, height);
        line(0, height*3/4f, width/2f, height*3/4f);
        line(width*3/4f, 0, width*3/4f, height/2f);


        pushMatrix();
        translate(width/2f, height/2f);
        ellipseMode(CENTER);
        for (Vector3 point: points) {
            ellipse((float)(point.getX()), (float)(point.getY()), 5, 5);
        }

        if (points.size() > 1) {
            b.draw(0, 1);
            s.draw(0, 1);
            iS.draw(0, 1);
            h.draw(0, 1);
        }

        popMatrix();
    }

    public void mousePressed() {
        addPoint(new Vector3(mouseX - width/2, mouseY - height/2, 0));
    }

    private void addPoint(Vector3 p) {
        points.add(p);

        if (points.size() > 1) {
            b = new BezierCurve(this, color(255, 0, 0), points);
            s = new BezierSpline(this, color(0, 255, 0), points);
            iS = new InterpolatedSpline(this, color(0, 0, 255), points);
            h = new HermiteSpline(this, color(255, 0, 255), points);
        }
    }
}