function c = returnOrder( c, cheak )
    C_M = 1;
    c_tmp = c;
    for i = 0 : length(c(1, :)) - 1
        c(:, cheak(i + C_M) + C_M) = c_tmp(:, i + C_M);
    end
end

