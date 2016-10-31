// ------------- ------------- ------------- -------------显示操作 ------------- ------------- ------------- -------------

var zmen = document.getElementById("zmen");
var zmenIn = document.getElementById("zmenIn");
var men = document.getElementById("men");
var menIn = document.getElementById("menIn");
var smen = document.getElementById("smen");
var smenIn = document.getElementById("smenIn");
var addres = document.getElementById("addres");
var my_jp = document.getElementById("my_jp");

//忘记密码显示
function  funMent() {
	if (zmen.style.display == "none") {
	  zmen.style.display = "block";
	  zmenIn.style.display = "block";
	  men.style.display="none";
	  smen.style.display="none";
	  menIn.style.display="none";
	} else {
	  zmen.style.display = "block";
	  zmenIn.style.display = "block";
	  men.style.display="none";
	  smen.style.display="none";
	  menIn.style.display="none";
    }
}
//登陆显示
var yzm ;
function   funCent() {
	var zsm = document.getElementById("zsm");
	yzm = random(true,4,4); //随机验证码
	zsm.innerHTML = yzm;
	//console.log(yzm);
    if (men.style.display == "none") {
	    men.style.display = "block";
	    menIn.style.display = "block";
	    zmen.style.display="none";
    } else {
	    men.style.display = "block";
	    menIn.style.display = "block";
	    zmen.style.display="none";
    }
}

//关闭按钮
function men_close(){
  	men.style.display = "none";
	menIn.style.display = "none";
	zmen.style.display="none";
	zmenIn.style.display="none";
	smen.style.display = "none";
	smenIn.style.display = "none";
	addres.style.display = "none";
	my_jp.style.display = "none";
}
//注册显示
function   funSent() {
  if (smen.style.display == "none") {
    smen.style.display = "block";
    smenIn.style.display = "block";
    men.style.display="none";
  } else {
    smen.style.display = "block";
    smenIn.style.display = "block";
    men.style.display="none";
  }
}
//  ------------- ------------- ------------- -------------注册 ------------- ------------- ------------- -------------
var zc_user = document.getElementById("input1"); 
var zc_mima = document.getElementById("mima"); 
var zc_mima_que = document.getElementById("mima2");  
var te1 = document.getElementById("te1");
var te2 = document.getElementById("te2");
var te3 = document.getElementById("te3");
var btn3 = document.getElementById("btn3");
var zc_btn = document.getElementById("zc_btn");
var bg = document.getElementById("bg");
var toux = document.getElementById("toux");

function ZCuser(){ 
	var name = zc_user.value;
	var parrent = /^1[34578]\d{9}$/;
	
	if(parrent.test(name)){
		te1.innerHTML = "手机号码正确";  
		ZCyzm();
	} else{
		te1.innerHTML = "请输正确的手机号!";
		this.value = ""; 
	} 
	return name;
}

zc_mima.onblur = function ZCmima(){ 
	var mima = zc_mima.value;
	var parrent = /^[a-zA-Z\d+]{6,8}$/;
	if( mima == ""){
		te2.innerHTML = "密码不能为空!";
	}else{
		if(parrent.test(mima)){
			te2.innerHTML = "密码正确"; 
			ZCmimaQR();
		} else{
			te2.innerHTML = "请输正确的密码!";
			this.value = ""; 
		} 
	}
	return mima;
}

zc_mima_que.onblur =  function(){ ZCmimaQR() };
function ZCmimaQR(){  
	var flag;
	var mima1 = zc_mima.value;//前
	var mima_qr = zc_mima_que.value;  //后 
	if( mima_qr == ""){
		te3.innerHTML = "确认密码不能为空!";
	}else{
		if (mima1 == mima_qr) {
		  te3.innerHTML = "密码一样";
		  return flag = 1;
		} else {
		  te3.innerHTML = "密码不一样，请重新输入";
		  this.value = "";
		}
	}
	return flag;
}
//注册验证码发送
btn3.onclick = function(){ 
	var zc_user = ZCuser();
	window.AndroidWebView.webViewYZM( zc_user ); 
};
//发送注册验证码
function ZCyzm(){
	var sleep = 5;   //测试设置5s
	var interval = null;
	if (!interval){ 
	    this.disabled = "disabled";
	    this.value = "重新发送 (" + sleep-- + ")";
	    btn3.style.backgroundColor = '#666';
	    interval = setInterval (function () { 
		    if (sleep == 0) {
		        if (!!interval) {
		          clearInterval (interval);
		          interval = null;
		          sleep = 5;
		          btn3.style.cursor = "pointer";
		          btn3.removeAttribute ('disabled');
		          btn3.value = "免费获取验证码";
		          btn3.style.backgroundColor = '#c8264d'; 
		        }
		        return false;
		    }
		    btn3.value = "重新发送 (" + sleep-- + ")";
		}, 1000);
	}
}

//注册成功后弹出奖品
zc_btn.onclick = function(){ 
	var zc_user = ZCuser(); //注册用户名
	var zc_mima = ZCmimaQR(); //注册密码
	var zc_yzm = document.getElementById("textN1").value; //输入的验证码
	//console.log(zc_user) 
	window.AndroidWebView.webViewResiger( zc_user , zc_mima , yzm ); 
}

