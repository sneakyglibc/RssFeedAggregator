package com.rssjavahtppserver.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import static java.util.Arrays.asList;

public class Handlers {
	public static class SignupPost implements HttpHandler {
		private MongoDatabase db;
		
		public SignupPost(MongoDatabase mdb) {
			db = mdb;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			Map<String, Object> params = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			String res;
			
			parseQuery(query, params);
			if (!params.containsKey("email") || !params.containsKey("password")) {
				res = "Wrong params.";
				he.sendResponseHeaders(400, res.length());
				OutputStream os = he.getResponseBody();
				os.write(res.getBytes());
				os.close();
				return;
			}	
			FindIterable<Document> it = db.getCollection("users").find(
			        new Document("email", params.get("email")));
			if (it.iterator().hasNext()) {
				res = "User already exists.";
				he.sendResponseHeaders(400, res.length());
			}
			else {
				db.getCollection("users").insertOne(new Document()
						.append("email", params.get("email"))
						.append("password", params.get("password"))
			            .append("flux", asList())
						.append("items", asList()));
				res = "OK";
				he.sendResponseHeaders(200, res.length());
			}
			OutputStream os = he.getResponseBody();
			os.write(res.getBytes());
			os.close();
		}
	}
	
	public static class LoginPost implements HttpHandler {
		private MongoDatabase db;
		
		public LoginPost(MongoDatabase mdb) {
			db = mdb;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			Map<String, Object> params = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			String res;
			
			parseQuery(query, params);
			if (!params.containsKey("email") || !params.containsKey("password")) {
				res = "Params not found.";
				he.sendResponseHeaders(400, res.length());
				OutputStream os = he.getResponseBody();
				os.write(res.getBytes());
				os.close();
				return;
			}
			FindIterable<Document> it = db.getCollection("users").find(
			        new Document("email", params.get("email")));
			if (!it.iterator().hasNext()) {
				res = "User not found.";
				he.sendResponseHeaders(400, res.length());
			}
			else{
				Document doc = it.iterator().next();
				System.out.println(doc);
				if (!Objects.equals(doc.get("password"), params.get("password"))) {
					res = "Wrong password.";
					he.sendResponseHeaders(400, res.length());
				}
				else {
					res = "OK";
					he.sendResponseHeaders(200, res.length());
				}
			}
			OutputStream os = he.getResponseBody();
			os.write(res.getBytes());
			os.close();
		}
	}
	
	public static class AddFluxPost implements HttpHandler {
		private MongoDatabase db;
		
		public AddFluxPost(MongoDatabase mdb) {
			db = mdb;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			Map<String, Object> params = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			String res;
			
			parseQuery(query, params);
			if (!params.containsKey("email") || !params.containsKey("password") 
					|| !params.containsKey("link") || !params.containsKey("title")) {
				res = "Wrong params.";
				he.sendResponseHeaders(400, res.length());
				OutputStream os = he.getResponseBody();
				os.write(res.getBytes());
				os.close();
				return;
			}
			FindIterable<Document> it = db.getCollection("users").find(
			        new Document("email", params.get("email")));
			if (!it.iterator().hasNext()) {
				res = "User not found.";
				he.sendResponseHeaders(400, res.length());
			}
			else{
				Document doc = it.iterator().next();
				if (!Objects.equals(doc.get("password"), params.get("password"))) {
					res = "Wrong password.";
					he.sendResponseHeaders(400, res.length());
				}
				else {
					Document flux = new Document("flux", new Document()
							.append("link", params.get("link"))
							.append("title", params.get("title")));
					db.getCollection("users").updateOne(
					        new Document("email", params.get("email")),
					        new Document("$push", flux));
					res = "OK";
					he.sendResponseHeaders(200, res.length());
				}
			}
			OutputStream os = he.getResponseBody();
			os.write(res.getBytes());
			os.close();
		}
	}
	
	public static class GetListFluxGet implements HttpHandler {
		private MongoDatabase db;
		
