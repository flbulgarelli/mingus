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
    assertEquals(eval('''letrec + = \x.\y.prim '+' x y in + 2 3'''), 5.0)
  }

  @Test
  def void testShow() {
    assertEquals(eval('''prim 'show' 4'''), '4')
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
  
	
}