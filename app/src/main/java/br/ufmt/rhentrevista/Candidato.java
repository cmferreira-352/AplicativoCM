package br.ufmt.rhentrevista;

public class Candidato {
    private String cpf;
    private String nome;
    private boolean estaAssinado = false;
    private int pontosPerdidos;

    public Candidato(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean estaAssinado() {
        return estaAssinado;
    }

    public void setEstaAssinado(boolean estaAssinado) {
        this.estaAssinado = estaAssinado;
    }

    public int getPontosPerdidos() {
        return pontosPerdidos;
    }

    public void setPontosPerdidos(int pontosPerdidos) {
        this.pontosPerdidos = pontosPerdidos;
    }
}
