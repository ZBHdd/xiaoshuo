/**
 * Created by isghost on 2017/8/13.
 */
var searchParams = new URLSearchParams(window.location.search)
var urlType = searchParams.get("type") || "other"
$(function(){
    // https://developer.mozilla.org/en-US/docs/Web/API/URLSearchParams
    var reqUrl = "/image/otherCollection.json";
    if(urlType == "self"){
        reqUrl = "/image/collections.json";
    }
    $.ajax({
        type: "POST",
        data: "pageNum=" + (searchParams.get("pageNum") || 1),
        url: reqUrl,
        success: fillPage
    })

});

/**
 * 填充界面
 */
function fillPage(result){
    showImage(result);
    showPagination(result);
}
/**
 * 显示图片
 * @param result
 */
function showImage(result){
    var baseUrl = result["baseUrl"];
    var imageUrl = result["imageUrl"];
    if(window.location.href.match("/imageView")){
        $("#imageWrapperID").find(".imageHeader").css("display", "none");
    }
    for(var i = 0;i<imageUrl.length;i++){
        var imageWrapper = $("#imageWrapperID").clone()
        imageWrapper.insertBefore("#imageWrapperID")
            .attr("id", "imageWrapperID" + i)
            .css("display", "inline-block")
            .find(".imageCeil").attr("src", baseUrl + imageUrl[i]);
        var tmpImg = new Image() ;
        tmpImg.src = baseUrl + imageUrl[i];
        tmpImg.onload = function() {
            imageWrapper.find("#loading-center-releative").css("display", "none");
        };

        imageWrapper.find(".collection").click(function(){
            $.ajax({
                type: "POST",
                data: "imageName=" + imageUrl[i],
                url: "/image/collectionImage.json",
                success: function(result){
                    if(typeof(result) != 'object'){
                        window.location.href = "/account/login";
                    }
                    else{
                        imageWrapper.find(".collection").css("color", "yellow");
                        layer.tips('已经收藏，请到个人收藏查看', '#imageWrapperID' + i, {
                            tips: [1, '#3595CC'],
                            time: 4000
                        });
                    }
                    console.log(result);
                }
            })
        })
    }
}

function showPagination(result){
    var curPageNum = searchParams.get("pageNum") || 1;
    curPageNum = parseInt(curPageNum);
    var maxPage = result.maxPage;
    const halfSize = 4;
    var showMinPage = 1;
    var showMaxPage = 1;
    if(curPageNum - halfSize < 1){
        showMinPage = 1;
        showMaxPage = Math.min(showMinPage + halfSize * 2, maxPage);
    }
    else if(curPageNum + halfSize > maxPage){
        showMaxPage = maxPage;
        showMinPage = Math.max(showMaxPage - halfSize * 2, 1);
    }
    else{
        showMinPage = curPageNum - halfSize;
        showMaxPage = curPageNum + halfSize;
    }
    var url = window.location.href.split('?')[0] + "?type=" + urlType + "&pageNum=";
    for(var i = showMinPage; i <= showMaxPage; i++){
        $("#page").clone().insertBefore("#page")
            .attr("display",  "inline-block")
            .attr("id", "page" + i)
            .find("a").text(i)
            .attr("href", url + i);
        if(i == curPageNum){
            $("#page" + i).addClass("active");
        }

    }

    $("#loading").css("display", "none");

    $("#prePage").attr("href", url + Math.max(curPageNum - 1, 1));
    $("#nextPage").attr("href", url + Math.min(curPageNum + 1, showMaxPage));
    disableUrl($("#prePage"), Math.max(curPageNum - 1, 1) == curPageNum);
    disableUrl($("#nextPage"), Math.min(curPageNum + 1, showMaxPage) == curPageNum);
}

function disableUrl(ele, disable){
    if(disable){
        ele.parent().addClass("disabled");
    }
}

