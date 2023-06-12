/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia;
import Controle.MarcaControle;
import Controle.IMarcaControle;
import Persistencia.IMarcaDao;
import Persistencia.MarcaDao;
import Modelos.MarcaModelo;
import Conection.DatabaseConnection;
import java.util.List;


public interface IMarcaDao {
    MarcaModelo adicionarMarca(String nome, String url);
    boolean atualizarMarca(int id, String novoNome, String novoUrl );
    boolean removerMarca(int id);
    List<MarcaModelo> listarMarcas();
    List<String> obterTodasMarcas();
    int obterIdMarcaPeloIndice(int indice);
}






