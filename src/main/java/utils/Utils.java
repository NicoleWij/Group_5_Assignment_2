package main.java.utils;

/**
 * This is a utility class that provides some common functionality related to
 * coordinates and vectors.
 * It contains two inner classes, Coordinate and Vector, each representing a
 * specific type of data structure.
 * The class is final and has a private constructor to prevent instantiation.
 */
public final class Utils {
    /**
     * The maximum size of the coordinate array.
     */
    private static final int COORDINATE_ARRAY_SIZE = 100;
    /**
     * The maximum size of the vector array.
     */
    private static final int VECTOR_ARRAY_SIZE = 15;
    /**
     * The maximum size of the matrix array.
     */
    private static final int MATRIX_ARRAY_SIZE = 15;
    /**
     * The delta value for comparing doubles.
     */
    private static final double DOUBLE_DELTA = 1e-5;

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws UnsupportedOperationException if an attempt is made to
     *                                       instantiate it.
     */
    private Utils() {
        throw new UnsupportedOperationException("This is a utility class and "
                + "cannot be instantiated");
    }

    /**
     * This is an enum representing the different types of connectors.
     */
    public enum Connectors {
        /**
         * The NOT_USED connector.
         */
        NOT_USED,
        /**
         * The OR connector.
         */
        OR,
        /**
         * The AND connector.
         */
        AND
    }

    public enum CompType {
        /**
         * LT stands for less than.
         */
        LT,
        /**
         * EQ stands for less than or equal to.
         */
        EQ,
        /**
         * GT stands for greater than.
         */
        GT
    }

    /**
     * The value of PI to 10 decimal places.
     */
    public static final double PI = 3.1415926535;

    /**
     * A record class representing a coordinate, which is an array of doubles.
     * The size of the array should not exceed 100.
     *
     * @param coords the array of doubles
     */
    public record Coordinate(double[] coords) {
        /**
         * Constructor for the Coordinate class.
         */
        public Coordinate {
            assert coords.length <= COORDINATE_ARRAY_SIZE : "Coordinate "
                    + "array must be less than or equal to "
                    + COORDINATE_ARRAY_SIZE + " elements";
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Coordinate: ");
            sb.append("[");
            for (double c : coords) {
                sb.append(c);
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 1); // remove last space
            sb.deleteCharAt(sb.length() - 1); // remove last comma
            sb.append("]");
            return sb.toString();
        }
    }

    /**
     * A record class representing a vector, which is an array of booleans.
     * The size of the array should not exceed 15.
     *
     * @param coords the array of booleans
     */
    public record Vector(boolean[] coords) {
        /**
         * Constructor for the Vector class.
         */
        public Vector {
            assert coords.length <= VECTOR_ARRAY_SIZE : "Vector array must "
                    + "be less than or equal to " + VECTOR_ARRAY_SIZE
                    + "elements";
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Vector: ");
            sb.append("[");
            for (boolean c : coords) {
                sb.append(c ? "T" : "F");
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 1); // remove last space
            sb.deleteCharAt(sb.length() - 1); // remove last comma
            sb.append("]");
            return sb.toString();
        }
    }

    /**
     * A record class representing a matrix, which is an array of arrays.
     *
     * @param matrix the array of arrays
     * @param <T>    the type of the matrix
     */
    public record Matrix<T>(T[][] matrix) {
        /**
         * Constructor for the Matrix class.
         */
        public Matrix {
            assert matrix.length <= MATRIX_ARRAY_SIZE : "Matrix must be less "
                    + "than or equal to " + MATRIX_ARRAY_SIZE + " elements";
            for (T[] row : matrix) {
                assert row.length <= MATRIX_ARRAY_SIZE : "Matrix must be less "
                        + "than or equal to " + MATRIX_ARRAY_SIZE + " elements";
            }
        }

        /**
         * Returns the element in the specified row and column.
         *
         * @param row    the row
         * @param column the column
         * @return the element
         * @author serhan
         * @date 30.01.2024
         */
        public T getElement(final int row, final int column) {
            return matrix[row][column];
        }

