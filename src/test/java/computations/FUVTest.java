package test.java.computations;

import main.java.computations.FUV;
import main.java.utils.Utils.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

/**
     * Class Description: Tests to see that FUV.java runs as it should.
     * FUV Description: FUV[i] should be set to true if PUV[i] is false
     * (indicating that the associated LIC should not hold back launch)
     * or if all elements in PUM row i are true.
     * 
     * @author Nicole Wijkman, Simon Hocker
     * @date 01.02.2024
     */
class FUVTest {

    @Test
    @DisplayName("Test FUV with all PUV entries true and corresponding PUM entries true")
    void testFUVAllTrue() {
        boolean[] puv = new boolean[15];
        Boolean[][] pumData = new Boolean[15][15];
        boolean[] expectedFUV = new boolean[15];

        for (int i = 0; i < 15; i++) {
            puv[i] = true;
            expectedFUV[i] = true;
            for (int j = 0; j < 15; j++) {
                pumData[i][j] = (i != j); // Set non-diagonal elements to true
            }
        }

        Matrix<Boolean> pum = new Matrix<>(pumData);
        boolean[] actualFUV = FUV.fuv(pum, puv);
        
        Assertions.assertArrayEquals(expectedFUV, actualFUV,
                "FUV should be all true when all PUV entries are true and corresponding PUM entries are true.");
    }

    @Test
    @DisplayName("Test FUV with some PUV entries false")
    void testFUVMixedPUV() {
        // Initialize PUV with some entries false
        boolean[] puv = new boolean[15];
        Boolean[][] pumData = new Boolean[15][15];
        boolean[] expectedFUV = new boolean[15];

        for (int i = 0; i < 15; i++) {
            puv[i] = i % 2 == 0; // Alternate true and false
            for (int j = 0; j < 15; j++) {
                if (i == j) {
                    pumData[i][j] = null; // Diagonal elements are not used
                } else {
                    pumData[i][j] = puv[i]; // Non-diagonal elements match PUV
                }
            }
        }

        Matrix<Boolean> pum = new Matrix<>(pumData);
        boolean[] actualFUV = FUV.fuv(pum, puv);

        // Determine expected FUV: true if PUV[i] is false, or all corresponding PUM entries are true
        for (int i = 0; i < expectedFUV.length; i++) {
            expectedFUV[i] = !puv[i] || allTrueExceptDiagonal(pum, i);
        }

        // Assert that the expected FUV matches the actual FUV
        Assertions.assertArrayEquals(expectedFUV, actualFUV,
                "FUV should be true for entries where PUV is false or all corresponding PUM entries are true.");
    }

    // Helper method to check if all elements in a PUM row, except the diagonal, are true
    private boolean allTrueExceptDiagonal(Matrix<Boolean> pum, int row) {
        for (int j = 0; j < pum.getRowCount(); j++) {
            if (row != j && !Boolean.TRUE.equals(pum.getElement(row, j))) {
                return false;
            }
        }
        return true;
    }

}
