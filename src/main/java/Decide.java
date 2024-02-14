package main.java;

import main.java.utils.Utils.Coordinate;
import java.util.ArrayList;
import main.java.computations.CMV;
import main.java.computations.FUV;
import main.java.computations.PUM;
import main.java.utils.Utils.Parameters;
import main.java.utils.Utils.Matrix;

/**
 * main.java.Decide class is responsible for the final launch decision.
 * It's composed of one static method that takes some inputs and returns
 * a boolean and some outputs.
 */

public final class Decide {
    /**
     * Private constructor to prevent instantiation.
     *
     * @throws UnsupportedOperationException if an attempt is made to
     *                                       instantiate it.
     */
    private Decide() {
        throw new UnsupportedOperationException("This is a utility class and "
                + "cannot be instantiated");
    }

    public record Output(boolean decision) { 

        /**
         * This method returns an encoded representation of the decision.
         * @return the string "YES" if the decision is true, "NO" otherwise.
         */
        public String launch() {
            return decision ? "YES" : "NO";
        }
    }

    /**
     * This method takes some inputs and returns a boolean and some outputs.
     * @author Maja Larsson, Simon Hocker - hocker
     * @date 01.02.2024
     * 
     * @param coordinateX the x coordinates of the points.
     * @param coordinateY the y coordinates of the points.
     * @param parameters the object containing the parameters.
     * @param lcm the logical connector matrix.
     * @param puv the preliminary unlocking vector.
     * @return a boolean and some outputs.
     */
    public static Output decide(final Coordinate coordinateX,
                                final Coordinate coordinateY,
                                final Parameters parameters,
                                final Matrix<String> lcm,
                                final boolean[] puv) {
        
        boolean launchDecision = true; 
        
        int numPoints = 0;
        if (coordinateX.coords().length == coordinateY.coords().length) {
            numPoints = coordinateX.coords().length;
        }
        else {
            System.out.println("Illegal Arguments");
            System.exit(0);
        }

        if (!checkInput(numPoints, parameters, lcm, puv)) {
            System.out.println("Illegal Arguments");
            System.exit(0);
        }
        ArrayList<Boolean> cmvVector = new ArrayList<Boolean>(15);
        cmvVector.add(CMV.checkCMV0(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV1(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV2(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV3(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV4(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV5(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV6(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV7(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV8(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV9(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV10(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV11(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV12(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV13(coordinateX, coordinateY, parameters));
        cmvVector.add(CMV.checkCMV14(coordinateX, coordinateY, parameters));

        Matrix<Boolean> pum = PUM.pum(lcm, cmvVector);
        boolean[] fuv = FUV.fuv(pum, puv);
        

        for (int i = 0; i < fuv.length; i++){
            if(!fuv[i]){
                launchDecision = false;
            }
        }

        return new Output(launchDecision);
    }

    public static boolean checkInput(final int numPoints,
        final Parameters parameters,
        final Matrix<String> lcm,
        final boolean[] puv) throws IllegalArgumentException{
            
            if (
                !((2 <= numPoints && numPoints <= 100) ||
                (parameters.length1() >= 0 && parameters.length2() >= 0) ||
                (parameters.area1() >= 0 && parameters.area2() >= 0) ||
                (0 <= parameters.epsilon() && parameters.epsilon() < Math.PI) ||
                (parameters.radius1() >= 0 && parameters.radius2() >= 0) ||
                (parameters.qPts() >= 2 && parameters.qPts() <= 100) ||
                (parameters.fPts() >= 1 && parameters.ePts() >= 1 && parameters.fPts() + parameters.ePts() <= numPoints-3) ||
                (parameters.quads() >= 1 && parameters.quads() <= 3) ||
                (parameters.nPts() >= 1 && parameters.nPts() <= numPoints ||
                (parameters.bPts() >= 1 && parameters.aPts() >= 1 && parameters.aPts() + parameters.bPts() <= numPoints-3) ||
                (parameters.cPts() >= 1 && parameters.dPts() >= 1 && parameters.cPts() + parameters.dPts() <= numPoints-3) ||
                (parameters.gPts() >= 1 && parameters.gPts() <= numPoints-2) ||
                (parameters.kPts() >= 1 && parameters.kPts() <= numPoints-2) ||
                (parameters.dist() >= 0)))){
                    return false;
                
            }

            

            return true;
        }

}
