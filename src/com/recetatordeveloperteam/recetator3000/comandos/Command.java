/**
 * 
 */
package com.recetatordeveloperteam.recetator3000.comandos;

import com.recetatordeveloperteam.recetator3000.comandos.commandFactory.CommandResponse;

public interface Command {

	public CommandResponse execute(Object datos);

}
