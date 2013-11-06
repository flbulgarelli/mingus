letrec
	w = \x.w x,
	id = \x.x,
	first = \x.\y.x, 
	>= = \x.\y.prim >= x y,
	+ = \x.\y.prim + x y, 
	show = \x.prim show x,
	show4 = \x.prim show 4
in 
	show (first (>= (+ 3 4) x) @(w w))