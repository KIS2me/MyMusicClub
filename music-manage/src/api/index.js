import {get,post} from "./http";

//判断管理员是否登录成功
export const login =(params) => post(`admin/login`,params);
export const logout =() => post(`admin/logout`);


//============歌手相关================
//添加歌手
export const setSinger =(params) => post(`singer/add`,params);
//编辑歌手
export const updateSinger =(params) => post(`singer/update`,params);
//删除歌手
export const delSinger =(id) => get(`singer/delete?id=${id}`);
//查询所有歌手
export const getAllSinger =() => get(`singer/selectAllSinger`);
//分页查询歌手
export const getSingersForPage = (currentPage) => get(`singer/selectSingerForPage?currentPage=${currentPage}`);
//根据歌手名字模糊查询歌手
export const getSingerLikeName = (name) => get(`singer/selectLikeName?name=${name}`);


//============歌曲相关================
//编辑歌曲
export const updateSong =(params) => post(`song/update`,params);
//删除歌曲
export const delSong =(id) => get(`song/delete?id=${id}`);
//根据歌手id查询歌曲
export const getSongBySingerId =(singerId) => get(`song/singer/detail?singerId=${singerId}`);
//根据歌曲id获取歌曲对象
export const songOfSongId =(id) => get(`song/selectById?songName=${id}`);
//根据歌曲名获取歌曲对象
export const songOfSongName =(songName) => get(`song/selectLikeName?songName=${songName}`);
//查询所有歌曲
export const allSong =() => get(`song/selectAllSong`);


//============歌单相关================
//添加歌单
export const setSongList =(params) => post(`songList/add`,params);
//编辑歌单
export const updateSongList =(params) => post(`songList/update`,params);
//删除歌单
export const delSongList =(id) => get(`songList/delete?id=${id}`);
//查询歌单
export const getAllSongList =() => get(`songList/selectAllSongList`);


//============歌单的歌曲相关============
//给歌单增加歌曲
export const listSongAdd =(params) => post(`listSong/add`,params);
//根据歌单id查询歌曲列表
export const listSongDetail =(songListId) => get(`listSong/selectBySongListId?songListId=${songListId}`);
//删除歌单的歌曲
export const delListSong =(songId,songListId) => get(`listSong/delete?songId=${songId}&songListId=${songListId}`);


//============用户相关================
//添加用户
export const setConsumer =(params) => post(`consumer/add`,params);
//编辑用户
export const updateConsumer =(params) => post(`consumer/update`,params);
//删除用户
export const delConsumer =(id) => get(`consumer/delete?id=${id}`);
//查询所有用户
export const getAllConsumer =() => get(`consumer/selectAllConsumer`);
//根据用户id查询该用户的详细信息
export const getUserOfId =(id) => get(`/consumer/selectById?id=${id}`);


//===============收藏===================
//指定用户的收藏列表
export const getCollectOfUserId =(userId) => get(`/collect/selectByUserId?userId=${userId}`);
//删除用户收藏的歌曲
export const deleteCollection =(userId,songId) => get(`collect/deleteByUserIdAndSongId?userId=${userId}&songId=${songId}`);


//===============评论===================
//指定歌单的评论列表
export const getCommentOfSongListId =(songListId) => get(`/comment/selectBySongListId?songListId=${songListId}`);
//删除评论
export const deleteComment =(id) => get(`comment/delete?id=${id}`);
