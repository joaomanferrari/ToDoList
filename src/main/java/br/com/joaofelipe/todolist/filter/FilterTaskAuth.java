package br.com.joaofelipe.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { 
                
                //pegar a autenticação do usuário
               var Authorization = request.getHeader("Authorization");
               
                // separa a palavra Basic do restante da string

               var authEncoded = Authorization.substring("Basic".length()).trim();

            
                //Decodifica a string que está em Base64 para um array de bytes

               byte[] authDecode = Base64.getDecoder().decode(authEncoded);

               //converte o array de bytes para string

               var authString =new String(authDecode);
            

               //cria um array para serparar o username e a senha, com o usuario estando
               //na posição 0 e a senha na posição 1

               String[] credentials =authString.split(":");
               String username = credentials[0];
                String password = credentials[1];

                //Exibe no console o usuário e a senha após a mensagem de autorizado

                System.out.println("Autorizado pelo filtro");
               System.out.println("Usuário: " + username);
               System.err.println("Senha: " + password);
                
                filterChain.doFilter(request, response);
        
    }

    

    
}
