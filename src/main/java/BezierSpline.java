import java.util.ArrayList;
import java.util.List;

public class BezierSpline extends Curve {

    private List<BezierCurve> curves;

    public BezierSpline(List<Vector3> points) {
        super(points);
        curves = new ArrayList<BezierCurve>();
        int n = points.size();
        int extraPoints = (n - 1) % 3;
        switch (extraPoints) {
            case 0:
                if (n >= 4) {
                    getCubicCurves(0, n);
                }
                break;
            case 1:
                if (n >= 8) {
                    getCubicCurves(0, n - 4);
                }
                if (n >= 5) {
                    curves.add(new BezierCurve(points.subList(n - 5, n - 2)));
                }
                if (n >= 3) {
                    curves.add(new BezierCurve(points.subList(n - 3, n)));
                }
                break;
            case 2:
                if (n >= 6) {
                    getCubicCurves(0, n - 2);
                }
                if (n >= 3) {
                    curves.add(new BezierCurve(points.subList(n - 3, n)));
                }
                break;
        }
        fillCurve();
    }

    private void getCubicCurves(int startIndex, int endIndex) {

        for (int i = startIndex; i < endIndex - 1; i += 3) {
            curves.add(new BezierCurve(points.subList(i, i + 4)));
        }
    }

    private void fillCurve() {

        curve = new ArrayList<Vector3>();

        for (BezierCurve cubic: curves) {
            curve.addAll(cubic.getCurve());
        }
    }
}
