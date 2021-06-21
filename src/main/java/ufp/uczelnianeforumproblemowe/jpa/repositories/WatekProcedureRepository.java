package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

@Repository
public class WatekProcedureRepository {

    private final EntityManager entityManager;

    public WatekProcedureRepository(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<BigInteger> pobierzWszystkiePodwatki(long id){
        return entityManager.createNamedStoredProcedureQuery("procedura").setParameter("idNadwatku", id).getResultList();
    }
}
