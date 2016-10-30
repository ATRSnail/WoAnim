/**
 * Created by zengqiangqiang on 2016/10/16.
 */
(function(win) {
    var doc = win.document;
    var docEl = doc.documentElement;
    var tid;
    var txt=document.getElementById("txt");
    function refreshRem() {
        var width = docEl.getBoundingClientRect().width;
        //console.log(width);
        if (width >640) { // �����
            txt.style.width="50%";
        }else{
            txt.style.width="90%";
        }
    }
    refreshRem();

})(window);
