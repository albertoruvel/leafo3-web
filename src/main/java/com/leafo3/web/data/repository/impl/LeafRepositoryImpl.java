package com.leafo3.web.data.repository.impl;

import com.leafo3.dto.entity.Page;
import com.leafo3.web.data.entity.Leaf;
import com.leafo3.web.data.repository.LeafRepository;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class LeafRepositoryImpl extends AbstractRepository implements LeafRepository {

    private static final int PAGE_SIZE = 10;

    @Override
    public Leaf createNewLeaf(Leaf leaf) throws PersistenceException {
        entityManager.persist(leaf);
        return leaf;
    }

    @Override
    public Page<Leaf> getUserLeafs(String email, int pageNumber) throws PersistenceException {
            Query q = entityManager.createQuery("SELECT l FROM Leaf l WHERE l.uploadedBy = :email")
                    .setParameter("email", email)
                    .setMaxResults(PAGE_SIZE)
                    .setFirstResult((pageNumber - 1) * PAGE_SIZE);

            List<Leaf> leafs = q.getResultList();
            //count
            q = entityManager.createQuery("SELECT COUNT(l.id) FROM Leaf l WHERE l.uploadedBy = :email")
                    .setParameter("email", email);
            Long count = (Long) q.getSingleResult();
            Page<Leaf> leafsPage = new Page<Leaf>();
            leafsPage.setPageItems(leafs);
            leafsPage.setPageNumber(pageNumber);
            leafsPage.setPagesAvailable((int) ((count.intValue() / PAGE_SIZE) + 1));
            return leafsPage;

    }

    @Override
    public Page<Leaf> getNewestLeaf(int pageNumber) throws PersistenceException {
        Query q = entityManager.createQuery("SELECT l FROM Leaf l ORDER BY l.creationDate desc")
                .setMaxResults(PAGE_SIZE)
                .setFirstResult((pageNumber - 1) * PAGE_SIZE);

        List<Leaf> leafs = q.getResultList();
        //count
        q = entityManager.createQuery("SELECT COUNT(l.id) FROM Leaf l ORDER BY l.creationDate desc");
        Long count = (Long) q.getSingleResult();
        Page<Leaf> leafsPage = new Page<Leaf>();
        leafsPage.setPageItems(leafs);
        leafsPage.setPageNumber(pageNumber);
        leafsPage.setPagesAvailable((int) ((count.intValue() / PAGE_SIZE) + 1));
        return leafsPage;
    }

    @Override
    public Page<Leaf> getMoreDamageLeafs(int pageNumber) throws PersistenceException {
        Query q = entityManager.createQuery("SELECT l FROM Leaf l ORDER BY l.damageClass desc")
                .setMaxResults(PAGE_SIZE)
                .setFirstResult((pageNumber - 1) * PAGE_SIZE);

        List<Leaf> leafs = q.getResultList();
        //count
        q = entityManager.createQuery("SELECT COUNT(l.id) FROM Leaf l ORDER BY l.damageClass desc");
        Long count = (Long) q.getSingleResult();
        Page<Leaf> leafsPage = new Page<Leaf>();
        leafsPage.setPageItems(leafs);
        leafsPage.setPageNumber(pageNumber);
        leafsPage.setPagesAvailable((int) ((count.intValue() / PAGE_SIZE) + 1));
        return leafsPage;
    }

    @Override
    public Leaf findLeaf(String id) throws PersistenceException {
        Leaf leaf = entityManager.find(Leaf.class, id);
        return leaf;
    }

    @Override
    public List<Object[]> getDamageByClassChartData() throws PersistenceException {
        Query q = entityManager.createNativeQuery("select count(*) count, iso_code isoCode, avg(damage_class) avg from leaf group by iso_code ");
        return q.getResultList();
    }

    @Override
    public List<Object[]> getPieChartData() throws PersistenceException {
        Query q = entityManager.createNativeQuery("select count(*) count, iso_code isoCode from leaf group by iso_code");
        return q.getResultList();
    }

    @Override
    public List<Leaf> getLeafs() throws PersistenceException {
        List<Leaf> leafs = entityManager.createQuery("SELECT l FROM Leaf l")
                .getResultList();
        return leafs;
    }

}
