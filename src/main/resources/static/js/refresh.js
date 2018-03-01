$(".movierefresh").click(
    function () {
      refresh("/movieRefeash", $(".movierefresh"))
    }
);
$(".hotrefresh").click(
    function () {
      refresh("/hotRefeash", $(".hotrefresh"))
    }
);
$(".zyrefresh").click(
    function () {
      refresh("/zyRefeash", $(".zyrefresh"))
    }
);
function refresh(url, paint) {
  $.ajax({
    type: 'get',
    url: url,
    async: false,
    dataType: "json",
    success: function (data) {
      var movieList = $("#movieList");
      var size = data.length;
      for (var i = 0; i < size; i++) {
        var videoBox = paint.parent().next().find(".videoItem");
        var imageValue = videoBox.eq(i).find(".videoImage").eq(0).attr("src");
        var titleValue = videoBox.eq(i).find(".videoTitle").eq(0).text();
        var urlValue = videoBox.eq(i).find(".videoUrl").eq(0).attr("href");
        var node = videoBox.eq(size);
        var image = node.find(".videoImage").eq(0).text();
        var title = node.find(".videoTitle").eq(0).text();
        var url = node.find(".videoUrl").eq(0).attr("href");
        videoBox.eq(i).find(".videoImage").eq(0).attr("src", data[i].imageUrl);
        videoBox.eq(i).find(".videoTitle").eq(0).text(data[i].title);
        videoBox.eq(i).find(".videoUrl").eq(0).attr("href","/play?u="+ data[i].realUrl);
        node.remove();
      }
    },
    error: function (data) {
    }
  });
}