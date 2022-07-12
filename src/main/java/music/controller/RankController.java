package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Rank;
import music.service.RankService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * 歌单评分控制类
 */
@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    private RankService rankService;

    /**
     * 添加评分
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addRank(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        //获取前端传来的参数
        String songListId = request.getParameter("songListId"); //删除字符串的头尾空白符
        String consumerId = request.getParameter("consumerId");
        String score = request.getParameter("score").trim();

        Rank rank = new Rank();
        rank.setSongListId(Integer.parseInt(songListId));
        rank.setConsumerId(Integer.parseInt(consumerId));
        rank.setScore(Integer.parseInt(score));

        boolean flag = rankService.insert(rank);

        if(flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "评价成功");
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "评价失败");
        }
        return jsonObject;
    }

    /**
     * 返回平均分
     */
    @RequestMapping(value = "/getAverageRate", method = RequestMethod.GET)
    public Object getAverageRate(HttpServletRequest request) {
        String songListId = request.getParameter("songListId").trim();
        return rankService.getAverageRate(Integer.parseInt(songListId));
    }
}
