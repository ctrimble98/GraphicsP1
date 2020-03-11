import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas2D extends JPanel {

    private Curves frame;
    private int xComp;
    private int yComp;

    public Canvas2D(Curves frame, int xComp, int yComp) {
        this.frame = frame;
        this.xComp = xComp;
        this.yComp = yComp;
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));

        switch(xComp + yComp) {
            case 1:
                add(new JLabel("X-Y"));
                break;
            case 2:
                add(new JLabel("Z-X"));
                break;
            case 3:
                add(new JLabel("Y-Z"));
                break;
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int[] point = new int[3];
                point[xComp] = e.getX();
                point[yComp] = e.getY();

                frame.addPoint(new Vector3(point[0], point[1], point[2]));
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.black);
        int r = 5;
        for (Vector3 point : frame.getPoints()) {
            double[] p = point.toArray();
            g2.fillOval((int)(p[xComp] - r/2.0), (int)(p[yComp] - r/2.0), r, r);
        }
        g2.setStroke(new BasicStroke(0.5f));
        for (Curve c: frame.getCurves()) {
            c.draw(xComp, yComp, g2);
        }
    }
}
