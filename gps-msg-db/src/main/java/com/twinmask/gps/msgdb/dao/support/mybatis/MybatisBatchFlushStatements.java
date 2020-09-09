package com.twinmask.gps.msgdb.dao.support.mybatis;

import java.util.List;

/**
 * @author Leo
 */
public interface MybatisBatchFlushStatements<M> {
	int batch(M mapper, List<?> values);
}
