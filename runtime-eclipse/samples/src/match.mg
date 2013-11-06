letrec 
 	Nothing = cons Nothing {},
 	Just = \v.cons Just {value=v},
 	maybe = \m.case m of Just {value=v} => v
in 
	maybe (Just 4) 	