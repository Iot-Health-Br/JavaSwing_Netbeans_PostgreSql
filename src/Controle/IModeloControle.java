/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controle;
import Controle.ModeloControle;
import Modelos.Modelo;
import Persistencia.IModeloDao;
import Persistencia.ModeloDao;
import java.util.List;

public interface IModeloControle {
    void adicionarModelo(String nomeModelo, String url, String nomeMarca);
    void atualizarModelo(int id, String novoNome, String novoUrl);
}


