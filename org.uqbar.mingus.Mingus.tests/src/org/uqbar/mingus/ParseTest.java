package org.uqbar.mingus;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.uqbar.mingus.mingus.Abstraction;
import org.uqbar.mingus.mingus.Application;
import org.uqbar.mingus.mingus.Program;
import org.uqbar.mingus.mingus.Term;
import org.uqbar.mingus.mingus.Variable;

@RunWith(XtextRunner.class)
@InjectWith(MingusInjectorProvider.class)
public class ParseTest {

  @Inject
  private ParseHelper<Program> parseHelper;

  @Inject
  private IScopeProvider scopeProvider;

  private void assertCanParse(String text, Class<?> expectedRootType) {
    try {
      Term value = parseHelper.parse(text).getValue();
      assertNotNull(value);
      assertTrue(
        typeMismatchMessage(expectedRootType, value),
        expectedRootType.isAssignableFrom(value.getClass()));
    } catch (Exception e) {
      throw new AssertionError("", e);
    }
  }

  private String typeMismatchMessage(Class<?> expectedRootType, Term value) {
    return "expected " + expectedRootType.getSimpleName() + " but was " + value.getClass().getSimpleName();
  }

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

}
