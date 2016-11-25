package com.lamfire.wkit.action;

import java.io.IOException;
import java.io.OutputStream;

import com.lamfire.logger.Logger;


public abstract class StreamAction extends Action{
	final static Logger LOGGER = Logger.getLogger(StreamAction.class);

	protected void write(OutputStream out , byte[] bytes){
		try {
			out.write(bytes);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	
	protected void write(OutputStream out , String data){
		try {
			out.write(data.getBytes());
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	
	protected void write(OutputStream out , String data ,String encoding){
		try {
			out.write(data.getBytes(encoding));
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
}