//注册成功后  
function zc_succeed(){
	men_close(); 
	var append_jp = document.getElementById("append_jp");
	append_jp.innerHTML = '<img src="images/zc_tk.png" />';  // 奖品随机  
	bg.style.display = "block";
	toux.style.display = "block";
} 
function tou_box(){ //点击背景隐藏 
	bg.style.display = "none";
	toux.style.display = "none";
}
//------------- -------------------------- -------------登录 ------------- -------------------------- -------------

var dengBtn = document.getElementById("dengBtn"); 
function random(Flag, min, max){ //随机验证码
    var str = "", range = min,
        arr = [ '1', '2', '3', '4','5', '6', '7', '8', '9', 
        		'a', 'b', 'c', 'd', 'e','f', 'g', 'h', 'i', 'j',
        		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
        		'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
        		'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
        		'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']; 
    if(Flag){  range = Math.round(Math.random() * (max-min)) + min; };  // 随机产生
    for(var i=0; i<range; i++){
        pos = Math.round(Math.random() * (arr.length-1));
        str += arr[pos];
    }
    return str;
}
//判断是否登录
function webViewWeatherLogon(userInfo){
//     alert(userInfo);
	return userInfo;
}
var userInfo = webViewWeatherLogon(userInfo);
//发送
function deng(){ //登录按钮
	if(userInfo != ""){
//	      alert(userInfo);
//			return userId;
	}else{
		denglu();
	}
}
function denglu(){ //登录方法
	var dengl_user = document.getElementById("input").value;
	var dengl_pwd = document.getElementById("dengl_pwd").value;
	var deng_yzm = document.getElementById("textN").value;  
	var d_user = dengl_user.value;
	var d_pwd = dengl_pwd.value;
	var d_yzm = deng_yzm.value;
	
	console.log("随机验证码"+yzm);
	console.log("输入验证码"+d_yzm);
	var parrentUser = /^1[34578]\d{9}$/;
	var parrentPwd = /^[a-zA-Z\d+]{6,18}$/;
	
	if( parrentUser.test(dengl_user) && parrentPwd.test(dengl_pwd) ){
		 //调用android程序中的方法，并传递参数  
		window.AndroidWebView.webViewLogin( dengl_user , dengl_pwd );
	} else{
		alert("输入有误，请重新输入！！")0
		 
	}   
}
//android传递信息给js
function showInfoFromJava(msg){ 
//  	alert("来自客户端的信息："+msg);
	alert("收到的信息"+msg)
	if(msg == "true"){
		alert("登录成功！！");
	} 
}
	
//------------- ----------------- ----------------- 找回密码 ----------------- ----------------- -----------------
// webViewForgetPassword(String phone,String password,String yzm) 
var resetPhone = document.getElementById("input2");
var resetPwd_q = document.getElementById("mima12");
var resetPwdH = document.getElementById("mima22");
var resetYzm = document.getElementById("textN12");
var hqYzm = document.getElementById("btn2");
var te32 = document.getElementById("te32");
var te22 = document.getElementById("te22");

function resetUSer (){ 
	var name = resetPhone.value;
	var parrent = /^1[34578]\d{9}$/;
	
	if(parrent.test(name)){
		te1.innerHTML = "手机号码正确";   
	} else{
		te1.innerHTML = "请输正确的手机号!";
		this.value = ""; 
	} 
	return name;
}
//修改密码验证码发送
hqYzm.onclick = function(){ 
	var reset_user = resetUSer();
	window.AndroidWebView.webViewYZM( reset_user ); 
};

function resetPwd(){  //提交
	var mima_q = resetPwd_q.value;
	var mima_h = resetPwdH.value;
	var parrentPwd = /^[a-zA-Z\d+]{6,18}$/;
	if( parrentPwd.test(mima_q) && parrentPwd.test(mima_h) ){ 
		window.AndroidWebView.webViewForgetPassword( resetPhone, resetPwd, resetYzm ) 
	}else{
		//alert("手机或者密码输入不正确...")
		te22.innerHTML = "密码格式有误";
		te32.innerHTML = "密码格式有误";
	}
	
} 
//
var sleep = 60, interval = null;
window.onload = function ()
{
  var btn = document.getElementById ('btn2'); 
  btn.onclick = function () {
  	var phoneYZ = resetPhone.value; 
  	var parrentPhone = /^1[34578]\d{9}$/;
  	if( parrentPhone.test(phoneYZ) ){
  		if (!interval) {
	      this.disabled = "disabled";
	      //this.style.cursor = "wait";
	      btn.style.backgroundColor = '#666';
	      this.value = "重新发送 (" + sleep-- + ")";
	      interval = setInterval (function ()
	      {
	        if (sleep == 0)
	        {
	          if (!!interval)
	          {
	            clearInterval (interval);
	            interval = null;
	            sleep = 60;
	            btn.style.cursor = "pointer";
	            btn.removeAttribute ('disabled');
	            btn.value = "免费获取验证码";
	            btn.style.backgroundColor = '#c8264d';
	          }
	          return false;
	        }
	        btn.value = "重新发送 (" + sleep-- + ")";
	      }, 1000);
	    }
  	}else{
  		alert("手机号码输入有误！")
  	} 
  }
};
