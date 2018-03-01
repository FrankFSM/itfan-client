(function () {
  var regx = /u=(.*)/;
  var src = regx.exec(window.location.href)[1];
  var api = "/episode";
  $.get(api, {
    "v": src
  }, function (data) {
    $("#listLoader").attr("class", "");
    if (data.length > 0) {

      for (var i = 0; i < data.length; i++) {
        var episode = $("<button class='ui basic black button bottom-space play-episode'></button>")
        episode.text(data[i].index);
        episode.attr("value", data[i].realUrl);
        $("#playList").append(episode);
      }
      $(".play-episode").click(function () {
        var self = $(this);
        self.attr("class", "bottom-space ui basic black loading button");
        var playUrl = $(this).val();
        playUrl = "/video?v=" + playUrl;
        $.get(playUrl, function (data) {
          $("#videoTitle").html("<i class='video play outline icon'></i>" + data.title);
          $("#video").attr("src", data.realUrl);
          self.attr("class", "bottom-space ui basic black button");
        })
      });
    }
  });
})();
