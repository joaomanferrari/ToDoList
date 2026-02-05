package br.com.joaofelipe.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.joaofelipe.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // pega o caminho da requisição
        var servletPath = request.getServletPath();

        // verifica se o caminho começa com /tasks, caso seja aplica a autenticação
        if (servletPath.startsWith("/tasks")) {

            // pega a autenticação do usuário
            var authorization = request.getHeader("Authorization");

            // verifica se o header Authorization existe e se começa com Basic
            if (authorization == null || !authorization.startsWith("Basic ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // separa a palavra Basic do restante da string
            var authEncoded = authorization.substring("Basic".length()).trim();

            // decodifica a string que está em Base64 para um array de bytes
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            // converte o array de bytes para string
            var authString = new String(authDecode);

            // cria um array para separar o username e a senha,
            // com o usuário estando na posição 0 e a senha na posição 1
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // valida se o usuário existe no banco de dados,
            // caso não exista retorna um erro 401
            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // valida a senha do usuário
            var passwordVerify = BCrypt.verifyer()
                    .verify(password.toCharArray(), user.getPassword());

            if (!passwordVerify.verified) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // salva o id do usuário autenticado na requisição
            request.setAttribute("IdUser", user.getId());
        }

        // continua o fluxo da requisição
        filterChain.doFilter(request, response);
    }
}
