package com.recetatordeveloperteam.recetator3000.comandos.commandFactory.imp;

import com.recetatordeveloperteam.recetator3000.comandos.Command;
import com.recetatordeveloperteam.recetator3000.comandos.CommandCreateRecipe;
import com.recetatordeveloperteam.recetator3000.comandos.IdEvent;
import com.recetatordeveloperteam.recetator3000.comandos.commandFactory.CommandFactory;

public class CommandFactoryImp extends CommandFactory {

	public Command newCommand(IdEvent id) {

		Command c = null;

		switch (id) {
			case CREATE_RECIPE:
				c = new CommandCreateRecipe();
				break;
			default:
				break;
		}

		return c;
	}

}
