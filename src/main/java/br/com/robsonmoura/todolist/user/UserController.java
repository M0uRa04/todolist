package br.com.robsonmoura.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * Estes parametros que o método está solicitando virão diretamente do
     * body da requisição, por isso utilizamos o @RequestBody
     *
     */
    @PostMapping("/")
    public void create (@RequestBody UserModel userModel){
        System.out.println(userModel.getUserName());
    }
}
