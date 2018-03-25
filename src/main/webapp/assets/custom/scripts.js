function start_wait() {
    $('#wait-dialog').modal({
        backdrop : 'static',
        keyboard : false
    });
}

function stop_wait() {
    $('#wait-dialog').modal('hide');
}