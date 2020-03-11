import org.ejml.simple.SimpleMatrix;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to generate the interpolated spline found at:
 * https://www.value-at-risk.net/cubic-spline-interpolation/
 * Wasn't useful in the end as wasn't parametric
 */
public class InterpolatedSpline extends Curve {

    private SimpleMatrix coeffs;

    public InterpolatedSpline(Color colour, List<Vector3> points) {
        super(colour, points, false);
        int n = points.size();
        int params = 4 * (n - 1);
        coeffs = new SimpleMatrix(params, params);

        fillBaseCoeffs(points, n, params);
        fillFirstDerivCoeffs(points, n, params);
        fillSecondDerivCoeffs(points, n, params);

        double x0 = points.get(0).getX();
        coeffs.set(params - 2, 0, 6*x0);
        coeffs.set(params - 2, 1, 2);

        double xn = points.get(n - 1).getX();
        coeffs.set(params - 1, params - 4, 6*xn);
        coeffs.set(params - 1, params - 3, 2);

        SimpleMatrix res = new SimpleMatrix(params, 1);
        res.set(0, 0, points.get(0).getY());
        for (int i = 1; i < n - 1; i++) {
            res.set(2*(i - 1) + 1, 0, points.get(i).getY());
            res.set(2*i, 0, points.get(i).getY());
        }
        res.set(params/2 - 1, 0, points.get(n - 1).getY());

        SimpleMatrix sln = coeffs.solve(res);

        curve = new ArrayList<Vector3>();
        fillCurve(sln, points);
    }

    private void fillBaseCoeffs(List<Vector3> points, int n, int  params) {
        double x0 = points.get(0).getX();
        coeffs.set(0, 0, Math.pow(x0, 3));
        coeffs.set(0, 1, Math.pow(x0, 2));
        coeffs.set(0, 2, x0);
        coeffs.set(0, 3, 1);

        for (int i = 1; i < n - 1; i++) {
            int j = 4*(i - 1);
            double x = points.get(i).getX();
            coeffs.set(2*i - 1, j, Math.pow(x, 3));
            coeffs.set(2*i - 1, j + 1, Math.pow(x, 2));
            coeffs.set(2*i - 1, j + 2, x);
            coeffs.set(2*i - 1, j + 3, 1);

            j += 4;
            coeffs.set(2*i, j, Math.pow(x, 3));
            coeffs.set(2*i, j + 1, Math.pow(x, 2));
            coeffs.set(2*i, j + 2, x);
            coeffs.set(2*i, j + 3, 1);
        }

        double xn = points.get(n - 1).getX();
        coeffs.set(2*(n - 1) - 1, params - 4, Math.pow(xn, 3));
        coeffs.set(2*(n - 1) - 1, params - 3, Math.pow(xn, 2));
        coeffs.set(2*(n - 1) - 1, params - 2, xn);
        coeffs.set(2*(n - 1) - 1, params - 1, 1);
    }

    private void fillFirstDerivCoeffs(List<Vector3> points, int n, int  params) {
        int offset = params / 2;
        for (int i = 1; i < n - 1; i++) {
            int j = 4 * (i - 1);
            int iInd = i - 1 + offset;

            double x = points.get(i).getX();
            coeffs.set(iInd, j, 3*Math.pow(x, 2));
            coeffs.set(iInd, j + 1, 2*x);
            coeffs.set(iInd, j + 2, 1);
            coeffs.set(iInd, j + 4, -3*Math.pow(x, 2));
            coeffs.set(iInd, j + 5, -2*x);
            coeffs.set(iInd, j + 6, -1);
        }
    }

    private void fillSecondDerivCoeffs(List<Vector3> points, int n, int  params) {

        int offset = params / 2 + n - 2;
        for (int i = 1; i < n - 1; i++) {
            int j = 4 * (i - 1);
            int iInd = i - 1 + offset;

            double x = points.get(i).getX();
            coeffs.set(iInd, j, 6*x);
            coeffs.set(iInd, j + 1, 2);
            coeffs.set(iInd, j + 4, -6*x);
            coeffs.set(iInd, j + 5, -2);
        }
    }

    private void fillCurve(SimpleMatrix sln, List<Vector3> points) {
        int n = points.size();
        for (int i = 0; i < n - 1; i++) {
            double x0 = points.get(i).getX();
            double x1 = points.get(i + 1).getX();
            for (double u = x0; u < x1; u += (x1 - x0)/1000) {
                curve.add(new Vector3(u, sln.get(4*i, 0)* Math.pow(u, 3) + sln.get(4*i + 1, 0)* Math.pow(u, 2) + sln.get(4*i + 2, 0) * u + sln.get(4*i + 3, 0), 0));
            }
            for (double u = x0; u > x1; u += (x1 - x0)/1000) {
                curve.add(new Vector3(u, sln.get(4*i, 0)* Math.pow(u, 3) + sln.get(4*i + 1, 0)* Math.pow(u, 2) + sln.get(4*i + 2, 0) * u + sln.get(4*i + 3, 0), 0));
            }
        }
    }
}
