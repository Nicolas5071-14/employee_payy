/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projet;

import com.cbozan.main.Login;
import com.cbozan.main.MainFrame;
import java.awt.EventQueue;
import java.util.Locale;

/**
 *
 * @author Nico
 */
public class Projet {

    public static void main(String[] args) {

        Locale.setDefault(new Locale("fr", "FR"));

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                new Login();

            }
        });

    }

}
