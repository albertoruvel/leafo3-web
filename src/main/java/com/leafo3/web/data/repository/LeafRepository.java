package com.leafo3.web.data.repository;

import com.leafo3.dto.entity.Page;
import com.leafo3.web.data.entity.Leaf;

import javax.persistence.PersistenceException;
import java.util.List;

public interface LeafRepository {

    /**
     *
     * @param leaf
     * @return
     * @throws PersistenceException
     */
    public Leaf createNewLeaf(Leaf leaf) throws PersistenceException;

    /**
     *
     * @param email
     * @param pageNumber
     * @return
     * @throws PersistenceException
     */
    public Page<Leaf> getUserLeafs(String email, int pageNumber) throws PersistenceException;

    /**
     *
     * @param pageNumber
     * @return
     * @throws PersistenceException
     */
    public Page<Leaf> getNewestLeaf(int pageNumber) throws PersistenceException;

    /**
     *
     * @param pageNumber
     * @return
     * @throws PersistenceException
     */
    public Page<Leaf> getMoreDamageLeafs(int pageNumber) throws PersistenceException;

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    public Leaf findLeaf(String id) throws PersistenceException;

    /**
     *
     * @return @throws DataAccessException
     */
    public List<Object[]> getDamageByClassChartData() throws PersistenceException;

    /**
     *
     * @return @throws DataAccessException
     */
    public List<Object[]> getPieChartData() throws PersistenceException;

    /**
     *
     * @return @throws DataAccessException
     */
    public List<Leaf> getLeafs() throws PersistenceException;
}