        /**
         * Method to get the number of rows in the matrix.
         *
         * @return The number of rows in the matrix.
         * @author serhan
         * @date 30.01.2024
         */
        public int getRowCount() {
            return matrix.length;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Matrix<?> other)) {
                return false;
            }
            if (matrix.length != other.matrix.length || matrix[0].length != other.matrix[0].length) {
                return false;
            }
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (!matrix[i][j].equals(other.matrix[i][j])) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (T[] ts : matrix) {
                for (T t : ts) {
                    sb.append(t);
                    sb.append(" ");
                }
                sb.deleteCharAt(sb.length() - 1); // remove last space
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        }
    }

    /**
     * Parameters record representing various parameters used in the LIC.
     *
     * @param length1 Length parameter for LICs 0, 7, 12.
     * @param radius1 Radius parameter for LICs 1, 8, 13.
     * @param epsilon Deviation from PI parameter for LICs 2, 9.
     * @param area1   Area parameter for LICs 3, 10, 14.
     * @param qPts    Number of consecutive points parameter for LIC 4.
     * @param quads   Number of quadrants parameter for LIC 4.
     * @param dist    Distance parameter for LIC 6.
     * @param nPts    Number of consecutive points parameter for LIC 6.
     * @param kPts    Number of int points parameter for LICs 7, 12.
     * @param aPts    Number of int points parameter for LICs 8, 13.
     * @param bPts    Number of int points parameter for LICs 8, 13.
     * @param cPts    Number of int points parameter for LIC 9.
     * @param dPts    Number of int points parameter for LIC 9.
     * @param ePts    Number of int points parameter for LICs 10, 14.
     * @param fPts    Number of int points parameter for LICs 10, 14.
     * @param gPts    Number of int points parameter for LIC 11.
     * @param length2 Maximum length parameter for LIC 12.
     * @param radius2 Maximum radius parameter for LIC 13.
     * @param area2   Maximum area parameter for LIC 14.
     */
    public record Parameters(
            double length1,
            double radius1,
            double epsilon,
            double area1,
            int qPts,
            int quads,
            double dist,
            int nPts,
            int kPts,
            int aPts,
            int bPts,
            int cPts,
            int dPts,
            int ePts,
            int fPts,
            int gPts,
            double length2,
            double radius2,
            double area2) {
    }

    /**
     * This method compares two doubles and returns a CompType enum.
     *
     * @param a the first double
     * @param b the second double
     * @return the CompType enum
     */
    public static CompType compareDouble(final double a, final double b) {
        if ((a - b) < DOUBLE_DELTA) {
            return CompType.EQ;
        } else if (a < b) {
            return CompType.LT;
        } else {
            return CompType.GT;
        }
    }

    /**
     * This method calculates the angle between three points.
     *
     * @param x1 the x coordinate of the first point
     * @param y1 the y coordinate of the first point
     * @param x2 the x coordinate of the second point
     * @param y2 the y coordinate of the second point
     * @param x3 the x coordinate of the third point
     * @param y3 the y coordinate of the third point
     * @return the angle in radians
     */
    public static double calculateAngle(final double x1, final double y1,
                                        final double x2, final double y2,
                                        final double x3, final double y3) {
        double ux = x1 - x2;
        double uy = y1 - y2;
        double vx = x3 - x2;
        double vy = y3 - y2;

        double dotProduct = (ux * vx) + (uy * vy);
        double magnitude1 = Math.sqrt(ux * ux + uy * uy);
        double magnitude2 = Math.sqrt(vx * vx + vy * vy);

        double cos = dotProduct / (magnitude1 * magnitude2);

        return Math.acos(cos);
    }

    /**
     * Description: This method calculates the distance between two points
     * (x1, y1) and (x2, y2).
     *
     * @param x1 the x-coordinate of the first point
     * @param y1 the y-coordinate of the first point
     * @param x2 the x-coordinate of the second point
     * @param y2 the y-coordinate of the second point
     * @return the distance between the two points
     * @author Nicole Wijkman
     * @date 31-01-2024
     */
    public static double distance(final double x1, final double y1,
                                  final double x2, final double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Description: This method calculates the radius of the circumcircle
     * given the sides of the triangle (a, b, c).
     *
     * @param a the length of side a
     * @param b the length of side b
     * @param c the length of side c
     * @return the radius of the circumcircle of the triangle
     * @author Nicole Wijkman
     * @date 31-01-2024
     */
    public static double calculateCircumcircleRadius(final double a,
                                                     final double b,
                                                     final double c) {
        double s = (a + b + c) / 2;
        return (a * b * c) / (4 * Math.sqrt(s * (s - a) * (s - b) * (s - c)));
    }

    /**
     * Description: This method calculates the area of a triangle given the coordinates of its vertices.
     *
     * @param x1 X-coordinate of the first point
     * @param y1 Y-coordinate of the first point
     * @param x2 X-coordinate of the second point
     * @param y2 Y-coordinate of the second point
     * @param x3 X-coordinate of the third point
     * @param y3 Y-coordinate of the third point
     * @return The area of the triangle formed by the three points
     * @author Maja
     * @date 30.01.2024
     * used in CMV3
     */
    public static double calcArea(final double x1, final double y1,
                                  final double x2, final double y2,
                                  final double x3, final double y3) {
        return (0.5) * Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
    }
}
