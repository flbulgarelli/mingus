package org.uqbar.mingus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.junit.runner.RunWith;
import org.uqbar.mingus.mingus.Program;
import org.uqbar.mingus.mingus.Term;

@RunWith(XtextRunner.class)
@InjectWith(MingusInjectorProvider.class)
public class AbstractMingusTest {

  @Inject
  private IGenerator generator;

  @Inject
  private ParseHelper<Program> parser;

  void assertGenerates(String code, String expectedGeneratedCode) throws Exception {
    assertEquals(expectedGeneratedCode, translate(code).toString());
  }

  public CharSequence translate(CharSequence code) throws Exception {
    InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
    generator.doGenerate(parser.parse(code).eResource(), fsa);
    CharSequence generatedCode = fsa.getFiles().get(IFileSystemAccess.DEFAULT_OUTPUT + "out.js");
    return generatedCode;
  }

  void assertCanParse(String text, Class<?> expectedRootType) {
    try {
      Term value = parser.parse(text).getValue();
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

}