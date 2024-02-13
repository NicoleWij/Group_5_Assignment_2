package main.java.computations;

import main.java.utils.Utils.*;

public class FUV {

    /**
     * Description: FUV[i] should be set to true if PUV[i] is false
     * (indicating that the associated LIC should not hold back launch)
     * or if all elements in PUM row i are true.
     *
     * @author Nicole Wijkman, Simon Hocker
     * @date 01.02.2024
     * @param pum The Preliminary Unlocking Matrix
     * @param puv Used to determine whether an LIC is considered as a factor in
     *            signaling launch. If false, it doesn't matter to the launch.
     *
     * @return fuvVector: Final Unlocking Vector
     */
    public static boolean[] fuv(Matrix<Boolean> pum, boolean[] puv) {
        boolean[] fuvVector = new boolean[15];

        for (int i = 0; i < puv.length; i++) {
            if (!puv[i]) {
                // If PUV[i] is false, FUV[i] should be true
                fuvVector[i] = true;
            } else {
                // If PUV[i] is true, check the corresponding row in PUM
                boolean allTrue = true; // Assume all are true initially
                for (int j = 0; j < pum.getRowCount(); j++) {
                    if (i != j && !pum.getElement(i, j)) { // Exclude diagonal element i == j
                        allTrue = false;
                        break; // Exit the loop if a false element is found
                    }
                }
                // FUV[i] is true if all PUM elements in row i are true (excluding diagonal); otherwise, it's false
                fuvVector[i] = allTrue;
            }
        }

        return fuvVector;
    }

}
