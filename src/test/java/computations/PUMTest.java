package test.java.computations;

import main.java.computations.PUM;
import main.java.utils.Utils.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

/**
 * PUMTest is a test class for the PUM class.
 * @author serhan
 * @date 2.02.2024
 *
 *
 * */
class PUMTest {
    @Test
    @DisplayName("Test PUM")
    void testPUM() {
        Matrix<String> lcm = new Matrix<String>(new String[][]{
                {"ANDD", "ORR", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"ORR", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"}
        }
        );
        ArrayList<Boolean> cmvRes = new ArrayList<Boolean>();
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);

        // Because the relation between 0th and 1st row is ORR, and they are both false, the result should be false.
        // So in [0][1] and [1][0] the result should be false, and the rest should be true because they are "NOTUSED".

        Matrix<Boolean> expected = new Matrix<>(new Boolean[][]{
                {null, false, true, true, true, true, true, true, true, true, true, true, true, true, true},
                {false, null, true, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, null, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, true, null, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, true, true, null, true, true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, null, true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, null, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, null, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, null, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, null, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, null, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, true, null, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, true, true, null, true, true},
                {true, true, true, true, true, true, true, true, true, true, true, true, true, null, true},
                {true, true, true, true, true, true, true, true, true, true, true, true, true, true, null}
        });

        Matrix<Boolean> actual = PUM.pum(lcm, cmvRes);
        Assertions.assertEquals(expected, actual);


        cmvRes.clear();
        cmvRes.add(true);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);

        // Because the relation between 0th and 1st row is ORR, and one of them is true, the result should be true. So
        // in [0][1] and [1][0] the result should be true, and the rest should be true because they are "NOTUSED".

        expected = new Matrix<>(new Boolean[][]{
                {null, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, null, true, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, null, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, true, null, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, true, true, null, true, true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, null, true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, null, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, null, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, null, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, null, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, null, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, true, null, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, true, true, null, true, true},
                {true, true, true, true, true, true, true, true, true, true, true, true, true, null, true},
                {true, true, true, true, true, true, true, true, true, true, true, true, true, true, null}
        });

        actual = PUM.pum(lcm, cmvRes);
        Assertions.assertEquals(expected, actual);


        lcm = new Matrix<String>(new String[][]{
                {"ANDD", "ORR", "NOTUSED", "NOTUSED", "NOTUSED", "ANDD", "ORR", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "ANDD", "ORR"},
                {"ORR", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "ANDD"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"ANDD", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"ORR", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"},
                {"NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "ANDD", "NOTUSED"},
                {"ANDD", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "ANDD", "NOTUSED", "NOTUSED"},
                {"ORR", "ANDD", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED", "NOTUSED"}
        }
        );

        cmvRes.clear();
        cmvRes.add(false);
        cmvRes.add(true);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(true);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(false);
        cmvRes.add(true);
        cmvRes.add(false);

        //  After calculating the boolean values, the result should be the following:

        expected = new Matrix<>(new Boolean[][]{
                {null, true, true, true, true, false, true, true, true, true, true, true, true, false, false},
                {true, null, true, true, true, true, true, true, true, true, true, true, true, true, false},
                {true, true, null, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, true, null, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, true, true, null, true, true, true, true, true, true, true, true, true, true},
                {false, true, true, true, true, null, true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, null, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, null, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, null, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, null, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, null, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, true, null, true, true, true},
                {true, true, true, true, true, true, true, true, true, true, true, true, null, false, true},
                {false, true, true, true, true, true, true, true, true, true, true, true, false, null, true},
                {false, false, true, true, true, true, true, true, true, true, true, true, true, true, null}
        });
        actual = PUM.pum(lcm, cmvRes);
        Assertions.assertEquals(expected, actual);

    }
}
