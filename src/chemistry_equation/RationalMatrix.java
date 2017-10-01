package chemistry_equation;

/**
 * Created by Александр on 27.09.2017.
 */
public class RationalMatrix {
    // encapsulation of rectangle matrix
    private Rational[][] matrix;
    private int rowNum;
    private int colNum;
    private int check[];

    public RationalMatrix(Rational[][] matrix){
        if (matrix == null)
            throw new ArrayIndexOutOfBoundsException();

        rowNum = matrix.length;
        colNum = matrix[0].length;

        for(Rational[] row : matrix){
            if(row.length != colNum)
                throw new ArrayIndexOutOfBoundsException();
        }
        this.matrix = new Rational[rowNum][colNum];
        for(int i = 0; i < rowNum; ++i)
            System.arraycopy(matrix[i], 0, this.matrix[i],0, colNum);

        check = new int[colNum];
        for(int i = 0; i < colNum; ++i)
            check[i] = i;
    }

    public int getRowNum(){
        return rowNum;
    }
    public int getColNum(){
        return colNum;
    }
    public Rational[][] getMatrix(){
        return matrix;
    }
    public Rational get(int i, int j){
        if (i < 0 || j < 0 || i >= rowNum || j >= colNum )
            throw new ArrayIndexOutOfBoundsException();
        return matrix[i][j];
    }
    public Rational[] getRow(int i){
        if (i < 0 || i >= rowNum )
            throw new ArrayIndexOutOfBoundsException();
        return matrix[i];
    }
    public Rational[] getCol(int j){
        if (j < 0 || j >= colNum )
            throw new ArrayIndexOutOfBoundsException();

        Rational[] column = new Rational[rowNum];

        for(int i = 0; i < rowNum; ++i)
            column[i] = matrix[i][j];

        return column;
    }

//    public static void main(String[] args) {
//        Rational[][] matrix = new Rational[3][4];
//
//        matrix[0][0] = new Rational(-4, -11);
//        matrix[0][1] = new Rational(-3, -10);
//        matrix[0][2] = new Rational(-1, 1);
//        matrix[0][3] = new Rational( 2, -2);
//
//        matrix[1][0] = new Rational(-3, -1);
//        matrix[1][1] = new Rational( 2, -2);
//        matrix[1][2] = new Rational(1, 3);
//        matrix[1][3] = new Rational( 3, -2);
//        System.arraycopy(matrix[0], 0, matrix[1], 0, 4);
//
//        matrix[2][0] = new Rational(-3, -1);
//        matrix[2][1] = new Rational( 0, -2);
//        matrix[2][2] = new Rational(5, 3);
//        matrix[2][3] = new Rational( 2, -2);
//
//        RationalMatrix ratMatr = new RationalMatrix(matrix);
//
//        ratMatr.printMatrDouble();
//
////        ratMatr.topTriangulation();
////        ratMatr.diagonalize();
////        System.out.println();
////        ratMatr.printMatrDouble();
//
//        System.out.println();
//        long[][] rez = ratMatr.solveEquationSystem();
//        for(long[] row : rez){
//            for(long val : row){
//                System.out.print(val + ", ");
//            }
//            System.out.println();
//        }
//
//    }

    public long[][] solveEquationSystem(){
//        System.out.println("before");
//        printMatr();
        topTriangulation();
//        System.out.println("Tridiag:");
//        printMatr();
        diagonalize();
//        System.out.println("Diag:");
//        printMatr();
        RationalMatrix kombination = makeKomb();
        long[][] result = new long[kombination.colNum][kombination.rowNum];
        for(int i = 0; i < kombination.colNum; ++i){
            result[i] = Rational.makeLongMas( kombination.getCol(i) );
        }
        return result;
    }

