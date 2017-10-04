function [ c ] = diagonalize( c )
    rowNum = length(c(:, 1));
    colNum = length(c(1, :));
    C_M = 1;
    for i = 1 : rowNum - 1
        for j = 0 : i-1
            coeff = c(j + C_M, i + C_M)/c(i + C_M, i + C_M);
            rowJTmp = c(j + C_M, :);
            rowITmp = c(i + C_M, :);
            for k = 0 : colNum - 1
    c(j + C_M, k + C_M) = rowJTmp(k + C_M) - coeff * rowITmp(k + C_M);
            end
        end
    end
end

