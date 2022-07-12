package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Song;
import music.service.SongService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 歌曲控制类
 */
@RestController
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;

    /**
     * 添加歌曲
     *
     * multipartFile为上传的mp3文件
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file") MultipartFile songFile) {
        JSONObject jsonObject = new JSONObject();

        //获取前端传来的参数
        String singerId = request.getParameter("singerId").trim(); //删除字符串的头尾空白符
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String pic = "/img/songPic/tubiao.jpg"; //默认歌曲图片
        String lyric = request.getParameter("lyric").trim();

        //上传歌曲
        //若MultipartFile对象不存在，说明没有文件，更新失败
        if(songFile.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "歌曲上传失败");
            return jsonObject;
        }

        //文件名=当前时间毫秒值+原文件名，避免相同文件名重名
        String fileName = System.currentTimeMillis() + songFile.getOriginalFilename();
        //被存储文件的父文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        //若父文件路径不存在，则新建文件夹
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdir();
        }
        //实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") +  fileName);
        //存储到数据库中的相对文件地址
        String storeSongPath = "/song/" + fileName;
        try {
            songFile.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setLyric(lyric);
            song.setPic(pic);
            song.setUrl(storeSongPath);

            //添加歌曲
            boolean flag = songService.insert(song);
            if(flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "添加成功");
                jsonObject.put("song", storeSongPath);
            }else {
                jsonObject.put(Constants.CODE, 0);
                jsonObject.put(Constants.MSG, "添加失败");
            }
        } catch(IOException e) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "歌曲上传错误" + e.getMessage());
        }

        return jsonObject;
    }

    /**
     * 更新歌曲
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String lyric = request.getParameter("lyric").trim();

        Song song = new Song();
        song.setId(Integer.parseInt(id));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);

        //修改歌曲
        boolean flag = songService.update(song);
        if(flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "更新成功");
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败");
        }

        return jsonObject;
    }

    /**
     * 删除歌曲
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        //删除歌曲
        boolean flag = songService.delete(Integer.parseInt(id));

        //前端不取json格式数据
        return flag;
    }

    /**
     * 根据歌曲id查询歌曲
     */
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public Object selectById(HttpServletRequest request) {
        String id = request.getParameter("id");
        return songService.selectById(Integer.parseInt(id));
    }

    /**
     * 根据歌曲名模糊查询歌曲
     */
    @RequestMapping(value = "/selectLikeName", method = RequestMethod.GET)
    public Object selectByName(HttpServletRequest request) {
        String name = request.getParameter("name");
        return songService.selectLikeName(name);
    }

    /**
     * 根据歌手id查询歌曲
     */
    @RequestMapping(value = "/singer/detail", method = RequestMethod.GET)
    public Object getSongBySingerId(HttpServletRequest request) {
        String singerId = request.getParameter("singerId");
        return songService.selectBySingerId(Integer.parseInt(singerId));
    }

    /**
     * 查询所有歌曲
     */
    @RequestMapping(value = "/selectAllSong", method = RequestMethod.GET)
    public Object selectAllSong() {
        return songService.selectAllSong();
    }

    /**
     * 更新歌曲图片
     *
     * MultipartFile是SpringMVC提供简化上传操作的工具类，SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息
     */
    @RequestMapping(value = "/updateSongPic", method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();

        //若MultipartFile对象不存在，说明没有文件，更新失败
        if(avatorFile.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "文件上传失败");
            return jsonObject;
        }

        //文件名=当前时间毫秒值+原文件名，避免相同文件名重名
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //被存储文件的父文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "img" + System.getProperty("file.separator") + "songPic";
        //若父文件路径不存在，则新建文件夹
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdir();
        }
        //实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") +  fileName);
        //存储数据库中的相对文件地址
        String storeAvatorPath = "/img/songPic/" + fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            //更新头像
            boolean flag = songService.update(song);

            //更新成功
            if(flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "更新成功");
                //传回存储的地址
                jsonObject.put("pic", storeAvatorPath);
            }else {
                jsonObject.put(Constants.CODE, 0);
                jsonObject.put(Constants.MSG, "更新失败");
            }
        } catch(IOException e) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "文件上传错误" + e.getMessage());
        }

        return jsonObject;
    }

    /**
     * 更新歌曲文件
     *
     * MultipartFile是SpringMVC提供简化上传操作的工具类，SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息
     */
    @RequestMapping(value = "/updateSongUrl", method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();

        //若MultipartFile对象不存在，说明没有文件，更新失败
        if(avatorFile.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "文件上传失败");
            return jsonObject;
        }

        //文件名=当前时间毫秒值+原文件名，避免相同文件名重名
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //被存储文件的父文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        //若父文件路径不存在，则新建文件夹
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdir();
        }
        //实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") +  fileName);
        //存储数据库中的相对文件地址
        String storeAvatorPath = "/song/" + fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeAvatorPath);
            //更新头像
            boolean flag = songService.update(song);

            //更新成功
            if(flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "更新成功");
                //传回存储的地址
                jsonObject.put("url", storeAvatorPath);
            }else {
                jsonObject.put(Constants.CODE, 0);
                jsonObject.put(Constants.MSG, "更新失败");
            }
        } catch(IOException e) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "文件上传错误" + e.getMessage());
        }

        return jsonObject;
    }
}
