/*
 * generated by Xtext
 */
package org.uqbar.mingus.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.uqbar.mingus.mingus.Abstraction
import org.uqbar.mingus.mingus.Application
import org.uqbar.mingus.mingus.Letrec
import org.uqbar.mingus.mingus.NumberLiteral
import org.uqbar.mingus.mingus.Program
import org.uqbar.mingus.mingus.Variable
import org.uqbar.mingus.mingus.Suspention
import org.uqbar.mingus.mingus.Forcing
import org.uqbar.mingus.mingus.Binding
import java.util.List

class MingusGenerator implements IGenerator {
  
  override void doGenerate(Resource resource, IFileSystemAccess fsa) {
    fsa.generateFile( "out.js", translate((resource.allContents.head as Program).value))
  }
  
  def dispatch translate(Variable variable) 
    '''«translateId(variable.name)»'''
  
  def dispatch translate(Application application) 
    '''«translate(application.function)»(«translate(application.argument)»)'''
  
  def dispatch translate(Abstraction abstraction) 
    '''(function(«translateId(abstraction.parameter)»){return «translate(abstraction.body)»})'''
  
  def dispatch translate(NumberLiteral literal) 
    '''«literal.value»'''
    
  def dispatch translate(Letrec letrec) 
    '''(function(){«translateBindings(letrec.bindings)»return «translate(letrec.body)»})()'''
    
  def translateBindings(List<Binding> bindings) 
    '''var «bindings.map([binding|'''«translateId(binding.name)»=«translate(binding.value)»''']).join(',')»;'''

    
  def dispatch translate(Suspention suspention) 
    '''(function(){return «translate(suspention.value)»})'''
    
  def dispatch translate(Forcing forcing) 
    '''«translate(forcing.value)»()'''

  def translateId(String id) {
    if(Character::isLetter(id.charAt(0)))
      id
    else
     translateOperator(id)
  }
  def String translateOperator(String id) {
    id.toCharArray.map([x|'__' + translateOperatorChar(x.toString)]).join
  }

  def String translateOperatorChar(String x) {
    switch x  {
      case '$': "$"
      case '>': "gt"
      case '<': "lt"
      case '=': "eq"
      case '+': "plus"
      case '-': "minus"
      case '!': "bang"
      case '@': "at"
      case ':': 'colon'
      case '*': 'mult'
      case '|': 'pipe'
      case '&' : 'amp'
      case '?' : 'quest'
      case '?' : 'perc'
      case '~' : 'newf'
      case '^' : 'circ'
      case '/' : 'slash'
    }
  }
}
