package edu.spbu.test;

import com.sun.org.apache.xpath.internal.operations.Equals;
import edu.spbu.DenseMatrix;
import edu.spbu.SM;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatTest {

    // Проверка умножения Dense на Dense
    @Test
    public void mulDDTest() {
        DenseMatrix a = new DenseMatrix("Test/DDtest1");
        DenseMatrix b = new DenseMatrix("Test/DDtest2");
        DenseMatrix res = new DenseMatrix("Test/DDres");
        assertEquals(res, a.mul(b));
    }

    // Проверка умножения Dense на Sparse
    @Test
    public void mulDSTest() {
        DenseMatrix a = new DenseMatrix("Test/DStest1");
        SM b = new SM("Test/DStest2");
        SM res = new SM("Test/DSres");
        assertEquals(res, a.mul(b));
    }
    // Проверка умножения Sparse на Dense
    @Test
    public void mulSDTest() {
        SM a = new SM("Test/SDtest1");
        DenseMatrix b = new DenseMatrix("Test/SDtest2");
        SM res = new SM("Test/SDres");
        assertEquals(res, a.mul(b));
    }

    // Проверка умножения Sparse на Sparse
    @Test
    public void mulSSTest() {
        SM a = new SM("Test/SStest1");
        SM b = new SM("Test/SStest2");
        SM res = new SM("Test/SSres");
        assertEquals(res, a.mul(b));
    }


}
