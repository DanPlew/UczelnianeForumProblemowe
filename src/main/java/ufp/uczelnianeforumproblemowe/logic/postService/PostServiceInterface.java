package ufp.uczelnianeforumproblemowe.logic.postService;

import ufp.uczelnianeforumproblemowe.jpa.models.Post;

import java.util.List;

public interface PostServiceInterface {
    List<Post> pobierzWszystkiePostyNaPodstawieTematu(long idTematu);
}
