package br.ce.wcaquino.exceptions;

public class FilmeSemEstoqueException extends Exception {

    private final String message;

    public FilmeSemEstoqueException(String message) {
        super(message);
        this.message = message;
    }
}
