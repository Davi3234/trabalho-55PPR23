package com.mycompany.restaum;

import com.mycompany.view.ViewGame;
import controller.TabuleiroController;
import model.Tabuleiro;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author davif
 */
public class RestaUm {
    public static void main(String[] args) {
        new ViewGame(new TabuleiroController());
    }
}
