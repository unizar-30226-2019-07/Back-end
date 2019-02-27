package hello;

import org.springframework.data.repository.CrudRepository;
import hello.Media;

public interface MediaRepositorio extends CrudRepository<Media, Integer> {

}
