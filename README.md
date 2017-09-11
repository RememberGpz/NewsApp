# NewsApp
基于MD设计风格和MVP模式的一款简易新闻客户端

***
## 此项目主要技术
* Glide图片加载框架加载图片
* okhttp网络请求
* 遵循Material Design设计原则
* Gson解析网络请求返回的数据
* 利用shareSDK实现第三方登录、分享功能
* Bomb后端云

***
## 已做效果




*登录界面如下图所示：*

![登录](https://github.com/RememberGpz/NewsApp/blob/master/shootscreen/login.gif)  


*注册界面*

![注册](https://github.com/RememberGpz/NewsApp/blob/master/shootscreen/register.gif)  

*分享界面*

![分享](https://github.com/RememberGpz/NewsApp/blob/master/shootscreen/share.gif)  

*验证码登录*

![验证码登录](https://github.com/RememberGpz/NewsApp/blob/master/shootscreen/codeLogin.gif)  

*协议*

![协议](https://github.com/RememberGpz/NewsApp/blob/master/shootscreen/agreement.gif)  

</br></br></br>

***
## biuld.gradle文件依赖</br> </br>

	compile 'uk.co.chrisjenx:calligraphy:2.3.0'
    compile 'com.android.support:appcompat-v7:26.+'
    compile 'com.android.support:design:26.+'
    compile 'com.android.support:cardview-v7:26.0.+'

    compile 'com.github.bumptech.glide:glide:3.5.2'     //glide图片加载
    compile 'org.litepal.android:core:1.3.2'            //litepal数据框架
    compile 'com.google.code.gson:gson:2.7'             //gson数据
    compile 'com.squareup.okhttp:okhttp:2.7.0'          //okhttp网络请求
    compile 'com.rengwuxian.materialedittext:library:2.1.4'         //material design风格的输入框,TextInputEditText加强版

    compile 'de.hdodenhof:circleimageview:2.1.0'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    testCompile 'junit:junit:4.12'
    //bmob的sdk
    compile 'cn.bmob.android:bmob-sdk:3.5.0'
    //如果你想应用能够兼容Android6.0，请添加此依赖(org.apache.http.legacy.jar)
    compile 'cn.bmob.android:http-legacy:1.0'
    compile files('libs/ShareSDK-Alipay-3.0.2.jar')