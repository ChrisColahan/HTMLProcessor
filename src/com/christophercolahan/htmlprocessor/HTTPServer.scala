package com.christophercolahan.htmlprocessor

import java.net._
import java.io._

/**
 * @author christopher colahan
 * 
 * An optional stand alone HTTP server to use for testing
 * HTTP pages.
 */
object HTTPServer {
	
	/**
	 * Example:
	 * extracts '/index.html'
	 * from:
	 * GET /index.html HTTP/1.1
	 */
	def extractFileFromHeader() : String = {
		
		
		
		return ""
	}
	
	def handleConnection(connection: Socket, pagesRootDir: String) : Unit = {
			val out = new PrintStream(connection.getOutputStream)
			val in = new BufferedReader(new InputStreamReader(connection.getInputStream))
			
			var tmp = ""
			var header = ""
			
			tmp = in.readLine
			
			while(!tmp.equals("")) {
				header += tmp
				tmp = in.readLine()
			}
			
			out.println("<!doctype html>" + 
				HTMLPage.getPage(pagesRootDir + "index.html", 
					Map("title" -> "TITLE YO", 
						"body" -> HTMLPage.getPage(pagesRootDir + "body.html", Map()))))
			connection.close()
	}
	
	def startServer(port: Int, pagesRootDir: String) : Unit = {
		val server = new ServerSocket(port)
		
		println("Server started on port " + port + ".")
		println("Serving files from " + new File(pagesRootDir).getAbsolutePath)
		
		while(true) {
			handleConnection(server.accept, pagesRootDir)
		}
	}
	
	def main(args: Array[String]) {
		startServer(80, "pages/")
	}
	
}