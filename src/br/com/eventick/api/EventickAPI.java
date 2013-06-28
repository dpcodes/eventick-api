/**
 * Copyright (c) 2013 Rodrigo Alves
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to use, copy and modify copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.eventick.api;

import java.io.IOException;
import java.util.List;

import br.com.eventick.http.Requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * Classe elementar de representacao da API do Eventick
 * Tambem responsavel pela autenticacao
 * @author Rodrigo Alves
 *
 */
public class EventickAPI {
	private final Gson gson;
	private final Requests requests;
	
	public static String URL = "https://www.eventick.com.br/api/v1";
	private String token;
	
	public EventickAPI(String token) {
		this.gson = new Gson();
		this.requests = new Requests();
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Gson getGson() {
		return this.gson;
	}
	
	public Requests getRequests() {
		return this.requests;
	}

	/**
	 * Retorna a colecao de eventos do usuario
	 * @return
	 * @throws IOException 
	 */
	public List<Event> getMyEvents() throws IOException {
		List<Event> collection = null;
		
		String fetchURL = String.format("%s/events", URL);
		String json = requests.get(fetchURL, this.getToken());
		JsonObject jsonObject = gson.fromJson(json, JsonElement.class).getAsJsonObject();			
		JsonArray jsonArray = jsonObject.get("events").getAsJsonArray();
		
		String strEve;
		Event eve;
		int i = 0;
		
		for (; i < jsonArray.size(); i++) {
			strEve = jsonArray.get(i).toString();
			eve = gson.fromJson(json, Event.class);
			eve.setApi(this);
			
			collection.add(eve);
		}
		
		return collection;
	}
	
	/**
	 * Otenha qualquer um de seus eventos a partir do id
	 * @param id, ID do evento
	 * @return 
	 * @return um objeto {@link Event} daquele evento
	 * @throws IOException 
	 */
	public Event getEventById(int id) throws IOException {	
		String fetchURL = String.format("%s/events/%d", URL, id);
		String json = requests.get(fetchURL, this.getToken());
		
        // System.out.println(json);
		
		JsonObject jsonObject = gson.fromJson(json, JsonElement.class).getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("events").getAsJsonArray();
		
	    Event eve = gson.fromJson(jsonArray.get(0), Event.class);
		return eve;
	}
}