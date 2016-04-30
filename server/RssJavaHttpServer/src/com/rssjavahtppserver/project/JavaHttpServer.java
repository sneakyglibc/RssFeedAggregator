package com.rssjavahtppserver.project;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class JavaHttpServer {
	private HttpServer server;
	private MongoClient mongoClient;

	public void Start(int port) {
		try {
			mongoClient = new MongoClient();
			MongoDatabase db = mongoClient.getDatabase("rssjava");

			server = HttpServer.create(new InetSocketAddress(port), 0);
			System.out.println("server started at " + port);
			server.createContext("/signup", new Handlers.SignupPost(db));
			server.createContext("/login", new Handlers.LoginPost(db));
			server.createContext("/addFlux", new Handlers.AddFluxPost(db));
			server.createContext("/getListFlux", new Handlers.GetListFluxGet(db));
			server.createContext("/remFlux", new Handlers.RemFluxPost(db));
			server.createContext("/addItem", new Handlers.AddItemPost(db));
			server.createContext("/getListItem", new Handlers.GetListItemGet(db));
			server.createContext("/remItem", new Handlers.RemItemPost(db));
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Stop() {
		server.stop(0);
		System.out.println("server stopped");
	}
}
