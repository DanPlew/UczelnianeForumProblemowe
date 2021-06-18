package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ufp.uczelnianeforumproblemowe.jpa.models.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select post from Post post inner join Temat temat on temat.id = post.temat.id where temat.id = ?1")
    List<Post> pobierzWszystkiePostyNaPodstawieTematu(long idTematu);
}
