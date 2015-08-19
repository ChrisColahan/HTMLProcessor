
package com.christophercolahan.htmlprocessor


import java.io._
import scala.io._
import scala.xml._

/**
 * @author christopher colahan
 * 
 * Technically, could be used for any XML compliant file; not just HTML.
 */
object HTMLPage {
	
	/**
	 * get a flat page without any replacing of vars
	 */
	def getFlatPage(file: String) : String = {
		scala.io.Source.fromFile(file).mkString
	}
	
	def getPage(file: String, vars: Map[String, Any]) : String = {
    
		val lines = scala.io.Source.fromFile(file).mkString
		
		val changed = vars.foldLeft(lines){ case(text, (key, replacement)) => text.replace("{" + key + "}", replacement.toString()) }
    
		val xml = XML.loadString(changed)
    
		return xml.toString()
	}
	
	/**
	 * same as getPage, but any vars that are relative paths to other files are included automatically.
	 * Note: Does not protect against circular references.
	 */
	def getPageWithLocalRefs(file: String, vars: Map[String, Any]) : String = {
		//first get the page with the pre-defined variables replaced
		var page = getPage(file, vars)
		
		var newPage = ""
		
		val parentDir = new File(file).getParent
		
		while(page.contains('{') && page.contains('}')) {
			
			val begin = page.indexOf('{')
			val end = page.indexOf('}')
			
			val tmpVar = page.substring(begin + 1, end)
			
			val f = new File(parentDir + "/" + tmpVar)
			
			if(f.exists()) {
				if(f.getPath.substring(f.getPath.lastIndexOf('.')).equals(".html")) {
					page = page.substring(0, begin) + getPageWithLocalRefs(f.getPath, vars) + page.substring(end + 1)
				}
				else {
					page = page.substring(0, begin) + getFlatPage(f.getPath) + page.substring(end + 1)
				}
			}
			
			newPage += page.substring(0, end + 1)
			
			page = page.substring(end + 1)
		}
		return newPage + page
	}
	
	def minimize(htmlSource : String) : String = {
		//removes all whitespace between tags and removes all comments
		htmlSource.split(">").map { x => x.trim + ">" }.mkString.trim
	}
	
}
