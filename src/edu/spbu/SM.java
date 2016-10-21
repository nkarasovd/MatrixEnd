package edu.spbu;

/**
 * Created by 1111111 on 06.10.2016.
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.MarshalException;
import java.util.*;

import static java.lang.Double.parseDouble;

public class SM implements Matrix {


    public SM(String file) {
        readSparse(file);
    }

    public SM(int size) {
        this.MatrixS = new HashMap<>();
        this.size = size;

    }

    HashMap<Point, Double> MatrixS = new HashMap<Point, Double>();

    Point P = new Point();

    int size;


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

    // Заполняем HashMap
    public void readSparse(String file) {
        this.size = getSize(file);
        this.P = new Point();
        Scanner in = null;
        try {
            in = new Scanner(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (in.hasNextLine()) {

            String[] s = in.nextLine().split(" ");
            size = s.length;
            for (int j = 0; j < s.length; j++) {
                if (parseDouble(s[j]) != 0.0) {
                    P.x = i;
                    P.y = j;
                    this.MatrixS.put(this.P, parseDouble(s[j]));

                }
            }
            i++;
        }
    }

    // Выводим sparse matrix на экран
    public void SparseOut() {
        this.P = new Point();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.P.x = i;
                this.P.y = j;
                if (this.MatrixS.get(this.P) != null)
                    System.out.print(MatrixS.get(this.P) + " ");


                else System.out.print(0.0 + " ");

            }
            System.out.println();
        }

    }

    // Как следует перемножать
    public Matrix mul(Matrix other) {
        if (other instanceof SM) {
            return (Matrix) ((SM) other).MulSparse(this);
        } else {
            if (other instanceof DenseMatrix)
                return this.mulSD((DenseMatrix) other);

            else return null;
        }
    }


    // Перемножение двух Sparse Matrix (надо доделать)
    public SM MulSparse(SM other) {
        SM res = new SM(size);
        HashMap<Point, Double> MatrixSss = new HashMap<Point, Double>();
        Iterator<HashMap.Entry<Point, Double>> IMatrix1 = this.MatrixS.entrySet().iterator();
        while (IMatrix1.hasNext()) {
            HashMap.Entry<Point, Double> e1 = IMatrix1.next();
            Point h1 = new Point();
            h1 = e1.getKey();
            Iterator<HashMap.Entry<Point, Double>> IMatrix2 = other.MatrixS.entrySet().iterator();
            while (IMatrix2.hasNext()) {
                HashMap.Entry<Point, Double> e2 = IMatrix2.next();
                Point h2 = e2.getKey();
                if (h1.x == h2.y) {
                    Point h3 = new Point();
                    Double r = 0.0;
                    MatrixSss.put(h3,r);
                if (this.MatrixS.get(h1) != null && other.MatrixS.get(h2) != null) {

                    h3.x = h1.x;
                    h3.y = h2.y;

                    r = r + e1.getValue()*e2.getValue();
                    MatrixSss.put(h3,r);
                }


                }
            }
        }
        res.MatrixS =  MatrixSss;
        res.SparseOut();
        return res;
    }

    // Умножение Sparse на Dense
    public SM mulSD(DenseMatrix other) {

        SM res = new SM(this.size);
        for (Point coordinate : this.MatrixS.keySet()) {
            for (int j = 0; j < other.size; j++) {
                Point v = new Point(coordinate.x, j);
                if (res.MatrixS.get(v) == null) {

                    res.MatrixS.put(v, this.MatrixS.get(coordinate) * other.matrixD[coordinate.y][j]);

                } else {
                    res.MatrixS.put(v, res.MatrixS.get(v) + this.MatrixS.get(coordinate) * other.matrixD[coordinate.y][j]);
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

    // Метод equals для Sparse Matrix
    public boolean equals(Object o) {
        if (!(o instanceof SM)) {
            return false;
        }
        SM other = (SM) o;
        if (this.MatrixS.size() == other.MatrixS.size()) {
            for (Point Coord : this.MatrixS.keySet()) {

                if (other.MatrixS.get(Coord) == null) {
                    // System.out.println("NULL");
                    return false;
                } else if (!(this.MatrixS.get(Coord).equals(other.MatrixS.get(Coord)))) {
                    // System.out.println("Разные матрицы");
                    return false;
                }

            }
        } else
            return false;


        return true;
    }


    @Override
    public String toString() {
        return "Sparse Matrix";
    }
}