		public GetListFluxGet(MongoDatabase mdb) {
			db = mdb;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			Map<String, Object> params = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			String res;
			
			parseQuery(query, params);
			if (!params.containsKey("email") || !params.containsKey("password")) {
				res = "Wrong params.";
				he.sendResponseHeaders(400, res.length());
				OutputStream os = he.getResponseBody();
				os.write(res.getBytes());
				os.close();
				return;
			}
			FindIterable<Document> it = db.getCollection("users").find(
			        new Document("email", params.get("email")));
			if (!it.iterator().hasNext()) {
				res = "User not found.";
				he.sendResponseHeaders(400, res.length());
			}
			else{
				Document doc = it.iterator().next();
				if (!Objects.equals(doc.get("password"), params.get("password"))) {
					res = "Wrong password.";
					he.sendResponseHeaders(400, res.length());
				}
				else {
					List<Document> list = (List<Document>) doc.get("flux");
					ArrayList<String> flux = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						flux.add(list.get(i).toJson());
					}
					res = flux.toString();
					he.sendResponseHeaders(200, res.length());
				}
			}
			OutputStream os = he.getResponseBody();
			os.write(res.getBytes());
			os.close();
		}
	}
	
	public static class RemFluxPost implements HttpHandler {
		private MongoDatabase db;
		
		public RemFluxPost(MongoDatabase mdb) {
			db = mdb;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			Map<String, Object> params = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			String res;
			
			parseQuery(query, params);
			if (!params.containsKey("email") || !params.containsKey("password") 
					|| !params.containsKey("link")) {
				res = "Wrong params.";
				he.sendResponseHeaders(400, res.length());
				OutputStream os = he.getResponseBody();
				os.write(res.getBytes());
				os.close();
				return;
			}
			FindIterable<Document> it = db.getCollection("users").find(
			        new Document("email", params.get("email")));
			if (!it.iterator().hasNext()) {
				res = "User not found.";
				he.sendResponseHeaders(400, res.length());
			}
			else{
				Document doc = it.iterator().next();
				if (!Objects.equals(doc.get("password"), params.get("password"))) {
					res = "Wrong password.";
					he.sendResponseHeaders(400, res.length());
				}
				else {
					Document flux = new Document("flux", 
							new Document("link", params.get("link")));
					db.getCollection("users").updateOne(
					        new Document("email", params.get("email")),
					        new Document("$pull", flux));
					res = "OK";
					he.sendResponseHeaders(200, res.length());
				}
			}
			OutputStream os = he.getResponseBody();
			os.write(res.getBytes());
			os.close();
		}
	}
	
	public static class AddItemPost implements HttpHandler {
		private MongoDatabase db;
		
		public AddItemPost(MongoDatabase mdb) {
			db = mdb;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			Map<String, Object> params = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			String res;
			
			parseQuery(query, params);
			if (!params.containsKey("email") || !params.containsKey("password") 
					|| !params.containsKey("link") || !params.containsKey("title")) {
				res = "Wrong params.";
				he.sendResponseHeaders(400, res.length());
				OutputStream os = he.getResponseBody();
				os.write(res.getBytes());
				os.close();
				return;
			}
			FindIterable<Document> it = db.getCollection("users").find(
			        new Document("email", params.get("email")));
			if (!it.iterator().hasNext()) {
				res = "User not found.";
				he.sendResponseHeaders(400, res.length());
			}
			else{
				Document doc = it.iterator().next();
				if (!Objects.equals(doc.get("password"), params.get("password"))) {
					res = "Wrong password.";
					he.sendResponseHeaders(400, res.length());
				}
				else {
					Document item = new Document("items", new Document()
							.append("link", params.get("link"))
							.append("title", params.get("title")));
					db.getCollection("users").updateOne(
					        new Document("email", params.get("email")),
					        new Document("$push", item));
					res = "OK";
					he.sendResponseHeaders(200, res.length());
				}
			}
			OutputStream os = he.getResponseBody();
			os.write(res.getBytes());
			os.close();
		}
	}
	
	public static class GetListItemGet implements HttpHandler {
		private MongoDatabase db;
		
		public GetListItemGet(MongoDatabase mdb) {
			db = mdb;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			Map<String, Object> params = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			String res;
			
			parseQuery(query, params);
			if (!params.containsKey("email") || !params.containsKey("password")) {
				res = "Wrong params.";
				he.sendResponseHeaders(400, res.length());
				OutputStream os = he.getResponseBody();
				os.write(res.getBytes());
				os.close();
				return;
			}
			FindIterable<Document> it = db.getCollection("users").find(
			        new Document("email", params.get("email")));
			if (!it.iterator().hasNext()) {
				res = "User not found.";
				he.sendResponseHeaders(400, res.length());
			}
			else{
				Document doc = it.iterator().next();
				if (!Objects.equals(doc.get("password"), params.get("password"))) {
					res = "Wrong password.";
					he.sendResponseHeaders(400, res.length());
				}
				else {
					List<Document> list = (List<Document>) doc.get("items");
					ArrayList<String> flux = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						flux.add(list.get(i).toJson());
					}
					res = flux.toString();
					he.sendResponseHeaders(200, res.length());
				}
			}
			OutputStream os = he.getResponseBody();
			os.write(res.getBytes());
			os.close();
		}
	}

	public static class RemItemPost implements HttpHandler {
		private MongoDatabase db;
		
		public RemItemPost(MongoDatabase mdb) {
			db = mdb;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			Map<String, Object> params = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			String res;
			
			parseQuery(query, params);
			if (!params.containsKey("email") || !params.containsKey("password") 
					|| !params.containsKey("link")) {
				res = "Wrong params.";
				he.sendResponseHeaders(400, res.length());
				OutputStream os = he.getResponseBody();
				os.write(res.getBytes());
				os.close();
				return;
			}
			FindIterable<Document> it = db.getCollection("users").find(
			        new Document("email", params.get("email")));
			if (!it.iterator().hasNext()) {
				res = "User not found.";
				he.sendResponseHeaders(400, res.length());
			}
			else{
				Document doc = it.iterator().next();
				if (!Objects.equals(doc.get("password"), params.get("password"))) {
					res = "Wrong password.";
					he.sendResponseHeaders(400, res.length());
				}
				else {
					Document item = new Document("items",
							new Document("link", params.get("link")));
					db.getCollection("users").updateOne(
					        new Document("email", params.get("email")),
					        new Document("$pull", item));
					res = "OK";
					he.sendResponseHeaders(200, res.length());
				}
			}
			OutputStream os = he.getResponseBody();
			os.write(res.getBytes());
			os.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {

		if (query != null) {
			String pairs[] = query.split("[&]");

			for (String pair : pairs) {
				String param[] = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
				}

				if (param.length > 1) {
					value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
				}

				if (parameters.containsKey(key)) {
					Object obj = parameters.get(key);
					if (obj instanceof List<?>) {
						List<String> values = (List<String>) obj;
						values.add(value);
					} else if (obj instanceof String) {
						List<String> values = new ArrayList<String>();
						values.add((String) obj);
						values.add(value);
						parameters.put(key, values);
					}
				} else {
					parameters.put(key, value);
				}
			}
		}
	}
}
