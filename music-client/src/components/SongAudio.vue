<template>
    <div class="song-audio">
        <audio ref="player"
            :src="url"
            controls = "controls"
            preload = "true"
            @canplay="startPlay"
            @ended="ended"
            @timeupdate="timeupdate"
        ></audio>
    </div>
</template>
<script>
import {mapGetters} from 'vuex';
export default {
    name: 'song-audio',
    computed: {
        ...mapGetters([
            'id',               //歌曲id
            'url',              //歌曲地址
            'isPlay',           //播放状态
            'listOfSongs',     //当前歌曲列表
            'curTime',         //当前音乐的播放位置
            'changeTime',      //指定播放时刻
            'autoNext',        //用于自动触发播放下一首
            'volume'           //音量
        ])
    },
    watch:{
        //监听播放还是暂停
        isPlay(){
            this.togglePlay();
        },
        //跳转到指定播放时刻
        changeTime(){
            this.$refs.player.currentTime = this.changeTime;
        },
        //改变音量
        volume(val){
            this.$refs.player.volume = val;
        }
    },
    methods:{
        //获取链接后准备播放
        startPlay(){
            let player = this.$refs.player;
            this.$store.commit('setDuration',player.duration);
            //开始播放
            player.play();
            this.$store.commit('setIsPlay',true);
        },
        //播放完成之后触发
        ended(){
           this.$store.commit('setIsPlay',false);
           this.$store.commit('setCurTime',0);
           this.$store.commit('setAutoNext', !this.autoNext);
        },
        //开始、暂停
        togglePlay() {
            let player = this.$refs.player;
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
        },
        //音乐播放时记录音乐的播放位置
        timeupdate(){
            this.$store.commit('setCurTime',this.$refs.player.currentTime);
        },
        //解析歌词
        parseLyric(text){
            let lines = text.split("\n");                   //将歌词按行分解成数组
            let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g; //时间格式的正则表达式
            let result = [];                                //返回值
            //对于歌词格式不对的直接返回
            if(!(/\[.+\]/.test(text))){
                return [[0,text]]
            }
            //去掉前面格式不正确的行
            while(!pattern.test(lines[0])){
                lines = lines.slice(1);
            }
            //遍历每一行，形成一个每行带着俩元素的数组，第一个元素是以秒为计算单位的时间，第二个元素是歌词
            for(let item of lines){
                let time = item.match(pattern);  //存前面的时间段
                let value = item.replace(pattern,'');//存后面的歌词
                for(let item1 of time){
                    let t = item1.slice(1,-1).split(":");   //取出时间，换算成数组
                    if(value!=''){
                        result.push([parseInt(t[0],10)*60 + parseFloat(t[1]),value]);
                    }
                }
            }
            //按照第一个元素--时间--排序
            result.sort(function(a,b){
                return a[0] - b[0];
            });
            return result;
        },
        //转向歌词页面
        toLyric(){
            this.$router.push({path: `/lyric`});
        },
        //下载音乐
        download() {
            download(this.url)
                .then(res=>{
                    let content = res.data;
                    let eleLink = document.createElement('a');
                    eleLink.download = `${this.artist}-${this.title}.mp3`;
                    eleLink.style.display = 'none';
                    //把字符内容转换成blob地址
                    let blob = new Blob([content]);
                    eleLink.href = URL.createObjectURL(blob);
                    //把链接地址加到document里
                    document.body.appendChild(eleLink);
                    //触发点击
                    eleLink.click();
                    //然后移除掉这个新加的控件
                    document.body.removeChild(eleLink);
                })
                .catch(err =>{
                    console.log(err);
                })
        }
    }
}
</script>

<style>
    .song-audio {
        display: none;
    }
</style>
