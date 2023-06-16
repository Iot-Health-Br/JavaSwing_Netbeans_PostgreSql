/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia;

import Controle.IVeiculoControle;
import Controle.VeiculoControle;
import Modelos.Veiculo;
import Persistencia.IVeiculoDao;
import Persistencia.VeiculoDao;
import Telas.TelaDosVeiculos;

import java.util.List;


public interface IVeiculoDao {

    boolean atualizarVeiculo(int id, String novaMarca, String novaModelo, String novaCor, String novaPlaca, String novoUrl );
    Veiculo adicionarVeiculo(String marca, String modelo,String cor, String Placa, String url);
    List<Veiculo> listarVeiculos();
}
