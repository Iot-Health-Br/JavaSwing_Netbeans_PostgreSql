/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Controle.IVeiculoControle;
import Controle.VeiculoControle;
import Modelos.Veiculo;
import Persistencia.IVeiculoDao;
import Persistencia.VeiculoDao;
import Telas.TelaDosVeiculos;

import Conection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;



public class VeiculoDao implements IVeiculoDao {
    private Connection connection;
    
    private static final String TABELA_VEICULOS = "tabeladeveiculos";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_MARCAS = "marcas";
    private static final String COLUNA_MODELOS = "modelos";
    private static final String COLUNA_URL = "url";

    
    public VeiculoDao() {
         criarTabela();
    }
    
    public VeiculoDao(Connection connection) {
        this.connection = connection;
    }
    
    private void criarTabela() {
        try (Connection conexao = DatabaseConnection.getConnection();
             Statement statement = conexao.createStatement()) {
            String query = String.format("CREATE TABLE IF NOT EXISTS %s (%s SERIAL PRIMARY KEY, %s VARCHAR(255), %s VARCHAR(255), %s VARCHAR(255))",
                    TABELA_VEICULOS, COLUNA_ID, COLUNA_MARCAS, COLUNA_MODELOS, COLUNA_URL );
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela de veiculos");
        }
    }

    
    public List<Veiculo> listarVeiculos() {
        List<Veiculo> veiculos = new ArrayList<>();

        try (Connection conexao = DatabaseConnection.getConnection();
             Statement statement = conexao.createStatement()) {
            String query = String.format("SELECT * FROM %s", TABELA_VEICULOS);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt(COLUNA_ID);
                String marcas = resultSet.getString(COLUNA_MARCAS);
                String modelo = resultSet.getString(COLUNA_MODELOS);               
                String url = resultSet.getString(COLUNA_URL);

                Veiculo veiculo = new Veiculo(id, marcas, modelo, url);
                veiculos.add(veiculo);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veiculos;
    }
    
    public boolean atualizarVeiculo(int id, String novaMarca, String novaModelo, String novaUrl) {
        try (Connection conexao = DatabaseConnection.getConnection();
            PreparedStatement statement = conexao.prepareStatement(
                 String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?", TABELA_VEICULOS, COLUNA_MARCAS, COLUNA_MODELOS, COLUNA_URL, COLUNA_ID))) {
            statement.setString(1, novaMarca);
            statement.setString(2, novaModelo);
            statement.setString(3, novaUrl);
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
    public Veiculo adicionarVeiculo(String Marca, String Modelo, String url) {
                
        try (Connection conexao = DatabaseConnection.getConnection();
                // Verificar se o veiculo já está cadastrada colocar a placa
                PreparedStatement verificacaoStatement = conexao.prepareStatement(
                 String.format("SELECT * FROM %s WHERE %s = ?", TABELA_VEICULOS, COLUNA_MARCAS ));
                
                // Verificar se a foto do carro já está cadastrada
                PreparedStatement verificacaoLogo = conexao.prepareStatement(
                 String.format("SELECT * FROM %s WHERE %s = ?", TABELA_VEICULOS, COLUNA_URL ));
                 
                // Inserir a Marca e URL no banco de dados
                PreparedStatement insercaoStatement = conexao.prepareStatement(
                 String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?) ", TABELA_VEICULOS, COLUNA_MARCAS, COLUNA_MODELOS, COLUNA_URL),
                 Statement.RETURN_GENERATED_KEYS)) {

        // Verificar se a marca já está cadastrada
        verificacaoStatement.setString(1, Marca);
        ResultSet resultSet = verificacaoStatement.executeQuery();
        if (resultSet.next()) {
            JOptionPane.showMessageDialog(null, "O veiculo já está cadastrado.");
            return null;
        }
        
        // Verificar se a logo já está cadastrada
        verificacaoLogo.setString(1, url);
        ResultSet resultLogo = verificacaoLogo.executeQuery();
        if (resultLogo.next()) {
            JOptionPane.showMessageDialog(null, "A foto do veiculo já está cadastrada.");
            return null;
        }
               
        // Inserir a marca no banco de dados
        insercaoStatement.setString(1, Marca );
        insercaoStatement.setString(2, Modelo );
        insercaoStatement.setString(3, url );
        int rowsAffected = insercaoStatement.executeUpdate();

        if (rowsAffected == 0) {
            return null;
        }

        try (ResultSet generatedKeys = insercaoStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                
                return new Veiculo(id, Marca, Modelo, url);
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