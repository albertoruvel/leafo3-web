package com.leafo3.data.repository;

import com.leafo3.data.dto.DamageClassChart;
import com.leafo3.data.dto.Page;
import com.leafo3.data.dto.PieChartModel;
import com.leafo3.data.entity.Leaf;
import com.leafo3.exception.DataAccessException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public interface LeafRepository {

    /**
     *
     * @param leaf
     * @return
     * @throws DataAccessException
     */
    public Leaf createNewLeaf(Leaf leaf) throws DataAccessException;

    /**
     *
     * @param email
     * @param pageNumber
     * @return
     * @throws DataAccessException
     */
    public Page<Leaf> getUserLeafs(String email, int pageNumber) throws DataAccessException;

    /**
     *
     * @param pageNumber
     * @return
     * @throws DataAccessException
     */
    public Page<Leaf> getNewestLeaf(int pageNumber) throws DataAccessException;

    /**
     *
     * @param pageNumber
     * @return
     * @throws DataAccessException
     */
    public Page<Leaf> getMoreDamageLeafs(int pageNumber) throws DataAccessException;

    /**
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    public Leaf findLeaf(String id) throws DataAccessException;

    /**
     *
     * @return @throws DataAccessException
     */
    public List<DamageClassChart> getDamageByClassChartData() throws DataAccessException;

    /**
     *
     * @return @throws DataAccessException
     */
    public List<PieChartModel> getPieChartData() throws DataAccessException;

    /**
     *
     * @return @throws DataAccessException
     */
    public List<Leaf> getLeafs() throws DataAccessException;
}
