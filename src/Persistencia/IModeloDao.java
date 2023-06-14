/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia;
import Controle.ModeloControle;
import Controle.IModeloControle;
import Modelos.Modelo;
import Persistencia.ModeloDao;


import java.util.List;


public interface IModeloDao {
    int buscarIdMarca(String nomeMarca);
    boolean atualizarModelo(int id, String novoNome, String novoUrl, int novaMarca );
    Modelo adicionarModelo(String nome, String url, int idMarca);
    List<Modelo> listarModelos();
}
