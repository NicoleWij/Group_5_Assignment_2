package main.java.computations;
import main.java.utils.Utils.*;


import java.util.ArrayList;

public class PUM {
    /**
     * Description: PUM is a function that takes LCM and the CMV as inputs
     * and apply logical operations from LCM matrix to the LIC boolean vector.
     * @author serhan
     * @date 30.01.2024
     * @param lcm Logical Connector Matrix
     * @param cmvRes CMV boolean vector
     * @return PUM matrix
     */
    public static Matrix<Boolean> pum(Matrix<String> lcm, ArrayList<Boolean> cmvRes) {
        Boolean[][] tmpArr = new Boolean[lcm.getRowCount()][lcm.getRowCount()];
        for (int i = 0; i < lcm.getRowCount(); i++) {
            for (int j = 0; j < lcm.getRowCount(); j++) {
                if (i == j) {
                    continue;
                }
                switch (lcm.getElement(i, j)) {
                    case "ORR" :
                        tmpArr[i][j] = cmvRes.get(i) || cmvRes.get(j);
                        break;
                    case "ANDD":
                        tmpArr[i][j] = cmvRes.get(i) && cmvRes.get(j);
                        break;
                    case "NOTUSED":
                        tmpArr[i][j] = true;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid LCM element: " + lcm.getElement(i, j));
                }
            }

        }
        return new Matrix<Boolean>(tmpArr);
    }
}

