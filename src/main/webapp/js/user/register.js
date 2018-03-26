/**
 * Created by isghost on 2017/8/5.
 */
// 判断是否有重复的名字 -1 未检查 0 无重复 1 重复
var hasRepeatUsername = -1;
// 延迟执行函数
var delayFunc = null;
// ajax请求
var ajaxQueryUsername = null;
// 很长时间不用，起手就一个Deprecated，日了狗，替代方案在https://api.jquery.com/ready/
$(function () {
    setSubmitBtn();
    hiddenAllUsernameTip();
    checkRepeat($("#username").val());
    //---------------------------   用户    -----------------------------------------
    $("#username").focus(function () {
        $("#username_tip").css("display", "none");
    });
    $("#username").blur(function () {

        setSubmitBtn();
        if (!checkUsername()) {
            $("#username_tip").css("display", "inline-block");
            hasRepeatUsername = -1;
            return;
        }
    });
    //---------------------------   密码    -----------------------------------------
    $("#password").focus(function () {
        $("#password_tip").css("display", "none");
        return;
    })

    $("#password").blur(function () {
        setSubmitBtn();
        if (!checkPassword()) {
            $("#password_tip").css("display", "inline-block");

            return;
        }

        if (checkRePassword()) {
            $("#re_password_tip").css("display", "none");
        }
        else {
            $("#re_password_tip").css("display", "inline-block");
        }
    });
//---------------------------   重复密码    -----------------------------------------
    $("#rePassword").focus(function () {
        $("#re_password_tip").css("display", "none");
        return;
    })

    $("#rePassword").blur(function () {
        setSubmitBtn();
        if (!checkRePassword()) {
            $("#re_password_tip").css("display", "inline-block");
            return;
        }
    });
    //------------------------- 条款 -------------------------------
    $("#terms").change(function () {
        setSubmitBtn();
    });
    //------------------     文本变化时，都要判断按钮是否可以点击  ----------------
    var IDs = ["username", "password", "rePassword"];
    for (var k in IDs) {
        $("#" + IDs[k]).bind("input propertychange", function () {
            if ($(this).attr("id") == "username") {
                hiddenAllUsernameTip();
                checkRepeat($(this).val());
            }
            ;
            setSubmitBtn();
        });
    }
});

/**
 * 检查用户名
 */
function checkUsername() {
    var username = $("#username").val();
    // 正则里面的{}居然不能带空格
    return !!username.match(/^[\w\d]{6,18}$/);
}

/**
 * 检查密码
 */
function checkPassword() {
    var password = $("#password").val();
    return !(password.length < 6 || password.length > 16);
}

/**
 * 检查密码是否一致
 */
function checkRePassword() {
    var password = $("#password").val();
    var rePassword = $("#rePassword").val();
    // 当长度为零时，不提示密码不致，因为用户还未进行输入
    return !(password !== rePassword && rePassword.length > 0);
}

/**
 * 用户条款
 * 新版jquery，对于无属性值的属性，要使用prop
 */
function checkTerm() {
    var checkBox = $("#terms")
    console.log(checkBox.prop("checked"));
    return checkBox.prop("checked");
}

/**
 * 发起检查重名请求
 */
function checkRepeat(username) {
    console.log("begin ");
    hasRepeatUsername = -1; // 就会变成未检查
    if (delayFunc) {
        console.log("delayFunc break");
        clearTimeout(delayFunc);
        delayFunc = null;
    }
    if (!checkUsername()) {
        if (ajaxQueryUsername) {
            console.log("ajaxQueryUsername11111 break");
            ajaxQueryUsername.abort();
        }
        return;
    }
    delayFunc = setTimeout(function () {
        delayFunc = null;
        console.log("second = ");
        if (ajaxQueryUsername) {
            console.log("ajaxQueryUsername break");
            ajaxQueryUsername.abort();
        }
        hasRepeatUsername = -1;
        ajaxQueryUsername = $.ajax({
            type: "POST",
            data: "username=" + username,
            url: "/user/findUser",
            dataType: "json",
            success: checkRepeatCallback
        });
    }, 1000);
}

/**
 * 检查是否有重复名字回调
 */
function checkRepeatCallback(returnInfo) {
    ajaxQueryUsername = null;
    console.log("returnInfo = ", returnInfo);
    if (!returnInfo) {
        return;
    }
    hiddenAllUsernameTip();
    if (returnInfo.success) {
        hasRepeatUsername = 1;
        $("#username_repeat").css("display", "inline-block");
    }
    else {
        hasRepeatUsername = 0;
        $("#username_success").css("display", "inline-block");
    }
    setSubmitBtn();
}

/**
 * 隐藏所有用户名提示
 */
function hiddenAllUsernameTip() {
    $("#username_tip").css("display", "none");
    $("#username_repeat").css("display", "none");
    $("#username_success").css("display", "none");
}

/**
 * 设置提交按钮
 */
function setSubmitBtn() {
    var submit = $(".input_submit");
    var rePassword = $("#rePassword").val();
    if (checkTerm()
        && checkUsername()
        && checkUsername()
        && checkRePassword()
        && rePassword.length > 0
        && hasRepeatUsername === 0) {
        submit.removeAttr("disabled");
        if($("#password").val().length !== 32){
            $("#password").val($.md5($("#password").val()))
            $("#rePassword").val($.md5($("#rePassword").val()))
        }
    }
    else {
        submit.attr("disabled", "disabled");
    }

}