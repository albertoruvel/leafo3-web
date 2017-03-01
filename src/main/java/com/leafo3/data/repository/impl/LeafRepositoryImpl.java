package com.leafo3.data.repository.impl;

import com.leafo3.data.dto.DamageClassChart;
import com.leafo3.data.dto.Page;
import com.leafo3.data.dto.PieChartModel;
import com.leafo3.data.entity.Leaf;
import com.leafo3.data.repository.LeafRepository;
import com.leafo3.exception.DataAccessException;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
@Stateless
public class LeafRepositoryImpl implements LeafRepository {

    @PersistenceContext
    private EntityManager em;

    private static final int PAGE_SIZE = 10;

    @Override
    public Leaf createNewLeaf(Leaf leaf) throws DataAccessException {
        try {
            em.persist(leaf);
            return leaf;
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public Page<Leaf> getUserLeafs(String email, int pageNumber) throws DataAccessException {
        try {
            Query q = em.createQuery("SELECT l FROM Leaf l WHERE l.uploadedBy = :email")
                    .setParameter("email", email)
                    .setMaxResults(PAGE_SIZE)
                    .setFirstResult((pageNumber - 1) * PAGE_SIZE);

            List<Leaf> leafs = q.getResultList();
            //count
            q = em.createQuery("SELECT COUNT(l.id) FROM Leaf l WHERE l.uploadedBy = :email")
                    .setParameter("email", email);
            Long count = (Long) q.getSingleResult();
            Page<Leaf> leafsPage = new Page<Leaf>();
            leafsPage.setPageItems(leafs);
            leafsPage.setPageNumber(pageNumber);
            leafsPage.setPagesAvailable((int) ((count.intValue() / PAGE_SIZE) + 1));
            return leafsPage;
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public Page<Leaf> getNewestLeaf(int pageNumber) throws DataAccessException {
        try {
            Query q = em.createQuery("SELECT l FROM Leaf l ORDER BY l.creationDate desc")
                    .setMaxResults(PAGE_SIZE)
                    .setFirstResult((pageNumber - 1) * PAGE_SIZE);

            List<Leaf> leafs = q.getResultList();
            //count
            q = em.createQuery("SELECT COUNT(l.id) FROM Leaf l ORDER BY l.creationDate desc");
            Long count = (Long) q.getSingleResult();
            Page<Leaf> leafsPage = new Page<Leaf>();
            leafsPage.setPageItems(leafs);
            leafsPage.setPageNumber(pageNumber);
            leafsPage.setPagesAvailable((int) ((count.intValue() / PAGE_SIZE) + 1));
            return leafsPage;
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public Page<Leaf> getMoreDamageLeafs(int pageNumber) throws DataAccessException {
        try {
            Query q = em.createQuery("SELECT l FROM Leaf l ORDER BY l.damageClass desc")
                    .setMaxResults(PAGE_SIZE)
                    .setFirstResult((pageNumber - 1) * PAGE_SIZE);

            List<Leaf> leafs = q.getResultList();
            //count
            q = em.createQuery("SELECT COUNT(l.id) FROM Leaf l ORDER BY l.damageClass desc");
            Long count = (Long) q.getSingleResult();
            Page<Leaf> leafsPage = new Page<Leaf>();
            leafsPage.setPageItems(leafs);
            leafsPage.setPageNumber(pageNumber);
            leafsPage.setPagesAvailable((int) ((count.intValue() / PAGE_SIZE) + 1));
            return leafsPage;
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public Leaf findLeaf(String id) throws DataAccessException {
        try {
            Leaf leaf = em.find(Leaf.class, id);
            return leaf;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public List<DamageClassChart> getDamageByClassChartData() throws DataAccessException {
        try {
            Query q = em.createNativeQuery("select count(*) count, iso_code isoCode, avg(damage_class) avg from leaf group by iso_code ");
            List<DamageClassChart> map = q.getResultList();
            return map;
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public List<PieChartModel> getPieChartData() throws DataAccessException {
        try {
            Query q = em.createNativeQuery("select count(*) count, iso_code isoCode from leaf group by iso_code");
            return q.getResultList();
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public List<Leaf> getLeafs() throws DataAccessException {
        try{
            List<Leaf> leafs = em.createQuery("SELECT l FROM Leaf l")
                    .getResultList();
            return leafs;
        }catch(Exception ex){
            throw new DataAccessException(ex.getMessage());
        }
    }

}
