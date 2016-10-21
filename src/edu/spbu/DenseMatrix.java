
package edu.spbu;
/**
 * Created by 1111111 on 06.10.2016.
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class DenseMatrix implements Matrix {
    // Наша матрица, с которой будем работать
    double[][] matrixD;
    // Размер матрицы
    int size;

    // Конструктор, обращаемся по размеру
    public DenseMatrix(int x, int y) {
        matrixD = new double[x][y];
    }

    // Конструктор, обращаемся по файлу
    public DenseMatrix(String file) {
        readDense(file);
    }

    // Находим размер матрицы
    public int getSize(String file) {
        int size = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String str = in.readLine();
            String[] arr = str.split("\\s+");
            size = arr.length;
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    // Считываем матрицу
    public void readDense(String file) {
        this.size = getSize(file);
        Scanner in = null;
        try {
            in = new Scanner(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.matrixD = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixD[i][j] = in.nextDouble();
            }
        }
        in.close();
    }


    // Выводим матрицу на экран
    public void DenseOut() {
        for (int i = 0; i < matrixD.length; i++) {
            for (int j = 0; j < matrixD[0].length; j++) {
                System.out.print(matrixD[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Как следует перемножать
    public Matrix mul(Matrix other) {
        if (other instanceof DenseMatrix)
            return this.mulDD((DenseMatrix) other);

        else if (other instanceof SM)
            return this.mulDS((SM) other);

        else return null;
    }

    // Перемножение Dense на Dense
    public DenseMatrix mulDD(DenseMatrix other) {
        if (matrixD[0].length == other.matrixD.length) {
            DenseMatrix res = new DenseMatrix(matrixD.length, other.matrixD[0].length);
            for (int i = 0; i < matrixD.length; i++) {
                for (int j = 0; j < other.matrixD[0].length; j++) {
                    res.matrixD[i][j] = 0.0;
                    for (int k = 0; k < matrixD[0].length; k++) {
                        res.matrixD[i][j] = res.matrixD[i][j] + this.matrixD[i][k] * other.matrixD[k][j];

                    }
                }

            }

            res.DenseOut();
            return res;
        } else {
            System.out.println("This matrix cant be multiplied");
            return null;
        }
    }

    // Перемножение Dense на Sparse
    public SM mulDS(SM other) {

        SM res = new SM(this.size);

        for (Point coordinate : other.MatrixS.keySet()) {
            for (int j = 0; j < this.size; j++) {
                Point v = new Point(j, coordinate.y);
                if (res.MatrixS.get(v) == null) {

                    res.MatrixS.put(v, other.MatrixS.get(coordinate) * this.matrixD[j][coordinate.x]);

                } else {
                    res.MatrixS.put(v, res.MatrixS.get(v) + other.MatrixS.get(coordinate) * this.matrixD[j][coordinate.x]);
                }
            }
        }

        Iterator<Point> it = res.MatrixS.keySet().iterator();
        while (it.hasNext()) {
            if (res.MatrixS.get(it.next()).equals(0.0))
                it.remove();
        }
        res.SparseOut();
        return res;
    }

    // Метод equals для Dense Matrix
    public boolean equals(Object o) {
        if (!(o instanceof DenseMatrix)) {
            return false;
        }
        DenseMatrix other = (DenseMatrix) o;
        boolean point = true;
        if (matrixD.length == other.matrixD.length && matrixD[0].length == other.matrixD[0].length) {
            for (int i = 0; i < matrixD.length; i++)
                for (int j = 0; j < matrixD[0].length; j++) {
                    if (matrixD[i][j] != other.matrixD[i][j])
                        point = false;
                }
        } else {
            System.out.println("This matrix is not equal");
            point = false;
        }
        return point;
    }

    @Override
    public String toString() {
        return "Dense Matrix";
    }

}

