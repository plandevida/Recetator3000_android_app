package com.recetatordeveloperteam.recetator3000.comandos.commandFactory;

import com.recetatordeveloperteam.recetator3000.comandos.IdEvent;

public class CommandResponse {

	private IdEvent event;
	private Object data;
	
	public IdEvent getEvent() { return event; }
	
	public void setEvento(IdEvent evento) { this.event = evento; }
	
	public Object getData() { return data; }
	
	public void setData(Object data) { this.data=data; }

}
