import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to produce a Cubic Hermite Spline between a set of points
 */
public class HermiteSpline extends Curve {

    private List<Vector3> tangents;

    public HermiteSpline(/*PApplet canvas, */Color colour, List<Vector3> points) {
        super(/*canvas, */colour, points, false);
        tangents = new ArrayList<Vector3>();

        fillTangents();
        fillCurve();
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

    public List<Vector3> findIntersection(StraightLine line) {

        List<Vector3> intersections = new ArrayList<Vector3>();

        for (int i = 0; i < points.size() - 1; i++) {
            Vector3 u = line.getPoint();
            Vector3 v = line.getDerivative();

            // Columns of rotation matrix
            double vMag = v.getMag();
            Vector3 R1 = new Vector3(v.getX() / vMag, v.getY(), 0);
            Vector3 R2 = new Vector3(-1*v.getY() / vMag, v.getX() / vMag, 0);

            double k00 = points.get(i).getX() * R2.getX() + points.get(i).getY() * R2.getY();
            double k10 = tangents.get(i).getX() * R2.getX() + tangents.get(i).getY() * R2.getY();
            double k01 = points.get(i + 1).getX() * R2.getX() + points.get(i + 1).getY() * R2.getY();
            double k11 = tangents.get(i + 1).getX() * R2.getX() + tangents.get(i + 1).getY() * R2.getY();
            double uR = u.getX() * R2.getX() + u.getY() * R2.getY();

            double a = 2 * k00 + k10 - 2 * k01 + k11;
            double b = -3 * k00 - 2 * k10 + 3 * k01 - k11;
            double c = k10;
            double d = k00 - uR;

            try {
                Cubic cubicSolver = new Cubic();
                cubicSolver.solve(a, b, c, d);
                List<Double> slnsForT = new ArrayList<Double>();
                slnsForT.add(cubicSolver.x1);
                if (cubicSolver.nRoots == 3) {
                    slnsForT.add(cubicSolver.x2);
                    slnsForT.add(cubicSolver.x3);
                }

                for (double t: slnsForT) {
                    if (t >= 0 && t <= 1) {
                        Vector3 h00 = points.get(i).mult(2*Math.pow(t, 3) - 3*Math.pow(t, 2) + 1);
                        Vector3 h10 = tangents.get(i).mult(Math.pow(t, 3) - 2*Math.pow(t, 2) + t);
                        Vector3 h01 = points.get(i+1).mult(-2*Math.pow(t, 3) + 3*Math.pow(t, 2));
                        Vector3 h11 = tangents.get(i+1).mult(Math.pow(t, 3) - Math.pow(t, 2));
                        intersections.add(h00.add(h10).add(h01).add(h11));
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return intersections;
    }
}
