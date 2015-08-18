
package com.christophercolahan.htmlprocessor


import scala.io._
import scala.xml._

/**
 * @author christopher colahan
 * 
 * Technically, could be used for any XML compliant file; not just HTML.
 */
object HTMLPage {

	def getPage(file: String, vars: Map[String, Any]) : String = {
    
		val lines = scala.io.Source.fromFile(file).mkString
		
		val changed = vars.foldLeft(lines){ case(text, (key, replacement)) => text.replace("{" + key + "}", replacement.toString()) }
    
		val xml = XML.loadString(changed)
    
		return xml.toString()
	}
	
}
