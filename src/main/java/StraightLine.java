import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StraightLine extends Curve {

    private Vector3 point;
    private Vector3 derivative;
    private List<Vector3> intersections;

    public Vector3 getPoint() {
        return point;
    }

    public Vector3 getDerivative() {
        return derivative;
    }

    public StraightLine(Color colour, Vector3 point, Vector3 derivative) {
        super(colour, new ArrayList<Vector3>(), false);
        this.point = point;
        this.derivative = derivative.normalise();
    }

    @Override
    public void draw(int xIndex, int yIndex, Graphics2D g2) {
        double[] p1 = point.add(derivative.mult(1000)).toArray();
        double[] p2 = point.add(derivative.mult(-1000)).toArray();
        g2.setColor(new Color(6, 89, 10));
        g2.drawLine((int)p1[xIndex], (int)p1[yIndex], (int)p2[xIndex], (int)p2[yIndex]);
        if (intersections != null) {
            for (Vector3 intersection: intersections) {
                double d = 5;
                g2.drawOval((int)(intersection.toArray()[xIndex] - (d/2)), (int)(intersection.toArray()[yIndex]  - (d/2)), (int)d, (int)d);
            }
        }
    }

    public void addIntersections(List<Vector3> intersections) {
        this.intersections = intersections;
    }
}
