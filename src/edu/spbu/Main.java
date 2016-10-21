package edu.spbu;

import java.awt.*;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        DenseMatrix m1 = new DenseMatrix("MatrixExample/dense1");
        // DenseMatrix m2 = new DenseMatrix("MatrixExample/dense2");
        // m1.mulDD(m2).Matout();

        SM m3 = new SM("MatrixExample/sparse1");
        SM m4 = new SM("MatrixExample/sparse2");
       // m3.SparseOut();
      // System.out.println();
       // m4.SparseOut();
       // m3.MulSparse(m4);
       // m3.mulSD(m1);
        m3.MulSparse(m4);



    }
}
