/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;
import Controle.MarcaControle;
import Controle.IMarcaControle;
import Persistencia.IMarcaDao;
import Persistencia.MarcaDao;
import Modelos.MarcaModelo;
import Conection.DatabaseConnection;
/**
 *
 * @author Igor
 */
public class MarcaModelo {
    private int id;
    private String nome;
    private String url;

    public MarcaModelo(int id, String nome, String url) {
        this.id = id;
        this.nome = nome;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }    
    
}



