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
    
    public void adicionarVeiculo(String nomeMarca, String nomeModelo, String url) {
        
        Veiculo veiculo = veiculoDao.adicionarVeiculo(nomeMarca, nomeModelo, url);
        
        if (veiculo != null) {
            tableModel.addRow(new Object[]{veiculo.getId(), veiculo.getMarca(), veiculo.getModelo(), veiculo.getUrl()});
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o Veiculo");
        }
    }

    public void atualizarVeiculo(int id, String novaMarca, String novaModelo, String novoUrl) {
                
        if (veiculoDao.atualizarVeiculo(id, novaMarca, novaModelo, novoUrl )) {
            int rowIndex = getRowIndexById(id);
            if (rowIndex != -1) {
                tableModel.setValueAt(novaMarca, rowIndex, 1);
                tableModel.setValueAt(novaModelo, rowIndex, 2);
                tableModel.setValueAt(novoUrl, rowIndex, 3);
            }
        } else {
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