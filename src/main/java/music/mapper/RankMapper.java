package music.mapper;

import music.domain.Rank;
import org.springframework.stereotype.Repository;

/**
 * 歌单评分dao
 */
@Repository
public interface RankMapper {
    /**
     * 增加
     */
    int insert(Rank rank);

    /**
     * 查询某个歌单的评分总和
     */
    int selectScoreSum(Integer songListId);

    /**
     * 查询某个歌单的评价总人数
     */
    int selectRankNum(Integer songListId);
}
