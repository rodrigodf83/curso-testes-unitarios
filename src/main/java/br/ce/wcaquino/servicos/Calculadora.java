package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

    public int somar(int a, int b) {

        return a + b;
    }

    public int subtrai(int a, int b) {
        return a - b;
    }

    public int dividir(int a, int b) throws NaoPodeDividirPorZeroException {
        if (b == 0)
            throw new NaoPodeDividirPorZeroException("Um número não pode ser dividio por zero!");

        return a / b;
    }
}
