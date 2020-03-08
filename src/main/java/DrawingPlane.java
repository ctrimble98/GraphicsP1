import processing.core.PApplet;

import java.util.List;

public class DrawingPlane {

    private float xOffset;
    private float yOffset;
    private int xIndex;
    private int yIndex;
    private PApplet canvas;

    public DrawingPlane(float xOffset, float yOffset, Plane plane) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        switch (plane) {
            case XY:
                xIndex = 0;
                yIndex = 1;
                break;
            case XZ:
                xIndex = 2;
                yIndex = 0;
                break;
            case YZ:
                xIndex = 1;
                yIndex = 2;
                break;
        }
    }

    public void drawCurve(Curve curve) {
//        List<Vector3>
//        if (points.size() == 0) {
//            return;
//        }
//        double[] previousPoint = points.get(0).toArray();
//        for (int i = 1; i < points.size(); i++) {
//            double[] point = points.get(i).toArray();
//            canvas.line((float) previousPoint[xIndex], (float)previousPoint[yIndex], (float) point[xIndex], (float) point[yIndex]);
//        }
    }
}
