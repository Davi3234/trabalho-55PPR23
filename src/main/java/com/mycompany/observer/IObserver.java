/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package observer;

import model.Ponto;

/**
 *
 * @author davif
 */
public interface IObserver {
    public void notificaSelecionarBotao(Ponto ponto);
    public void notificaCancelarJogada(Ponto ponto);
    public void notificaFinalizarJogada();
    public void notificaInicioJogo();
    public void notificaVitoriaJogo();
    public void notificaDerrotaJogo();
}
