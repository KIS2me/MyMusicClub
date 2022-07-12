import Axios from "axios";
import {get,post} from "./http";

//============歌手相关================
//查询歌手
export const getAllSinger =() => get(`singer/selectAllSinger`);
//根据性别查询歌手
export const getSingerOfSex = (sex) => get(`singer/selectBySex?sex=${sex}`);


//============歌曲相关================
//根据歌手id查询歌曲
export const songOfSingerId =(id) => get(`song/singer/detail?singerId=${id}`);
//根据歌曲id查询歌曲对象
export const songOfSongId =(id) => get(`song/selectById?songId=${id}`);
//根据歌曲名模糊查询歌曲对象
export const likeSongOfName =(keywords) => get(`song/selectLikeName?songName=${keywords}`);


//============歌单相关================
//查询歌单
export const getAllSongList =() => get(`songList/selectAllSongList`);
//返回根据标题模糊查询得到的歌单列表
export const getSongListOfLikeTitle = (keywords) => get(`songList/selectLikeTitle=${keywords}`);
//根据风格模糊查询歌单列表
export const getSongListOfLikeStyle = (style) => get(`songList/selectLikeStyle?style=${style}`);


//============歌单的歌曲相关============
//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`listSong/selectBySongListId?songListId=${songListId}`);


//============用户相关================
//查询用户
export const getAllConsumer =() => get(`consumer/selectAllConsumer`);
//注册
export const SignUp =(params) => post(`/consumer/add`,params);
//登录
export const loginIn =(params) => post(`/consumer/login`,params);
//根据用户id查询该用户的详细信息
export const getUserOfId =(id) => get(`/consumer/selectById?id=${id}`);
//更新用户信息
export const updateUserMsg =(params) => post(`/consumer/update`,params);


//下载音乐
export const download = (url)  => Axios({
  method: 'get',
  url: url,
  responseType: 'blob'
});


//============评分============
//提交评分
export const setRank =(params) => post(`/rank/add`,params);
//获取指定歌单的平均分
export const getRankOfSongListId = (songListId) => get(`/rank/getAverageRate?songListId=${songListId}`);


//===========评论======================
//提交评论
export const setComment =(params) => post(`/comment/add`,params);
//点赞
export const setLike =(params) => post(`/comment/like`,params);
//返回当前歌单或歌曲的评论列表
export const getAllComment = (type,id) => {
    if(type == 0){              //歌曲
        return get(`/comment/commentOfSongId?songId=${id}`);
    }else{                      //歌单
        return get(`/comment/commentOfSongListId?songListId=${id}`);
    }
}


//===============收藏===================
//新增收藏
export const setCollect =(params) => post(`/collect/add`,params);
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/selectByUserId?userId=${userId}`);
