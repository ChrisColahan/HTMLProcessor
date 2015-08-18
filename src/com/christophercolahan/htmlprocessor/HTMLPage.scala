
package com.christophercolahan.htmlprocessor

import java.net._
import java.io._
import scala.io._
import scala.xml._

object XMLPage {

	def getPage(fileName: String, vars: Map[String, Any]) : String = {
    
		val lines = scala.io.Source.fromFile("pages/" + fileName).mkString
    
		vars.foreach { case(k,v) => lines.replace("{" + k + "}", v.toString()) }
    
		val changed = vars.foldLeft(lines){ case(text, (key, replacement)) => text.replace("{" + key + "}", replacement.toString()) }
    
		val xml = XML.loadString(changed)
    
		return xml.toString()
	}

	def main(args: Array[String]) {

		val server = new ServerSocket(80)

		println("Server started on port 80.")
		
		while(true) {
			val s = server.accept()
			val out = new PrintStream(s.getOutputStream())

			out.println("<!doctype html>" + getPage("index.html", Map("title" -> "TITLE YO", "body" -> getPage("body.html", Map()))))
			s.close()
		}
	}
}
