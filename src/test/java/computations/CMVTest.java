package test.java.computations;

import main.java.computations.CMV;
import main.java.utils.Utils;
import main.java.utils.Utils.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

class CMVTest {

    /**
     * Test for CMV 0
     * 
     * @author Simon Hocker - hocker
     * @date 31-01-2024
     */
    @Test
    @DisplayName("Test CMV 0")
    void testCMV0() {
        
        // Conditions met: Two consecutive data points are further apart than LENGTH1
        Parameters params = new Parameters(
        4.4, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);
        double[] x = {1.23, 7.51};
        double[] y = {2.31, 3.20};
        Coordinate xCoords = new Coordinate(x);
        Coordinate yCoords = new Coordinate(y);
        boolean lic0true = CMV.checkCMV0(xCoords, yCoords, params);
        Assertions.assertTrue(lic0true, "CMV0 should return true");

        // Conditions not met: No two consecutive data points are further apart than LENGTH1
        params = new Parameters(
        1.56, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);
        double[] x2 = {1.23, 2.51};
        double[] y2 = {2.31, 3.20};
        Coordinate xCoords2 = new Coordinate(x2);
        Coordinate yCoords2 = new Coordinate(y2);
        boolean lic0false = CMV.checkCMV0(xCoords2, yCoords2, params);
        Assertions.assertFalse(lic0false, "CMV0 should return false");

        // Constraint check: Insufficient Points
        params = new Parameters(
        1.56, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);
        double[] x3 = {1.23};
        double[] y3 = {2.31};
        Coordinate xCoords3 = new Coordinate(x3);
        Coordinate yCoords3 = new Coordinate(y3);
        lic0false = CMV.checkCMV0(xCoords3, yCoords3, params);
        Assertions.assertFalse(lic0false, "CMV0 should return false");
    }

    /**
     * Tests for CMV 1
     * 
     * @author Nicole Wijkman
     * @date 31-01-2024
     */
    @Test
    @DisplayName("Test CMV 1")
    void testCMV1() {
        // Conditions not met: Three consecutive data points CAN be contained within a circle with RADIUS1
        Coordinate xCoords = new Coordinate(new double[] { 1, 0, 0, 0, 0 });
        Coordinate yCoords = new Coordinate(new double[] { 0, 0, -1, 0, -1 });
        Parameters params = new Parameters(
                0, 1.5, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV1(xCoords, yCoords, params),
                "CMV1 should return false for conditions not met");

        // Conditions met: Three consecutive data points CANNOT be contained within a circle with RADIUS1
        xCoords = new Coordinate(new double[] { 1, 0, 0, 0, 0 });
        yCoords = new Coordinate(new double[] { 0, 0, -1, 0, 5 });

        Assertions.assertTrue(CMV.checkCMV1(xCoords, yCoords, params),
                "CMV1 should return true for conditions met");

        // Constraint check: Test with RADIUS1 boundary condition
        xCoords = new Coordinate(new double[] { 1, 0, 0, 0, 0 });
        yCoords = new Coordinate(new double[] { 0, 0, -1, 0, -1 });
        params = new Parameters(
                0, -1, 0, 0, 0, 0, // RADIUS1 is set to less than 0
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV1(xCoords, yCoords, params),
                "CMV1 should return false for RADIUS1 boundary condition");
    }

    /**
     * Tests for CMV 2
     * 
     * @author Martin
     * @date 01-02-2024
     */
    @Test
    @DisplayName("Test CMV 2")
    void testCMV2() {
        // Check that 
        Coordinate xCoords = new Coordinate(new double[] {1, 0, 0});
        Coordinate yCoords = new Coordinate(new double[] {0, 0, -1});
        Parameters param = new Parameters(0, 0, Utils.PI / 4, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0);

        // angle between (1.0, 0.0), (0.0, 0.0) and (0.0, -1.0) is equal to : PI / 2 wich is less than PI - PI/4
        Assertions.assertTrue(CMV.checkCMV2(xCoords, yCoords, param));

        param = new Parameters(0, 0, 3 * Utils.PI / 4, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0);

        // angle between (1.0, 0.0), (0.0, 0.0) and (0.0, -1.0) is equal to : PI / 2 wich is more than PI - 3*PI/4
        Assertions.assertFalse(CMV.checkCMV2(xCoords, yCoords, param));

        xCoords = new Coordinate(new double[] {1, 0, 0, 0});
        yCoords = new Coordinate(new double[] {0, 0, -1, 2});

        // angle between (0, 0) (0, -1) and (0, 2) is equal to 0. Then it's true
        Assertions.assertTrue(CMV.checkCMV2(xCoords, yCoords, param));
    }

