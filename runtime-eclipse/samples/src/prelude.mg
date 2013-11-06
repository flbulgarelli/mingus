letrec
  Cons = \x.\xs.cons Cons {head=x, tail=xs},
  Nil = cons Nil {},
   
  filter = \f.\xs.case xs of
  	Nil {} => Nil,
  	Cons {head=x, tail=xs} => 
  		if (f x) 
  		  (Cons x @(filter f !xs)) 
  		  (filter f !xs),
  		  
  map = \f.\xs.case xs of
  	Nil {} => Nil,
  	Cons {head=x, tail=xs} => Cons (f x) @(map f !xs),
  	
  + = \x.\y.prim + x y,
  - = \x.\y.prim - x y,
  
  w = \x.w x,
  
  head = \xs.case xs of
     Cons {head=x} => x,
  tail = \xs.case xs of 
      Cons {tail=xs} => !xs,
      
  !! = \idx.\xs.case idx of
     0 => head xs,
     n => !! (- idx 1) (tail xs)
in
  !! 1 (Cons 1 @(Cons 3 @Nil)) 
--!! 0 (map (+ 1) (Cons 2 (Cons 2 (Cons 3 (Cons 4 @(w 1))))))