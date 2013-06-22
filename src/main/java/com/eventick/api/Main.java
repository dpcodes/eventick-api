package main.java.com.eventick.api;

import java.io.IOException;

import org.apache.log4j.Logger;

public class Main {
    private static Logger log = Logger.getLogger(EventickAPI.class);  
	public static void main(String[] args) throws IOException {
		
		String meuToken = "ENGb1waVGydVbGwQr8U2";
		EventickAPI api = new EventickAPI(meuToken);
		
		int idEvento = 492;
		Event evento = api.getEventById(idEvento);
	
		System.out.println(evento.getTitle());
	}
}