    private void topTriangulation(){
        Rational[] rowJ = new Rational[colNum];
        Rational[] rowI = new Rational[colNum];

        int i = 0;
        while(i < rowNum - 1){
            boolean flag = false;
            if (!matrix[i][i].isZero()) {
                flag = true;
            }else {
                int k = i + 1;
                while (k < colNum - 1 && !flag) {
                    if (!matrix[i][k].isZero()) {
                        swap(i, k);
                        flag = true;
                    } else {
                        k = k + 1;
                    }
                }
            }
            if (flag) {
                for (int j = i + 1; j < rowNum; ++j) {
                    Rational koeff = matrix[j][i].divide(matrix[i][i]);
                    System.arraycopy(matrix[j], 0, rowJ, 0, colNum);
                    System.arraycopy(matrix[i], 0, rowI, 0, colNum);
                    for (int g = 0; g < colNum; ++g) {
                        matrix[j][g] = rowJ[g].minus(koeff.multiply(rowI[g]));
                    }
                }
                i = i + 1;
            }else{// whole string equals zero
                deleteRow(i);
            //here i is not incrementing, rowNum is decreasing
            }
        }
        if(matrix[rowNum-1][colNum-1].isZero())
            deleteRow(rowNum-1);
    }

    private void diagonalize(){
        Rational[] rowJ = new Rational[colNum];
        Rational[] rowI = new Rational[colNum];

        for (int i = 1; i < rowNum; ++i){
            for (int j = 0; j < i; ++j){
                Rational koeff = matrix[j][i].divide(matrix[i][i]);
                System.arraycopy(matrix[j], 0, rowJ, 0, colNum);
                System.arraycopy(matrix[i], 0, rowI, 0, colNum);
                for (int k = 0; k < colNum; ++k){
                    matrix[j][k] = rowJ[k].minus(koeff.multiply(rowI[k]));
                }
            }
        }
    }

    private RationalMatrix makeKomb(){
        /*Kombination matrix is transposed*/
        Rational[][] komb = new Rational[colNum][colNum - rowNum];

        for (int i = 0; i < colNum - rowNum; ++i){
            for (int j = 0; j < rowNum; ++j){
                komb[j][i] = matrix[j][rowNum + i].divide(matrix[j][j]);
            }
            for (int j = rowNum; j < colNum; ++j) {
                if(j == rowNum + i)
                    komb[j][i] = new Rational(-1);
                else
                    komb[j][i] = new Rational(0);
            }
        }

        komb = returnOrderForKomb(komb);
        RationalMatrix rationalMatrix = new RationalMatrix(komb);
        return rationalMatrix;
    }

    private void deleteRow(int i){
        if(i < 0 || i >= rowNum)
            throw new ArrayIndexOutOfBoundsException();
        --rowNum;
        System.arraycopy(matrix, i + 1, matrix, i, rowNum - i);
    }

    private Rational[][] returnOrderForKomb(Rational[][] komb){

        Rational[][] newKomb = new Rational[colNum][colNum - rowNum];

        for (int j = 0; j < colNum; ++j)
            for(int i = 0; i < colNum - rowNum; ++i)
                System.arraycopy(komb[j], 0, newKomb[check[j]], 0, colNum - rowNum);


        return newKomb;
    }


    private void swap(int i, int j){
        if(i < 0 || j < 0 || i >= colNum || j >= colNum)
            throw new ArrayIndexOutOfBoundsException();

        int rowNum = matrix.length;
        for(int k = 0; k < rowNum; ++k) {
            Rational colTmp = matrix[k][i];
            matrix[k][i] = matrix[k][j];
            matrix[k][j] = colTmp;
        }
        int checkTmp = check[i];
        check[i] = check[j];
        check[j] = checkTmp;
    }

    public void printMatr() {
        for(int i = 0; i < rowNum; ++i){
            for(int j = 0; j < colNum; ++j)
                System.out.print(matrix[i][j].toString() + ", ");
            System.out.println();
        }
    }
    public void printMatrDouble() {
        for(int i = 0; i < rowNum; ++i){
            for(int j = 0; j < colNum; ++j)
                System.out.print(matrix[i][j].doubleValue() + ", ");

            System.out.println();
        }
    }

}
