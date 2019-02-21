let socket = new SockJS('/ws');
let stompClient = Stomp.over(socket);

function init(sessionId) {
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/message/' + sessionId, function (data) {
            if (data.body === 'done') {
                $('#btnMigrate').prop('disabled', false);
                $('#prepMigrate').prop('disabled', false);

            } else {
                var str = data.body;
                if (str.includes("Warning: ")) {
                    $("#err ul").append($("<li>").text(data.body));
                } else if(str.includes("MIGRATION DONE SUCCESSFULLY")) {
                    $("#load ul").append($("<li>").text(data.body)).addClass("success");
                } else {
                    $("#load ul").append($("<li>").text(data.body));
                }
            }
        });
    });
}

function migrate() {
    $('#prepMigrate').prop('disabled', true);
    $('#btnMigrate').prop('disabled', true);
    $.ajax('/migrate/migr', {
        method: 'POST'
    });
}


function generateSqls() {

    $('#prepMigrate').prop('disabled', true);
    $('#btnMigrate').prop('disabled', true);
    $('#errors').empty();
    $('#loading').empty();
    $.ajax('migrate/clear', {
        method: 'POST',
    });

    var checkedTables = "";
    $('input[name="checkik"]:checked').each(function (i, item) {
        checkedTables += ($(item).prop("value"));
        checkedTables += ",";
    });
    var url = $('input[name="urlTo"]').val();
    var username = $('input[name="usernameTo"]').val();
    var password = $('input[name="passwordTo"]').val();

    var myKeyVals = {urlTo: url, usernameTo: username, passwordTo: password, checkedTables: checkedTables}

    $.ajax('/prepare', {
        method: 'POST',
        data: myKeyVals
    });

    $.ajax('/migrate/err', {
        method: 'POST',
    });
}

