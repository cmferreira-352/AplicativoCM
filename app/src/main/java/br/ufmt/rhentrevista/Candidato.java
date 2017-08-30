package br.ufmt.rhentrevista;

public class Candidato {
    private String cpf;
    private String nome;
    private StatusEnum estaAssinado;
    private int pontosPerdidos;

    public Candidato(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        estaAssinado = StatusEnum.Registrado;
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

    public StatusEnum estaAssinado() {
        return estaAssinado;
    }

    public void setEstaAssinado(StatusEnum estaAssinado) {
        this.estaAssinado = estaAssinado;
    }

    public int getPontosPerdidos() {
        return pontosPerdidos;
    }

    public void setPontosPerdidos(int pontosPerdidos) {
        this.pontosPerdidos = pontosPerdidos;
    }
}
