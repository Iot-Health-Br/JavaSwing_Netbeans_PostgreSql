/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controle;
import Controle.IVeiculoControle;
import Controle.VeiculoControle;
import Modelos.Veiculo;
import Persistencia.IVeiculoDao;
import Persistencia.VeiculoDao;
import Telas.TelaDosVeiculos;


public interface IVeiculoControle {
    void adicionarVeiculo( String Marca, String Modelo,String cor, String placa, String anofab, String anoModelo, String combustivel,String km, String renavam, String compra, String venda, String Url);
    void atualizarVeiculo(int id, String novaMarca, String novoModelo, String novaCor, String novaPlaca, String anofab, String anoModelo, String novoCombustivel,String novoKm, String novoRenavam, String novoCompra, String novoVenda, String novoUrl);
}
