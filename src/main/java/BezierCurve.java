import java.util.ArrayList;

public class BezierCurve extends Curve {

    public BezierCurve(ArrayList<Vector3> points) {
        this.points = points;
        int n = points.size();
        if (pascalsTriangle.size() < n) {
            fillPascals(n);
        }
        fillCurve();
    }

    private void fillCurve() {

        curve = new ArrayList<Vector3>();
        int n = points.size();
        for (double u = 0; u <= 1; u += 0.005) {
            curve.add(getPointOnCurve(u, n));
        }

        System.out.println(getPointOnCurve(0, n));
        System.out.println(getPointOnCurve(1, n));
        for (int i: pascalsTriangle.get(n - 1)) {
            System.out.print(i+ " ");
        }
    }

    private Vector3 getPointOnCurve(double u, int n) {
        Vector3 result = new Vector3(0, 0, 0);
        for (int i = 0; i < n; i++) {
            Vector3 partial = points.get(i).copy();
            partial.mult(pascalsTriangle.get(n - 1)[i] * Math.pow(u, i) * Math.pow((1 - u), n - 1 - i));
            result.add(partial);
        }
        return result;
    }
}
