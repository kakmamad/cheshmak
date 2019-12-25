package me.cheshmak.cordova;

import java.lang.RuntimeException;

@SuppressWarnings("serial")
public class CheshmakConfigException extends RuntimeException {
	public CheshmakConfigException(String message) {
        super(message);
    }
}
