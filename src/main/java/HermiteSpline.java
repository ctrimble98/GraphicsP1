import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class HermiteSpline extends Curve {

    private List<Vector3> tangents;

    public HermiteSpline(PApplet canvas, int colour, List<Vector3> points) {
        super(canvas, colour, points, false);
        tangents = new ArrayList<Vector3>();

        fillTangents();
        fillCurve();
//        curve.forEach(System.out::println);
    }

    private void fillTangents() {
        tangents.add(points.get(1).sub(points.get(0)));

        for (int i = 1; i < points.size() - 1; i++) {
            Vector3 diff0 = points.get(i).sub(points.get(i-1));
            Vector3 diff1 = points.get(i+1).sub(points.get(i));
            tangents.add(diff1.add(diff0).mult(0.5));
        }

        tangents.add(points.get(points.size() - 1).sub(points.get(points.size() - 2)));
    }

    private void fillCurve() {
        curve = new ArrayList<Vector3>();
        for (int i = 0; i < points.size() - 1; i++) {
            for (double u = 0; u < 1; u += stepsize) {
                Vector3 h00 = points.get(i).mult(2*Math.pow(u, 3) - 3*Math.pow(u, 2) + 1);
                Vector3 h10 = tangents.get(i).mult(Math.pow(u, 3) - 2*Math.pow(u, 2) + u);
                Vector3 h01 = points.get(i+1).mult(-2*Math.pow(u, 3) + 3*Math.pow(u, 2));
                Vector3 h11 = tangents.get(i+1).mult(Math.pow(u, 3) - Math.pow(u, 2));
                curve.add(h00.add(h10).add(h01).add(h11));
            }
        }
    }
}
