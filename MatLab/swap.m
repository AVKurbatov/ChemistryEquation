function [ matr, cheak] = swap( matr, cheak, i, j )
    C_M = 1;
    colTmp = matr(:, i + C_M);
    matr(:, i + C_M) = matr(:, j + C_M);
    matr(:, j + C_M) = colTmp;
    
    cheak_tmp = cheak(i + C_M);
    cheak(i + C_M) = cheak(j + C_M);
    cheak(j + C_M) = cheak_tmp;
end

