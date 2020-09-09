package com.twinmask.gps.msgdb.dao.support.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BatchMapperSupport {
    /**
     * @description 批量操作相关操作类型：主要提供增加、修改和删除
     */
    public enum BatchType {
        INSERT(0), DELETE(1), UPDATE(2);

        BatchType(int value) {
            this.value = value;
        }

        private int value;

        public int getValue() {
            return this.value;
        }
    }

    /**
     * @version 1.0
     * @description 批量操作相关操作方式：Mapper中的单个对象和修改和Mapper中对象集合操作
     * @project: mds-op
     * @Date:2018年8月9日
     * @Company: yitd
     */
    public enum BatchMode {
        /**
         * 支技Mapper中的单个对象操作
         */
        SINGLE(0),
        /**
         * 支技Mapper中对象集合操作
         */
        LIST(1);

        BatchMode(int value) {
            this.value = value;
        }

        private int value;

        public int getValue() {
            return this.value;
        }
    }

    /**
     * 常规批量操作,主要支持BathcMapper接口中定义的方法
     *
     * @param sqlSession SqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @param batchType  操作类型 :insert、update、delete {@link BatchType}
     * @param BatchMode  批量操作相关操作方式：Mapper中的单个对象和修改和Mapper中对象集合操作 {@link BatchMode}
     * @return
     */
    public static <T> int flushStatements(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values, int fromIndex,
                                          int toIndex, BatchType batchType, BatchMode batchMode) {

        switch (batchMode) {
            case SINGLE:
                return flushStatementsSingle(sqlSession, mapper, values, fromIndex, toIndex, batchType);
            default:
                return flushStatementsList(sqlSession, mapper, values, fromIndex, toIndex, batchType);
        }
    }

    /**
     * 执行insert并批量提交
     *
     * @param sqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @param batchMode  批量操作相关操作方式：Mapper中的单个对象和修改和Mapper中对象集合操作{@link BatchMapperSupport.BatchMode}
     * @return
     */
    public static <T> int flushStatements(SqlSession sqlSession, String statement, List<T> values, int fromIndex,
                                          int toIndex, BatchMode batchMode) {
        switch (batchMode) {
            case SINGLE:
                return flushSingle(sqlSession, statement, values, fromIndex, toIndex);
            default:
                return flushList(sqlSession, statement, values, fromIndex, toIndex);
        }

    }

    /**
     * 常规批量操作,支持BathcMapper中batchSave,batchDelete和batchUpdate方法
     *
     * @param sqlSession SqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @param batchType  {@link BatchType}
     * @return
     */
    private static <T> int flushStatementsList(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values,
                                               int fromIndex, int toIndex, BatchType batchType) {
        switch (batchType) {
            case INSERT:
                return batchSave(sqlSession, mapper, values, fromIndex, toIndex);
            case DELETE:
                return batchDelete(sqlSession, mapper, values, fromIndex, toIndex);
            case UPDATE:
                return batchUpdate(sqlSession, mapper, values, fromIndex, toIndex);
            default:
                return -1;
        }
    }

    /**
     * 支持BathcMapper中batchSaveSingle,batchDeleteSingle和batchUpdateSingle方法
     *
     * @param sqlSession SqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @param batchType  {@link BatchType}
     * @return
     */
    private static <T> int flushStatementsSingle(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values,
                                                 int fromIndex, int toIndex, BatchType batchType) {
        switch (batchType) {
            case INSERT:
                return batchSaveBySingle(sqlSession, mapper, values, fromIndex, toIndex);
            case DELETE:
                return batchDeleteBySingle(sqlSession, mapper, values, fromIndex, toIndex);
            case UPDATE:
                return batchUpdateBySingle(sqlSession, mapper, values, fromIndex, toIndex);
            default:
                return -1;
        }
    }

    /**
     * 执行insert并批量提交
     *
     * @param sqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @return
     */
    private static <T> int batchSave(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values, int fromIndex,
                                     int toIndex) {
        int result = mapper.batchSave(values.subList(fromIndex, toIndex));
        sqlSession.flushStatements();
        return result;
    }

    /**
     * 执行insert并批量提交
     *
     * @param sqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @return
     */
    private static <T> int batchDelete(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values, int fromIndex,
                                       int toIndex) {
        int result = mapper.batchDelete(values.subList(fromIndex, toIndex));
        sqlSession.flushStatements();
        return result;
    }

    /**
     * 执行insert并批量提交
     *
     * @param sqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @return
     */
    private static <T> int batchUpdate(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values, int fromIndex,
                                       int toIndex) {
        int result = mapper.batchUpdate(values.subList(fromIndex, toIndex));
        sqlSession.flushStatements();
        return result;
    }

    /**
     * 执行insert并批量提交
     *
     * @param sqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @return
     */
    private static <T> int batchSaveBySingle(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values,
                                             int fromIndex, int toIndex) {
        List<T> list = values.subList(fromIndex, toIndex);
        list.forEach(item -> {
            mapper.batchSaveSingle(item);
        });
        sqlSession.flushStatements();
        return list.size();
    }

    /**
     * 执行insert并批量提交
     *
     * @param sqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @return
     */
    private static <T> int batchDeleteBySingle(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values,
                                               int fromIndex, int toIndex) {
        List<T> list = values.subList(fromIndex, toIndex);
        list.forEach(item -> {
            mapper.batchDeleteSingle(item);
        });
        sqlSession.flushStatements();
        return list.size();
    }

    /**
     * 执行insert并批量提交
     *
     * @param sqlSession {@link SqlSession}
     * @param mapper     {@link BatchMapper}
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @return
     */
    private static <T> int batchUpdateBySingle(SqlSession sqlSession, BatchMapper<T> mapper, List<T> values,
                                               int fromIndex, int toIndex) {
        List<T> list = values.subList(fromIndex, toIndex);
        list.forEach(item -> {
            mapper.batchUpdateSingle(item);
        });
        sqlSession.flushStatements();
        return list.size();
    }

    /**
     * 支技Mapper中的list
     *
     * @param sqlSession {@link SqlSession}
     * @param statement  Mapper文件完整命名空间，如：<mapper namespace=
     *                   "com.ytd.mds.dao.mapper.SmsTempTestMapper">
     * @param values     数据集合{@link List}
     * @param fromIndex  开始索引
     * @param toIndex    结束索引
     * @return
     */
    private static <T> int flushList(SqlSession sqlSession, String statement, List<T> values, int fromIndex,
                                     int toIndex) {
        int result = sqlSession.insert(statement, values.subList(fromIndex, toIndex));
        sqlSession.flushStatements();
        return result;
    }

    /**
     * 支持Mapper单个操作
     *
     * @param sqlSession
     * @param statement
     * @param values
     * @param fromIndex
     * @param toIndex
     * @return
     */
    private static <T> int flushSingle(SqlSession sqlSession, String statement, List<T> values, int fromIndex,
                                       int toIndex) {
        List<T> items = values.subList(fromIndex, toIndex);
        items.forEach(obj -> {
            sqlSession.insert(statement, obj);
        });
        sqlSession.flushStatements();
        return items.size();
    }

    /**
     * 执行批处理，并刷新数据
     *
     * @param sqlSession                  {@link SqlSession}
     * @param mapper                      Mybatis的Mapper对象 {@link Mapper}
     * @param mybatisBatchFlushStatements {@link MybatisForEachBatchFlushStatements}
     * @param values                      数据集合{@link List}
     * @param fromIndex                   开始索引
     * @param toIndex                     结束索引
     * @return
     */
    public static <T> int batchForEach(SqlSession sqlSession, T mapper,
                                       MybatisForEachBatchFlushStatements<T> mybatisBatchFlushStatements, List<?> values, int fromIndex,
                                       int toIndex) {

        List<?> items = values.subList(fromIndex, toIndex);
        items.forEach(item -> {
            mybatisBatchFlushStatements.batch(mapper, item);
        });
        sqlSession.flushStatements();
        return items.size();
    }

    /**
     * 执行批处理，并刷新数据
     *
     * @param sqlSession                  {@link SqlSession}
     * @param mapper                      Mybatis的Mapper对象 {@link Mapper}
     * @param mybatisBatchFlushStatements {@link MybatisBatchFlushStatements}
     * @param values                      数据集合{@link List}
     * @param fromIndex                   开始索引
     * @param toIndex                     结束索引
     * @return
     */
    public static <T> int batch(SqlSession sqlSession, T mapper,
                                MybatisBatchFlushStatements<T> mybatisBatchFlushStatements, List<?> values, int fromIndex, int toIndex) {
        List<?> items = values.subList(fromIndex, toIndex);
        mybatisBatchFlushStatements.batch(mapper, items);
        sqlSession.flushStatements();
        return items.size();
    }
}
