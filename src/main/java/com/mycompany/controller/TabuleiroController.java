package controller;

import observer.IObserver;
import java.util.ArrayList;
import java.util.List;
import model.Ponto;
import model.Tabuleiro;

public class TabuleiroController {

    private Tabuleiro tabuleiro;
    private Ponto pontoSelecionadoOrigem;
    private Ponto pontoSelecionadoDestino;
    private List<IObserver> listeners = new ArrayList<>(); 

    public TabuleiroController() {
    }
    
    public Tabuleiro getTabuleiro(){
        return this.tabuleiro;
    }

    public void iniciarJogo() {
        this.tabuleiro = new Tabuleiro();
        this.notificarInicioJogo();
    }

    public void botaoClick(int i, int j) {
        Ponto ponto = new Ponto(i, j);
        
        if(this.isPosicaoInvalida(ponto) || (this.getPosicao(ponto) == 0 && this.pontoSelecionadoOrigem == null)){
            return;
        }

        if (this.pontoSelecionadoOrigem == null) {
            this.pontoSelecionadoOrigem = ponto;
            this.notificarSelecionarBotao(new Ponto(i, j));
            return;
        }
        if (!this.pontoSelecionadoOrigem.equals(ponto)) {
            this.pontoSelecionadoDestino = ponto;
            this.moverBotao();
            this.notificarFinalizarJogada();
            return;
        }
        this.notificarCancelarJogada(ponto);
        this.pontoSelecionadoOrigem = null;
    }

    public int getPosicao(Ponto ponto) {
        return this.tabuleiro.getPosition(ponto.getX(), ponto.getY());
    }

    public boolean isPosicaoInvalida(Ponto ponto) {
        return this.getPosicao(ponto) == -1;
    }

    public void moverBotao() {

        if (!this.validaLimitePontos(this.pontoSelecionadoOrigem, this.pontoSelecionadoDestino)) {
            this.pontoSelecionadoOrigem = null;
            this.pontoSelecionadoDestino = null;
            return;
        }

        Ponto pontoIntermediario = this.loadPontoIntermediario(this.pontoSelecionadoOrigem, this.pontoSelecionadoDestino);
        
        int valorOrigem = this.tabuleiro.getPosition(this.pontoSelecionadoOrigem.getX(), this.pontoSelecionadoOrigem.getY());
        int valorDestino = this.tabuleiro.getPosition(this.pontoSelecionadoDestino.getX(), this.pontoSelecionadoDestino.getY());
        int valorIntermediario = this.tabuleiro.getPosition(pontoIntermediario.getX(), pontoIntermediario.getY());

        if (valorOrigem == 1 && valorDestino == 0 && valorIntermediario == 1) {
            this.tabuleiro.setPosition(this.pontoSelecionadoDestino.getX(), this.pontoSelecionadoDestino.getY(), 1);
            this.tabuleiro.setPosition(this.pontoSelecionadoOrigem.getX(), this.pontoSelecionadoOrigem.getY(), 0);
            this.tabuleiro.setPosition(pontoIntermediario.getX(), pontoIntermediario.getY(), 0);
            this.pontoSelecionadoDestino = null;
            this.pontoSelecionadoOrigem = null;
            
            this.tabuleiro.decrementaPecasEmJogo();

            this.validaTerminoJogo();
        }
    }

    public boolean validaLimitePontos(Ponto pontoOrigem, Ponto pontoDestino) {
        int deltaX = Math.abs(pontoOrigem.getX() - pontoDestino.getX());
        int deltaY = Math.abs(pontoOrigem.getY() - pontoDestino.getY());
        return (deltaX == 2 && deltaY == 0) || (deltaY == 2 && deltaX == 0);
    }

    public Ponto loadPontoIntermediario(Ponto pontoOrigem, Ponto pontoDestino) {
        int direcaoX = Integer.signum(pontoDestino.getX() - pontoOrigem.getX());
        int direcaoY = Integer.signum(pontoDestino.getY() - pontoOrigem.getY());
        return new Ponto(pontoOrigem.getX() + direcaoX, pontoOrigem.getY() + direcaoY);
    }
    
    public void addListener(IObserver observador){
        this.listeners.add(observador);
    }
    
    private void notificarSelecionarBotao(Ponto ponto){
        for (IObserver listener : listeners) {
            listener.notificaSelecionarBotao(ponto);
        }
    }
    
        
    private void notificarFinalizarJogada(){
        for (IObserver listener : listeners) {
            listener.notificaFinalizarJogada();
        }
    }
    
