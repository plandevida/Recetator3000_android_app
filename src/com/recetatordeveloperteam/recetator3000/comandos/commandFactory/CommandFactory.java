package com.recetatordeveloperteam.recetator3000.comandos.commandFactory;

import com.recetatordeveloperteam.recetator3000.comandos.Command;
import com.recetatordeveloperteam.recetator3000.comandos.IdEvent;
import com.recetatordeveloperteam.recetator3000.comandos.commandFactory.imp.CommandFactoryImp;

public abstract class CommandFactory {

	private static CommandFactory commandFactoryInstance;
	
	public static CommandFactory getInstance() {
		createCommandFactory();
		return commandFactoryInstance;
	}
	
	private static void createCommandFactory() {
		if (commandFactoryInstance == null)
			commandFactoryInstance = new CommandFactoryImp();
	}
	
	public abstract Command newCommand(IdEvent id);

}
