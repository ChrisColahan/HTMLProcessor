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
	 * 'GET /index.html HTTP/1.1'
	 */
	def extractFileFromHeader(header: String) : String = {
		return header.split(" ")(1)
	}
	
	def handleConnection(connection: Socket, pagesRootDir: String, vars: Map[String, Any]) : Unit = {
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
			HTMLPage.minimize(HTMLPage.getPageWithLocalRefs(pagesRootDir + "index.html", Map[String, Any]())))
		
		connection.close()
	}
	
	def startServer(port: Int, pagesRootDir: String) : Unit = {
		val server = new ServerSocket(port)
		
		println("Server started on port " + port + ".")
		println("Serving files from " + new File(pagesRootDir).getAbsolutePath)
		
		while(true) {
			handleConnection(server.accept, pagesRootDir, Map[String, Any]())
		}
	}
	
	def main(args: Array[String]) {
		startServer(80, "pages/")
	}
	
}