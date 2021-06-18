package ufp.uczelnianeforumproblemowe.logic.postService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.models.Post;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.repositories.PostRepository;

import java.util.List;

@Service
public class PostService implements PostServiceInterface{

    private final PostRepository postRepository;

    public PostService(@Autowired PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> pobierzWszystkiePostyNaPodstawieTematu(long idTematu) {
        return postRepository.pobierzWszystkiePostyNaPodstawieTematu(idTematu);
    }
}
