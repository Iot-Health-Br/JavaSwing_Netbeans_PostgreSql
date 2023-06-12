/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Controle.ModeloControle;
import Controle.IModeloControle;
import Modelos.Modelo;
import Persistencia.IModeloDao;
import Persistencia.ModeloDao;
import Conection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class ModeloDao implements IModeloDao {
    private Connection connection;
    
    private static final String TABELA_MODELOS = "tabelademodelos";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_MODELOS = "modelos";
    private static final String COLUNA_URL = "url";
    private static final String COLUNA_IDMARCAS = "idmarcas";

    public ModeloDao() {
         criarTabela();
    }
    
    public ModeloDao(Connection connection) {
        this.connection = connection;
    }
    
    private void criarTabela() {
        try (Connection conexao = DatabaseConnection.getConnection();
             Statement statement = conexao.createStatement()) {
            String query = String.format("CREATE TABLE IF NOT EXISTS %s (%s SERIAL PRIMARY KEY, %s VARCHAR(255)UNIQUE, %s VARCHAR(255), %s INTEGER )",
                    TABELA_MODELOS, COLUNA_ID, COLUNA_MODELOS, COLUNA_URL, COLUNA_IDMARCAS);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela de modelos");
        }
    }


    @Override
    public int buscarIdMarca(String nomeMarca) {
        int idMarca = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM tabelademarcas WHERE marcas = ?");
            stmt.setString(1, nomeMarca);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idMarca = rs.getInt("id");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idMarca;
    }

    @Override
    public void inserirModelo(String nomeModelo, String url, int idMarca) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO tabelademodelos (modelos, url, idmarcas) VALUES (?, ?, ?)");
            stmt.setString(1, nomeModelo);
            stmt.setString(2, url);
            stmt.setInt(3, idMarca);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Modelo adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Modelo> listarModelos() {
        List<Modelo> modelos = new ArrayList<>();

        try (Connection conexao = DatabaseConnection.getConnection();
             Statement statement = conexao.createStatement()) {
            String query = String.format("SELECT * FROM %s", TABELA_MODELOS);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt(COLUNA_ID);
                String nome = resultSet.getString(COLUNA_MODELOS);
                String url = resultSet.getString(COLUNA_URL);
                String idmarcas = resultSet.getString(COLUNA_IDMARCAS);
                Modelo modelo = new Modelo(id, nome, url, idmarcas);
                modelos.add(modelo);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modelos;
    }
    
    public boolean atualizarModelo(int id, String novoNome, String novaUrl) {
        try (Connection conexao = DatabaseConnection.getConnection();
            PreparedStatement statement = conexao.prepareStatement(
                 String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABELA_MODELOS, COLUNA_MODELOS, COLUNA_URL, COLUNA_ID))) {
            statement.setString(1, novoNome);
            statement.setString(2, novaUrl);
            statement.setInt(3, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}



