package music.service.impl;

import music.domain.Rank;
import music.mapper.RankMapper;
import music.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 歌单评分service实现类
 */
@Service
public class RankServiceImpl implements RankService {
    @Autowired
    private RankMapper rankMapper;

    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank) > 0;
    }

    @Override
    public int selectScoreSum(Integer songListId) {
        return rankMapper.selectScoreSum(songListId);
    }

    @Override
    public int selectRankNum(Integer songListId) {
        return rankMapper.selectRankNum(songListId);
    }

    @Override
    public int getAverageRate(Integer songListId) {
        int rankNum = rankMapper.selectRankNum(songListId);
        if(rankNum == 0) {
            return 5;
        }

        int scoreSum = rankMapper.selectScoreSum(songListId);
        return scoreSum / rankNum;
    }
}
