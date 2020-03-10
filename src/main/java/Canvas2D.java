import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas2D extends JPanel {

    private ArrayList<Vector3> points;
    private ArrayList<Curve> curves;
    private int xComp;
    private int yComp;

    public Canvas2D(ArrayList<Vector3> points, ArrayList<Curve> curves, int xComp, int yComp) {
        this.points = points;
        this.curves = curves;
        this.xComp = xComp;
        this.yComp = yComp;
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.black);
        int r = 5;
        for (Vector3 point : points) {
            double[] p = point.toArray();
            g2.fillOval((int)(p[xComp] - r/2.0), (int)(p[yComp] - r/2.0), r, r);
        }
        g2.setStroke(new BasicStroke(0.5f));
        for (Curve c: curves) {
            c.draw(xComp, yComp, g2);
        }
    }
}
