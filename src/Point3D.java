
/**
 * This class represents a 2D point in the plane, with Z coordinate as its radius (or accuracy)/

 * @author boaz.benmoshe
 */


public class Point3D {
    //public static final double EPS1 = 0.001, EPS2 = Math.pow(EPS1,2), EPS=EPS2;
    public static final Point3D ORIGIN = new Point3D(0, 0, 0);
    public static final double EPS = 0.001;
    private double _x, _y, _z;

    public Point3D(double x, double y, double z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public Point3D(Point3D p) {
        this(p.x(), p.y(), p.z());
    }

    public Point3D(String s) {
        try {
            String[] a = s.split(",");
            _x = Double.parseDouble(a[0]);
            _y = Double.parseDouble(a[1]);
            _z = Double.parseDouble(a[2]);
        } catch (IllegalArgumentException e) {
            System.err.println("ERR: got wrong format string for Point2D init, got:" + s + "  should be of format: x,y");
            throw (e);
        }
    }


    public double distance() {
        return this.distance(ORIGIN);
    }

    public double distance(Point3D p2) {
        double dx = this.x() - p2.x();
        double dy = this.y() - p2.y();
        double dz = this.z() - p2.z();
        double t = (dx * dx + dy * dy + dz * dz);
        return Math.sqrt(t);
    }
    @Override
    public boolean equals(Object p) {
        if (p == null || !(p instanceof Point3D)) {
            return false;
        }
        Point3D p2 = (Point3D) p;
        return ((_x == p2._x) && (_y == p2._y) && (_z == p2._z));
    }

    public boolean close2equals(Point3D p2, double eps) {
        return (this.distance(p2) < eps);
    }

    public boolean close2equals(Point3D p2) {
        return close2equals(p2, EPS);
    }

    /**
     * This method returns the vector between this point and the target point. The vector is represented as a Point2D.
     *
     * @param target
     * @return
     */
    public Point3D vector(Point3D target) {
        double dx = target.x() - this.x();
        double dy = target.y() - this.y();
        double dz = target.z() - this.z();
        return new Point3D(dx, dy, dz);
    }

    public Point3D norm() {
        double d = this.distance();
        return new Point3D(_x / d, _y / d, _z / d);
    }

    public void move(Point3D vec) {
        this._x += vec.x();
        this._y += vec.y();
        this._z += vec.z();
    }

    /////////////////////// You should implement the methods below ///////////////////////////
    public void scale(Point3D cen, double ratio) {
        double dx = x() - cen.x();
        double dy = y() - cen.y();
        double dz = z() - cen.z();
        dx *= ratio;
        dy *= ratio;
        dz *= ratio;
        _x = dx + cen.x();
        _y = dy + cen.y();
        _z = dz + cen.z();
        /////////////////////////////////////
    }
    public void scale(double ratio) {
        this.scale(ORIGIN, ratio);
    }
    public void rotate2D_deg(Point3D cen, double angleDegrees) {
        double angRad = Math.toRadians(angleDegrees);
        double dx = x() - cen.x();
        double dy = y() - cen.y();
        double ang = Math.atan2(dy, dx);
        double d = Math.sqrt(dx * dx + dy * dy);
        ang += angRad;
        dx = d * Math.cos(ang);
        dy = d * Math.sin(ang);
        _x = cen.x() + dx;
        _y = cen.y() + dy;
    }
    public void rotate2D_rad(Point3D cen, double angle_rad) {
        double ang = angle_rad(cen);
        double d = distance(cen);
        ang += angle_rad;
        _x = cen.x() + d * Math.cos(ang);
        _y = cen.y() + d * Math.sin(ang);
    }
    public double angle_rad(Point3D p) {
        double dx = x() - p.x();
        double dy = y() - p.y();
        return Math.atan2(dy, dx);
    }
    public Point3D vec(Point3D p) {
        Point3D a = new Point3D(p.x() - x(), p.y() - y(), p.z() - z());
        return a;
    }

    public double x() {
        return _x;
    }

    public double y() {
        return _y;
    }

    public double z() {
        return _z;
    }

    public int ix() {
        return (int) (_x+0.5);
    }

    public int iy() {
        return (int) (_y+0.5);
    }

    public int iz() {
        return (int) (_z+0.5);
    }

    public String toString() {
        return _x + "," + _y + "," + _z;
    }
    public String toStringI() {
        return ix() + "," + iy() + "," + iz();
    }
    public String hash() {
        return ix() + "," + iy();
    }
}
