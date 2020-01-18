package avkurbatov_home.chemistry_equation.math;

import java.util.*;
import java.util.stream.Collectors;

public class RationalMatrix {
    private final List<List<Rational>> matrix;
    private final List<Integer> currentOrderOfColumns;

    private int rowsNumber;
    private final int colsNumber;

    public RationalMatrix(List<List<Rational>> matrix) {
        validateArray(matrix);

        rowsNumber = matrix.size();
        colsNumber = matrix.get(0).size();

        this.matrix = matrix;

        currentOrderOfColumns = new ArrayList<>(colsNumber);
        for(int i = 0; i < colsNumber; ++i) {
            currentOrderOfColumns.add(i);
        }
    }


    /**
     * @Returns list of solutions of equation
     * */
    public List<List<Long>> solveEquationSystem() {
        performUpperTriangulation();
        performDiagonalization();
        final RationalMatrix solutionsCombination = calculateSolutionsCombination();
        List<List<Long>> solutions = new ArrayList<>(solutionsCombination.colsNumber);

        for(int solutionCount = 0; solutionCount < solutionsCombination.colsNumber; ++solutionCount) {
            solutions.add(convertToLongsArray( solutionsCombination.getColumn(solutionCount) ));
        }
        deleteZeroRows(solutions);

        decreaseMinusSignsNumber(solutions);

        return solutions;
    }

    public List<List<Rational>> getMatrix() {
        return matrix;
    }

