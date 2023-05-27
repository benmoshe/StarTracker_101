import java.awt.*;
import java.util.Collections;
import java.util.HashMap;

/**
 * This is NOT a Junit class - as it tests GUI components which
 * should not be tested using Junit.
 * <p>
 * The Code uses the STDDraw class:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 * @author boaz.benmoshe
 */
public class Main {
    //private static Simulator _ar;
    public static final int CHANGE_DRONE = 1, ADD_BS =0, REMOVE_BS=2;
    public static int _mode = CHANGE_DRONE;

    public static void main(String[] a) {
        init();
        int size = 200;
        Point3D min = new Point3D(70,30,0);
        Point3D max = new Point3D(400,600,0);
        StarMap st0 = new StarMap();
        st0.addRandomPoints(min, max,size);
        StarMap st1 = new StarMap(st0);
        Collections.shuffle(st0);  // to avoid any ordered "tricks"
        Collections.shuffle(st1);  // to avoid any ordered "tricks"
        while(st0.size()>size/2) {st0.remove(0);} // remove half the size of the common points;
        while(st1.size()>size/2) {st1.remove(0);} // remove half the size of the common points;

        ///////// full 2D transformation of st1: move, rotate, scale ////////
        st1.move(max);
        st1.rotate_rad(max,-0.5);
        st1.scale(max, 0.8);

        StdDraw.setPenColor(Color.BLUE); drawStarMap(st0,4);
        StdDraw.setPenColor(Color.RED);  drawStarMap(st1,4);

        StarMap st01 = Algo.findTransform(st0, st1); // this is the Ransac algo.
        StdDraw.setPenColor(Color.GREEN);
        if(st01!=null) {
            drawStarMap(st01,6);
        }
        StdDraw.show();
    }

    public static void init() {
        StdDraw.setScale(0, 1000);
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
    }
    public static void drawStarMap(StarMap st, int s) {
        for (Point3D p : st) {
            int d = (int)(p.z()*6);
            StdDraw.circle(p.x(), p.y(), s+d);
        }
    }
}
