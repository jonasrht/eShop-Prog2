package eshop.exceptions;

public class ArtikelNichtGefundenException extends Exception{

    public ArtikelNichtGefundenException() {
        super("Artikel nicht gefunden");
    }
    
}