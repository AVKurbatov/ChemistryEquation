function [ c, rowNum ] = topTriangulation( c )
C_M = 1;

rowNum = length(c(:,1));
colNum = length(c(1,:));

i = 0;
while i < rowNum - 1
    flag = false;
    if c(i + C_M, i + C_M) ~= 0
        flag = true;
    else
        k = i + 1;
        while k < colNum - 1 && ~flag
            if c(i + C_M, k + C_M) ~= 0
                [c, cheak] = swap(c, cheak, i, k);
                flag = true;
            else
                k = k + 1;
            end
        end
    end 
    if flag
        for j = i+1 : rowNum-1
        coeff = c(j + C_M, i + C_M)/c(i + C_M, i + C_M);
        rowJTmp = c(j + C_M, :);
        rowITmp = c(i + C_M, :);
            for k = 0 : colNum - 1
    c(j + C_M, k + C_M) = rowJTmp(k + C_M) - coeff * rowITmp(k + C_M);
            end        
        end
        i = i + 1;
    else% ¬с€ строка равна нулю
        [c, rowNum ] = deleteRow( c, rowNum, i );
        % не инекрементируем
    end
    if c(rowNum, colNum) == 0
        [c, rowNum ] = deleteRow( c, rowNum, rowNum );
    end
end

end

