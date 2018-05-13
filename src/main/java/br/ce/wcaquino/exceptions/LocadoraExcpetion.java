package br.ce.wcaquino.exceptions;

public class LocadoraExcpetion extends Exception {

    private final String message;

    public LocadoraExcpetion(String message) {
        super(message);
        this.message = message;
    }
}
