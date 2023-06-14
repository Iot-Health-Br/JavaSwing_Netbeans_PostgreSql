/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;
import Controle.IModeloControle;
import Modelos.Modelo;
import Persistencia.IMarcaDao;
import Persistencia.MarcaDao;
import Persistencia.IModeloDao;
import Persistencia.ModeloDao;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ModeloControle implements IModeloControle {
    private IModeloDao modeloDao;
    private DefaultTableModel tableModel;

    public ModeloControle(IModeloDao modeloDao) {
        this.modeloDao = modeloDao;
    } 
    
    public ModeloControle(IModeloDao modeloDao, DefaultTableModel tableModel) {
        this.modeloDao = modeloDao;
        this.tableModel = tableModel;
    }
    
    @Override
    public void adicionarModelo(String nomeModelo, String url, String nomeMarca) {
        
        int idMarca = modeloDao.buscarIdMarca(nomeMarca);     
        Modelo marca = modeloDao.adicionarModelo(nomeModelo, url, idMarca);
        
        if (marca != null) {
            tableModel.addRow(new Object[]{marca.getId(), marca.getModelo(), marca.getMarca(), marca.getUrl()});
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar marca");
        }
    }
    
    @Override
    public void atualizarModelo(int id, String novoNome, String novoUrl, String novaMarca) {
        int idMarca = modeloDao.buscarIdMarca(novaMarca);
        
        
        if (modeloDao.atualizarModelo(id, novoNome, novoUrl, idMarca )) {
            int rowIndex = getRowIndexById(id);
            if (rowIndex != -1) {
                tableModel.setValueAt(novoNome, rowIndex, 1);
                tableModel.setValueAt(idMarca, rowIndex, 2);
                tableModel.setValueAt(novoUrl, rowIndex, 3);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar marca");
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


