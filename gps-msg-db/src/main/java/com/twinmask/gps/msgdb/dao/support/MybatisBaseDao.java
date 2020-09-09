package com.twinmask.gps.msgdb.dao.support;

import com.twinmask.gps.msgdb.dao.support.mybatis.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import reactor.util.annotation.NonNull;

import java.util.List;

/**
 * @version 1.0
 * @description 封装Mybaits持久化基础功能
 * @project: mds-op
 * @Date:2018年8月7日
 * @Company: yitd
 */
public abstract class MybatisBaseDao {

    @Autowired
    @Qualifier("sqlSessionFactory")
    protected SqlSessionFactory sqlSessionFactory;

    /**
     * 批量提交默认每批次提次数量 默认为1000条/次
     */
    protected static final int DEFAULT_BATCH_COMMIT_COUNT = 1000;

    protected String getStatement(String methodName) {
        return getNamespace().concat(".").concat(methodName);
    }

    protected String getNamespace() {
        return "";
    }

    /**
     * 使用完整命名空间进行批量操作，支持Mybaits的Mapper中单个对象操作
     *
     * @param statement Mapper文件完整命名空间，如：<mapper namespace=
     *                  "com.ytd.mds.dao.mapper.SmsTempTestMapper">
     * @param values    数据集合{@link List}
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchStatementBySingle(@NonNull String statement, @NonNull List<T> values)
            throws DataPersistenceException {
        return batchCommitByStatement(statement, values, BatchMapperSupport.BatchMode.SINGLE);
    }

    /**
     * 使用 Mapper ID 进行批量操作，支持 Mybaits 的Mapper中单个对象操作
     *
     * @param methodName
     * @param values
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchMethodBySingle(@NonNull String methodName, @NonNull List<T> values)
            throws DataPersistenceException {
        return batchStatementBySingle(getStatement(methodName), values);
    }

    /**
     * 使用完整命名空间进行批量操作，支持Mybaits的Mapper中对象集合操作
     *
     * @param statement Mapper文件完整命名空间，如：<mapper namespace=
     *                  "com.ytd.mds.dao.mapper.SmsTempTestMapper">
     * @param values    数据集合{@link List}
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchStatementByList(@NonNull String statement, @NonNull List<T> values)
            throws DataPersistenceException {
        return batchCommitByStatement(statement, values, BatchMapperSupport.BatchMode.LIST);
    }

    /**
     * 使用完整命名空间进行批量操作
     *
     * @param statement Mapper文件完整命名空间，如：<mapper namespace=
     *                  "com.ytd.mds.dao.mapper.SmsTempTestMapper">
     * @param values    数据集合{@link List}
     * @param batchMode 批量操作相关操作方式：Mapper中的单个对象和修改和Mapper中对象集合操作{@link BatchMapperSupport.BatchMode}
     * @return
     */
    private <T> int batchCommitByStatement(@NonNull String statement, @NonNull List<T> values,
                                           BatchMapperSupport.BatchMode batchMode) {
        SqlSession sqlSession = null;
        int toIndex = DEFAULT_BATCH_COMMIT_COUNT;
        int totalSize = values.size();
        int result = 0;
        try {
            // 获取批量方式的sqlsession
            sqlSession = openBatchSession();

            long startTime = System.currentTimeMillis();

            for (int fromIndex = 0; fromIndex < totalSize; ) {
                if (toIndex >= totalSize) {
                    toIndex = totalSize;
                    result += BatchMapperSupport.flushStatements(sqlSession, statement, values, fromIndex, toIndex,
                            batchMode);
                    break;
                } else {
                    result += BatchMapperSupport.flushStatements(sqlSession, statement, values, fromIndex, toIndex,
                            batchMode);
                    fromIndex = toIndex;
                    toIndex = fromIndex + DEFAULT_BATCH_COMMIT_COUNT;
                }
            }
            sqlSession.flushStatements();
        } catch (Exception e) {
            throw new DataPersistenceException(e);
        } finally {
            closeSession(sqlSession);
        }

        return result;
    }