    /**
     * Tests for CMV 3
     * 
     * @author Maja
     * @date 01-02-2024
     */
    @Test
    @DisplayName("Test CMV 3")
    void testCMV3() {
        // Conditions not met: No three consecutive data points are the vertices of a triangle with area greater than AREA1
        Coordinate xCoordsNotMet = new Coordinate(new double[] { 0.1, 0.3, 0.2 });
        Coordinate yCoordsNotMet = new Coordinate(new double[] { 0.2, 0.1, 0.3 });
        Parameters paramsNotMet = new Parameters(
                0, 0, 0, 1, 0, 0, // AREA1 is set to 1 here
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0);

        Assertions.assertFalse(CMV.checkCMV3(xCoordsNotMet, yCoordsNotMet, paramsNotMet),
                "CMV3 should return false for conditions not met");

        // Conditions met: Three consecutive data points are the vertices of a triangle with area greater than AREA1
        Coordinate xCoordsMet = new Coordinate(new double[] { 0.1, 0.3, 0.2 });
        Coordinate yCoordsMet = new Coordinate(new double[] { 0.2, 0.1, 0.3 });
        Parameters paramsMet = new Parameters(
                0, 0, 0, 0.01, 0, 0, // AREA1 is set to 0.01 here
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0);

        Assertions.assertTrue(CMV.checkCMV3(xCoordsMet, yCoordsMet, paramsMet),
                "CMV3 should return true for conditions met");

        // Test with AREA1 boundary condition
        Coordinate xCoordsBoundary = new Coordinate(new double[] { 1, 0, 0, 0, 0 });
        Coordinate yCoordsBoundary = new Coordinate(new double[] { 0, 0, -1, 0, -1 });
        Parameters paramsBoundary = new Parameters(
                0, 0, 0, -1, 0, 0, // AREA1 is set to -1 here
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV3(xCoordsBoundary, yCoordsBoundary, paramsBoundary),
                "CMV3 should return false for AREA1 boundary condition");
    }

