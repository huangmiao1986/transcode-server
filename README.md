# transcode-server
#视频转码

##环境安装：
第一步 官网下载 http://ffmpeg.org/  </br>
第二步 1.下载x264 2. 安装h264高清无损编码 ./configure --disable-asm --enable-shared</br>
make </br>
make install</br>
第三步 解压ffmpeg并安装</br>
./configure --enable-gpl --enable-libx264 --enable-pthreads --enable-shared --enable-nonfree --enable-encoder=libx264 --extra-cflags='-Wall -g ' --prefix=/usr --disable-yasm </br>
make </br>
make install </br>
第四步 部署transcode-server项目</br>
