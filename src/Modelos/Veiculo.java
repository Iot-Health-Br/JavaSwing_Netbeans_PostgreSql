/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Controle.IVeiculoControle;
import Controle.VeiculoControle;
import Persistencia.IVeiculoDao;
import Persistencia.VeiculoDao;
import Telas.TelaDosVeiculos;


public class Veiculo {
    private int id;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;
    private int renavam;
    private String preçoCompra;
    private String preçoVenda;
    private int anoFabricacao;
    private int anoModelo;
    private String combustivel;
    private int quilometragem;   
    private String url;
    
public Veiculo( int id, String marca, String modelo, String url) {
        this.id = id;
        this.modelo = marca;
        this.marca = modelo;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getRenavam() {
        return renavam;
    }

    public void setRenavam(int renavam) {
        this.renavam = renavam;
    }

    public String getPreçoCompra() {
        return preçoCompra;
    }

    public void setPreçoCompra(String preçoCompra) {
        this.preçoCompra = preçoCompra;
    }

    public String getPreçoVenda() {
        return preçoVenda;
    }

    public void setPreçoVenda(String preçoVenda) {
        this.preçoVenda = preçoVenda;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public int getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(int anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
