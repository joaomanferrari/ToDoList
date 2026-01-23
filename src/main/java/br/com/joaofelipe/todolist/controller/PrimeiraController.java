package br.com.joaofelipe.todolist.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")
// https://localhost:8080/primeiraRota -------
public class PrimeiraController {

    /**
     * MÉTODOS DE ACESSO DO HTTP
     * GET - Buscar um dado
     * POST - adicionar um dado
     * PUT - alterar um dado
     * DELETE - remover um dado
     * PATCH - alterar somente uma parte de um dado
     */

    //Método (Funcionalidade) de uma classe
    @GetMapping("/primeiroMetodo")
    public String PrimeiraMensagem(){

        return "Funcionou";
    }
}   
