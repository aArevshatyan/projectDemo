$("#from").click(function() {
    $(".message").css("transform", "translateX(100%)");
    if ($(".message").hasClass("to")) {
        $(".message").removeClass("to");
    }
    $(".message").addClass("from");
});

$("#to").click(function() {
    $(".message").css("transform", "translateX(0)");
    if ($(".message").hasClass("to")) {
        $(".message").removeClass("from");
    }
    $(".message").addClass("to");
});
