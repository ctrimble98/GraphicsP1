public class Vector3 {

    private double x, y, z;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 mult(double a) {
        return new Vector3(x * a, y * a, z * a);
    }

    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    public Vector3 sub(Vector3 v) {
        return new Vector3(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    public double dot(Vector3 v) {
        return x*v.getX() + y*v.getY() + z*v.getZ();
    }

    public Vector3 cross(Vector3 v){
        return new Vector3(y*v.getZ() - z*v.getY(), z*v.getX() - x*v.getZ(), x*v.getY() - y*v.getX());
    }

    public double getMag() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3 normalise() {
        double mag = getMag();
        return new Vector3(x/mag, y/mag, z/mag);
    }

    public Vector3 average(Vector3 v) {
        return new Vector3((x + v.getX())/2, (y + v.getY())/2, (z + v.getZ())/2);
    }

    public Vector3 copy() {
        return new Vector3(x, y, z);
    }

    @Override
    public String toString() {
        return new String("[" + x + ", " + y + ", "+ z + "]");
    }

    public double[] toArray() {
        return new double[]{x, y, z};
    }
}
