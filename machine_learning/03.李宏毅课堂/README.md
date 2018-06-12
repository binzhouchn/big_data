# 李宏毅课堂

## 李宏毅课堂之线性代数

**1.矩阵的特性**

1) A, B, C are mxn matrices, and s and t are scalars(相当于常量) <br>
• A + B = B + A <br>
• (A + B) + C = A + (B + C) <br>
• (st)A = s(tA) <br>
• s(A + B) = sA + sB <br>
• (s+t)A = sA + tA 

2) Let A and B be k x m matrices, C be an m x n matrix, and P and Q be n x p matrices <br>
• For any scalar s, s(AC) = (sA)C = A(sC) <br>
• (A + B)C = AC + BC <br>
• C(P+Q)=CP+CQ <br>
• IA = A = AI <br>
• The product of any matrix and a zero matrix is a zero matrix

3) determinants <br>
2x2 matrix A, det(A) = ad - bc <br>
det(AB) = det(A)det(B) <br>
det(A+B) != det(A) + det(B) <br> 
det(A转置) =det(A)

4) basis <br>
A basis is the smallest generation set <br>
A basis is the largest independent set in the subspace. <br>
if S is independent --> S is basis <br>
if S is a generation set --> S is basis

**2. concluding remarks**

![concluding_remarks.png](concluding_remarks.png)

**3. consistent vs inconsistent**

• A system of linear equations is called consistent if it has one or more solutions. <br>
• A system of linear equations is called inconsistent if its solution set is empty. 

**4. gradient descent**

![gradient_descent.png](gradient_descent.png)

gradient descent有几个问题：<br> 
stuck at local minimal <br>
stuck at saddle point <br>
very slow at the plateau <br>

**5. xx**
