package org.uqbar.mingus;

import org.junit.Test;
import org.uqbar.mingus.mingus.Abstraction;
import org.uqbar.mingus.mingus.Application;
import org.uqbar.mingus.mingus.ConstructorApplication;
import org.uqbar.mingus.mingus.Forcing;
import org.uqbar.mingus.mingus.Let;
import org.uqbar.mingus.mingus.Letrec;
import org.uqbar.mingus.mingus.NumberLiteral;
import org.uqbar.mingus.mingus.PrimitiveApplication;
import org.uqbar.mingus.mingus.StringLiteral;
import org.uqbar.mingus.mingus.Suspention;
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
  public void canParseSimpleLets() {
    assertCanParse("let x = 2 in x", Let.class);
  }

  @Test
  public void canParseMultiLetrecs() {
    assertCanParse("let x = 2; y = 3 in f x y", Let.class);
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

  @Test
  public void canParseSuspentionOfVariables() {
    assertCanParse("@x", Suspention.class);
  }

  @Test
  public void canParseSuspentionOfNumbers() {
    assertCanParse("@1", Suspention.class);
  }

  @Test
  public void canParseSuspentionOfGeneralTerms() {
    assertCanParse("@(f 2)", Suspention.class);
  }

  @Test
  public void canParseForcingOfVariables() {
    assertCanParse("!f", Forcing.class);
  }

  @Test
  public void canParseForcingOfGeneralTerms() {
    assertCanParse("!(f 2)", Forcing.class);
  }
  
  @Test
  public void canParseStrings() {
    assertCanParse("'foo'", StringLiteral.class);
  }

  @Test
  public void canParseOperators() {
    assertCanParse("- (+ x 1) y", Application.class);
    assertCanParse(">= z 0", Application.class);
    assertCanParse(": 1 xs", Application.class);
    assertCanParse("$ f x", Application.class);
    assertCanParse("(++) xs ys", Application.class);
  }
  
  @Test
  public void canParsePrimitives() throws Exception {
    assertCanParse("prim foo 2 4 5", PrimitiveApplication.class);
    assertCanParse("prim / 2 4 5", PrimitiveApplication.class);
  }
  
  @Test
  public void canParseEmptyConstructor() throws Exception {
    assertCanParse("cons Nothing {}", ConstructorApplication.class);
  }
  
  @Test
  public void canParseNonEmptyConstructor() throws Exception {
    assertCanParse("cons Cons {head=1, tail=(cons Nil {})}", ConstructorApplication.class);
  }

}
