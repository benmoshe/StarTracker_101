import java.util.HashMap;
import java.util.Random;
public class Algo {
    private static final long RAND_SEED = 31, MIN_ID_SIZE = 10, MIN_DIST=50;
    private static final Random _rand = new Random(RAND_SEED);

    /**
     * This is the main RANSAC Pattern matching algorithm:
     * 0) Input: two StarMaps (St0, St1)
     * 1) Construct a HashMap (aka HashTable) from the first StarMap HM(St0) = HM0.
     * 2) While(no transformation found) {
     * 3.1)  Pick an ordered pair of points from the first StarMap
     * 3.2)  Pick an ordered pair of points from the second StarMap
     * 3.3)  Compute the transformation (TF) using the two pair of points.
     * 3.4)  Transform the second StraMap by the TF(St1) =  ST2
     * 3.5)  Count how many (c) overlap stars are in the HM0 and ST2 - using "geometric hashing".
     * 3.6)  if (c>10) return St2
     * }
     * @param st0 the large (aka the DB) StarMao
     * @param st1 the smaller (aka the frame) StarMap
     * @return the back transformed StarMap from St12St0.
     */
    public static StarMap findTransform(StarMap st0, StarMap st1) {
        int size = st1.size();
        HashMap<String, Point3D> hm0 = new HashMap<String, Point3D>();
        for (Point3D point3D : st0) {
            hm0.put(point3D.hash(), point3D);
        }
        StarMap ans = null;
        int cc=0;
        while(ans==null && cc < size*size*8) {
            Point3D p0 = randomPoint(st0);
            Point3D p1 =  randomPoint(st0);
            Point3D p2 = randomPoint(st1);
            Point3D p3 =  randomPoint(st1);
            if(p0.distance(p1)>MIN_DIST && p2.distance(p3)>MIN_DIST) {
                StarMap st2 = new StarMap(st1); // very inefficient!!
                st2.transform(p2, p3, p0, p1);  // this is the transformation "back".
                int same = countSame(hm0, st2); // hash based testing for
                if (same >= MIN_ID_SIZE && same>size/10) {
                    ans = st2;
                    System.out.println("Transformation found, size: "+same);
                }
            }
            cc++;
        }
        return  ans;
    }

    /**
     * This function gets both a HashMap and a StarMap, and computes how many stars are overlapping (up to an integer),
     * Using simple String based hashing.
     * @param hm
     * @param st1
     * @return
     */
    public static int countSame(HashMap<String, Point3D> hm, StarMap st1) {
        int count = 0;
        Point3D vecUp = new Point3D(0,0,1);
        for (Point3D p : st1) {
            Point3D a = hm.get(p.hash());
            if(a!=null) {p.move(vecUp);count++;}
        }
        return count;
    }

    /**
     *
     * @param min the min x,y point of the rectangle
     * @param max the max x,y point of the rectangle
     * @return a random point within the [min, max] Rectangle - axis parallel
     */
    public static Point3D randomPoint(Point3D min, Point3D max) {
        double dx = max.x()-min.x();
        double dy = max.y()-min.y();
        double dz = max.z()-min.z();
        dx *= _rand.nextDouble();
        dy *= _rand.nextDouble();
        dz *= _rand.nextDouble();
        return new Point3D(min.x()+dx, min.y()+dy, min.z()+dz);
    }

    /**
     *
     * @param st the starmap.
     * @return a random point within this StarMap
     */
    public static Point3D randomPoint(StarMap st) {
        int size0 = st.size();
        int ind0 = (int)(_rand.nextDouble()*size0);
        return st.get(ind0);
    }
}
