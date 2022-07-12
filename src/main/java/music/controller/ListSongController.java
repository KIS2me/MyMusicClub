package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.ListSong;
import music.service.ListSongService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * 歌单的歌曲管理controller
 */
@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongService listSongService;

    /**
     * 给歌单添加歌曲
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addListSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();

        //获取前端传来的参数
        String songId = request.getParameter("songId");  //歌曲id
        String songListId = request.getParameter("songListId"); //歌单id

        ListSong listSong = new ListSong();
        listSong.setSongId(Integer.parseInt(songId));
        listSong.setSongListId(Integer.parseInt(songListId));

        boolean flag = listSongService.insert(listSong);
        if(flag){
            jsonObject.put(Constants.CODE,1);
            jsonObject.put(Constants.MSG,"保存成功");
        }else {
            jsonObject.put(Constants.CODE,0);
            jsonObject.put(Constants.MSG,"保存失败");
        }

        return jsonObject;

    }

    /**
     * 根据歌曲id和歌单id删除歌单内的歌曲
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(HttpServletRequest request){
        String songId = request.getParameter("songId");                 //歌曲id
        String songListId = request.getParameter("songListId");        //歌单id
        boolean flag = listSongService.deleteBySongIdAndSongListId(Integer.parseInt(songId), Integer.parseInt(songListId));
        return flag;
    }

    /**
     * 根据歌单id查询歌曲
     */
    @RequestMapping(value = "/selectBySongListId",method = RequestMethod.GET)
    public Object selectBySongListId(HttpServletRequest request) {
        String songListId = request.getParameter("songListId");
        return listSongService.selectBySongListId(Integer.parseInt(songListId));
    }
}




















