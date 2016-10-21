package edu.spbu;

public interface Matrix {
    // Интерфейс
    Matrix mul(Matrix other);

    @Override
    boolean equals(Object o);
}
