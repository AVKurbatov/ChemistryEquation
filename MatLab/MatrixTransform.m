clear
clc
C_M = 1;
W = 3; % количество уравнений и переменных

% c0 = randn(rowNum, colNum); % система уравнений со свободными членами
c0(1, :) = [1, 1, 0, -1];
c0(2, :) = [0, 1, 2, -2];
% c0(1, :) = [0.363636363, 0.3, -1,- 1];
% c0(2, :) = c0(1, :);
% c0(3, :) = [3.0, 0.0, 1.666666666667, -1];

rowNum = length(c0(:,1));
colNum = length(c0(1,:));

c = c0;
cheak = (0 : colNum - 1);
% Верхнетреуголизация
[ c, rowNum ] = topTriangulation( c );
% Диагонализация
c = diagonalize( c );

komb = zeros(colNum, colNum - rowNum);
for i = 0 : colNum - rowNum - 1
    for j = 0 : rowNum - 1
        komb(j + C_M, i + C_M) = c(j + C_M, rowNum + i + C_M) ...
            / c(j + C_M, j + C_M);
    end
    komb(rowNum + i + C_M, i + C_M) = -1;
end
komb = returnOrder(komb.', cheak).';
c0*komb;
-komb


% % Приведение к единичному виду
% for i = 0 : rowNum - 1
%     c(i + C_M, colNum) = c(i + C_M, colNum) / c(i + C_M, i + C_M);
%     c(i + C_M, i + C_M) = 1;
% end
% 
% c0(1:rowNum, 1:rowNum)*c(1:W, W+1) - c0(1:W, W+1) % Ответ метода Гаусса

%%
rowNum = 3;
colNum = rowNum + 2;
c0 = randn(rowNum, colNum);
c = c0;
cheak = (0 : colNum - 1);
[c, cheak] = swap(c, cheak, 0, 2);
[c, cheak] = swap(c, cheak, 0, 3);
[c, cheak] = swap(c, cheak, 1, 2);
c = returnOrder(c, cheak);
c0
c
