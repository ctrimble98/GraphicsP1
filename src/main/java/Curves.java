import processing.core.PApplet;

import java.util.ArrayList;

public class Curves extends PApplet {

    private BezierCurve b;

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
        ArrayList<Vector3> points = new ArrayList<Vector3>();
        points.add(new Vector3(0, 0, 0));
        points.add(new Vector3(2, 0, 0));
        points.add(new Vector3(0, 2, 0));
        points.add(new Vector3(2, 2, 0));
        points.add(new Vector3(-2, -2, 0));
        points.add(new Vector3(-2, 2, 0));
        b = new BezierCurve(points);
    }

    @Override
    public void draw(){

        line(0, height/2, width, height/2);
        line(width/2, 0, width/2, height);


        pushMatrix();
        translate(width/2, height/2);
        ellipseMode(CENTER);
        for (int i = 0; i < b.getPoints().size(); i++) {
            ellipse((float)(100*b.getPoints().get(i).getX()), (float)(100*b.getPoints().get(i).getY()), 5, 5);
        }
        for (Vector3 v: b.getCurve()) {
            point((float)(100*v.getX()), (float)(100*v.getY()));
        }
        popMatrix();
    }
}