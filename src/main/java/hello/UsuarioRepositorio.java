package hello;

import org.springframework.data.repository.CrudRepository;
import hello.Usuario;

public interface UsuarioRepositorio extends CrudRepository<Usuario, Integer> {

}
