package org.uqbar.mingus;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.uqbar.mingus.mingus.Program;

@RunWith(XtextRunner.class)
@InjectWith(MingusInjectorProvider.class)
public class GenerationTest {

  @Inject
  private IGenerator generator;

  @Inject
  private ParseHelper<Program> parser;

  void assertGenerates(String code, String expectedGeneratedCode) throws Exception {
    InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
    generator.doGenerate(parser.parse(code).eResource(), fsa);
    CharSequence generatedCode = fsa.getFiles().get(IFileSystemAccess.DEFAULT_OUTPUT + "out.js");
    assertEquals(expectedGeneratedCode, generatedCode.toString());
  }

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
}