    public List<Integer> getCurrentOrderOfColumns() {
        return currentOrderOfColumns;
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public int getColsNumber() {
        return colsNumber;
    }

    private Rational value(final int rowCount, final int colCount){
        return matrix.get(rowCount).get(colCount);
    }

    private void setValue(final int rowCount, final int colCount, Rational rational){
        matrix.get(rowCount).set(colCount, rational);
    }

    /**
     * Input matrix validation method.
     * The matrix can be not valid only because of a developer mistake, so it throws several unchecked exceptions
     * */
    private static void validateArray(List<List<Rational>> matrix) {
        if (matrix == null) {
            throw new NullPointerException("Unable to use null matrix");
        }

        final int colsNumber = matrix.get(0).size();

        for (List<Rational> row : matrix) {
            if (row.size() != colsNumber) {
                throw new IllegalArgumentException("Matrix must be rectangular");
            }
        }
    }

    private List<Rational> getColumn(int colCount) {
        if (colCount < 0 || colCount >= colsNumber) {
            throw new ArrayIndexOutOfBoundsException("Unable to find column number " + colCount + " in " + this);
        }

        return matrix.stream().map(row -> row.get(colCount)).collect(Collectors.toList());
    }

    private void deleteZeroRows(List<List<Long>> solutions) {
        solutions.removeIf(solution -> solution.stream().allMatch(coefficient -> coefficient.equals(0L)));
    }

    private void decreaseMinusSignsNumber(List<List<Long>> solutions) {
        solutions.forEach(row -> {
            int positiveValuesCount = 0;
            int nonZeroValuesCount = 0;
            for (long value : row) {
                if (value != 0) {
                    ++nonZeroValuesCount;
                }
                if (value > 0) {
                    ++positiveValuesCount;
                } else if(value < 0) {
                    --positiveValuesCount;
                }
            }
            if (areThereTooMuchMinusSigns(positiveValuesCount, nonZeroValuesCount)) {
                changeSignInTheRow(row);
            }
        });
    }

    private static boolean areThereTooMuchMinusSigns(int plusCount, int notZero) {
        return notZero != 0 && plusCount * 2 < notZero;
    }

    /**Changing sign in the whole row*/
    private static void changeSignInTheRow(final List<Long> row) {
        for(int rowCount = 0; rowCount < row.size(); ++rowCount) {
            row.set(rowCount, -row.get(rowCount));
        }
    }

    /**
     * Converts the matrix to the upper triangulation view.
     * */
    void performUpperTriangulation() {
        int rowCount = 0;
        while (rowCount < rowsNumber - 1) {
            boolean isDiagonalElementNonZero = false;
            if (value(rowCount, rowCount).notEqualsZero()) {
                isDiagonalElementNonZero = true;
            } else {
                int colCount = rowCount + 1;
                while (colCount < colsNumber - 1 && !isDiagonalElementNonZero) {
                    if (value(rowCount, colCount).notEqualsZero()) {
                        swapColumns(rowCount, colCount);
                        isDiagonalElementNonZero = true;
                    } else {
                        ++colCount;
                    }
                }
            }
            if (isDiagonalElementNonZero) {
                for (int innerRowCount = rowCount + 1; innerRowCount < rowsNumber; ++innerRowCount) {
                    final Rational value = value(innerRowCount, rowCount).divide(value(rowCount, rowCount));
                    final List<Rational> rowInner = matrix.get(innerRowCount);
                    final List<Rational> rowOuter = matrix.get(rowCount);

                    for (int colCount = 0; colCount < colsNumber; ++colCount) {
                        setValue(innerRowCount, colCount, rowInner.get(colCount).minus(value.multiply(rowOuter.get(colCount))));
                    }
                }
                ++rowCount;
            } else {// that means that whole string equals zero
                deleteRow(rowCount);
            // here rowCount is not incrementing because rowsNumber is decreasing
            }
        }
        if(value(rowsNumber-1, colsNumber-1).equalsZero()) {
            deleteRow(rowsNumber - 1);
        }
    }

    /**
     * Converts the matrix to diagonal view.
     * Must be called only after performUpperTriangulation().
     * */
    void performDiagonalization() {
        for (int rowCount = 1; rowCount < rowsNumber; ++rowCount) {
            for (int innerRowCount = 0; innerRowCount < rowCount; ++innerRowCount) {
                final Rational value = value(innerRowCount, rowCount).divide(value(rowCount, rowCount));
                final List<Rational> rowInner = new ArrayList<>(matrix.get(innerRowCount));
                final List<Rational> rowOuter = new ArrayList<>(matrix.get(rowCount));

                for (int colsCount = 0; colsCount < colsNumber; ++colsCount) {
                    setValue(innerRowCount, colsCount, rowInner.get(colsCount).minus(value.multiply(rowOuter.get(colsCount))));
                }
            }
        }
    }

    /**
     * Calculates matrix with solutions combination.
     * The solutions combination matrix is transposed for the further convenience.
     * Must be called only after performDiagonalization()
     * */
    private RationalMatrix calculateSolutionsCombination() {
        final List<List<Rational>> combination = new ArrayList<>(colsNumber);
        for (int colsCount = 0; colsCount < colsNumber; ++colsCount) {
            combination.add(new ArrayList<>(colsNumber - rowsNumber));
        }

        for (int rowCount = 0; rowCount < colsNumber - rowsNumber; ++rowCount) {
            for (int colsCount = 0; colsCount < rowsNumber; ++colsCount) {
                combination.get(colsCount).add(value(colsCount, rowsNumber + rowCount).divide(value(colsCount, colsCount)));
            }
            for (int colsCount = rowsNumber; colsCount < colsNumber; ++colsCount) {
                if (colsCount == rowsNumber + rowCount) {
                    combination.get(colsCount).add(Rational.of(-1));
                } else {
                    combination.get(colsCount).add(Rational.of(0));
                }
            }
        }

        return new RationalMatrix(returnOrderForCombination(combination));
    }

    private void deleteRow(int rowCount) {
        if (rowCount < 0 || rowCount >= rowsNumber) {
            throw new ArrayIndexOutOfBoundsException();
        }
        --rowsNumber;
        matrix.remove(rowCount);
    }

    /**
     * Method restores original column order using currentOrderOfColumns
     * */
    private List<List<Rational>> returnOrderForCombination(List<List<Rational>> combination) {

        final List<List<Rational>> newCombination = new ArrayList<>(colsNumber);
        for (int colsCount = 0; colsCount < colsNumber; ++colsCount) {
            newCombination.add(new ArrayList<>(colsNumber - rowsNumber));

        }

        for (int colsCount = 0; colsCount < colsNumber; ++colsCount) {
            newCombination.set(currentOrderOfColumns.get(colsCount), combination.get(colsCount));
        }

        return newCombination;
    }

    private void swapColumns(int firstCol, int secondCol) {
        if (firstCol < 0 || secondCol < 0 || firstCol >= colsNumber || secondCol >= colsNumber) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for (int rowCount = 0; rowCount < rowsNumber; ++rowCount) {
            Rational rational = value(rowCount, firstCol);
            setValue(rowCount, firstCol, value(rowCount, secondCol));
            setValue(rowCount, secondCol, rational);
        }
        Collections.swap(currentOrderOfColumns, firstCol, secondCol);
    }

    /**
     * First rationals are reduced to a common denominator,
     * second the common denominator is discarded.
     * */
    private static List<Long> convertToLongsArray(List<Rational> rationals) {
        final int length = rationals.size();
        final List<Long> longs = new ArrayList<>(length);

        long commonDenominator = 1;
        for (Rational rational: rationals) {
            commonDenominator *= rational.getDenominator();
        }

        for (Rational rational: rationals) {
            longs.add(commonDenominator * rational.getNumerator() / rational.getDenominator());
        }

        cutLongsByACommonFactor(longs);

        return longs;
    }

    private static void cutLongsByACommonFactor(List<Long> longs) {
        final int size = longs.size();
        final List<Integer> signs = new ArrayList<>(size);
        for (int longsCount = 0; longsCount < size; ++longsCount) {
            long value = longs.get(longsCount);
            if (value > 0) {
                signs.add(1);
            } else {
                longs.set(longsCount, -value);
                signs.add(-1);
            }
        }

        cutPositiveLongsByACommonFactor(longs);

        for (int longsCount = 0; longsCount < size; ++longsCount) {
            longs.set(longsCount, longs.get(longsCount)*signs.get(longsCount));
        }
    }

    private static void cutPositiveLongsByACommonFactor(List<Long> longs) {
        long min = findMinValue(longs);

        int divider = 2;
        while (divider <= min) {
            boolean areAllLongsDividable = true;
            for (long value : longs) {
                areAllLongsDividable &= (value % divider == 0);
            }
            if (areAllLongsDividable) {
                divideArray(longs, divider);
                min /= divider;
            } else {
                ++divider;
            }
        }
    }

    private static long findMinValue(List<Long> longs) {
        long min = longs.get(0);
        for (long value: longs) {
            if(min < value) {
                min = value;
            }
        }
        return min;
    }

    private static void divideArray(List<Long> longs, int divider) {
        for (int count = 0; count < longs.size(); ++count) {
            longs.set(count, longs.get(count)/divider);
        }
    }

}
