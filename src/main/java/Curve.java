import java.util.ArrayList;

public abstract class Curve {

    public static ArrayList<int[]> pascalsTriangle = new ArrayList<int[]>();
    protected ArrayList<Vector3> points;
    protected ArrayList<Vector3> curve;

    public ArrayList<Vector3> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Vector3> points) {
        this.points = points;
    }

    public ArrayList<Vector3> getCurve() {
        return curve;
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
