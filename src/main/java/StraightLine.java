import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StraightLine extends Curve {

    private Vector3 point;
    private Vector3 derivative;

    public StraightLine(Color colour, Vector3 point, Vector3 derivative) {
        super(colour, new ArrayList(), false);
        this.point = point;
        this.derivative = derivative.normalise();
    }

    @Override
    public void draw(int xIndex, int yIndex, Graphics2D g2) {
        Vector3 p1 = point.add(derivative.mult(1000));
        Vector3 p2 = point.add(derivative.mult(-1000));
        g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    }
}
