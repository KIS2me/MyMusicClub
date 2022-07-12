package music.service;

import music.domain.Rank;

/**
 * 歌单评分service接口
 */
public interface RankService {
    /**
     * 增加
     */
    boolean insert(Rank rank);

    /**
     * 查询某个歌单的评分总和
     */
    int selectScoreSum(Integer songListId);

    /**
     * 查询某个歌单的评价总人数
     */
    int selectRankNum(Integer songListId);

    /**
     * 获取歌单平均评分
     */
    int getAverageRate(Integer songListId);
}
