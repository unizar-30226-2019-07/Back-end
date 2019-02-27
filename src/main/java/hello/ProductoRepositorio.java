package hello;

import org.springframework.data.repository.CrudRepository;
import hello.Producto;

public interface ProductoRepositorio extends CrudRepository<Producto, Integer> {

}
