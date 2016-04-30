package com.rssjavahtppserver.project;

public class Main {
	public static int port = 3000;
	public static void main(String[] args) {
		JavaHttpServer httpServer = new JavaHttpServer();
		httpServer.Start(port);
	}
}
