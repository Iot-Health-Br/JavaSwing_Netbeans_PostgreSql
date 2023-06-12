/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;
import Controle.MarcaControle;
import Controle.IMarcaControle;
import Persistencia.IMarcaDao;
import Persistencia.MarcaDao;
import Modelos.MarcaModelo;
import Conection.DatabaseConnection;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MarcaControle implements IMarcaControle {
    private IMarcaDao marcaDao;
    private DefaultTableModel tableModel;

    public MarcaControle(IMarcaDao marcaDao, DefaultTableModel tableModel) {
        this.marcaDao = marcaDao;
        this.tableModel = tableModel;
    }

    public void adicionarMarca(String nome, String url) {
        MarcaModelo marca = marcaDao.adicionarMarca(nome, url);
        if (marca != null) {
            tableModel.addRow(new Object[]{marca.getId(), marca.getNome(), marca.getUrl()});
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar marca");
        }
    }

    public void atualizarMarca(int id, String novoNome, String novoUrl) {
        if (marcaDao.atualizarMarca(id, novoNome, novoUrl)) {
            int rowIndex = getRowIndexById(id);
            if (rowIndex != -1) {
                tableModel.setValueAt(novoNome, rowIndex, 1);
                tableModel.setValueAt(novoUrl, rowIndex, 2);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar marca");
        }
    }

    public void removerMarca(int id) {
        if (marcaDao.removerMarca(id)) {
            int rowIndex = getRowIndexById(id);
            if (rowIndex != -1) {
                tableModel.removeRow(rowIndex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao remover marca");
        }
    }
    
public MarcaModelo buscarMarca(String nome) {
    for (int i = 0; i < tableModel.getRowCount(); i++) {
        String rowNome = (String) tableModel.getValueAt(i, 1);
        if (rowNome.equals(nome)) {
            int id = (int) tableModel.getValueAt(i, 0);
            String url = (String) tableModel.getValueAt(i, 2);
            return new MarcaModelo(id, nome, url);
        }
    }
    return null;
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

