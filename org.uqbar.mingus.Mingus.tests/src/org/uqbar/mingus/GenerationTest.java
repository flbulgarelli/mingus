package org.uqbar.mingus;

import org.junit.Test;

public class GenerationTest extends AbstractMingusTest {
  @Test
  public void translatesVariable() throws Exception {
    assertGenerates("x", "x");
  }

  @Test
  public void translatesAbstraction() throws Exception {
    assertGenerates("\\x.x", "(function(x){return x})");
  }

  @Test
  public void translatesApplication() throws Exception {
    assertGenerates("x y", "x(y)");
  }

  @Test
  public void translatesApplicationWithinAbstraction() throws Exception {
    assertGenerates("\\x.x y", "(function(x){return x(y)})");
  }

  @Test
  public void translatesApplicationOfAbstraction() throws Exception {
    assertGenerates("(\\x.x) y", "(function(x){return x})(y)");
  }

  @Test
  public void translatesNumbers() throws Exception {
    assertGenerates("12", "(12)");
  }

  @Test
  public void translatesStrings() throws Exception {
    assertGenerates("'40'", "\"40\"");
  }

  @Test
  public void translatesApplicationOfThreeVariables() throws Exception {
    assertGenerates("x y z", "x(y)(z)");
  }

  @Test
  // TODO may optimize here
  public void translatesAbstractionWithinAbstraction() throws Exception {
    assertGenerates("\\x.\\y.x y", "(function(x){return (function(y){return x(y)})})");
  }

  @Test
  public void translatesLetrecsIntoScopedVars() throws Exception {
    assertGenerates("letrec x = 2 in z x", "(function(){var x=(2);return z(x)})()");
  }

  @Test
  public void translatesMultiLetrecsIntoScopedVars() throws Exception {
    assertGenerates("letrec x = 2, y = 4 in z x y", "(function(){var x=(2),y=(4);return z(x)(y)})()");
  }

  @Test
  // TODO may optimize here
  public void translatesNestedLetrecsIntoNestedScopedVars() throws Exception {
    assertGenerates(
      "letrec x = f in letrec z = 3 in x z",
      "(function(){var x=f;return (function(){var z=(3);return x(z)})()})()");
  }

  @Test
  public void translatesSuspendsIntoNullaryFunctions() throws Exception {
    assertGenerates("@2", "(function(){return (2)})");
  }

  @Test
  public void translatesForcingsIntoNullaryFunctionApplication() throws Exception {
    assertGenerates("!x", "x()");
  }

  @Test
  public void translatesIds() throws Exception {
    assertGenerates("+ 2", "__plus((2))");
    assertGenerates("++ 2", "__plus__plus((2))");
    assertGenerates("$@>> 2", "__$__at__gt__gt((2))");
    assertGenerates("<= x", "__lt__eq(x)");
    assertGenerates("~! x", "__newf__bang(x)");
  }

  @Test
  public void translatesArithmeticPrimitives() throws Exception {
    assertGenerates("prim + 2 x", "((2) + x)");
    assertGenerates("prim * 2 x", "((2) * x)");
    assertGenerates("prim / 2 x", "((2) / x)");
    assertGenerates("prim - 2 x", "((2) - x)");
  }

  @Test
  public void translatesComparationPrimitives() throws Exception {
    assertGenerates("prim >= 2 x", "((2) >= x)");
    assertGenerates("prim > 2 x", "((2) > x)");
    assertGenerates("prim < 2 x", "((2) < x)");
    assertGenerates("prim <= 2 x", "((2) <= x)");
    assertGenerates("prim == 2 x", "((2) === x)");
    assertGenerates("prim /= 2 x", "((2) !== x)");
  }

  @Test
  public void translatesEmptyConstructorIntoObjectCreation() throws Exception {
    assertGenerates("cons nothing {}", "{tag:'nothing'}");
  }

  @Test
  public void translatesSingletonConstructorIntoObjectCreation() throws Exception {
    assertGenerates("cons some {value = 1}", "{tag:'some',value:(1)}");
  }

  @Test
  public void translatesGeneralConstructorIntoObjectCreation() throws Exception {
    assertGenerates("cons foo {x = 1, y = 2}", "{tag:'foo',x:(1),y:(2)}");
  }

  @Test
  public void translatesIfsIntoIfExpressions() throws Exception {
    assertGenerates("if x z k", "(function(){if(x){return z;}else{return k;}})()");
  }

  @Test
  public void translatesNestedIfsIntoNestedIfExpressions() throws Exception {
    assertGenerates(
      "if x z (if k j p)",
      "(function(){if(x){return z;}else{return (function(){if(k){return j;}else{return p;}})();}})()");
  }
  
  
  @Test
  public void translatesLiteralPatternsIntoComparisons() throws Exception {
    assertGenerates(
      "case 1 of 2 => 3",
      "(function($){if($===(2)){return (3);};fail();})((1))");
  }
  
  @Test
  public void translatesVariablePatternsIntoPassing() throws Exception {
    assertGenerates(
      "case 1 of x => x",
      "(function($){return (function(){var x=$;return x;})();fail();})((1))");
  }
  
  @Test
  public void translatesTaggedPatternsIntoTagComparison() throws Exception {
    assertGenerates(
      "case Cons 2 of Cons {head=x, tail=xs} => x",
      "(function($){if($.tag==='Cons'){return (function(){var x=$.head;var xs=$.tail;return x;})()};fail();})(Cons((2)))");
  }
  

}
