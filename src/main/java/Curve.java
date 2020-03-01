import java.util.ArrayList;
import java.util.List;

public abstract class Curve {

    protected static final double stepsize = 0.0001;

    public static List<int[]> pascalsTriangle = new ArrayList<int[]>();
    protected List<Vector3> points;
    protected List<Vector3> curve;

    public List<Vector3> getPoints() {
        return points;
    }

    public void setPoints(List<Vector3> points) {
        this.points = points;
    }

    public List<Vector3> getCurve() {
        return curve;
    }

    public Curve(List<Vector3> points) {
        this.points = points;
        int n = points.size();
        if (pascalsTriangle.size() < n) {
            fillPascals(n);
        }
    }

    public void fillPascals(int n) {

        if (pascalsTriangle.size() == 0) {
            pascalsTriangle.add(new int[] {1});
        }
        if (pascalsTriangle.size() < n) {
            for (int i = pascalsTriangle.size(); i < n; i++) {
                pascalsTriangle.add(new int[i + 1]);
                pascalsTriangle.get(i)[0] = 1;
                for (int j = 1; j < i; j++) {
                    pascalsTriangle.get(i)[j] = pascalsTriangle.get(i - 1)[j - 1] + pascalsTriangle.get(i - 1)[j];
                }
                pascalsTriangle.get(i)[i] = 1;
            }
        }
    }
}