    /**
     * 批量保存
     *
     * @param values 数据集合{@link List}
     * @param mapper {@link BatchMapper}
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchInsert(@NonNull List<T> values, BatchMapper<T> mapper) throws DataPersistenceException {
        return batchCommit(values, mapper, BatchMapperSupport.BatchType.INSERT, BatchMapperSupport.BatchMode.LIST);
    }

    /**
     * 批量更新
     *
     * @param values 数据集合{@link List}
     * @param mapper {@link BatchMapper}
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchUpdate(@NonNull List<T> values, BatchMapper<T> mapper) throws DataPersistenceException {
        return batchCommit(values, mapper, BatchMapperSupport.BatchType.UPDATE, BatchMapperSupport.BatchMode.LIST);
    }

    /**
     * 批量删除
     *
     * @param values 数据集合{@link List}
     * @param mapper {@link BatchMapper}
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchDelete(@NonNull List<T> values, BatchMapper<T> mapper) throws DataPersistenceException {
        return batchCommit(values, mapper, BatchMapperSupport.BatchType.DELETE, BatchMapperSupport.BatchMode.LIST);
    }

    /**
     * 批量保存
     *
     * @param values    数据集合{@link List}
     * @param mapper    {@link BatchMapper}
     * @param batchMode 批量操作相关操作方式：Mapper中的单个对象和修改和Mapper中对象集合操作{@link BatchMapperSupport.BatchMode}
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchInsert(@NonNull List<T> values, BatchMapper<T> mapper,
                                  BatchMapperSupport.BatchMode batchMode) throws DataPersistenceException {
        return batchCommit(values, mapper, BatchMapperSupport.BatchType.INSERT, batchMode);
    }

    /**
     * 批量更新
     *
     * @param values    数据集合{@link List}
     * @param mapper    {@link BatchMapper}
     * @param batchMode 批量操作相关操作方式：Mapper中的单个对象和修改和Mapper中对象集合操作{@link BatchMapperSupport.BatchMode}
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchUpdate(@NonNull List<T> values, BatchMapper<T> mapper,
                                  BatchMapperSupport.BatchMode batchMode) throws DataPersistenceException {
        return batchCommit(values, mapper, BatchMapperSupport.BatchType.UPDATE, batchMode);
    }

    /**
     * 批量删除
     *
     * @param values    数据集合{@link List}
     * @param mapper    {@link BatchMapper}
     * @param batchMode 批量操作相关操作方式：Mapper中的单个对象和修改和Mapper中对象集合操作{@link BatchMapperSupport.BatchMode}
     * @return
     * @throws DataPersistenceException
     */
    protected <T> int batchDelete(@NonNull List<T> values, BatchMapper<T> mapper,
                                  BatchMapperSupport.BatchMode batchMode) throws DataPersistenceException {
        return batchCommit(values, mapper, BatchMapperSupport.BatchType.DELETE, batchMode);
    }

    /**
     * 批量执行相关数据库操作
     *
     * @param values    数据集合{@link List}
     * @param mapper    {@link BatchMapper}
     * @param batchType {@link BatchMapperSupport.BatchType}
     * @return
     */
    private <T> int batchCommit(@NonNull List<T> values, BatchMapper<T> mapper, BatchMapperSupport.BatchType batchType,
                                BatchMapperSupport.BatchMode batchMode) {
        SqlSession sqlSession = null;
        int toIndex = DEFAULT_BATCH_COMMIT_COUNT;
        int totalSize = values.size();
        int result = 0;
        try {
            // 开启session
            sqlSession = openBatchSession();

            long startTime = System.currentTimeMillis();
            for (int fromIndex = 0; fromIndex < totalSize; ) {
                if (toIndex >= totalSize) {
                    toIndex = totalSize;
                    result += BatchMapperSupport.flushStatements(sqlSession, mapper, values, fromIndex, toIndex,
                            batchType, batchMode);
                    break;
                } else {
                    result += BatchMapperSupport.flushStatements(sqlSession, mapper, values, fromIndex, toIndex,
                            batchType, batchMode);
                    fromIndex = toIndex;
                    toIndex = fromIndex + DEFAULT_BATCH_COMMIT_COUNT;
                }
            }
            sqlSession.flushStatements();
        } catch (Exception e) {
            throw new DataPersistenceException(e);
        } finally {
            closeSession(sqlSession);
        }

        return result;
    }

