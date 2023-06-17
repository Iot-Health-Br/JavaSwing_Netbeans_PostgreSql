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

    boolean atualizarVeiculo(int id, String novaMarca, String novaModelo, String novaCor, String novaPlaca, int novoAnofab, int novoAnomodel, String novocombustivel,int novoKm, int novoRenavam,String novoCompra, String novoVenda ,String novoUrl );
    Veiculo adicionarVeiculo(String marca, String modelo,String cor, String Placa, int Anofab, int Anomodel, String combustivel,int km, int Renavam,String compra, String venda, String url);
    List<Veiculo> listarVeiculos();
}
