/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;

import Controle.IVeiculoControle;
import Controle.VeiculoControle;
import Modelos.Veiculo;
import Persistencia.IVeiculoDao;
import Persistencia.VeiculoDao;
import Telas.TelaDosVeiculos;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class VeiculoControle implements IVeiculoControle {
    private IVeiculoDao veiculoDao;
    private DefaultTableModel tableModel;

    public VeiculoControle(IVeiculoDao veiculoDao) {
        this.veiculoDao = veiculoDao;
    } 
    
    public VeiculoControle(IVeiculoDao veiculoDao, DefaultTableModel tableModel) {
        this.veiculoDao = veiculoDao;
        this.tableModel = tableModel;
    }
    
    @Override
    public void adicionarVeiculo(String Marca, String Modelo, String cor,String placa, String fab, String anomodelo, String combustivel, String km, String renavam, String compra, String venda, String Url) {
        
         int novofab = Integer.parseInt(fab); // Conversão de String para int
         int novomodelo = Integer.parseInt(anomodelo); // Conversão de String para int
         int novomKm = Integer.parseInt(km); // Conversão de String para int
         int novoRenavam = Integer.parseInt(renavam); // Conversão de String para int
        
        Veiculo veiculo = veiculoDao.adicionarVeiculo(Marca, Modelo, cor, placa, novofab, novomodelo, combustivel, novomKm, novoRenavam, compra, venda, Url);
        
        if (veiculo != null) {
            tableModel.addRow(new Object[]{veiculo.getId(), veiculo.getModelo(), veiculo.getMarca(),veiculo.getCor(),veiculo.getPlaca(),veiculo.getAnoFabricacao(),veiculo.getAnoModelo(),veiculo.getCombustivel(), veiculo.getQuilometragem(),veiculo.getRenavam(),veiculo.getPreçoCompra(),veiculo.getPreçoVenda(), veiculo.getUrl()});
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o Veiculo");
        }
    }

    @Override
    public void atualizarVeiculo(int id, String novaMarca, String novoModelo, String novaCor, String novaPlaca, String anofab, String anoModelo, String novoCombustivel,String novoKm, String novoRenavam, String novoCompra, String novoVenda, String novoUrl) {
         int novofab = Integer.parseInt(anofab); // Conversão de String para int
         int novomodelo = Integer.parseInt(anoModelo); // Conversão de String para int
         int Km = Integer.parseInt(novoKm); // Conversão de String para int
         int Renavam = Integer.parseInt(novoRenavam); // Conversão de String para int       
        
        
        if (veiculoDao.atualizarVeiculo(id, novaMarca, novoModelo, novaCor, novaPlaca, novofab, novomodelo, novoCombustivel, Km, Renavam, novoCompra, novoVenda, novoUrl )) {
            int rowIndex = getRowIndexById(id);
            if (rowIndex != -1) {
                tableModel.setValueAt(novaMarca, rowIndex, 1);
                tableModel.setValueAt(novoModelo, rowIndex, 2);
                tableModel.setValueAt(novaCor, rowIndex, 3);
                tableModel.setValueAt(novaPlaca, rowIndex, 4);               
                tableModel.setValueAt(anofab, rowIndex, 5);
                tableModel.setValueAt(anoModelo, rowIndex, 6);
                tableModel.setValueAt(novoCombustivel, rowIndex, 7);
                tableModel.setValueAt(novoKm, rowIndex, 8);
                tableModel.setValueAt(novoRenavam, rowIndex, 9);
                tableModel.setValueAt(novoCompra, rowIndex, 10);
                tableModel.setValueAt(novoVenda, rowIndex, 11);               
                tableModel.setValueAt(novoUrl, rowIndex, 12);
            }
        } 
        else {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o Veiculo");
        }
    }    
      
    private int getRowIndexById(int id) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int rowId = (int) tableModel.getValueAt(i, 0);
            if (rowId == id) {
                return i;
            }
        }
        return -1;
    }
    
}