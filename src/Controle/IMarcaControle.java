/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controle;
import Controle.MarcaControle;
import Persistencia.IMarcaDao;
import Persistencia.MarcaDao;
import Modelos.MarcaModelo;
import Conection.DatabaseConnection;
import java.util.List;

/**
 *
 * @author Igor
 */

public interface IMarcaControle {
    void adicionarMarca(String nome, String url);
    void atualizarMarca(int id, String novoNome, String novoUrl);
    void removerMarca(int id);
    MarcaModelo buscarMarca(String nome);
}




