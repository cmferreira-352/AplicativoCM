package br.ufmt.rhentrevista;

public class Falta {
    private String texto;
    private NivelFalta nivel;
    private int pontos;

    public Falta(String texto, NivelFalta nivel, int pontos) {
        this.texto = texto;
        this.nivel = nivel;
        this.pontos = pontos;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public NivelFalta getNivel() {
        return nivel;
    }

    public void setNivel(NivelFalta nivel) {
        this.nivel = nivel;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
