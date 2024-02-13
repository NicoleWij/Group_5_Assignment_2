package main.java.computations;

import main.java.utils.Utils;
import main.java.utils.Utils.Coordinate;
import main.java.utils.Utils.Parameters;

public final class CMV {
    /**
     * Private constructor to prevent instantiation.
     *
     * @throws UnsupportedOperationException if an attempt is made to
     *                                       instantiate it.
     */
    private CMV() {
        throw new UnsupportedOperationException("This is a utility class and "
                + "cannot be instantiated");
    }

    /**
     * Element #0 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Simon Hocker
     * @date 28.01.2024
     * Description: Checks if any 2 consecutive points have a distance greater
     * than given length
     */
    public static boolean checkCMV0(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {

        if (xCoordinates.coords().length < 2 || xCoordinates.coords().length != yCoordinates.coords().length) {
            return false;
        }

        double length = parameters.length1();
        double distanceX = 0.0;
        double distanceY = 0.0;
        double distance = 0.0;

        for (int i = 0; i < xCoordinates.coords().length - 1; i++) {
            distanceX = Math.abs(xCoordinates.coords()[i] - xCoordinates.coords()[i + 1]);
            distanceY = Math.abs(yCoordinates.coords()[i] - yCoordinates.coords()[i + 1]);
            distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
            if (distance > length) {
                return true;
            }
        }
        return false;
    }

    /**
     * Element #1 of the Conditions Met Vector.
     * Description: This method checks if there exists at least one set of three
     * consecutive data points that cannot
     * all be contained within or on a circle of radius RADIUS1.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Nicole Wijkman
     * @date 30-01-2024
     */
    // Inside the CMV class
    public static boolean checkCMV1(final Coordinate xCoordinates, final Coordinate yCoordinates,
                                    final Parameters parameters) {
        double radius1 = parameters.radius1();
        double[] xVals = xCoordinates.coords();
        double[] yVals = yCoordinates.coords();

        if (radius1 < 0) {
            return false;
        }

        for (int i = 0; i < xVals.length - 2; i++) {
            double a = Utils.distance(xVals[i], yVals[i], xVals[i + 1], yVals[i + 1]);
            double b = Utils.distance(xVals[i + 1], yVals[i + 1], xVals[i + 2], yVals[i + 2]);
            double c = Utils.distance(xVals[i], yVals[i], xVals[i + 2], yVals[i + 2]);

            double circumcircleRadius = Utils.calculateCircumcircleRadius(a, b, c);

            if (circumcircleRadius > radius1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Element #2 of the Conditions Met Vector.
     * There exists at least one set of three consecutive data points which form
     * an angle such that:
     * angle < (PI - epsilon)
     * or angle > (PI + epsilon)
     * The second of the three consecutive points is always the vertex of the
     * angle. If either the first point or the last point (or both) coincides
     * with the vertex, the angle is undefined and the LIC is not satisfied by
     * those three points.
     * (0 <= epsilon <= PI)
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     */
    public static boolean checkCMV2(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {
        double epsilon = parameters.epsilon();
        double[] x = xCoordinates.coords();
        double[] y = yCoordinates.coords();
        assert epsilon >= 0 && epsilon <= Utils.PI : "Epsilon must be between "
                + "0 and PI";
        assert x.length == y.length : "x and y arrays must be of equal length";
        assert x.length >= 3 : "x and y arrays must have at least 3 elements";
        for (int i = 0; i < x.length - 2; i++) {
            double angle = Utils.calculateAngle(x[i], y[i], x[i + 1], y[i + 1],
                    x[i + 2], y[i + 2]);
            if (angle < Utils.PI - epsilon || angle > Utils.PI + epsilon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Element #3 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Maja
     * @date 30.01.2024
     * Description: This method checks wether there exists at least one set of
     * three consecutive data points that
     * are the vertices of a triangle with area greater than AREA1
     */
    public static boolean checkCMV3(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {
        double area1 = parameters.area1();
        double[] xVals = xCoordinates.coords();
        double[] yVals = yCoordinates.coords();

        if (area1 < 0) {
            return false;
        }

        for (int i = 0; i < xVals.length - 2; i++) {
            double x1 = xVals[i];
            double y1 = yVals[i];
            double x2 = xVals[i + 1];
            double y2 = yVals[i + 1];
            double x3 = xVals[i + 2];
            double y3 = yVals[i + 2];

            double area = Utils.calcArea(x1, y1, x2, y2, x3, y3);
            if (area > area1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Element #4 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author serhan
     * @date 29.01.2023
     * Description: This method checks whether there exists at least one set
     * of Q PTS consecutive data points
     * that lie in more than QUADS quadrants.
     */
    public static boolean checkCMV4(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {
        int qPts = parameters.qPts();
        int quads = parameters.quads();
        double[] xVals = xCoordinates.coords();
        double[] yVals = yCoordinates.coords();

        if (qPts < 2 || qPts > xVals.length || quads < 1 || quads > 3) {
            return false;
        }

        for (int i = 0; i < xVals.length - qPts + 1; i++) {
            int[] quadCount = new int[4];
            for (int j = i; j < i + qPts; j++) {
                double x = xVals[j];
                double y = yVals[j];


                if (x >= 0 && y >= 0) { // quadrant 1
                    quadCount[0]++;
                } else if (x < 0 && y >= 0) { // quadrant 2
                    quadCount[1]++;
                } else if (x <= 0 && y < 0) { // quadrant 3
                    quadCount[2]++;
                } else { // quadrant 4
                    quadCount[3]++;
                }
            }
            int counter = 0;
            for (int j = 0; j < 4; j++) {
                if (quadCount[j] > 0) {
                    counter++;
                }
            }
            if (counter > quads) {
                return true;
            }
        }
        return false;
    }

    /**
     * Element #5 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Simon Hocker
     * @date 28.01.2024
     * Description: There exists at least one set of two consecutive data
     * points, (X[i],Y[i]) and (X[j],Y[j]), such
     * that X[j] - X[i] < 0. (where i = j-1)
     */
    public static boolean checkCMV5(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {

        if (xCoordinates.coords().length < 2 || xCoordinates.coords().length != yCoordinates.coords().length) {
            return false;
        }


        double diffX = 0.0;
        for (int i = 0; i < xCoordinates.coords().length - 1; i++) {
            diffX = xCoordinates.coords()[i + 1] - xCoordinates.coords()[i];
            if (diffX < 0.0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Element #6 of the Conditions Met Vector.
     * Description: Verifies if, within any set of N_PTS consecutive data points, at
     * least one point is farther than DIST from
     * the line connecting the first and last points of the set. If the first and
     * last points are the same, it checks if any
     * point is farther than DIST from this coincident point. Applicable only when
     * NUMPOINTS ≥ 3.
     * <p>
     * Constraints: 3 ≤ N_PTS ≤ NUMPOINTS, DIST ≥ 0
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Nicole Wijkman
     * @date 30-01-2024
     */
    public static boolean checkCMV6(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {
        int nPts = parameters.nPts();
        double dist = parameters.dist();
        double[] xVals = xCoordinates.coords();
        double[] yVals = yCoordinates.coords();

        // xVals.length represents the number of points NUMPOINTS
        if (nPts < 3 || nPts > xVals.length || xVals.length < 3 || dist < 0) {
            return false;
        }

        for (int i = 0; i <= xVals.length - nPts; i++) {
            double x1 = xVals[i];
            double y1 = yVals[i];
            double x2 = xVals[i + nPts - 1];
            double y2 = yVals[i + nPts - 1];

            // If the first and last points in the set are identical, check distance of each
            // intermediate point to coincident point
            if (x1 == x2 && y1 == y2) { // First and last points are identical
                for (int j = i + 1; j < i + nPts - 1; j++) {
                    double distance = Math.sqrt(Math.pow(xVals[j] - x1, 2) + Math.pow(yVals[j] - y1, 2));
                    if (distance > dist) {
                        return true;
                    }
                }
                // Otherwise, check the distance of each intermediate point to the line formed
                // by first and last points
            } else { // First and last points are different
                double lineLength = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
                for (int j = i + 1; j < i + nPts - 1; j++) {
                    double distance = Math.abs((y2 - y1) * xVals[j] - (x2 - x1) * yVals[j] + x2 * y1 - y2 * x1)
                            / lineLength;
                    if (distance > dist) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Element #7 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Martin
     * @date 01.02.2024
     * Description: There exists at least one set of two data points, separated by
     * exactly KPTS consecutive intervening points, that are a distance greater than
     * the length, LENGTH1, apart. The condition is not met when NUMPOINTS < 3.
     */
    public static boolean checkCMV7(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {

        int numPoints = xCoordinates.coords().length;
        if (numPoints < 3) {
            return false;
        }

        int kPts = parameters.kPts();
        double length1 = parameters.length1();

        if (kPts < 1 || kPts > numPoints - 2) {
            return false;
        }

        for (int i = 0; i < numPoints - kPts - 1; i++) {
            int j = i + kPts + 1;
            double distance = Utils.distance(xCoordinates.coords()[i], yCoordinates.coords()[i],
                    xCoordinates.coords()[j], yCoordinates.coords()[j]);
            if (distance > length1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Element #8 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Maja
     * @date 30.01.2024
     * Description: There exists at least one set of three data points separated by
     * exactly A PTS and B PTS
     * consecutive intervening points, respectively, that cannot be contained within
     * or on a circle of radius RADIUS1.
     */
    public static boolean checkCMV8(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {

        double radius1 = parameters.radius1();
        int aPts = parameters.aPts();
        int bPts = parameters.bPts();

        double[] xVals = xCoordinates.coords();
        double[] yVals = yCoordinates.coords();

        // The condition is not met when NUMPOINTS < 5
        if (xVals.length < 5) {
            return false;
        }
        // The condition is not met
        if (aPts < 1) {
            return false;
        }
        // The condition is not met
        if (bPts < 1) {
            return false;
        }
        // The condition is not met
        if (aPts + bPts > (xVals.length - 3)) {
            return false;
        }

        // three data points separated by exactly A PTS and B PTS consecutive
        // intervening points
        for (int i = 0; i < xVals.length - aPts - bPts; i++) {
            // using @author Nicole's functions distance and calculateCircumcircleRadius due
            // to CMV1 also checks that cannot be contained within or on a circle of radius RADIUS1
            double a = Utils.distance(xVals[i], yVals[i], xVals[i + aPts], yVals[i + aPts]);
            double b = Utils.distance(xVals[i + aPts], yVals[i + aPts], xVals[i + aPts + bPts], yVals[i + aPts + bPts]);
            double c = Utils.distance(xVals[i], yVals[i], xVals[i + aPts + bPts], yVals[i + aPts + bPts]);

            double circumcircleRadius = Utils.calculateCircumcircleRadius(a, b, c);

            if (circumcircleRadius > radius1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Element #9 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author serhan
     * @date 29.01.2023
     * Description: This method checks whether there exists at least one set
     * of three data points separated
     * by exactly C PTS and D PTS consecutive intervening points,
     * respectively, that form an angle such that:
     * angle < (PI-EPSILON) or angle > (PI+EPSILON)
     */
    public static boolean checkCMV9(final Coordinate xCoordinates,
                                    final Coordinate yCoordinates,
                                    final Parameters parameters) {
        double epsilon = parameters.epsilon();
        int c = parameters.cPts();
        int d = parameters.dPts();

        double pi = Math.PI;
        double[] xVals = xCoordinates.coords();
        double[] yVals = yCoordinates.coords();

        if (xVals.length < 5 || c < 1 || d < 1 || c + d > xVals.length - 3) {
            return false;
        }
        for (int i = 0; i < xVals.length - c - d; i++) {
            double x1 = xVals[i];
            double y1 = yVals[i];
            double x2 = xVals[i + c];
            double y2 = yVals[i + c];
            double x3 = xVals[i + c + d];
            double y3 = yVals[i + c + d];

            // If either the first
            // point or the last point (or both) coincide with the vertex, the angle is
            // undefined and the LIC
            // is not satisfied by those three points
            if ((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3)) {
                continue;
            }

            double angle = Utils.calculateAngle(x1, y1, x2, y2, x3, y3);
            if (angle < pi - epsilon || angle > pi + epsilon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Element #10 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Simon Hocker - hocker
     * @date 30.01.2024
     * Description: There exists at least one set of three data points
     * separated
     * by exactly E PTS and F PTS con- secutive intervening points,
     * respectively,
     * that are the vertices of a triangle with area greater than AREA1. The
     * condition is not met when NUMPOINTS < 5.
     */
    public static boolean checkCMV10(final Coordinate xCoordinates,
                                     final Coordinate yCoordinates,
                                     final Parameters parameters) {


        int n = xCoordinates.coords().length;
        if (n < 5 || n != yCoordinates.coords().length) {
            return false;
        }

        double area1 = parameters.area1();
        int ePts = parameters.ePts();
        int fPts = parameters.fPts();

        if (ePts < 1 || fPts < 1) {
            return false;
        }
        double[] p1 = new double[2];
        double[] p2 = new double[2];
        double[] p3 = new double[2];
        double area;

        for (int i = 0; i < (n - fPts - ePts - 2); i++) {
            p1[0] = xCoordinates.coords()[i];
            p1[1] = yCoordinates.coords()[i];
            p2[0] = xCoordinates.coords()[i + ePts + 1];
            p2[1] = yCoordinates.coords()[i + ePts + 1];
            p3[0] = xCoordinates.coords()[i + ePts + 1 + fPts + 1];
            p3[1] = yCoordinates.coords()[i + ePts + 1 + fPts + 1];
            area = Utils.calcArea(p1[0], p1[1], p2[0], p2[1], p3[0], p3[1]);
            if (area > area1) {
                return true;
            }

        }
        return false;
    }

    /**
     * Element #11 of the Conditions Met Vector.
     * Description: Checks if there exists at least one set of two data points,
     * separated by exactly G_PTS consecutive intervening points, such that
     * X[j] - X[i] < 0. The condition is not met when NUMPOINTS < 3.
     * <p>
     * 1 ≤ G_PTS ≤ NUMPOINTS−2.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates (not used in this LIC)
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Nicole Wijkman
     * @date 30-01-2024
     */
    public static boolean checkCMV11(final Coordinate xCoordinates,
                                     final Coordinate yCoordinates,
                                     final Parameters parameters) {
        int numPoints = xCoordinates.coords().length;
        int gPts = parameters.gPts();

        // Check constraints
        if (numPoints < 3 || gPts < 1 || gPts > numPoints - 2) {
            return false;
        }

        // Iterate over the points, leaving enough space for the G_PTS intervening
        // points plus the second point of the pair.
        for (int i = 0; i < numPoints - gPts - 1; i++) {
            int j = i + gPts + 1; // Calculate the index of the second point in the pair.
            // Check if the x-coordinate of the second point is less than that of the first
            // point.
            if (xCoordinates.coords()[j] - xCoordinates.coords()[i] < 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Element #12 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Martin
     * @date 01.02.2024
     * Description : "There exists at least one set of two data points,
     * separated by exactly K_PTS consecutive
     * intervening points, which are a distance greater than the length,
     * LENGTH1, apart. In addition,
     * there exists at least one set of two data points (which can be the same
     * or different from
     * the two data points just mentioned), separated by exactly K_PTS
     * consecutive intervening
     * points, that are a distance less than the length, LENGTH2, apart. Both
     * parts must be true
     * for the LIC to be true. The condition is not met when NUMPOINTS < 3."
     * 0 < LENGTH2
     */
    public static boolean checkCMV12(final Coordinate xCoordinates,
                                     final Coordinate yCoordinates,
                                     final Parameters parameters) {

        int numPoints = xCoordinates.coords().length;
        if (numPoints < 3) {
            return false;
        }
        int kPts = parameters.kPts();
        double length1 = parameters.length1();
        double length2 = parameters.length2();

        if (kPts < 0 || kPts > numPoints - 2 || length2 < 0) {
            return false;
        }

        boolean foundGreater = false;
        boolean foundLess = false;

        for (int i = 0; i < numPoints - kPts - 1; i++) {
            int j = i + kPts + 1;
            double distance = Utils.distance(xCoordinates.coords()[i], yCoordinates.coords()[i],
                    xCoordinates.coords()[j], yCoordinates.coords()[j]);
            if (!foundGreater && distance > length1) {
                foundGreater = true;
            }

            if (!foundLess && distance < length2) {
                foundLess = true;
            }

            if (foundGreater && foundLess) {
                return true;
            }
        }

        return false;
    }

    /**
     * Element #13 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author Maja
     * @date 30.01.2024
     * Description: Same as CMV8, in addition there exists at least one set of
     * three data points separated by exactly A PTS
     * and B PTS consecutive intervening points, respectively, that can be
     * contained in or on a
     * circle of radius RADIUS2. Both parts must be true for the LIC to be
     * true.
     */
    public static boolean checkCMV13(final Coordinate xCoordinates,
                                     final Coordinate yCoordinates,
                                     final Parameters parameters) {

        double radius1 = parameters.radius1();
        double radius2 = parameters.radius2();

        int aPts = parameters.aPts();
        int bPts = parameters.bPts();

        double[] xVals = xCoordinates.coords();
        double[] yVals = yCoordinates.coords();

        // The condition is not met when NUMPOINTS < 5
        if (xVals.length < 5) {
            return false;
        }
        // The condition is not met, due to 0 =< radius2
        if (radius2 < 0) {
            return false;
        }

        for (int i = 0; i < xVals.length - aPts - bPts; i++) {
            double a = Utils.distance(xVals[i], yVals[i], xVals[i + aPts], yVals[i + aPts]);
            double b = Utils.distance(xVals[i + aPts], yVals[i + aPts], xVals[i + aPts + bPts], yVals[i + aPts + bPts]);
            double c = Utils.distance(xVals[i], yVals[i], xVals[i + aPts + bPts], yVals[i + aPts + bPts]);

            double circumcircleRadius = Utils.calculateCircumcircleRadius(a, b, c);

            if ((circumcircleRadius > radius1) && (circumcircleRadius < radius2)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Element #14 of the Conditions Met Vector.
     *
     * @param xCoordinates the x coordinates
     * @param yCoordinates the y coordinates
     * @param parameters   object containing all the parameters
     * @return true if the condition is met, false otherwise
     * @author serhan
     * @date 29.01.2023
     * Description: This method checks whether there exists at least one set
     * of three data points, separated by exactly
     * E PTS and F PTS consecutive
     * intervening points, respectively, that are the vertices of a triangle
     * with area greater
     * than AREA1 and less than AREA2.
     */
    public static boolean checkCMV14(final Coordinate xCoordinates,
                                     final Coordinate yCoordinates,
                                     final Parameters parameters) {
        double area1 = parameters.area1();
        double area2 = parameters.area2();
        int ePts = parameters.ePts();
        int fPts = parameters.fPts();
        double[] xVals = xCoordinates.coords();
        double[] yVals = yCoordinates.coords();

        if (xVals.length < 5 || area2 < 0) {
            return false;
        }

        for (int i = 0; i < xVals.length - ePts - fPts; i++) {
            double x1 = xVals[i];
            double y1 = yVals[i];
            double x2 = xVals[i + ePts];
            double y2 = yVals[i + ePts];
            double x3 = xVals[i + ePts + fPts];
            double y3 = yVals[i + ePts + fPts];

            double area = Utils.calcArea(x1, y1, x2, y2, x3, y3);
            if ((area > area1) && (area < area2)) {
                return true;
            }
        }
        return false;
    }

}