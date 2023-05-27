import java.util.ArrayList;

/**
 * This class represents a set of stars or a "map of stars".
 *
 */
public class StarMap extends ArrayList<Point3D> {
    public StarMap() {super();}
    public StarMap(StarMap ot) {
        this();
        for (Point3D point3D : ot) {
            this.add(new Point3D(point3D));
        }
    }
    public void move(Point3D vec) {
        for (Point3D point3D : this) {
            point3D.move(vec);
        }
    }
    public void scale(Point3D cen, double rt) {
        for (Point3D point3D : this) {
            point3D.scale(cen, rt);
        }
    }
    public void rotate_deg(Point3D cen, double ang_deg) {
        for (Point3D point3D : this) {
            point3D.rotate2D_deg(cen, ang_deg);
        }
    }
    public void rotate_rad(Point3D cen, double ang_rad) {
        for (Point3D point3D : this) {
            point3D.rotate2D_rad(cen, ang_rad);
        }
    }
    /**
     * Translation first and only them rotation
     * @param cen - this point should be in the olready
     * @param ang_rad
     */
    public void transform(Point3D vec, Point3D cen, double rt, double ang_rad) {
            move(vec);
            scale(cen, rt);
            rotate_rad(cen, ang_rad);
    }

    /**
     * Performs a transformation (2D_ from p0,p1, to p2,p3)
     * assumein p0 & p1 ase with in this StarMap/
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     */
    public void transform(Point3D p0, Point3D p1, Point3D p2, Point3D p3) {
        Point3D vec = p0.vec(p2);
        double rt = p2.distance(p3) / p0.distance(p1);
        double a0 = p0.angle_rad(p1);
        double a2 = p2.angle_rad(p3);
        double da = a2-a0;
        transform(vec, p2, rt, da);
    }


    public void addRandomPoint(Point3D min, Point3D max) {
        Point3D p = Algo.randomPoint(min, max);
        this.add(p);
    }
    public void addRandomPoints(Point3D min, Point3D max, int num) {
        for(int i=0;i<num;i++) {
            addRandomPoint(min, max);
        }
    }
}
