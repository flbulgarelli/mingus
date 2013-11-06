letrec 
  Nil = cons Nil {},
  Cons = \x.\xs.cons Cons {head=x, tail=xs}
in
  Cons 1 (Cons 2 Nil)