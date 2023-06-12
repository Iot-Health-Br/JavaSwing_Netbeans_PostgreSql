/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;
import Controle.ModeloControle;
import Controle.IModeloControle;
import Persistencia.IModeloDao;
import Persistencia.ModeloDao;


public class Modelo {
    private int id;
    private String modelo;
    private String marca;
    private String url;

    public Modelo(int id, String modelo, String url, String marca) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
