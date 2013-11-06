package org.uqbar.mingus

import java.io.StringReader
import javax.script.ScriptEngineManager
import org.junit.Test

import static org.junit.Assert.*
import java.util.Map
class EvalTest extends AbstractMingusTest {
  
  def eval(CharSequence code) {
  	new ScriptEngineManager().getEngineByName("javascript").eval(new StringReader(translate(code).toString));
  }
   
  @Test
  def void testEval() {
  	assertEquals(eval('''
letrec 
  x = 2,
  y = 3, 
  z = \x.x,
  k = \x.\y.y 
in
  z (k x y)'''), 3.0)
  }
  
  @Test
  def void testSuspend() {
  	assertEquals(eval('''
letrec w = \x.w x in
letrec second = \x.\y.y in
  second @(w 1) 3'''), 3.0)
  }
  
  @Test
  def void testForce() {
  	assertEquals(eval('''letrec x = @3 in !x'''), 3.0)
  }
  
  @Test
  def void testArithmetic() {
    assertEquals(eval('''letrec + = \x.\y.prim + x y in + 2 3'''), 5.0)
  }

  @Test
  def void testShow() {
    assertEquals(eval('''prim show 4'''), '4')
  }

  @Test
  def void testConstruct() {
    var o = eval('''
letrec 
  Nil = cons Nil {},
  Cons = \x.\xs.cons Cons {head=x, tail=xs}
in
  Cons 1 (Cons 2 Nil)''') as Map
    assertEquals(1.0, o.get("head"))
    assertEquals(2.0, (o.get("tail") as Map).get('head'))
  }
  
  
    @Test
  def void testIf() {
    assertEquals(1.0, eval('''
letrec 
  z = \x.if (prim > x 5) 1 0 
in
  z 10'''))
  }
  
    @Test
  def void testFibonacci() {
    var o = eval('''
letrec

  == = \x.\y.prim == x y,
  - = \x.\y.prim - x y, 
  + = \x.\y.prim + x y,
  
  Tuple3 = \x.\y.\z.cons Tuple3 {val0 = x, val1 = y, val2 = z},
  
  fibo = \x.if (== x 0) 1
             (if (== x 1) 1 
               (+ (fibo (- x 1)) (fibo (- x 2))))
in
  Tuple3 (fibo 1) (fibo 2) (fibo 3)''') as Map
  
    assertEquals(1.0, o.get('val0'))
    assertEquals(2.0, o.get('val1'))
    assertEquals(3.0, o.get('val2'))
  }  
  
  @Test
  def void testVariablePattern() {
    assertEquals(1.0, eval('''
    case 1 of x => x
'''))
  }
  
    @Test
  def void testConstantPattern() {
    assertEquals(0.0, eval('''
    case 0 of 0 => 0
'''))
  }
  
      @Test
  def void testMultiPattern() {
    assertEquals(1.0, eval('''
    case 4 of 0 => 0, x => 1
'''))
  }
  
  
    @Test
  def void testTaggedPattern() {
    assertEquals(2.0, eval('''
    case (cons Just {value=2}) of Just {value=x} => x
'''))
  }
    
  
	
}