    /**
     * 开启批量SqlSession
     *
     * @return SqlSession {@link SqlSession}
     */
    private SqlSession openBatchSession() {
        return sqlSessionFactory.openSession(ExecutorType.BATCH, false);
    }

    /**
     * 释放SqlSession
     *
     * @param sqlSession SqlSession {@link SqlSession}
     */
    private void closeSession(SqlSession sqlSession) {
        if (null != sqlSession) {
            sqlSession.clearCache();
            sqlSession.close();
        }
    }

    /**
     * mybatis批量操作
     *
     * @param mapper                      Mybatis的Mapper对象 {@link Mapper}
     * @param values                      批量操作的数据集合
     * @param mybatisBatchFlushStatements {@link MybatisBatchFlushStatements}
     * @return
     */
    public <T> int batchUpdate(T mapper, @NonNull List<?> values,
                               MybatisBatchFlushStatements<T> mybatisBatchFlushStatements) {
        SqlSession sqlSession = null;
        int toIndex = DEFAULT_BATCH_COMMIT_COUNT;
        int totalSize = values.size();
        int result = 0;
        try {
            // 开启session
            sqlSession = openBatchSession();

            long startTime = System.currentTimeMillis();
            for (int fromIndex = 0; fromIndex < totalSize; ) {
                if (toIndex >= totalSize) {
                    toIndex = totalSize;
                    result += BatchMapperSupport.batch(sqlSession, mapper, mybatisBatchFlushStatements, values,
                            fromIndex, toIndex);
                    break;
                } else {
                    result += BatchMapperSupport.batch(sqlSession, mapper, mybatisBatchFlushStatements, values,
                            fromIndex, toIndex);
                    fromIndex = toIndex;
                    toIndex = fromIndex + DEFAULT_BATCH_COMMIT_COUNT;
                }
            }
            sqlSession.flushStatements();
        } catch (Exception e) {
            throw new DataPersistenceException(e);
        } finally {
            closeSession(sqlSession);
        }

        return result;
    }

    /**
     * mybatis批量操作
     *
     * @param mapper                      Mybatis的Mapper对象 {@link Mapper}
     * @param values                      批量操作的数据集合
     * @param mybatisBatchFlushStatements {@link MybatisForEachBatchFlushStatements}
     * @return
     */
    public <T> int batchUpdate(T mapper, @NonNull List<?> values,
                               MybatisForEachBatchFlushStatements<T> mybatisBatchFlushStatements) {
        SqlSession sqlSession = null;
        int toIndex = DEFAULT_BATCH_COMMIT_COUNT;
        int totalSize = values.size();
        int result = 0;
        try {
            // 开启session
            sqlSession = openBatchSession();

            long startTime = System.currentTimeMillis();
            for (int fromIndex = 0; fromIndex < totalSize; ) {
                if (toIndex >= totalSize) {
                    toIndex = totalSize;
                    result += BatchMapperSupport.batchForEach(sqlSession, mapper, mybatisBatchFlushStatements, values,
                            fromIndex, toIndex);
                    break;
                } else {
                    result += BatchMapperSupport.batchForEach(sqlSession, mapper, mybatisBatchFlushStatements, values,
                            fromIndex, toIndex);
                    fromIndex = toIndex;
                    toIndex = fromIndex + DEFAULT_BATCH_COMMIT_COUNT;
                }
            }
            sqlSession.flushStatements();
        } catch (Exception e) {
            throw new DataPersistenceException(e);
        } finally {
            closeSession(sqlSession);
        }

        return result;
    }

}
