/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import java.sql.*;

import Controle.MarcaControle;
import Controle.IMarcaControle;
import Persistencia.IMarcaDao;
import Persistencia.MarcaDao;
import Modelos.MarcaModelo;
import Conection.DatabaseConnection;
import Telas.TelaDasMarcas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class MarcaDao implements IMarcaDao {
    private static final String TABELA_MARCAS = "TabelaDeMarcas";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "marcas";
    private static final String COLUNA_URL = "url";

    public MarcaDao() {
        criarTabela();
    }

    private void criarTabela() {
        try (Connection conexao = DatabaseConnection.getConnection();
             Statement statement = conexao.createStatement()) {
            String query = String.format("CREATE TABLE IF NOT EXISTS %s (%s SERIAL PRIMARY KEY, %s VARCHAR(255)UNIQUE, %s VARCHAR(300)UNIQUE)",
                    TABELA_MARCAS, COLUNA_ID, COLUNA_NOME, COLUNA_URL);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela de marcas");
        }
    }

    public MarcaModelo adicionarMarca(String nome, String url) {
        
        try (Connection conexao = DatabaseConnection.getConnection();
                // Verificar se a marca já está cadastrada
                PreparedStatement verificacaoStatement = conexao.prepareStatement(
                 String.format("SELECT * FROM %s WHERE %s = ?", TABELA_MARCAS, COLUNA_NOME ));
                
                // Verificar se a logo já está cadastrada
                PreparedStatement verificacaoLogo = conexao.prepareStatement(
                 String.format("SELECT * FROM %s WHERE %s = ?", TABELA_MARCAS, COLUNA_URL ));
                 
                // Inserir a Marca e URL no banco de dados
                PreparedStatement insercaoStatement = conexao.prepareStatement(
                 String.format("INSERT INTO %s (%s, %s) VALUES (?, ?) ", TABELA_MARCAS, COLUNA_NOME, COLUNA_URL),
                 Statement.RETURN_GENERATED_KEYS)) {

        // Verificar se a marca já está cadastrada
        verificacaoStatement.setString(1, nome);
        ResultSet resultSet = verificacaoStatement.executeQuery();
        if (resultSet.next()) {
            JOptionPane.showMessageDialog(null, "A marca já está cadastrada.");
            return null;
        }
        
        // Verificar se a logo já está cadastrada
        verificacaoLogo.setString(1, url);
        ResultSet resultLogo = verificacaoLogo.executeQuery();
        if (resultLogo.next()) {
            JOptionPane.showMessageDialog(null, "A logomarca já está cadastrada.");
            return null;
        }
        
        // Inserir a marca no banco de dados
        insercaoStatement.setString(1, nome );
        insercaoStatement.setString(2, url );
        int rowsAffected = insercaoStatement.executeUpdate();

        if (rowsAffected == 0) {
            return null;
        }

        try (ResultSet generatedKeys = insercaoStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                return new MarcaModelo(id, nome, url);
            } else {
                return null;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}

public boolean atualizarMarca(int id, String novoNome, String novaUrl) {
    try (Connection conexao = DatabaseConnection.getConnection();
         PreparedStatement statement = conexao.prepareStatement(
                 String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABELA_MARCAS, COLUNA_NOME, COLUNA_URL, COLUNA_ID))) {
        statement.setString(1, novoNome);
        statement.setString(2, novaUrl);
        statement.setInt(3, id);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    public boolean removerMarca(int id) {
        try (Connection conexao = DatabaseConnection.getConnection();
             PreparedStatement statement = conexao.prepareStatement(
                     String.format("DELETE FROM %s WHERE %s = ?", TABELA_MARCAS, COLUNA_ID))) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MarcaModelo> listarMarcas() {
        List<MarcaModelo> marcas = new ArrayList<>();

        try (Connection conexao = DatabaseConnection.getConnection();
             Statement statement = conexao.createStatement()) {
            String query = String.format("SELECT * FROM %s", TABELA_MARCAS);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt(COLUNA_ID);
                String nome = resultSet.getString(COLUNA_NOME);
                String url = resultSet.getString(COLUNA_URL);
                MarcaModelo marca = new MarcaModelo(id, nome, url);
                marcas.add(marca);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marcas;
    }
    
        public List<String> obterTodasMarcas() {
        List<String> marcas = new ArrayList<>();
        try (Connection conexao = DatabaseConnection.getConnection();
             Statement statement = conexao.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format("SELECT %s FROM %s", COLUNA_NOME, TABELA_MARCAS))) {
            while (resultSet.next()) {
                String marca = resultSet.getString(COLUNA_NOME);
                marcas.add(marca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marcas;
    }
   

    public int obterIdMarcaPeloIndice(int indice) {
        int id = -1;
        try (Connection conexao = DatabaseConnection.getConnection();
             PreparedStatement statement = conexao.prepareStatement(
                     String.format("SELECT %s FROM %s LIMIT 1 OFFSET ?", COLUNA_ID, TABELA_MARCAS))) {
            statement.setInt(1, indice);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(COLUNA_ID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
   
}






