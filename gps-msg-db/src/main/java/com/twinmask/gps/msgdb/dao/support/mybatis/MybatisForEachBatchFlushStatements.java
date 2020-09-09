package com.twinmask.gps.msgdb.dao.support.mybatis;

/**
 * @author Leo
 */
public interface MybatisForEachBatchFlushStatements<M> {
	int batch(M mapper, Object value);
}
