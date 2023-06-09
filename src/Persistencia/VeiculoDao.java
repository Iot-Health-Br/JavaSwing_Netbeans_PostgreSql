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
    
    private static final String COLUNA_COR = "cor";
    private static final String COLUNA_PLACA = "placa";
    private static final String COLUNA_RENAVAM = "renavam";
    private static final String COLUNA_COMPRA = "preçoCompra";
    private static final String COLUNA_VENDA = "preçoVenda";
    private static final String COLUNA_ANOFAB = "anoFabricaçao";
    private static final String COLUNA_ANOMODELO = "AnoModelo";
    private static final String COLUNA_COMBUSTIVEL = "combsutivel";
    private static final String COLUNA_KM = "Quilometragem";   
    
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
             String query = String.format("CREATE TABLE IF NOT EXISTS %s (%s SERIAL PRIMARY KEY, %s VARCHAR(50), %s VARCHAR(50), %s VARCHAR(50), %s VARCHAR(8), %s INTEGER, %s INTEGER, %s VARCHAR(10), %s INTEGER, %s INTEGER, %s VARCHAR(50), %s VARCHAR(50),%s VARCHAR(100))",
        TABELA_VEICULOS, COLUNA_ID, COLUNA_MARCAS, COLUNA_MODELOS, COLUNA_COR, COLUNA_PLACA, COLUNA_ANOFAB, COLUNA_ANOMODELO,COLUNA_COMBUSTIVEL,COLUNA_KM, COLUNA_RENAVAM,COLUNA_COMPRA, COLUNA_VENDA, COLUNA_URL);
            statement.executeUpdate(query);
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela de veiculos");
            e.printStackTrace();          
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
                String cor = resultSet.getString(COLUNA_COR);
                String placa = resultSet.getString(COLUNA_PLACA); 
                String renavan = resultSet.getString(COLUNA_RENAVAM);
                String fab = resultSet.getString(COLUNA_ANOFAB);
                String model = resultSet.getString(COLUNA_ANOMODELO);
                String combustivel = resultSet.getString(COLUNA_COMBUSTIVEL);
                String km = resultSet.getString(COLUNA_KM);
                String compra = resultSet.getString(COLUNA_COMPRA);
                String venda = resultSet.getString(COLUNA_VENDA);
                String url = resultSet.getString(COLUNA_URL);
                
                int novoRenavam = Integer.parseInt(renavan);
                int novofab = Integer.parseInt(fab);
                int novomodel = Integer.parseInt(model);
                int novokm = Integer.parseInt(km);

                Veiculo veiculo = new Veiculo(id, marcas, modelo, cor, placa, novofab, novomodel,combustivel,novokm, novoRenavam,compra, venda, url);
                veiculos.add(veiculo);
            }

            resultSet.close();
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "Erro na listagem de veiculos");
            e.printStackTrace();       
        }

        return veiculos;
    }
    
    public boolean atualizarVeiculo(int id, String novaMarca, String novoModelo, String novaCor, String novaPlaca, int novafab, int novoModel,String novoCombustivel,int novokm, int novoRenavam, String novaCompra, String novaVenda, String novoUrl) {
        try (Connection conexao = DatabaseConnection.getConnection();
            PreparedStatement statement = conexao.prepareStatement(
 String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?,%s = ?, %s = ?, %s = ?, %s = ?,%s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", TABELA_VEICULOS, COLUNA_MARCAS, COLUNA_MODELOS, COLUNA_COR, COLUNA_PLACA, COLUNA_ANOFAB, COLUNA_ANOMODELO, COLUNA_COMBUSTIVEL, COLUNA_KM, COLUNA_RENAVAM, COLUNA_COMPRA, COLUNA_VENDA, COLUNA_URL, COLUNA_ID))) {
            statement.setString(1, novaMarca);
            statement.setString(2, novoModelo);
            statement.setString(3, novaCor);
            statement.setString(4, novaPlaca);
            
            statement.setInt(5, novafab);
            statement.setInt(6, novoModel);
            statement.setString(7, novoCombustivel);
            statement.setInt(8, novokm);
            statement.setInt(9, novoRenavam);
            statement.setString(10, novaCompra);
            statement.setString(11, novaVenda);
         
            statement.setString(12, novoUrl);
            statement.setInt(13, id);
                       
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Veiculo adicionarVeiculo(String Marca, String Modelo, String cor, String placa, int fab, int model,String combustivel,int km, int renavam, String compra, String venda, String url) {
                
        try (Connection conexao = DatabaseConnection.getConnection();
                // Verificar se o veiculo já está cadastrada colocar a placa
                PreparedStatement verificacaoStatement = conexao.prepareStatement(
                 String.format("SELECT * FROM %s WHERE %s = ?", TABELA_VEICULOS, COLUNA_PLACA ));
                 
                // Inserir OS DADOS DO VEICULO no banco de dados
                PreparedStatement insercaoStatement = conexao.prepareStatement(
                 String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", TABELA_VEICULOS, COLUNA_MARCAS, COLUNA_MODELOS, COLUNA_COR, COLUNA_PLACA, COLUNA_ANOFAB, COLUNA_ANOMODELO, COLUNA_COMBUSTIVEL, COLUNA_KM, COLUNA_RENAVAM,COLUNA_COMPRA, COLUNA_VENDA, COLUNA_URL),
                 Statement.RETURN_GENERATED_KEYS)) {

        // Verificar se a placa já está cadastrada
        verificacaoStatement.setString(1, placa);
        ResultSet resultSet = verificacaoStatement.executeQuery();
        if (resultSet.next()) {
            JOptionPane.showMessageDialog(null, "A placa do veiculo já está cadastrada.");
            return null;
        }
        
               
        // Inserir a marca no banco de dados
        insercaoStatement.setString(1, Marca );
        insercaoStatement.setString(2, Modelo );
        insercaoStatement.setString(3, cor );   
        insercaoStatement.setString(4, placa );
        insercaoStatement.setInt(5, fab );
        insercaoStatement.setInt(6, model );
        insercaoStatement.setString(7, combustivel );
        insercaoStatement.setInt(8, km );
        insercaoStatement.setInt(9, renavam );
        insercaoStatement.setString(10, compra );
        insercaoStatement.setString(11, venda );
        insercaoStatement.setString(12, url );
        
        int rowsAffected = insercaoStatement.executeUpdate();

        if (rowsAffected == 0) {
            return null;
        }

        try (ResultSet generatedKeys = insercaoStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                
                return new Veiculo(id, Marca, Modelo, cor, placa, fab, model, combustivel, km,  renavam, compra, venda, url);
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