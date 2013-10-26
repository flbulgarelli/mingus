package org.uqbar.mingus;

import org.junit.Test;
import org.uqbar.mingus.mingus.Abstraction;
import org.uqbar.mingus.mingus.Application;
import org.uqbar.mingus.mingus.Letrec;
import org.uqbar.mingus.mingus.NumberLiteral;
import org.uqbar.mingus.mingus.Variable;

public class ParseTest extends AbstractMingusTest {

  @Test
  public void canParseVariable() {
    assertCanParse("x", Variable.class);
  }

  @Test
  public void canParseApplicationOfTwoVariables() {
    assertCanParse("x y", Application.class);
  }

  @Test
  public void canParseApplicationOfThreVariables() {
    assertCanParse("x y z", Application.class);
  }

  @Test
  public void canParseApplicationOfThreVariablesWithParenthesisOnFirstPair() {
    assertCanParse("(x y) z", Application.class);
  }

  @Test
  public void canParseApplicationOfThreVariablesWithParenthesisOnSecondPair() {
    assertCanParse("x (y z)", Application.class);
  }

  @Test
  public void canParseAbstraction() {
    assertCanParse("\\x.y", Abstraction.class);
  }

  @Test
  public void canParseVariableWithParenthesis() {
    assertCanParse("(x)", Variable.class);
  }

  @Test
  public void canParseApplicationWithParenthesis() {
    assertCanParse("(x y)", Application.class);
  }

  @Test
  public void canParseAbstractionWithParenthesis() {
    assertCanParse("(\\x.y)", Abstraction.class);
  }

  @Test
  public void canParseAbstractionApplication() {
    assertCanParse("(\\x.y) z", Application.class);
  }

  @Test
  public void canParseApplicationWithinAbstraction() {
    assertCanParse("\\x.y z", Abstraction.class);

  }

  @Test
  public void canParseAbstractionWithinAbstraction() {
    assertCanParse("\\x.\\y.x y", Abstraction.class);
  }

  @Test
  public void canParseNumberLiterals() {
    assertCanParse("10", NumberLiteral.class);
  }

  @Test
  public void canParseSimpleLetrecs() {
    assertCanParse("letrec x = 2 in x", Letrec.class);
  }

  @Test
  public void canParseLetercsWithinParenthesis() {
    assertCanParse("(letrec x = f in z x) h", Application.class);
  }

  @Test
  public void canParseNestedLetrecs() {
    assertCanParse("letrec x = 2 in (let y = x in y)", Letrec.class);
  }

  @Test
  public void canParseRecursiveLetrecs() {
    assertCanParse("letrec x = x x in x", Letrec.class);
  }

}