    /**
     * Tests for CMV 4
     * 
     * @author serhan
     * @date 31.01.2024
     */
    @Test
    @DisplayName("Test CMV 4")
    void testCMV4() {

        // (0,0) in quadrant 1, (0,-1) in quadrant 3, (1,-2) in quadrant 4
        Coordinate xCoords = new Coordinate(new double[] { 1, 0, 0, 1, 5 });
        Coordinate yCoords = new Coordinate(new double[] { 0, 0, -1, -2, 5 });

        Parameters myParam = new Parameters(0, 0, 0, 0, 3, 2, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        Assertions.assertTrue(CMV.checkCMV4(xCoords, yCoords, myParam));

        myParam = new Parameters(0, 0, 0, 0, 4, 3, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        // (0,0) in quadrant 1, (0,-1) in quadrant 3, (1,-2) in quadrant 4 but (5,5) and (1,0) are also in quadrant 1
        Assertions.assertFalse(CMV.checkCMV4(xCoords, yCoords, myParam));

        xCoords = new Coordinate(new double[] { 1, 0, 0, 1, 5 });
        yCoords = new Coordinate(new double[] { 0, 0, -1, 2, 5 });

        myParam = new Parameters(0, 0, 0, 0, 2, 1, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        // Testing the limits of qPts and quads
        Assertions.assertTrue(CMV.checkCMV4(xCoords, yCoords, myParam));

        myParam = new Parameters(0, 0, 0, 0, 4, 1, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        Assertions.assertTrue(CMV.checkCMV4(xCoords, yCoords, myParam));


        xCoords = new Coordinate(new double[] { 0, -1, 1, 0, 0, 3, 5 });
        yCoords = new Coordinate(new double[] { 0, 0, 0, -1, 0, 2, -5 });

        myParam = new Parameters(0, 0, 0, 0, 4, 3, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        // (-1,0) in quadrant 2, (0,-1) in quadrant 3, (1,0) in quadrant 1, (5, -5) in
        // quadrant 4 but we cannot reach it

        Assertions.assertFalse(CMV.checkCMV4(xCoords, yCoords, myParam));

        myParam = new Parameters(0, 0, 0, 0, 5, 3, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        // (-1,0) in quadrant 2, (0,-1) in quadrant 3, (1,0) in quadrant 1, (5, -5) in
        // quadrant 4 but we cannot reach it

        Assertions.assertFalse(CMV.checkCMV4(xCoords, yCoords, myParam));

        // (-1,0) in quadrant 2, (0,-1) in quadrant 3, (1,0) in quadrant 1, (5, -5) in
        // quadrant 4 but we can reach it

        myParam = new Parameters(0, 0, 0, 0, 6, 3, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        Assertions.assertTrue(CMV.checkCMV4(xCoords, yCoords, myParam));
    }

    /**
     * Tests for CMV 5
     * 
     * @author Simon Hocker - hocker
     * @date 31.01.2024
     */
    @Test
    @DisplayName("Test CMV 5")
     void testCMV5() {
        // Conditions met: A set of of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), exists such that X[j] - X[i] < 0
        Parameters params = new Parameters(
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);
        double[] x = {0.0, -1.0, 0.0};
        double[] y = {0.0, 0.0, 0.0};
        Coordinate xCoords = new Coordinate(x);
        Coordinate yCoords = new Coordinate(y);
        boolean lic5true = CMV.checkCMV5(xCoords, yCoords, params);
        Assertions.assertTrue(lic5true, "LIC 5 should return true.");

        // Conditions not met: NO set of of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), exists such that X[j] - X[i] < 0
        params = new Parameters(
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);
        double[] x2 = {0.0, 1.0, 2.0};
        double[] y2 = {0.0, 0.0, 0.0};
        xCoords = new Coordinate(x2);
        yCoords = new Coordinate(y2);
        boolean lic5false = CMV.checkCMV5(xCoords, yCoords, params);
        Assertions.assertFalse(lic5false, "LIC 5 should return false.");

        // Condition not met: insufficient number of points
        params = new Parameters(
        1.56, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);
        double[] x3 = {1.23};
        double[] y3 = {2.31};
        Coordinate xCoords3 = new Coordinate(x3);
        Coordinate yCoords3 = new Coordinate(y3);
        lic5false = CMV.checkCMV5(xCoords3, yCoords3, params);
        Assertions.assertFalse(lic5false, "CMV0 should return false");
    }

    /**
     * Tests for CMV 6
     * 
     * @author Nicole Wijkman
     * @date 31-01-2024
     */
    @Test
    @DisplayName("Test CMV 6")
    void testCMV6() {

        // Condition met: At least one set of consecutive data points N_PTS exists so that the points lies a distance greater 
        // than DIST from the line joining the first and last of these N_PTS
        Coordinate xCoords = new Coordinate(new double[] { 0, 1, 4, 0 });
        Coordinate yCoords = new Coordinate(new double[] { 0, 1, 0, 0 });
        Parameters params = new Parameters(
                0, 0, 0, 0, 0, 0,
                1.5, 3, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0);
        Assertions.assertTrue(CMV.checkCMV6(xCoords, yCoords, params),
                "CMV6 should return true when condition is met");

        // Condition not met (straight line): No set of consecutive data points N_PTS exists so that the points lies a distance
        // greater than DIST from the line joining the first ad last of these N_PTS
        xCoords = new Coordinate(new double[] { 0, 1, 2, 3 });
        yCoords = new Coordinate(new double[] { 0, 0, 0, 0 });
        
        Assertions.assertFalse(CMV.checkCMV6(xCoords, yCoords, params),
                "CMV6 should return false for a straight line");

        // Condition not met (same point): If the first and last points of these N PTS are identical, then the calculated 
        // distance to compare with DIST will be the distance from the coincident point to all other points of the N PTS 
        // consecutive points.
        xCoords = new Coordinate(new double[] { 0, 0, 0, 0 });
        yCoords = new Coordinate(new double[] { 0, 0, 0, 0 });
        
        Assertions.assertFalse(CMV.checkCMV6(xCoords, yCoords, params),
                "CMV6 should return false when all points are the same");

        // Test case for nPts less than 3
        xCoords = new Coordinate(new double[] { 0, 1, 4, 0 });
        yCoords = new Coordinate(new double[] { 0, 1, 0, 0 });
        params = new Parameters(
                0, 0, 0, 0, 0, 0,
                1.5, 2, 0, 0, 0, 0, 0, // nPts less than 3
                0, 0, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV6(xCoords, yCoords, params),
                "CMV6 should return false for nPts < 3");

        // Test case for nPts greater than number of points
        xCoords = new Coordinate(new double[] { 0, 1 });
        yCoords = new Coordinate(new double[] { 0, 1 });
        params = new Parameters(
                0, 0, 0, 0, 0, 0,
                1.5, 3, 0, 0, 0, 0, 0, // nPts greater than number of points
                0, 0, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV6(xCoords, yCoords, params),
                "CMV6 should return false for nPts > number of points");

        // Test case for dist < 0
        xCoords = new Coordinate(new double[] { 0, 1, 4, 0 });
        yCoords = new Coordinate(new double[] { 0, 1, 0, 0 });
        params = new Parameters(
                0, 0, 0, 0, 0, 0,
                -1, 3, 0, 0, 0, 0, 0, // dist < 0
                0, 0, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV6(xCoords, yCoords, params),
                "CMV6 should return false for dist <= 0");
    }

    /**
     * Tests for CMV 7
     * 
     * @author Martin
     * @date 01-02-2024
     */
    @Test
    @DisplayName("Test CMV 7")
    void testCMV7() {
        Coordinate xCoords = new Coordinate(new double[] {1, 0, 0, 0, 0});
        Coordinate yCoords = new Coordinate(new double[] {0, 0, -1, 0, -1});

        Parameters params = new Parameters(1, 0, 0, 0, 0, 0,
        0, 0, 1, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);

        // Need to have at least 2 points with kPts = 1 points between them
        // that have a distance greater than LENGTH1 = 1 which is the case here
        // since (1, 0) and (0, -1) have a distance of sqrt(2) > 1
        Assertions.assertTrue(CMV.checkCMV7(xCoords, yCoords, params));

        params = new Parameters(1, 0, 0, 0, 0, 0,
        0, 0, 2, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);

        Assertions.assertFalse(CMV.checkCMV7(xCoords, yCoords, params));

        // Same as above but with LENGTH1 = 2. So this time it should return
        // since the distance between (1, 0) and (0, -1) is sqrt(2) < 2
        params = new Parameters(2, 0, 0, 0, 0, 0,
        0, 0, 1, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);

        Assertions.assertFalse(CMV.checkCMV7(xCoords, yCoords, params));
    }

    /**
     * Tests for CMV 8
     * 
     * @author Maja
     * @date 01-02-2024
     */
    @Test
    @DisplayName("Test CMV 8")
    void testCMV8() {
        // Test for conditions not met
        // Checks to see if there exists at least one set of three data points separated by exactly aPts and bPts
        // The three points can not be contained within or on a circle of radius1
        Coordinate xCoordsNotMet = new Coordinate(new double[] { 0.1, 0.8, 0.3, 0.5, 0.3 });
        Coordinate yCoordsNotMet = new Coordinate(new double[] { 0.2, 0.9, 0.1, 0.8, 0.2 });
        Parameters paramsNotMet = new Parameters(
                0, 7, 0, 0, 0, 0,
                0, 0, 0, 2, 2, 0, 0,
                0, 0, 0, 0, 0, 0);

        Assertions.assertFalse(CMV.checkCMV8(xCoordsNotMet, yCoordsNotMet, paramsNotMet),
                "CMV8 should return false for conditions not met");

        // Test for conditions met
        // Checks to see if there exists at least one set of three data points separated by exactly aPts and bPts
        // The three points can not be contained within or on a circle of radius1
        Coordinate xCoordsMet = new Coordinate(new double[] { 0.1, 0.8, 0.3, 0.5, 0.3, 0.2, 0.3 });
        Coordinate yCoordsMet = new Coordinate(new double[] { 0.2, 0.9, 0.1, 0.8, 0.2, 0.3, 0.9 });
        Parameters paramsMet = new Parameters(
                0, 0.1, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, 0, 0);

        Assertions.assertTrue(CMV.checkCMV8(xCoordsMet, yCoordsMet, paramsMet),
                "CMV8 should return true for conditions met");

        // Condition not met due to numpoints < 5
        Coordinate xCoordsBoundary = new Coordinate(new double[] { 0.1, 0.8, 0.3, 0.5 });
        Coordinate yCoordsBoundary = new Coordinate(new double[] { 0.2, 0.9, 0.1, 0.8 });
        Parameters paramsBoundary = new Parameters(
                0, 0.1, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV8(xCoordsBoundary, yCoordsBoundary, paramsBoundary),
                "CMV8 should return false for NUMPOINTS boundary condition");
    }

    /**
     * Tests for CMV 9
     * @author Serhan
     * @date 31.01.2024
     */
    @Test
    @DisplayName("Test CMV 9")
    void testCMV9() {
        // Conditions not met: Test for C(1) + D(2) < Numpoints(5) - 3 ==> False
        Coordinate xCoords = new Coordinate(new double[] {1, 0, 0, 0, 0});
        Coordinate yCoords = new Coordinate(new double[] {0, 0, -1, 0, -1});
        Parameters params = new Parameters(
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 1, 2,
    0, 0, 0, 0, 0, 0);

        Assertions.assertFalse(CMV.checkCMV9(xCoords, yCoords, params));


        // Looking for a range out of the range 90 <= <= 270, (0,0) (0,-1) (0,5) == 0 degree so it is true.
        xCoords = new Coordinate(new double[] {1, 0, 0, 0, 0, 0, 0});
        yCoords = new Coordinate(new double[] {0, 0, -1, 0, 5, 0, 0});
        params = new Parameters(
        0, 0, Math.PI / 2, 0, 0, 0,
        0, 0, 0, 0, 0, 1, 2,
        0, 0, 0, 0, 0, 0);

        Assertions.assertTrue(CMV.checkCMV9(xCoords, yCoords, params));

        // Looking for a range out of the range 0 <= <= 360, every angle is in this range so it is false.
        params = new Parameters(
                0, 0, Math.PI, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 2,
                0, 0, 0, 0, 0, 0);

        Assertions.assertFalse(CMV.checkCMV9(xCoords, yCoords, params));

        // Looking for a range out of the range 45 <= <= 315, only angle can be calculated is 60 degree so it is false.
        xCoords = new Coordinate(new double[] {1, 0, -1, 0, 0, 0});
        yCoords = new Coordinate(new double[] {0, 0, 0, 0, Math.sqrt(3),0});

        params = new Parameters(
                0, 0, Math.PI * 3 / 4, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 2,
                0, 0, 0, 0, 0, 0);

        Assertions.assertFalse(CMV.checkCMV9(xCoords, yCoords, params));

        // Looking for a range out of the range 60 + e <=  <= 300 -e, we have 60 degree so it is true.
        params = new Parameters(
                0, 0, (Math.PI-0.001)*2 / 3, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 2,
                0, 0, 0, 0, 0, 0);

        Assertions.assertTrue(CMV.checkCMV9(xCoords, yCoords, params));

        // Looking for a range out of the range 120 <=  <= 240, we have 0 degree (0,0) (11,11) (2,2) so it is true.
        xCoords = new Coordinate(new double[] {1, 1, 0, 11, 0, 2});
        yCoords = new Coordinate(new double[] {0, 0, 0, 11, 0, 2});
        params = new Parameters(
                0, 0, (Math.PI) / 3, 0, 0, 0,
                0, 0, 0, 0, 0, 2, 1,
                0, 0, 0, 0, 0, 0);

        Assertions.assertTrue(CMV.checkCMV9(xCoords, yCoords, params));

        // Looking for a range out of the range 120 <=  <= 240, we have C + D > Numpoints - 3 so it is false.
        xCoords = new Coordinate(new double[] {1, 1, 0, 0, -11});
        yCoords = new Coordinate(new double[] {0, 0, 0, 0, -11});

        Assertions.assertFalse(CMV.checkCMV9(xCoords, yCoords, params));


    }

    /**
     * Tests for CMV 10
     * 
     * @author Simon Hocker - hocker
     * @date 31-01-2024
     */
    @Test
    @DisplayName("Test CMV 10")
    void testCMV10() {

        // Conditions met: One set of three data points separated by exactly E_PTS and F_PTS consecutive intervening points
        // that are the vertices of a triangle with area greater than AREA1
        Coordinate xCoords = new Coordinate(new double[] {0, 0, 2, 0, 0});
        Coordinate yCoords = new Coordinate(new double[] {2, 0, 1, 0, 0});
        Parameters params = new Parameters(
        0, 0, 0, 1.95, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        1, 1, 0, 0, 0, 0);
        Assertions.assertTrue(CMV.checkCMV10(xCoords, yCoords, params));
        
        // Conditions not met: No set of three data points separated by exactly E_PTS and F_PTS consecutive intervening 
        // points that are the vertices of a triangle with area greater than AREA1
        xCoords = new Coordinate(new double[] {0, 0, -2, 0, 0});
        yCoords = new Coordinate(new double[] {-2, 0, -1, 0, 0});
        params = new Parameters(
        0, 0, 0, 2.0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        1, 1, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV10(xCoords, yCoords, params));

        // Insufficient coordinates, NUMPOINTS < 5
        xCoords = new Coordinate(new double[] {0, 0, -1, 0});
        yCoords = new Coordinate(new double[] {0, 0, -2, 0});
        params = new Parameters(
        0, 0, 0, 1.0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        1, 1, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV10(xCoords, yCoords, params));

        // Insufficient ePts/fPts
        xCoords = new Coordinate(new double[] {0, 0, -1, 0, 0});
        yCoords = new Coordinate(new double[] {0, 0, -2, 0, 0});
        params = new Parameters(
        0, 0, 0, 1.0, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0);
        Assertions.assertFalse(CMV.checkCMV10(xCoords, yCoords, params));

    }

    /**
     * Tests for CMV 11
     * 
     * @author Nicole Wijkman
     * @date 31-01-2024
     */
    @Test
    @DisplayName("Test CMV 11")
    void testCMV11() {
        // Conditions met: X[j] - X[i] will be negative
        Coordinate xCoords = new Coordinate(new double[] { 2, 1, 0, -1 });
        Coordinate yCoords = new Coordinate(new double[] { 0, 0, 0, 0 });
        Parameters params = new Parameters(
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 1, 0, 0, 0);
        Assertions.assertTrue(CMV.checkCMV11(xCoords, yCoords, params),
                "CMV11 should return true for positive scenario");

        // Conditions not met: X[j] - X[i] will not be negative
        xCoords = new Coordinate(new double[] { -1, 0, 1, 2 });
        yCoords = new Coordinate(new double[] { 0, 0, 0, 0 });
        Assertions.assertFalse(CMV.checkCMV11(xCoords, yCoords, params),
                "CMV11 should return false for negative scenario");

        // Constraint check: NUMPOINTS < 3
        xCoords = new Coordinate(new double[] { 1, 2 });
        yCoords = new Coordinate(new double[] { 1, 2 });
        Assertions.assertFalse(CMV.checkCMV11(xCoords, yCoords, params),
                "CMV11 should return false when NUMPOINTS < 3");

        // Constraint check: G_PTS < 1
        xCoords = new Coordinate(new double[] { 0, 1, 2, 3, 4 });
        yCoords = new Coordinate(new double[] { 0, 0, 0, 0, 0 });
        params = new Parameters(
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0); // gPts set to 0
        Assertions.assertFalse(CMV.checkCMV11(xCoords, yCoords, params),
                "CMV11 should return false for G_PTS < 1");

        // Constraint check: G_PTS > NUMPOINTS - 2
        params = new Parameters(
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 0, 0, 0); // gPts set to 4
        // NUMPOINTS - 2 in this case is 5-2 = 3
        Assertions.assertFalse(CMV.checkCMV11(xCoords, yCoords, params),
                "CMV11 should return false for G_PTS > NUMPOINTS - 2");
    }

    /**
     * Tests for CMV 12
     * 
     * @author Martin
     * @date 01-02-2024
     */
    @Test
    @DisplayName("Test CMV 12")
    void testCMV12() {
        Coordinate xCoords = new Coordinate(new double[] {1, 0, 0, 0, 0});
        Coordinate yCoords = new Coordinate(new double[] {0, 0, -1, 0, -1});
        Parameters params = new Parameters(
            0, 0, 0, 0, 0, 0, 
            0, 0, 2, 0, 0, 0, 0, 
            0, 0, 0, 4, 0, 0);

        // Need to have at least 2 points with kPts = 2 points between them
        // that have a distance greater than LENGTH1 = 0 and also 2 points
        // that have a distance less than LENGTH2 = 4 which is the case here
        // since (1, 0) and (0, 0) have a distance of 1 > 0 and 1 < 4
        Assertions.assertTrue(CMV.checkCMV12(xCoords, yCoords, params));

        params = new Parameters(
            1, 0, 0, 0, 0, 0, 
            0, 0, 2, 0, 0, 0, 0, 
            0, 0, 0, 2, 0, 0);

        // Same as above but with LENGTH1 = 1 and LENGTH2 = 2. So this time it
        // so it should return false since the distance between (1, 0) and
        // (0, 0) is not strictly greater than 1
        Assertions.assertFalse(CMV.checkCMV12(xCoords, yCoords, params));

        params = new Parameters(
            1, 0, 0, 0, 0, 0, 
            0, 0, 1, 0, 0, 0, 0, 
            0, 0, 0, 4, 0, 0);

        // Same as above but with LENGTH2 = 4 and kPts = 1. So this time it
        // compares (1, 0) with (0, -1) which have a distance of sqrt(2) < 4
        // and sqrt(2) > 1 so it should return true
        Assertions.assertTrue(CMV.checkCMV12(xCoords, yCoords, params));

        xCoords = new Coordinate(new double[] {1, 0});
        yCoords = new Coordinate(new double[] {0, 0});

        // Should return false since numpoints < 3
        Assertions.assertFalse(CMV.checkCMV12(xCoords, yCoords, params));

        xCoords = new Coordinate(new double[] {1, -3, 0, 6, 0, 6});
        yCoords = new Coordinate(new double[] {0, -4, -1, 0, -1, 1});
        params = new Parameters(8, 0, 0, 0, 0, 0,
         0, 0, 1, 0, 0, 0, 0,
         0, 0, 0, 2, 0, 0);

        // Should return true since there are 2 points with a distance greater
        // Than 8 and 2 points with a distance less than 2 :
        // (-3, -4) and (6, 0) have a distance greater than 8 and
        // (1, 0) and (0, -1) have a distance less than 2
        Assertions.assertTrue(CMV.checkCMV12(xCoords, yCoords, params));
    }

     /**
     * Tests for CMV 13
     * 
     * @author Maja
     * @date 01-02-2024
     */
    @Test
    @DisplayName("Test CMV 13")
    void testCMV13() {
        // Test for both conditions are met
        // Checks to see if there exists at least one set of three data points separated by exactly aPts and bPts
        // The three points can not be contained within or on a circle of radius1
        // In addition, the poinst can be contained within or on a circle of radius2 
        Coordinate xCoordsMet = new Coordinate(new double[] { 1, 2, 3, 1, 2, 5 });
        Coordinate yCoordsMet = new Coordinate(new double[] { 2, 2, 2, 1, 1, 1 });
        Parameters paramsMet = new Parameters(
                0, 0.3, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, 2.5, 0);

        Assertions.assertTrue(CMV.checkCMV13(xCoordsMet, yCoordsMet, paramsMet),
                "CMV13 should return true for both conditions met");
        
        // Test for first condition not met in CMV13
        // Checks to see if there exists at least one set of three data points separated by exactly aPts and bPts
        // The three points can not be contained within or on a circle of radius1
        // In addition, the points can be contained within or on a circle of radius2
        Coordinate xCoordsNotMet = new Coordinate(new double[] { 1, 2, 3, 1, 2, 5 });
        Coordinate yCoordsNotMet = new Coordinate(new double[] { 2, 2, 2, 1, 1, 1 });
        Parameters paramsNotMet = new Parameters(
                0, 2.5, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, 2.5, 0);

        Assertions.assertFalse(CMV.checkCMV13(xCoordsNotMet, yCoordsNotMet, paramsNotMet),
                "CMV13 should return false for first condition not met");
        
        // Test for second condition not met in CMV13
        // Checks to see if there exists at least one set of three data points separated by exactly aPts and bPts
        // The three points can not be contained within or on a circle of radius1
        // In addition, the points can be contained within or on a circle of radius2
        Parameters paramsNotMet2 = new Parameters(
                0, 0.3, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, 0.3, 0);

        Assertions.assertFalse(CMV.checkCMV13(xCoordsNotMet, yCoordsNotMet, paramsNotMet2),
                "CMV13 should return false for second condition not met");

        // Condition not met due to radius2 is negative, should return false because the condition 0=< radius2 is not met
        Parameters paramsBoundary = new Parameters(
                0, 0.3, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, -2.5, 0);
        Assertions.assertFalse(CMV.checkCMV13(xCoordsNotMet, yCoordsNotMet, paramsBoundary),
                "CMV13 should return false for radius2 boundary condition");
        
        // Condition not met due to numpoints < 5
        Coordinate xCoordsBoundary = new Coordinate(new double[] { 0.1, 0.8, 0.3, 0.5 });
        Coordinate yCoordsBoundary = new Coordinate(new double[] { 0.2, 0.9, 0.1, 0.8 });
        Parameters paramsNumBoundary = new Parameters(
                0, 0.3, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, 2.5, 0);
        Assertions.assertFalse(CMV.checkCMV13(xCoordsBoundary, yCoordsBoundary, paramsNumBoundary),
                "CMV13 should return false for NUMPOINTS boundary condition");
    }

    /**
     * Tests for CMV 14
     * @author Serhan
     * @date 31.01.2024
     */
    @Test
    @DisplayName("Test CMV 14")
    void testCMV14() {
        // Conditions met
        Coordinate xCoords = new Coordinate(new double[] {1, 0, 10, -1, 0});
        Coordinate yCoords = new Coordinate(new double[] {1, 0, 2, -3, -1});

        // Area of triangle (1,1) (10,2) (-1,-3) = 17
        Parameters params = new Parameters(
        0, 0, 0, 16, 0, 0,
        0, 0, 0, 0, 0, 0, 0,
        2, 1, 0, 0, 0, 18);

        // 16 < 17 < 18 so it is true
        Assertions.assertTrue(CMV.checkCMV14(xCoords, yCoords, params));


        // Conditions not met
        params = new Parameters(
         0, 0, 0, 14, 0, 0,
         0, 0, 0, 0, 0, 0, 0,
         2, 1, 0, 0, 0, 16);

        // 14 < 17 < 16 so it is false
        Assertions.assertFalse(CMV.checkCMV14(xCoords, yCoords, params));
        params = new Parameters(
         0, 0, 0, 17, 0, 0,
         0, 0, 0, 0, 0, 0, 0,
         2, 1, 0, 0, 0, 18);

        // 17 < 17 < 18 so it is false
        Assertions.assertFalse(CMV.checkCMV14(xCoords, yCoords, params));


        // Condition is met: Area is 0 so -0.1 < 0 < 0.5 so it is true
        xCoords = new Coordinate(new double[] {1, 0, 1, -1, 1});
        yCoords = new Coordinate(new double[] {1, 0, 1, -3, 1});

        params = new Parameters(
                0, 0, 0, -0.1, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                2, 2, 0, 0, 0, 0.5);

        Assertions.assertTrue(CMV.checkCMV14(xCoords, yCoords, params));

        // Condition is met: Edge check, (-1,1) (0,0) (10,3)
        xCoords = new Coordinate(new double[] {1, 0, -1, 0, 10});
        yCoords = new Coordinate(new double[] {1, 0, 1, 0, 3});

        params = new Parameters(
                0, 0, 0, 6, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                1, 1, 0, 0, 0, 7);

        Assertions.assertTrue(CMV.checkCMV14(xCoords, yCoords, params));

        // Constraint check: Area2 cannot be lower than 0 so it is false
        params = new Parameters(
                0, 0, 0, 6, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                1, 1, 0, 0, 0, -1);

        Assertions.assertFalse(CMV.checkCMV14(xCoords, yCoords, params));

        // Constraint check: numPoints < 5 so it is false
        xCoords = new Coordinate(new double[] {1, 0, -1, 0});
        yCoords = new Coordinate(new double[] {1, 0, 1, 0});

        Assertions.assertFalse(CMV.checkCMV14(xCoords, yCoords, params));
    }

}
