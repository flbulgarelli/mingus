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
    assertGenerates("12", "12");
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
    assertGenerates("letrec x = 2 in z x", "(function(){var x=2;return z(x)})()");
  }

  @Test
  // TODO may optimize here
  public void translatesNestedLetrecsIntoNestedScopedVars() throws Exception {
    assertGenerates(
      "letrec x = f in letrec z = 3 in x z",
      "(function(){var x=f;return (function(){var z=3;return x(z)})()})()");
  }
  
  @Test
  public void translatesSuspendsIntoNullaryFunctions() throws Exception {
    assertGenerates("@2", "(function(){return 2})");
  }

  @Test
  public void translatesForcingsIntoNullaryFunctionApplication() throws Exception {
    assertGenerates("!x", "x()");
  }

}
