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

    private static final String TABELA_MARCAS = "tabelademarcas";
    private static final String COLUNA_MARCAS = "marcas";
    
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

    try (Connection conexao = DatabaseConnection.getConnection();
         PreparedStatement statement = conexao.prepareStatement("SELECT id FROM tabelademarcas WHERE Marcas = ?")) {

        statement.setString(1, nomeMarca);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            idMarca = resultSet.getInt("id");
        }
        resultSet.close();
    } 
    catch (SQLException e) {
        e.printStackTrace();
    }
    return idMarca;

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
    
    public boolean atualizarModelo(int id, String novoNome, String novaUrl, int novaMarca) {
        try (Connection conexao = DatabaseConnection.getConnection();
            PreparedStatement statement = conexao.prepareStatement(
                 String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?", TABELA_MODELOS, COLUNA_MODELOS, COLUNA_URL, COLUNA_IDMARCAS, COLUNA_ID))) {
            statement.setString(1, novoNome);
            statement.setString(2, novaUrl);
            statement.setInt(3, novaMarca);
            statement.setInt(4, id);
                       
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Modelo adicionarModelo(String nome, String url, int idMarca) {
                
        try (Connection conexao = DatabaseConnection.getConnection();
                // Verificar se a marca já está cadastrada
                PreparedStatement verificacaoStatement = conexao.prepareStatement(
                 String.format("SELECT * FROM %s WHERE %s = ?", TABELA_MODELOS, COLUNA_MODELOS ));
                
                // Verificar se a logo já está cadastrada
                PreparedStatement verificacaoLogo = conexao.prepareStatement(
                 String.format("SELECT * FROM %s WHERE %s = ?", TABELA_MODELOS, COLUNA_URL ));
                 
                // Inserir a Marca e URL no banco de dados
                PreparedStatement insercaoStatement = conexao.prepareStatement(
                 String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?) ", TABELA_MODELOS, COLUNA_MODELOS, COLUNA_URL,COLUNA_IDMARCAS),
                 Statement.RETURN_GENERATED_KEYS)) {

        // Verificar se a marca já está cadastrada
        verificacaoStatement.setString(1, nome);
        ResultSet resultSet = verificacaoStatement.executeQuery();
        if (resultSet.next()) {
            JOptionPane.showMessageDialog(null, "O modelo já está cadastrado.");
            return null;
        }
        
        // Verificar se a logo já está cadastrada
        verificacaoLogo.setString(1, url);
        ResultSet resultLogo = verificacaoLogo.executeQuery();
        if (resultLogo.next()) {
            JOptionPane.showMessageDialog(null, "A logo do modelo já está cadastrada.");
            return null;
        }
        
        
        
        // Inserir a marca no banco de dados
        insercaoStatement.setString(1, nome );
        insercaoStatement.setString(2, url );
        insercaoStatement.setInt(3, idMarca );
        int rowsAffected = insercaoStatement.executeUpdate();

        if (rowsAffected == 0) {
            return null;
        }

        try (ResultSet generatedKeys = insercaoStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                
                int numero = idMarca;
                String numeroString = Integer.toString(numero);
                
                return new Modelo(id, nome, url, numeroString);
            } else {
                return null;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
    }
}



