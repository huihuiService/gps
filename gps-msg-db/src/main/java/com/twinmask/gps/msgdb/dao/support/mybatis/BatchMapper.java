package com.twinmask.gps.msgdb.dao.support.mybatis;

import java.util.List;

/**
 * @author Leo
 */
public interface BatchMapper<T> {

    /**
     * 批量保存
     *
     * @param values
     * @return
     */
    int batchSave(List<T> values);

    /**
     * 批量修改
     *
     * @param values
     * @return
     */
    int batchUpdate(List<T> values);

    /**
     * 批量删除
     *
     * @param values
     * @return
     */
    int batchDelete(List<T> values);


    /**
     * 批量保存
     *
     * @param values
     * @return
     */
    int batchSaveSingle(T values);

    /**
     * 批量修改
     *
     * @param values
     * @return
     */
    int batchUpdateSingle(T values);

    /**
     * 批量删除
     *
     * @param values
     */
    int batchDeleteSingle(T values);
}
