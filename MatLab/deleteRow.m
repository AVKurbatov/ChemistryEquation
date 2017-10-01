function [ c, rowNum ] = deleteRow( c, rowNum, i )
    C_M = 1;
    if i > 0
        c_tmp((0 + C_M) : (i - 1 + C_M), :) = c((0 + C_M) : (i - 1 + C_M), :);
    end
    c_tmp((i + C_M):(rowNum-2 + C_M), :)     = c((i + 1 + C_M):(rowNum-1 + C_M), :);

    c = c_tmp;
    rowNum = rowNum - 1;
end