    private void notificarCancelarJogada(Ponto ponto){
        for (IObserver listener : listeners) {
            listener.notificaCancelarJogada(ponto);
        }
    }
    
    private void notificarInicioJogo(){
        for (IObserver listener : listeners) {
            listener.notificaInicioJogo();
        }
    }
    
    private void notificarVitoriaJogo(){
        for (IObserver listener : listeners) {
            listener.notificaVitoriaJogo();
        }
    }
    
    private void notificarDerrotaJogo(){
        for (IObserver listener : listeners) {
            listener.notificaDerrotaJogo();
        }
    }

    public void validaTerminoJogo() {
        if(this.tabuleiro.getCountPecasEmJogo() == 1){
            this.notificarVitoriaJogo();
            return;
        }
        if(!this.validaDisponibilidadeJogadaPossivel()){
            this.notificarDerrotaJogo();
            return;
        }
    }
    
    public boolean validaDisponibilidadeJogadaPossivel(){
        for (int i = 0; i < this.tabuleiro.getTabuleiro().length; i++) {
            for (int j = 0; j < this.tabuleiro.getTabuleiro()[i].length; j++) {
                Ponto ponto = new Ponto(i,j);
                
                if(this.getPosicao(ponto) != 1){
                    continue;
                }
                if(this.calculaPontoDestino(ponto) != null){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Ponto loadPontoCima(Ponto ponto){
        return new Ponto(ponto.getX() - 2, ponto.getY());
    }
    public Ponto loadPontoBaixo(Ponto ponto){
        return new Ponto(ponto.getX() + 2, ponto.getY());
    }
    public Ponto loadPontoEsquerda(Ponto ponto){
        return new Ponto(ponto.getX(), ponto.getY() - 2);
    }
    public Ponto loadPontoDireita(Ponto ponto){
        return new Ponto(ponto.getX(), ponto.getY() + 2);
    }
    
    public boolean validaLimitePontoCima(Ponto pontoOrigem){
        return this.loadPontoCima(pontoOrigem).getX() > 0;
    }
    
    public boolean validaLimitePontoBaixo(Ponto pontoOrigem){
        return this.loadPontoBaixo(pontoOrigem).getX() < this.tabuleiro.getTabuleiro().length;
    }
    
    public boolean validaLimitePontoEsquerda(Ponto pontoOrigem){
        return this.loadPontoEsquerda(pontoOrigem).getY() > 0;
    }
    
    public boolean validaLimitePontoDireita(Ponto pontoOrigem){
        return this.loadPontoDireita(pontoOrigem).getY() < this.tabuleiro.getTabuleiro()[0].length;
    }
    
    public Ponto calculaPontoDestino(Ponto pontoOrigem){
        if(this.validaLimitePontoCima(pontoOrigem) && this.getPosicao(this.loadPontoCima(pontoOrigem)) == 0 && this.getPosicao(this.loadPontoIntermediario(pontoOrigem, this.loadPontoCima(pontoOrigem))) == 1){
            return this.loadPontoIntermediario(pontoOrigem, this.loadPontoCima(pontoOrigem));
        }
        if(this.validaLimitePontoBaixo(pontoOrigem) && this.getPosicao(this.loadPontoBaixo(pontoOrigem)) == 0 && this.getPosicao(this.loadPontoIntermediario(pontoOrigem, this.loadPontoBaixo(pontoOrigem))) == 1){
            return this.loadPontoIntermediario(pontoOrigem, this.loadPontoBaixo(pontoOrigem));
        }
        if(this.validaLimitePontoEsquerda(pontoOrigem) && this.getPosicao(this.loadPontoEsquerda(pontoOrigem)) == 0 && this.getPosicao(this.loadPontoIntermediario(pontoOrigem, this.loadPontoEsquerda(pontoOrigem))) == 1){
            return this.loadPontoIntermediario(pontoOrigem, this.loadPontoEsquerda(pontoOrigem));
        }
        if(this.validaLimitePontoDireita(pontoOrigem) && this.getPosicao(this.loadPontoDireita(pontoOrigem)) == 0 && this.getPosicao(this.loadPontoIntermediario(pontoOrigem, this.loadPontoDireita(pontoOrigem))) == 1){
            return this.loadPontoIntermediario(pontoOrigem, this.loadPontoDireita(pontoOrigem));
        }
        return null;
    }
}
