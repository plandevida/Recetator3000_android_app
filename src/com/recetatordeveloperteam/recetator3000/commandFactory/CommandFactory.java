package com.recetatordeveloperteam.recetator3000.commandFactory;

import com.recetatordeveloperteam.recetator3000.commandFactory.eventos.IdEvent;
import com.recetatordeveloperteam.recetator3000.commandFactory.imp.CommandFactoryImp;

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
	
	public abstract CommandResponse sendPetition(IdEvent id, String[] data);

}
