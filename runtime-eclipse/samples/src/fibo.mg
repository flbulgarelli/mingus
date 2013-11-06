letrec
  == = \x.\y.prim == x y,
  - = \x.\y.prim - x y, 
  + = \x.\y.prim + x y,
  
  Tuple3 = \x.\y.\z.cons Tuple3 {val0 = x, val1 = y, val2 = z},
  
  fibo = \x.if (== x 0) 1
             (if (== x 1) 1 
               (+ (fibo (- x 1)) (fibo (- x 2))))
in
  Tuple3 (fibo 1) (fibo 2) (fibo 3) 