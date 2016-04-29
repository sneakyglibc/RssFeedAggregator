package com.rssjavahtppserver.project;

public class Main {
	public static int port = 3000;
	public static void main(String[] args) {
//		// start http server
		JavaHttpServer httpServer = new JavaHttpServer();
		httpServer.Start(port);
		
		// start https server
		//SimpleHttpsServer httpsServer = new SimpleHttpsServer();
		//httpsServer.Start(port);	
	}
}
