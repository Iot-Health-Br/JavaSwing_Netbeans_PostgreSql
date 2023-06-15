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
    void adicionarVeiculo( String Marca, String Modelo, String Url);
    void atualizarVeiculo(int id, String novaMarca, String novoModelo, String novoUrl);
}