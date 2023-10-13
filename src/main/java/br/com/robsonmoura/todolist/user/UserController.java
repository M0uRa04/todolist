package br.com.robsonmoura.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    /**
     * Estes parametros que o método está solicitando virão diretamente do
     * body da requisição, por isso utilizamos o @RequestBody
     *
     */
    @PostMapping("/")
    public ResponseEntity create (@RequestBody UserModel userModel){
        var user = this.userRepository.findByUserName(userModel.getUserName());
        
        if(user != null){
            //Retornar Mensagem de erro
            //Retornar Status Code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário ja existe");
        }

        /**Explicando esta linha de código:
         * Nesta linha transformamos a senha do usuário em um hashCode para que sua senha fique protegida
         * Primeiramente importamos a biblioteca Bcrypt, chamamos seu metodo withDefaults pois ela ja encrypta com seus proprios padroes
         * Depois transformamos o hashCode em string: 12, userModel.getPassword() nesta parte estamos passando o parametro 12 como a 
         * "Força de encryptação" e depois passamos a senha do usuário.
         * Porém no hashToString ele exige que passemos um char ao invés de uma string como neste caso, então por ultimo tranformamos
         * esta string em um array de char.
         * por fim armazenamos o hash em uma var "passowrdHashed"
        */
        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
